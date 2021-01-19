package com.example.clubeseis.sql;

import java.util.Calendar;

public class Data {
    private static String UserEmail;
    private static String UserPassword;
    private static int UserID;
    private static int UserCarteira;
    private static String UserName;
    private static boolean UserLog;
    private static Calendar AlocDate;

    public static Calendar getAlocDate() {
        return AlocDate;
    }

    public static void setAlocDate(Calendar alocDate) {
        AlocDate = alocDate;
    }

    public boolean isUserLog() {
        return UserLog;
    }

    public void setUserLog(boolean userLog) {
        UserLog = userLog;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public int getUserCarteira() {
        return UserCarteira;
    }

    public void setUserCarteira(int userCarteira) {
        UserCarteira = userCarteira;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public void loggout() {
        UserCarteira = 0;
        UserEmail = null;
        UserName = null;
        UserID = 0;
        UserPassword = null;
        UserLog = false;
        AlocDate = null;
    }

}
