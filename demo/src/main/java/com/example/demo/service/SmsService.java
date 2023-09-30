package com.example.demo.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.account-sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth-token}")
    private String twilioAuthToken;

    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    public void sendOtp(String to, String otp) {
        Twilio.init(twilioAccountSid, twilioAuthToken);
        PhoneNumber from = new PhoneNumber(twilioPhoneNumber);
        PhoneNumber toPhoneNumber = new PhoneNumber(to);

        Message message = Message.creator(
                toPhoneNumber,
                from,
                "Your OTP is: " + otp
        ).create();
    }
}

