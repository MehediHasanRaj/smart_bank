package com.raj.smart_bank.controller;

import com.raj.smart_bank.dto.BankResponse;
import com.raj.smart_bank.dto.CreditDebitRequest;
import com.raj.smart_bank.dto.EnquiryRequest;
import com.raj.smart_bank.dto.UserRequest;
import com.raj.smart_bank.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping()
    public BankResponse createdAccount(@RequestBody UserRequest userRequest){
        return userService.createdAccount(userRequest);
    }

    @GetMapping("/balanceEnquiry")
    public BankResponse balanceEnquery(@RequestBody EnquiryRequest request){
        return userService.balanceEnquiry(request);
    }

    @GetMapping("/nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest request){
        return userService.nameEnquiry(request);
    }

    @PostMapping("credit")
    public BankResponse credit(@RequestBody CreditDebitRequest request){
        return userService.creditAccount(request);
    }

    @PostMapping("debit")
    public BankResponse debit(@RequestBody CreditDebitRequest request){
        return userService.debitAccount(request);
    }

}
