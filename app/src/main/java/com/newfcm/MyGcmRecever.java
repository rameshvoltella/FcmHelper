package com.newfcm;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ramz.fcmhelperlib.FirebaseHelper;

import java.util.Map;

/**
 * Created by oliveboard on 10/4/18.
 *
 * @auther Ramesh M Nair
 */

public class MyGcmRecever implements FirebaseHelper.NotificationBaseHandler {

    Context mContext;

    public MyGcmRecever(Context mContext) {
        this.mContext = mContext;
    }
    @Override
    public void notificationOpened(Map<String, String> remoteMessage) {
        Log.d("got it ","uhooooooooooooooooooooo");
    }

    @Override
    public void onFCMTokenRefresh(String token) {
        Log.d("nananan","iyaa>>>>"+token);
//        if(mContext!=null)
//        {
//            Toast.makeText(mContext,"YAYAYYA"+token,1).show();
//        }
    }
}
