package com.raj.smart_bank.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXIST_MESSAGE = "Account already exists";
    public static final String ACCOUNT_EXIST_CODE = "001";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account creation successful";
    public static final String ACCOUNT_CREATION_SUCCESS = "002";
    public static final String ACCOUNT_NOT_EXISTS_MESSAGE = "Account not exists";
    public static final String ACCOUNT_NOT_EXISTS_CODE = "003";
    public static final String ACCOUNT_FOUND_MESSAGE = "Account found";
    public static final String ACCOUNT_FOUND_CODE = "004";
    public static final String ACCOUNT_CREDITED_SUCCESS = "005";
    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "Credit successful!";
    public static final String INSUFFICIENT_FUNDS_CODE = "006";
    public static final String INSUFFICIENT_FUNDS_MESSAGE = "Insufficient Funds";
    public static final String ACCOUNT_DEBITED_SUCCESS = "007";
    public static final String ACCOUNT_DEBITED_SUCCESS_MESSAGE = "debit successful!";
    public static final String TRANSACTION_SUCCESS_CODE = "008";
    public static final String TRANSACTION_SUCCESS_MESSAGE = "transaction successful!";

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
