package com.javatmp.demo.jpa.relation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@Data
@Entity
@Table(name = "author")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "author_id")
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private LocalDate birthDate;

    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL)
    private Collection<Book> books;
}
