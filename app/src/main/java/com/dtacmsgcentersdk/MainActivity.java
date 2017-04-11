package com.dtacmsgcentersdk;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dtacmsgcentersdk.service.NotificationHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import th.co.dtac.digitalservices.msgcenter.core.APIWrapper;
import th.co.dtac.digitalservices.msgcenter.listener.OnTokenRefreshListener;
import th.co.dtac.digitalservices.msgcenter.listener.PushInstanceIDListenerService;
import th.co.dtac.digitalservices.msgcenter.model.RespMessage;
import th.co.dtac.digitalservices.msgcenter.utils.Shared;
import th.co.dtac.digitalservices.msgcenter.utils.SubrNumbEncrypter;

public class MainActivity extends AppCompatActivity implements OnTokenRefreshListener {

//    private final String PHONE_NUM_MEEN = "0817395963";
//    private final String PHONE_NUM_NIM = "0899218414";
//    private final String PHONE_NUM_BANK = "0869974299";
//    private final String PHONE_NUM_PPUM = "0816554002";
//    private final String PHONE_NUM_PTEE = "0926393653";
//    private final String MEEN_UDID = "meenudid";
//    private final String NIM_UDID = "nimudid";
//    private final String BANK_UDID = "bankudid";
//    private final String PPUM_UDID = "ppumudid";

    private ProgressDialog mPd;

    private BroadcastReceiver receiverFCMMessageReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Type type = new TypeToken<Map<String, String>>(){}.getType();
            Map<String, String> map = new Gson().fromJson(intent.getStringExtra("data"), type);
            NotificationHandler.getInstance().sendNotification(MainActivity.this, map);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPd = new ProgressDialog(this);

        PushInstanceIDListenerService.setOnTokenRefresh(this);

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msgcenterRegister();
//                try {
////                    String passwordEnc = AESCipher.encrypt("66880897888");
//                    String passwordEnc = AESCipher.encrypt("66817395963");
//                    String passwordDec = AESCipher.decrypt(passwordEnc);
//                    System.out.println("xxx " + passwordEnc);
//                    System.out.println("xxx " + passwordEnc.length());
//                    System.out.println("xxx " + passwordDec);
//
//                    if (passwordEnc.equals("j8K8dLffM4hbBx+s8vklKw==")) {
//                        System.out.println("xxxx ");
//                    }
//
//                }catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        });

        findViewById(R.id.btn_view_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((EditText)findViewById(R.id.edt_phone)).getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Please input phone number", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                intent.putExtra("phone", ((EditText)findViewById(R.id.edt_phone)).getText().toString());
                startActivity(intent);
            }
        });

        String mockUdid = Shared.getMockUDID(this);
        Shared.commitMockUDID(this, mockUdid);
        ((EditText)findViewById(R.id.edt_udid)).setText(mockUdid);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Shared.getFcmToken(this).equals("")) {
//            msgcenterRegister();
            ((TextView)findViewById(R.id.tv_fcm_token)).setText("Your FCM Token is  " + Shared.getFcmToken(MainActivity.this));
            System.out.println("xxx " + Shared.getFcmToken(MainActivity.this));
        }

        IntentFilter filterFCM = new IntentFilter();
        filterFCM.addAction("fcm_message_receive");
        registerReceiver(receiverFCMMessageReceive, filterFCM);
    }

    @Override
    public void onDestroy() {
        try {
            unregisterReceiver(receiverFCMMessageReceive);
        }catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }


    private void msgcenterRegister() {
        if (((EditText)findViewById(R.id.edt_phone)).getText().toString().equals("")) {
            Toast.makeText(MainActivity.this, "Please input phone number", Toast.LENGTH_LONG).show();
            return;
        }
        if (((EditText)findViewById(R.id.edt_udid)).getText().toString().equals("")) {
            Toast.makeText(MainActivity.this, "Please input udid", Toast.LENGTH_LONG).show();
            return;
        }

        mPd.setMessage("loading");
        mPd.show();

        String version = "";
        int verCode = 1;
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
            verCode = pInfo.versionCode;
        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String phone = ((EditText)findViewById(R.id.edt_phone)).getText().toString();

        SubrNumbEncrypter subrNumbEncrypter = new SubrNumbEncrypter();

        APIWrapper.getInstance().register(this, subrNumbEncrypter.encrypt(phone), "android", "T/P",
                Build.VERSION.RELEASE, Build.MODEL, "dtac_app", version, String.valueOf(verCode), String.valueOf(verCode),
                Shared.getMockUDID(this), "", Shared.getLang(this), Shared.getFcmToken(this), new Callback<RespMessage>() {
                    @Override
                    public void onResponse(Call<RespMessage> call, Response<RespMessage> response) {
                        RespMessage respMessage = response.body();
                        if (respMessage != null && respMessage.getStatus() == 200) {
                            System.out.println("fcm registration " + respMessage.getMessage());
                            Toast.makeText(MainActivity.this, "fcm registration " + respMessage.getMessage(), Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(MainActivity.this, "fcm registration " + respMessage.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        mPd.dismiss();
                    }

                    @Override
                    public void onFailure(Call<RespMessage> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void onTokenRefresh(String refreshedToken) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView)findViewById(R.id.tv_fcm_token)).setText("Your FCM Token is  " + Shared.getFcmToken(MainActivity.this));
//                msgcenterRegister();
            }
        });
    }
}
