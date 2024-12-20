package com.raj.smart_bank.controller;


import com.itextpdf.text.DocumentException;
import com.raj.smart_bank.entity.Transaction;
import com.raj.smart_bank.service.impl.BankStatement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@Tag(name = "Transaction Management")
@RestController
@AllArgsConstructor
@RequestMapping("/service")
public class TransactionController {
    private BankStatement bankStatement;

    @Operation(summary = "PDF Bank Statement Generate",description = "Bank statement generate with range of date")
    @GetMapping("/bankStatement")
    public List<Transaction> getTransactions(@RequestParam String accountNumber, @RequestParam String startDate, @RequestParam String endDate) throws DocumentException, FileNotFoundException {
        return bankStatement.generateStatement(accountNumber, startDate, endDate);
    }

}
