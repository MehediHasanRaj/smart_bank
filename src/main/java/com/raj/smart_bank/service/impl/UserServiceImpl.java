package com.raj.smart_bank.service.impl;

import com.raj.smart_bank.dto.AccountInfo;
import com.raj.smart_bank.dto.BankResponse;
import com.raj.smart_bank.dto.UserRequest;
import com.raj.smart_bank.entity.User;
import com.raj.smart_bank.repository.UserRepository;
import com.raj.smart_bank.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.raj.smart_bank.utils.AccountUtils.ACCOUNT_EXIST_CODE;
import static com.raj.smart_bank.utils.AccountUtils.ACCOUNT_EXIST_MESSAGE;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;


    @Override
    public BankResponse createdAccount(UserRequest userRequest) {
        //creating an account and saving a new user into db
        if(userRepository.existsByEmail(userRequest.getEmail())){
            return BankResponse.builder()
                    .responseMessage(ACCOUNT_EXIST_MESSAGE)
                    .responseCode(ACCOUNT_EXIST_CODE)
                    .accountInfo(null)
                    .build();
        }

        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .email(userRequest.getEmail())
                .accountBalance(BigDecimal.ZERO)
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .build();
        User savedUser = userRepository.save(newUser);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName()+" "+savedUser.getLastName())
                        .build())
                .build();
    }
}
