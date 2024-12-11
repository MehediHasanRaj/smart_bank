package com.raj.smart_bank.controller;

import com.raj.smart_bank.dto.*;
import com.raj.smart_bank.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Customer Management")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @Operation(summary = "Create Customer account")
    @PostMapping()
    public BankResponse createdAccount(@RequestBody UserRequest userRequest){
        return userService.createdAccount(userRequest);
    }

    @Operation(summary = "Highly Secure User Login")
    @PostMapping("/login")
    public BankResponse login(@RequestBody LoginDto loginDto){
        return userService.login(loginDto);
    }

    @Operation(summary = "Customer Balance Enquiry")
    @GetMapping("/balanceEnquiry")
    public BankResponse balanceEnquery(@RequestBody EnquiryRequest request){
        return userService.balanceEnquiry(request);
    }

    @Operation(summary = "Create Customer Name Enquiry")
    @GetMapping("/nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest request){
        return userService.nameEnquiry(request);
    }

    @Operation(summary = "Credit Balance for Customer")
    @PostMapping("/credit")
    public BankResponse credit(@RequestBody CreditDebitRequest request){
        return userService.creditAccount(request);
    }

    @Operation(summary = "Debit Balance for Customer")
    @PostMapping("/debit")
    public BankResponse debit(@RequestBody CreditDebitRequest request){
        return userService.debitAccount(request);
    }

    @Operation(summary = "Balance Transfer")
    @PostMapping("/transfer")
    public BankResponse transfer(@RequestBody TransferRequest request){
        return userService.transfer(request);
    }



}
