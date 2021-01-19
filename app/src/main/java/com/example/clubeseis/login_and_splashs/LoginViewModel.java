package com.example.clubeseis.login_and_splashs;

import android.widget.Button;


public class LoginViewModel {
    private boolean isUsernameReady = false;
    private boolean isPasswordReady = false;
    private boolean isEverythingReady = false;

    public boolean isUsernameReady() {
        return isUsernameReady;
    }

    public void setUsernameReady(boolean usernameReady) {
        isUsernameReady = usernameReady;
    }

    public boolean isPasswordReady() {
        return isPasswordReady;
    }

    public void setPasswordReady(boolean passwordReady) {
        isPasswordReady = passwordReady;
    }

    public boolean isEverythingReady() {
        return isEverythingReady;
    }

    public void setEverythingReady(boolean everythingReady) {
        isEverythingReady = everythingReady;
    }

    //validação de dados (importante)
    public boolean isUserNameValid(String username) {
        if (username == null || username.contains("'") || username.contains(" ")) {
            this.setUsernameReady(false);
            return false;
        } else if (username.contains("@")) {
            this.setUsernameReady(true);
            return true;
        } else {
            this.setUsernameReady(false);
            return false;
        }
    }


    // A placeholder password validation check
    public boolean isPasswordValid(String password) {
        if (password != null && password.trim().length() > 6) {
            this.setPasswordReady(true);
            return true;
        } else {
            this.setPasswordReady(false);
            return false;
        }
    }

    public void isEverythingValid(Button button) {
        if (this.isPasswordReady && this.isUsernameReady) {
            this.setEverythingReady(true);
            button.setEnabled(true);
        } else {
            this.setEverythingReady(false);
            button.setEnabled(false);
        }
    }
}
