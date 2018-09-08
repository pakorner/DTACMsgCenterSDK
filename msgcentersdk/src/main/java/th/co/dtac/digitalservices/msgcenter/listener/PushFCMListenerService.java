/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package th.co.dtac.digitalservices.msgcenter.listener;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import java.util.Map;

import th.co.dtac.digitalservices.msgcenter.utils.Shared;

public class PushFCMListenerService extends FirebaseMessagingService {

    private static OnTokenRefreshListener listener;

    public static void setOnTokenRefresh(OnTokenRefreshListener onTokenRefreshListener) {
        listener = onTokenRefreshListener;
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        Map<String, String> data = message.getData();
        try {

//            System.out.println("xxx " + data);
            Log.d("MESSAGE CENTER", "data : "+data);
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("fcm_message_receive");
            broadcastIntent.putExtra("data", new Gson().toJson(data));
            sendBroadcast(broadcastIntent);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNewToken(String token) {
        Log.d("TAG", "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println("Refreshed token: " + token);

        // Add custom implementation, as needed.
        Shared.commitFcmToken(this, token);
        // Once a token is generated, we subscribe to topic.
        FirebaseMessaging.getInstance().subscribeToTopic("global");

        if (listener != null) {
            listener.onTokenRefresh(token);
        }
    }

}
