package com.example.BookApp.author.domain;

import com.example.BookApp.author.constants.AuthorMessageConstants;
import com.example.BookApp.common.Audit;
import com.example.BookApp.common.CommonMessageConstants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@Table(name = "Author", //
        uniqueConstraints = {@UniqueConstraint(name = "UniqueFirstAndLastName", columnNames = {"firstName", "lastName"}) //
        })
public class AuthorJpa {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = AuthorMessageConstants.NOT_BLANK_MSG)
    @Size(max = 32, message = CommonMessageConstants.VALUE_TOO_LONG)
    @Length(min = 2, message = CommonMessageConstants.VALUE_TOO_SHORT)
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = AuthorMessageConstants.NOT_BLANK_MSG)
    @Size(max = 32, message = CommonMessageConstants.VALUE_TOO_LONG)
    @Length(min = 2, message = CommonMessageConstants.VALUE_TOO_SHORT)
    private String lastName;

    @Embedded
    private Audit audit = new Audit();

    @Version
    private Long version;
}
