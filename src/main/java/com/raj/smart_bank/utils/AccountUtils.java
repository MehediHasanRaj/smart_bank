package com.raj.smart_bank.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXIST_MESSAGE = "Account already exists";
    public static final String ACCOUNT_EXIST_CODE = "001";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account creation successful";
    public static final String ACCOUNT_CREATION_SUCCESS = "002";

    public static String generateAccountNumber() {
        //year + random 6 digit
        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;
        int randomNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);

        StringBuilder accountNumber = new StringBuilder();
        accountNumber.append(String.valueOf(currentYear));
        accountNumber.append(String.valueOf(randomNumber));
        return accountNumber.toString();
    }
}
