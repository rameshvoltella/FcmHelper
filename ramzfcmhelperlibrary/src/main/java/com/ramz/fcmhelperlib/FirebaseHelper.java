package com.ramz.fcmhelperlib;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by ramzi on 10/4/18.
 *
 * @auther Ramesh M Nair
 */

public class FirebaseHelper {
    static FirebaseHelper.Builder mInitBuilder;

    public static void sendMessage(Map<String, String> remoteMessage) {
        if (mInitBuilder != null && mInitBuilder.mNotificationOpenedHandler != null) {
            mInitBuilder.mNotificationOpenedHandler.notificationOpened(remoteMessage);
        } else {
        }
    }

    public static FirebaseHelper.Builder startInit(Context context) {
        return new FirebaseHelper.Builder(context);
    }

    public static void passFcmToken(String refreshedToken) {
        if (mInitBuilder != null && mInitBuilder.mNotificationOpenedHandler != null) {
            mInitBuilder.mNotificationOpenedHandler.onFCMTokenRefresh(refreshedToken);
        } else {
        }
    }

    public static class Builder {
        private Context context = null;
        public NotificationBaseHandler mNotificationOpenedHandler;

        public Builder() {

        }

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            } else {
                this.context = context;
            }
        }

        public Builder setNotificationOpenedHandler(NotificationBaseHandler handler) {
            mNotificationOpenedHandler = handler;
            return this;
        }

        public void intFCM() {
            try {
                mInitBuilder = getCurrentOrNewInitBuilder();
                mInitBuilder.mNotificationOpenedHandler = mNotificationOpenedHandler;

                boolean enableRegisteration = true;

                ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                Bundle bundle = ai.metaData;
                Log.d("CCCC","mmmaaaaaaaaaaaaaaaaaaaaaa"+bundle);

                for (int i = 0; bundle.containsKey("to[" + i + "]"); i++) {
                    String toElement = bundle.getString("to[" + i + "]");
                    Log.d("CCCC",toElement);
                }
                String senderId = bundle.getString("gcm_sender_id").replaceAll("sender_id-", "");
                String appId = bundle.getString("ramzi_app_id");
                String apikey = bundle.getString("gcm_api_key");

                if (senderId == null || senderId.equals("0")) {
                    enableRegisteration = false;
                    throw new IllegalArgumentException("Sender name must not be null.");
                }
                if (apikey == null) {
                    enableRegisteration = false;
                    throw new IllegalArgumentException("api key must not be null.");

                }
                if (appId == null) {
                    enableRegisteration = false;
                    throw new IllegalArgumentException("something wrong with your package name");

                }

                if (enableRegisteration) {
                    FirebaseOptions.Builder builder = new FirebaseOptions.Builder()
                            .setApplicationId(appId)
                            .setGcmSenderId(senderId)
                            .setApiKey(apikey);
                    FirebaseApp.initializeApp(context, builder.build());
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
            } catch (NullPointerException e) {
                Log.e(TAG, "Failed to load meta-data, NullPointer: " + e.getMessage());
            }
        }

    }

    public interface NotificationBaseHandler {

        void notificationOpened(Map<String, String> remoteMessage);
        void onFCMTokenRefresh(String token);
    }




    public static FirebaseHelper.Builder getCurrentOrNewInitBuilder() {
        if (mInitBuilder == null)
            mInitBuilder = new FirebaseHelper.Builder();
        return mInitBuilder;
    }



    public static  String getFcmToken()
    {
        return FirebaseInstanceId.getInstance().getToken();
    }
}
