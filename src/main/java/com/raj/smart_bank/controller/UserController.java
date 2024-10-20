package com.raj.smart_bank.controller;

import com.raj.smart_bank.dto.BankResponse;
import com.raj.smart_bank.dto.UserRequest;
import com.raj.smart_bank.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping()
    public BankResponse createdAccount(@RequestBody UserRequest userRequest){
        return userService.createdAccount(userRequest);
    }

}
