package com.example.clubeseis.Cadastro;

import android.widget.Button;

public class CadastroViewModel {

    private boolean isEmailReady = false;
    private boolean isPasswordReady = false;
    private boolean isSecondPasswordReady = false;
    private boolean isEverythingReady = false;

    public boolean isEmailReady() {
        return isEmailReady;
    }

    public void setEmailReady(boolean emailReady) {
        isEmailReady = emailReady;
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

    public boolean isSecondPasswordReady() {
        return isSecondPasswordReady;
    }

    public void setSecondPasswordReady(boolean secondPasswordReady) {
        isSecondPasswordReady = secondPasswordReady;
    }

    //validação de dados (importante)
    public boolean isEmailValid(String email) {
        if (email == null || email.contains("'") || email.contains(" ")) {
            this.setEmailReady(false);
            return false;
        } else if (email.contains("@")) {
            this.setEmailReady(true);
            return true;
        } else {
            this.setEmailReady(false);
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

    public boolean isSecondPasswordValid(String password, String password2) {
        if (password != null && password2 != null) {
            if (password2.equals(password)) {
                this.setSecondPasswordReady(true);
                return true;
            } else {
                this.setSecondPasswordReady(false);
                return false;
            }
        } else {
            return false;
        }
    }

    public void isEverythingValid(Button button) {
        if (this.isPasswordReady && this.isEmailReady && this.isSecondPasswordReady) {
            this.setEverythingReady(true);
            button.setEnabled(true);
        } else {
            this.setEverythingReady(false);
            button.setEnabled(false);
        }
    }
}
