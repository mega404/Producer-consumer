package com.example.Main;

import com.example.App.Control;
import com.example.App.Demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;




@SpringBootApplication
@ComponentScan(basePackages = { "com.example.Services" })
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);

		//Demo.Main();
	}

}
