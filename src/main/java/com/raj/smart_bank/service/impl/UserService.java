package com.raj.smart_bank.service.impl;

import com.raj.smart_bank.dto.BankResponse;
import com.raj.smart_bank.dto.UserRequest;

public interface UserService {
    BankResponse createdAccount(UserRequest userRequest);
}
