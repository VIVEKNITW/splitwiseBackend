package com.project.splitwise;

import com.project.splitwise.commands.CommandRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class SplitwiseApplication implements CommandLineRunner {
    private Scanner scanne;
    private CommandRegistry commandRegistry;

    @Autowired
    public SplitwiseApplication(CommandRegistry commandRegistry) {
        scanner = new Scanner(System.in);
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void run(String... args) throws Exception {
//        while (true) {
//            System.out.println("Tell what do you want to do?");
//            String input = scanner.nextLine();
//            commandRegistry.execute(input);
////            if (input.startsWith("UpdatePro"))
//        }
        System.out.println("Application started!");
    }

    public static void main(String[] args) {
        SpringApplication.run(SplitwiseApplication.class, args);
        // ...
    }

}
