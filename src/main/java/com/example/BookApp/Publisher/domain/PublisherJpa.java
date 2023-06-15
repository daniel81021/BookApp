package com.example.BookApp.Publisher.domain;

import com.example.BookApp.common.Audit;
import com.example.BookApp.common.CommonMessageConstants;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "Publisher")
public class PublisherJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = CommonMessageConstants.NOT_BLANK_MSG)
    @Size(max = 32, message = CommonMessageConstants.VALUE_TOO_LONG)
    @Length(min = 2, message = CommonMessageConstants.VALUE_TOO_SHORT)
    private String name;

    @Version
    private Long version;

    @Embedded
    private Audit audit = new Audit();
}
