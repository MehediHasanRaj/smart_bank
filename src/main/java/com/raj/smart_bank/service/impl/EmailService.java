package com.raj.smart_bank.service.impl;

import com.raj.smart_bank.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
