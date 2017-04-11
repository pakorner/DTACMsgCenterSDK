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

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import th.co.dtac.digitalservices.msgcenter.utils.Shared;

public class PushInstanceIDListenerService extends FirebaseInstanceIdService {

    private static OnTokenRefreshListener listener;

    public static void setOnTokenRefresh(OnTokenRefreshListener onTokenRefreshListener) {
        listener = onTokenRefreshListener;
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is also called
     * when the InstanceID token is initially generated, so this is where
     * you retrieve the token.
     */

    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println("Refreshed token: " + refreshedToken);

        // Add custom implementation, as needed.
        Shared.commitFcmToken(this, refreshedToken);
        // Once a token is generated, we subscribe to topic.
        FirebaseMessaging.getInstance().subscribeToTopic("global");

        if (listener != null) {
            listener.onTokenRefresh(refreshedToken);
        }
    }
    // [END refresh_token]*

}
