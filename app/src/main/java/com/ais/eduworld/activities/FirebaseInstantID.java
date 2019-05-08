//package com.ais.eduworld.activities;
//
//import android.util.Log;
//
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.FirebaseInstanceIdService;
//
//public class FirebaseInstantID extends FirebaseInstanceIdService {
//    private static final String TAG = "MyFirebaseIIDService";
//
//    @Override
//    public void onTokenRefresh() {
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Log.d(TAG, "Firebase token: " + refreshedToken);
////        PreferenceUtil.setDeviceId(getApplicationContext(),refreshedToken);
//    }
//}
