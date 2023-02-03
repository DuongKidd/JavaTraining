/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.task;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duong.training.repositories.PasswordResetTokenRepository;
import com.duong.training.repositories.VerificationTokenRepository;

@Service
@Transactional
public class RemoveExpiryDateTokenTask {
	
	@Autowired
	private VerificationTokenRepository verifycationTokenRepository;
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Scheduled(cron = "0 0 11 * * ?")
	public void removeExpiryDateToken() {
		Date now = Date.from(Instant.now());
		verifycationTokenRepository.deleteByExpiryDateLessThan(now);
		passwordResetTokenRepository.deleteByExpiryDateLessThan(now);
	}
	
}
