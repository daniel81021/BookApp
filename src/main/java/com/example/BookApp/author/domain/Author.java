package com.example.BookApp.author.domain;

import com.example.BookApp.common.Audit;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Author {

    private Long id;

    private String firstName;

    private String lastName;

    private Long version;

    private Audit audit;
}
