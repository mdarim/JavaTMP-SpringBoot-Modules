package com.javatmp.demo.cache;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import jakarta.persistence.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@RedisHash("organization")
public class Organization  {

    @Id
    String id;
    String name;
    String contactName;
    String contactEmail;
    String contactPhone;
}
