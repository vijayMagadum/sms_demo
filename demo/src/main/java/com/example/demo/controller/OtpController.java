package com.example.demo.controller;

import com.example.demo.Enity.OtpEntity;
import com.example.demo.OtpGenerator;
import com.example.demo.Repository.OtpRepository;
import com.example.demo.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private OtpRepository otpRepository;

    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(@RequestParam String phoneNumber) {
        String generatedOtp = OtpGenerator.generateOtp();
        smsService.sendOtp(phoneNumber, generatedOtp);

        // Store the OTP in the database
        OtpEntity otpEntity = new OtpEntity();
        otpEntity.setPhoneNumber(phoneNumber);
        otpEntity.setOtp(generatedOtp);
        otpRepository.save(otpEntity);

        // Return a success status code (200 OK) with a success message
        return ResponseEntity.ok("OTP sent successfully");
    }


    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String enteredOtp, @RequestParam String phoneNumber) {
        List<OtpEntity> otpEntities = (List<OtpEntity>) otpRepository.findByPhoneNumber(phoneNumber);

        if (!otpEntities.isEmpty()) {
            for (OtpEntity otpEntity : otpEntities) {
                if (enteredOtp.equals(otpEntity.getOtp())) {
                    // OTP is correct, delete it from the database
                    otpRepository.deleteAll(otpEntities);

                    // Return a success status code (200 OK)
                    return ResponseEntity.ok("OTP verified successfully");
                }
            }
        }

        // OTP is incorrect or not found, return an error status code (400 Bad Request)
        return ResponseEntity.badRequest().body("Invalid OTP. Please try again.");
    }



}


