package com.example.clubeseis.Cadastro;

import android.widget.Button;

public class CheckViewModel {

    protected boolean isCPFReady = false;
    protected boolean isCartReady = false;
    protected boolean isEverythingReady = false;

    public boolean isCPFReady() {
        return isCPFReady;
    }

    public void setCPFReady(boolean CPFReady) {
        isCPFReady = CPFReady;
    }

    public boolean isCartReady() {
        return isCartReady;
    }

    public void setCartReady(boolean cartReady) {
        isCartReady = cartReady;
    }

    public boolean isEverythingReady() {
        return isEverythingReady;
    }

    public void setEverythingReady(boolean everythingReady) {
        isEverythingReady = everythingReady;
    }
    //validação de dados (importante)

    public boolean isCPFValido(String cpf) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (cpf.trim().length() == 11 &&
                !cpf.equals("00000000000") && !cpf.equals("11111111111") &&
                !cpf.equals("22222222222") && !cpf.equals("33333333333") &&
                !cpf.equals("44444444444") && !cpf.equals("55555555555") &&
                !cpf.equals("66666666666") && !cpf.equals("77777777777") &&
                !cpf.equals("88888888888") && !cpf.equals("99999999999")) {

            try {
                Long.parseLong(cpf);
            } catch (NumberFormatException e) {
                setCPFReady(false);
                return false;
            }

            int d1, d2;
            int digito1, digito2, resto;
            int digitoCPF;
            String nDigResult;

            d1 = d2 = 0;
            digito1 = digito2 = resto = 0;

            for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
                digitoCPF = Integer.valueOf(cpf.substring(nCount - 1, nCount)).intValue();

                // multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4
                // e assim por diante.
                d1 = d1 + (11 - nCount) * digitoCPF;

                // para o segundo digito repita o procedimento incluindo o primeiro
                // digito calculado no passo anterior.
                d2 = d2 + (12 - nCount) * digitoCPF;
            }
            ;

            // Primeiro resto da divisão por 11.
            resto = (d1 % 11);

            // Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
            // menos o resultado anterior.
            if (resto < 2)
                digito1 = 0;
            else
                digito1 = 11 - resto;

            d2 += 2 * digito1;

            // Segundo resto da divisão por 11.
            resto = (d2 % 11);

            // Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
            // menos o resultado anterior.
            if (resto < 2)
                digito2 = 0;
            else
                digito2 = 11 - resto;

            // Digito verificador do CPF que está sendo validado.
            String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());

            // Concatenando o primeiro resto com o segundo.
            nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

            // comparar o digito verificador do cpf com o primeiro resto + o segundo
            // resto.
            if (nDigVerific.equals(nDigResult)) {
                this.setCPFReady(true);
                return true;
            } else {
                this.setCPFReady(false);
                return false;
            }
        } else {
            this.setCPFReady(false);
            return false;
        }
    }


    // A placeholder password validation check
    public boolean isCartValid(String cart) {
        if (cart.trim().length() == 9) {
            this.setCartReady(true);
            return true;
        } else {
            this.setCartReady(false);
            return false;
        }
    }

    public void isEverythingValid(Button button) {
        if (this.isCartReady && this.isCPFReady) {
            this.setEverythingReady(true);
            button.setEnabled(true);
        } else {
            this.setEverythingReady(false);
            button.setEnabled(false);
        }
    }
}
