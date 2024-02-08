package com.mechamate;

import com.mechamate.service.ProfileManager;
import com.mechamate.service.DatabaseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MechaMate {

	public static void main(String[] args) {
		Log log = new Log();
		SpringApplication.run(MechaMate.class, args);
	}


}


