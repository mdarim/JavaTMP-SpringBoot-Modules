package com.javatmp.fs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

/**
 * Spring Boot Main Runner Class
 */
@SpringBootApplication
@Slf4j
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner springBootMain() throws Exception {
        return args -> {
            log.info("*** Start Spring Boot Project ***");
            RecordsFile recordsFile = new RecordsFile(
                    "C:\\NetBeansProjects\\JavaTMP\\JavaTMP-SpringBoot-Modules\\spring-boot-projects\\spring-boot-native-storage\\testDatabase.jdb", 64);
            RecordWriter rw = new RecordWriter("foo.lastAccessTime");
            rw.writeObject(new Date());
            recordsFile.insertRecord(rw);

            RecordReader rr = recordsFile.readRecord("foo.lastAccessTime");
            Date d = (Date)rr.readObject();
            System.out.println("last access was at: " + d.toString());

            RecordWriter rw1 = new RecordWriter("foo.lastAccessTime");
            rw1.writeObject(new Date());
            recordsFile.updateRecord(rw1);

            recordsFile.deleteRecord("foo.lastAccessTime");

        };
    }

}
