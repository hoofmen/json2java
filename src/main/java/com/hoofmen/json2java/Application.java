package com.hoofmen.json2java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.lang.System.exit;

/**
 * Created by Osman H. on 9/15/17.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private Json2JavaService json2JavaService;

    public static void main(String[] args) throws Exception {
	System.out.println("Hello people");
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0) {
            json2JavaService.readConfiguration(args[0].toString());
        } else {
            json2JavaService.printUsage();
        }

        exit(0);
    }
}
