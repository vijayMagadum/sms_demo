package com.example.demo.Repository;

import com.example.demo.Enity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
    List<OtpEntity> findByPhoneNumber(String phoneNumber);
}

