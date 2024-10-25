package com.raj.smart_bank.service.impl;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.raj.smart_bank.dto.EmailDetails;
import com.raj.smart_bank.entity.Transaction;
import com.raj.smart_bank.entity.User;
import com.raj.smart_bank.repository.TransactionRepository;
import com.raj.smart_bank.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;




@Component
@AllArgsConstructor
@Slf4j
public class BankStatement {
    // retrieve list of transaction in a range given account
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private EmailService emailService;

    private static final String FILE = "src/main/resources/files/myStatement.pdf";

    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) throws FileNotFoundException, DocumentException {

        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
        List<Transaction> transactions = transactionRepository.findAll().stream().filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> transaction.getCreateDate().isAfter(start.minusDays(1))).filter(transaction -> transaction.getCreateDate().isBefore(end.plusDays(1))).toList();
//        return transactions;
        User user = userRepository.findByAccountNumber(accountNumber);
        String customerName = user.getFirstName() + " " + user.getLastName();


        //generate pdf
        Rectangle statementSize = new Rectangle(PageSize.A4);
        Document document = new Document(statementSize);
        log.info("setting size of document");
        OutputStream outputStream = new FileOutputStream(FILE);
        PdfWriter.getInstance(document,outputStream);
        document.open();

        PdfPTable bankInfoTable = new PdfPTable(1);
        PdfPCell bankName = new PdfPCell(new Phrase("Smart Bank"));
        bankName.setBorder(0);
        bankName.setBackgroundColor(BaseColor.BLUE);
        bankName.setPadding(20f);

        PdfPCell bankAddress = new PdfPCell(new Phrase("39, Affleck Road, Colchester, UK"));
        bankAddress.setBorder(0);

        bankInfoTable.addCell(bankName);
        bankInfoTable.addCell(bankAddress);

        PdfPTable statementInfo = new PdfPTable(2);
        PdfPCell customerInfo = new PdfPCell(new Phrase("Start Date: " + startDate));
        customerInfo.setBorder(0);
        PdfPCell statement = new PdfPCell(new Phrase("STATEMENT OF ACCOUNT"));
        statement.setBorder(0);

        PdfPCell stopDate = new PdfPCell(new Phrase("End Date: "+ endDate));
        stopDate.setBorder(0);
        PdfPCell name = new PdfPCell(new Phrase("Customer Name: "+ customerName));
        name.setBorder(0);
        PdfPCell space = new PdfPCell();
        space.setBorder(0);
        PdfPCell address = new PdfPCell(new Phrase("Address: "+ user.getAddress()));
        address.setBorder(0);

        PdfPTable transactionTable = new PdfPTable(4);
        PdfPCell date = new PdfPCell(new Phrase("Date"));
        date.setBorder(0);
        date.setBackgroundColor(BaseColor.GRAY);
        PdfPCell amount = new PdfPCell(new Phrase("Amount"));
        amount.setBorder(0);
        amount.setBackgroundColor(BaseColor.GRAY);
        PdfPCell transactionType = new PdfPCell(new Phrase("Transaction Type"));
        transactionType.setBorder(0);
        transactionType.setBackgroundColor(BaseColor.GRAY);
        PdfPCell status = new PdfPCell(new Phrase("Status"));
        status.setBorder(0);
        status.setBackgroundColor(BaseColor.GRAY);

        transactionTable.addCell(date);
        transactionTable.addCell(amount);
        transactionTable.addCell(transactionType);
        transactionTable.addCell(status);


        transactions.forEach(transaction -> {
            transactionTable.addCell(new Phrase(String.valueOf(transaction.getCreateDate())));
            transactionTable.addCell(new Phrase(String.valueOf(transaction.getAmount())));
            transactionTable.addCell(new Phrase(String.valueOf(transaction.getTransactionType())));
            transactionTable.addCell(new Phrase(String.valueOf(transaction.getStatus())));
        });

        statementInfo.addCell(customerInfo);
        statementInfo.addCell(statement);
        statementInfo.addCell(stopDate);
        statementInfo.addCell(name);
        statementInfo.addCell(space);
        statementInfo.addCell(address);

        document.add(bankInfoTable);
        document.add(statementInfo);
        document.add(transactionTable);

        document.close();

        //sending email
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("STATEMENT")
                .emailBody("Your statement is attached in this email")
                .attachment(FILE)
                .build();
        emailService.sendEmailWithAttachment(emailDetails);


        return transactions;

    }


    //send via email
}
