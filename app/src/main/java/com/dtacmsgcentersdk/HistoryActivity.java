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
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dtacmsgcentersdk.adapter.HistoryAdapter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import th.co.dtac.digitalservices.msgcenter.core.APIWrapper;
import th.co.dtac.digitalservices.msgcenter.model.RespHistory;
import th.co.dtac.digitalservices.msgcenter.model.RespMessage;
import th.co.dtac.digitalservices.msgcenter.utils.Shared;
import th.co.dtac.digitalservices.msgcenter.utils.SubrNumbEncrypter;


public class HistoryActivity extends AppCompatActivity {

    private HistoryAdapter historyAdapter;
    private ProgressDialog mPd;

    private SubrNumbEncrypter mSubrNumbEncrypter;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            System.out.println(intent.getExtras().keySet());
            listHistory();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mPd = new ProgressDialog(this);
        mSubrNumbEncrypter = new SubrNumbEncrypter();

        listHistory();

        ((TextView)findViewById(R.id.tv_lang)).setText(Shared.getLang(this).toUpperCase());
        if (Shared.getLang(this).equalsIgnoreCase("th")) {
            ((Switch)findViewById(R.id.switch_lang)).setChecked(true);
        }else {
            ((Switch)findViewById(R.id.switch_lang)).setChecked(false);
        }

        ((Switch)findViewById(R.id.switch_lang)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Shared.commitLang(HistoryActivity.this, "th");
                }else {
                    Shared.commitLang(HistoryActivity.this, "en");
                }
                ((TextView)findViewById(R.id.tv_lang)).setText(Shared.getLang(HistoryActivity.this).toUpperCase());

                if (historyAdapter != null) {
                    historyAdapter.notifyDataSetChanged();
                }
            }
        });

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msgcenterRegister();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction("push_notification");
        registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroy() {
        try {
            unregisterReceiver(receiver);
        }catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private void listHistory() {
        String phone;
        Bundle bundle = getIntent().getExtras();

        if (bundle.containsKey("data_ref")) {
            JsonParser parser = new JsonParser();
            JsonObject dataRef = parser.parse(bundle.getString("data_ref")).getAsJsonObject();
            if (dataRef.has("phone")) {
                phone = dataRef.get("phone").getAsString();
            }else {
                phone = "";
                Toast.makeText(this, "No phone in data_ref", Toast.LENGTH_LONG).show();
            }
        }else {
            phone = bundle.getString("phone");
        }

        APIWrapper.getInstance().getHistory(this, mSubrNumbEncrypter.encrypt(phone), 1, 100, new Callback<RespHistory>() {
            @Override
            public void onResponse(Call<RespHistory> call, Response<RespHistory> response) {
                RespHistory respHistory = response.body();
                if (respHistory == null) {
                    return;
                }
                if (respHistory.getStatus() == 200) {
                    historyAdapter = new HistoryAdapter(HistoryActivity.this, R.layout.item_history, respHistory.getHistory());
                    ((ListView) findViewById(R.id.lv_content)).setAdapter(historyAdapter);
                }else {
                    Toast.makeText(HistoryActivity.this, respHistory.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RespHistory> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void msgcenterRegister() {
        String phone;
        Bundle bundle = getIntent().getExtras();

        if (bundle.containsKey("data_ref")) {
            JsonParser parser = new JsonParser();
            JsonObject dataRef = parser.parse(bundle.getString("data_ref")).getAsJsonObject();
            if (dataRef.has("phone")) {
                phone = dataRef.get("phone").getAsString();
            }else {
                phone = "";
                Toast.makeText(this, "No phone in data_ref", Toast.LENGTH_LONG).show();
            }
        }else {
            phone = bundle.getString("phone");
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

        APIWrapper.getInstance().register(this, mSubrNumbEncrypter.encrypt(phone), "android", "T/P",
                Build.VERSION.RELEASE, Build.MODEL, "dtac_app", version, String.valueOf(verCode), String.valueOf(verCode),
                Shared.getMockUDID(this), "", Shared.getLang(this), Shared.getFcmToken(this), new Callback<RespMessage>() {
                    @Override
                    public void onResponse(Call<RespMessage> call, Response<RespMessage> response) {
                        RespMessage respMessage = response.body();
                        if (respMessage != null && respMessage.getStatus() == 200) {
                            System.out.println("fcm registration " + respMessage.getMessage());
                            Toast.makeText(HistoryActivity.this, "fcm registration " + respMessage.getMessage(), Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(HistoryActivity.this, "fcm registration " + respMessage.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        mPd.dismiss();
                    }

                    @Override
                    public void onFailure(Call<RespMessage> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

}
