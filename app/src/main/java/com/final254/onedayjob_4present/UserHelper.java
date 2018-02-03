package com.final254.onedayjob_4present;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserHelper {

    Context context;
    SharedPreferences sharedPerfs;
    Editor editor;

    // Prefs Keys
    static String perfsName = "UserHelper";
    static int perfsMode = 0;

    public UserHelper(Context context) {
        this.context = context;
        this.sharedPerfs = this.context.getSharedPreferences(perfsName, perfsMode);
        this.editor = sharedPerfs.edit();
    }

    public void createSession(String sMemberID) {

        editor.putBoolean("LoginStatus", true);
        editor.putString("MemberID", sMemberID);

        editor.commit();
    }

    public void deleteSession() {
        editor.clear();
        editor.commit();
    }

    public boolean getLoginStatus() {
        return sharedPerfs.getBoolean("LoginStatus", false);
    }

    public String getMemberID() {
        return sharedPerfs.getString("MemberID", null);
    }
}