package com.example.BookApp.book.domain;

import com.example.BookApp.author.domain.AuthorVM;
import com.example.BookApp.book.enums.CoverType;
import com.example.BookApp.common.CommonMessageConstants;
import com.example.BookApp.publisher.domain.PublisherVM;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookVM {

    private Long id;

    @NotNull
    @NotBlank(message = CommonMessageConstants.NOT_BLANK_MSG)
    @Size(max = 50, message = CommonMessageConstants.VALUE_TOO_LONG)
    @Length(min = 2, message = CommonMessageConstants.VALUE_TOO_SHORT)
    private String title;

    private Set<AuthorVM> authors;

    private PublisherVM publisher;

    @NotBlank(message = CommonMessageConstants.NOT_BLANK_MSG)
    @Size(max = 32, message = CommonMessageConstants.VALUE_TOO_LONG)
    @Length(min = 2, message = CommonMessageConstants.VALUE_TOO_SHORT)
    private String city;

    private String publicationYear;

    @Min(1)
    private Integer publicationNumber;

    @NotBlank(message = CommonMessageConstants.NOT_BLANK_MSG)
    @Size(max = 13, message = CommonMessageConstants.VALUE_TOO_LONG)
    @Length(min = 8, message = CommonMessageConstants.VALUE_TOO_SHORT)
    private String isbn;

    @NotNull(message = CommonMessageConstants.NOT_BLANK_MSG)
    private CoverType cover;

    @NotNull
    @Min(value = 1, message = CommonMessageConstants.MIN_VALUE)
    private Integer size;

    private Long version;
}
