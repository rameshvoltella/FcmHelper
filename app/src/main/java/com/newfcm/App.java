package com.newfcm;

import android.app.Application;

import com.ramz.fcmhelperlib.FirebaseHelper;


/**
 * Created by oliveboard on 10/4/18.
 *
 * @auther Ramesh M Nair
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        FirebaseHelper.enableFcm(getApplicationContext());

                 FirebaseHelper.startInit(getApplicationContext())
                .setNotificationOpenedHandler(new MyGcmRecever(getApplicationContext())).
                intFCM();
    }
}
