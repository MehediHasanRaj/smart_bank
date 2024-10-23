package com.raj.smart_bank.service.impl;

import com.raj.smart_bank.dto.BankResponse;
import com.raj.smart_bank.dto.CreditDebitRequest;
import com.raj.smart_bank.dto.EnquiryRequest;
import com.raj.smart_bank.dto.UserRequest;

public interface UserService {
    BankResponse createdAccount(UserRequest userRequest);
    BankResponse balanceEnquiry(EnquiryRequest request);
    String nameEnquiry(EnquiryRequest request);
    BankResponse creditAccount(CreditDebitRequest request);
    BankResponse debitAccount(CreditDebitRequest request);

}
