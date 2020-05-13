package com.rktechapps.erekhanew.apputil;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.rktechapps.erekhanew.R;

public class AppConfig {
    private Context context;
    private SharedPreferences sharedPreferences;

    public AppConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file),Context.MODE_PRIVATE);
    }


    public boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void updateUserInfo(String mobile, String userId, String otp, String name)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_id),userId);
        editor.putString(context.getString(R.string.pref_user_mobile),mobile);
        editor.putString(context.getString(R.string.pref_user_otp),otp);
        editor.putString(context.getString(R.string.pref_user_name),name);
        editor.apply();
    }

    public String getUserMobileNumber()
    {
        String mobile_number ="0";
        mobile_number = sharedPreferences.getString(context.getString(R.string.pref_user_mobile),"0");
        return mobile_number;
    }

    public String getUserOtp()
    {
        String otp ="0";
        otp = sharedPreferences.getString(context.getString(R.string.pref_user_otp),"0");
        return otp;
    }

    public void updateValidateOtpStatus()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_is_valid_otp),true);
        editor.apply();

    }

    public boolean isOtpValidate()
    {
        return sharedPreferences.getBoolean(context.getString(R.string.pref_is_valid_otp),false);
    }

    public String getUserId()
    {
        return sharedPreferences.getString(context.getString(R.string.prefe_user_id),"0");
    }
    public String getUserName(){
        return sharedPreferences.getString(context.getString(R.string.pref_user_name),"");
    }

    public void updateUserOtpSuccessInfo(String userId,String mobile,String userName,String password,
                                         String name,String nearestPoliceStation)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.prefe_user_id),userId);
        editor.putString(context.getString(R.string.pref_user_mobile),mobile);
        editor.putString(context.getString(R.string.pref_user_login_name),userName);
        editor.putString(context.getString(R.string.pref_user_login_password),password);
        editor.putString(context.getString(R.string.pref_user_name),name);
        editor.putString(context.getString(R.string.pref_user_nearest_police_station),nearestPoliceStation);
        editor.apply();
    }


    public void updateLoginStatus(boolean status)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_is_login),status);
        editor.apply();
    }

    public boolean checkLoginStatus()
    {
        return  sharedPreferences.getBoolean(context.getString(R.string.pref_is_login),false);
    }

    public String getNearestPoliceStation()
    {
        return  sharedPreferences.getString(context.getString(R.string.pref_user_nearest_police_station),"");
    }

}
