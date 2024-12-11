package com.raj.smart_bank.service.impl;

import com.raj.smart_bank.config.jwt.jwt.JwtUtils;
import com.raj.smart_bank.dto.*;
import com.raj.smart_bank.config.jwt.MyUserDetails;
import com.raj.smart_bank.entity.Role;
import com.raj.smart_bank.entity.User;
import com.raj.smart_bank.repository.UserRepository;
import com.raj.smart_bank.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.raj.smart_bank.utils.AccountUtils.ACCOUNT_EXIST_CODE;
import static com.raj.smart_bank.utils.AccountUtils.ACCOUNT_EXIST_MESSAGE;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;
    @Autowired
    private TransactionService transactionService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

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
                .password(this.encoder.encode(userRequest.getPassword()))
                .accountBalance(BigDecimal.ZERO)
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .role(Role.valueOf("ROLE_ADMIN"))
                .build();
        User savedUser = userRepository.save(newUser);

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("New Account Created")
                .emailBody("congratulations! your account has been created. \n" +
                        "Account Name: " + savedUser.getFirstName() + " " + savedUser.getLastName()+"\n" +
                        "account Number: " + savedUser.getAccountNumber()
                )
                .build();
        emailService.sendEmailAlert(emailDetails);
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

    public BankResponse login(LoginDto loginDto){
        //authentication manager know how to authenticate the user

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));
        if(!authentication.isAuthenticated()){
            BankResponse.builder()
                    .responseCode("404")
                    .responseMessage("Authentication Error!!")
                    .build();
        }
        //trying to login, send email
        EmailDetails loginAlert = EmailDetails.builder()
                .subject("you are logged in")
                .recipient(loginDto.getEmail())
                .emailBody("you are into your account. if you are not, contact with bank")
                .build();
        emailService.sendEmailAlert(loginAlert);
        //we will generate token, as authenticated
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal(); // this method return the UserDetails object
        String generated_token = jwtUtils.generateTokenFromUsername(userDetails);
        return BankResponse.builder()
                .responseCode("Login success")
                .responseMessage(generated_token)
                .build();
    }


    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if(!isAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(foundUser.getAccountNumber())
                        .accountName(foundUser.getFirstName()+ " " + foundUser.getLastName())
                        .build())
                .build();

    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if(!isAccountExist){
            return AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE;
        }

        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
        return foundUser.getFirstName() + " " + foundUser.getLastName();
    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest request) {
       boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if(!isAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User userToCredit = userRepository.findByAccountNumber(request.getAccountNumber());
        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));
        userRepository.save(userToCredit);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName())
                        .accountBalance(userToCredit.getAccountBalance())
                        .accountNumber(userToCredit.getAccountNumber())

                        .build())
                .build();

    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest request) {
        //neeed to check that account exist

        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if(!isAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        // if amount not more than balance
        User userToDebit = userRepository.findByAccountNumber(request.getAccountNumber());
        if(userToDebit.getAccountBalance().compareTo(request.getAmount())>=0){
            userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
            userRepository.save(userToDebit);

            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                    .responseMessage(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName())
                            .accountBalance(userToDebit.getAccountBalance())
                            .accountNumber(userToDebit.getAccountNumber())
                            .build())
                    .build();
        }
        else{
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_FUNDS_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_FUNDS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }



    }

    @Override
    public BankResponse transfer(TransferRequest request) {
        //get the account to debit
        boolean isFromAccountExist = userRepository.existsByAccountNumber(request.getFromAccount());
        boolean isToAccountExist = userRepository.existsByAccountNumber(request.getToAccount());
        //checking available balance
        if(!isFromAccountExist || !isToAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        //debit account
        User fromAccount = userRepository.findByAccountNumber(request.getFromAccount());
        if(fromAccount.getAccountBalance().compareTo(request.getAmount())<0){
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_FUNDS_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_FUNDS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        fromAccount.setAccountBalance(fromAccount.getAccountBalance().subtract(request.getAmount()));
        userRepository.save(fromAccount);
        EmailDetails debitAlert = EmailDetails.builder()
                .subject("Debit alert!!")
                .recipient(fromAccount.getEmail())
                .emailBody("Balance deducted from your account!!\nYour deducted amount" + request.getAmount() +"\n now your balance : " + fromAccount.getAccountBalance())
                .build();
        emailService.sendEmailAlert(debitAlert);
        TransactionDto transactionDtoCredit = TransactionDto.builder()
                .amount(request.getAmount())
                .transactionType("Debit")
                .accountNumber(fromAccount.getAccountNumber())
                .build();

        transactionService.saveTransaction(transactionDtoCredit);



        // get the account to be credited
        User toAccount = userRepository.findByAccountNumber(request.getToAccount());
        toAccount.setAccountBalance(toAccount.getAccountBalance().add(request.getAmount()));

        userRepository.save(toAccount);
        EmailDetails creditAlert = EmailDetails.builder()
                .subject("Credit alert!!")
                .recipient(toAccount.getEmail())
                .emailBody("Balance added to your account!!\nYour added amount" + request.getAmount() +"\n now your balance : " + fromAccount.getAccountBalance())
                .build();
        emailService.sendEmailAlert(creditAlert);

        //saving the transaction
        TransactionDto transactionDto = TransactionDto.builder()
                .amount(request.getAmount())
                .transactionType("Credit")
                .accountNumber(toAccount.getAccountNumber())
                .build();

        transactionService.saveTransaction(transactionDto);


        //credited account
        return BankResponse.builder()
                .responseCode(AccountUtils.TRANSACTION_SUCCESS_CODE)
                .responseMessage(AccountUtils.TRANSACTION_SUCCESS_MESSAGE)
                .accountInfo(null)
                .build();
    }


}
