package com.raj.smart_bank.controller;


import com.itextpdf.text.DocumentException;
import com.raj.smart_bank.entity.Transaction;
import com.raj.smart_bank.service.impl.BankStatement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/service")
public class TransactionController {
    private BankStatement bankStatement;

    @GetMapping("/bankStatement")
    public List<Transaction> getTransactions(@RequestParam String accountNumber, @RequestParam String startDate, @RequestParam String endDate) throws DocumentException, FileNotFoundException {
        return bankStatement.generateStatement(accountNumber, startDate, endDate);
    }

}
