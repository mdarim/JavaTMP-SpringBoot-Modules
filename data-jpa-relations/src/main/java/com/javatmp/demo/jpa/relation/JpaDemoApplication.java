package com.javatmp.demo.jpa.relation;

import com.javatmp.demo.jpa.relation.entity.Author;
import com.javatmp.demo.jpa.relation.entity.Book;
import com.javatmp.demo.jpa.relation.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.stream.Stream;

@Slf4j
@SpringBootApplication
public class JpaDemoApplication implements CommandLineRunner {

	public static void main(String args[]) {
		SpringApplication.run(JpaDemoApplication.class, args);
	}

    @Autowired
    AuthorRepository authorRepository;


	@Override
	@Transactional
	public void run(String... strings) throws Exception {
        log.debug("*** Start run ***");
        Author author = Author.builder()
                .id(null)
                .firstName("Mohamed")
                .lastName("Sulibi")
                .birthDate(LocalDate.now())
                .build();
        Book book = Book.builder()
                .id(null)
                .title("Dream forever")
                .build();
        author.setBooks(Stream.of(book).toList());
        authorRepository.save(author);

        authorRepository.findAll().forEach(a -> {
            log.debug(a.toString());
        });
	}
}
