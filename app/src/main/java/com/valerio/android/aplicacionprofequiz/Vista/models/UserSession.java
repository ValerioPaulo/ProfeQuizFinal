package com.valerio.android.aplicacionprofequiz.Vista.models;

public class UserSession {
    private static String userEmail;

    public static String getUserEmail() {
        return userEmail;
    }

    public static void setUserEmail(String email) {
        userEmail = email;
    }
}
