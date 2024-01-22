package com.project.splitwise;

import com.project.splitwise.commands.CommandRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class SplitwiseApplication implements CommandLineRunner {


    @Override
    public void run(String... args){
        System.out.println("Application started!");
    }

    public static void main(String[] args) {
        SpringApplication.run(SplitwiseApplication.class, args);
        // ...
    }

}
