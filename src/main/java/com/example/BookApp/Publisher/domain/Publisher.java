package com.example.BookApp.publisher.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Publisher {

    private Long id;
    private String name;
    private Long version;

}
