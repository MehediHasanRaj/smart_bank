package com.raj.smart_bank.service.impl;

import com.raj.smart_bank.dto.*;

public interface UserService {
    BankResponse createdAccount(UserRequest userRequest);
    BankResponse balanceEnquiry(EnquiryRequest request);
    String nameEnquiry(EnquiryRequest request);
    BankResponse creditAccount(CreditDebitRequest request);
    BankResponse debitAccount(CreditDebitRequest request);
    BankResponse transfer(TransferRequest request);
    BankResponse login(LoginDto loginDto);

}
