package com.website.blogapp.bootstrap;

import jakarta.annotation.PostConstruct;

public class DatabaseStartupCheck {
	@PostConstruct
	public void waitForDatabase() {
		try {
			Thread.sleep(5000); // Wait 5 seconds before starting
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
