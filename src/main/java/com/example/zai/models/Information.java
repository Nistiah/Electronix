package com.example.zai.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;


@Table(name = "informations")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    @Length(max = 5000)
    private String content;

}
