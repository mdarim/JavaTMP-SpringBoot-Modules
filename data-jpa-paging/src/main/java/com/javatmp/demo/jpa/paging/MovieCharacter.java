package com.javatmp.demo.jpa.paging;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Data
@Table(name = "character")
class MovieCharacter {

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private String movie;

}
