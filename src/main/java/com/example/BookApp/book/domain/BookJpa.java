package com.example.BookApp.book.domain;

import com.example.BookApp.author.domain.AuthorJpa;
import com.example.BookApp.book.enums.CoverType;
import com.example.BookApp.common.Audit;
import com.example.BookApp.common.CommonMessageConstants;
import com.example.BookApp.publisher.domain.PublisherJpa;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@Table(name = "Book", //
        uniqueConstraints = {@UniqueConstraint(name = "UniqueBook", //
                columnNames = {"title", "publisher_id", "year_of_publication", "isbn_number", "cover"}) //
        })
public class BookJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    @NotBlank(message = CommonMessageConstants.NOT_BLANK_MSG)
    @Size(max = 50, message = CommonMessageConstants.VALUE_TOO_LONG)
    @Length(min = 2, message = CommonMessageConstants.VALUE_TOO_SHORT)
    private String title;

    @OneToMany
    @JoinColumn(name = "book_id")
    private List<AuthorJpa> authors = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private PublisherJpa publisher;

    @Column(name = "place_of_publication", nullable = false)
    @NotBlank(message = CommonMessageConstants.NOT_BLANK_MSG)
    @Size(max = 32, message = CommonMessageConstants.VALUE_TOO_LONG)
    @Length(min = 2, message = CommonMessageConstants.VALUE_TOO_SHORT)
    private String city;

    @Size(max=5, message = CommonMessageConstants.VALUE_TOO_LONG)
    @Column(name = "year_of_publication")
    private String publicationYear;

    @Column(name = "publication_number")
    @Min(1)
    private Integer publicationNumber;

    @Column(name = "isbn_number", nullable = false)
    @NotBlank(message = CommonMessageConstants.NOT_BLANK_MSG)
    @Size(max = 13, message = CommonMessageConstants.VALUE_TOO_LONG)
    @Length(min = 8, message = CommonMessageConstants.VALUE_TOO_SHORT)
    private String isbn;

    @Column(name = "cover")
    @NotNull(message = CommonMessageConstants.NOT_BLANK_MSG)
    @Enumerated(EnumType.STRING)
    private CoverType cover;

    @NotNull
    @Min(value = 1, message = CommonMessageConstants.MIN_VALUE)
    private Integer size;

    @Embedded
    private Audit audit = new Audit();

    @Version
    private Long version;

    @PrePersist
    private void prePersistForEntity() {
        city = city == null || city.isBlank() ? "b.m.w." : city;
        publicationYear = publicationYear == null || publicationYear.isBlank() ? "b.d.w" : publicationYear;
        isbn = isbn == null ? null : cleanISBN(isbn);
    }

    @PreUpdate
    private void preUpdateForEntity(){
        publicationYear = publicationYear == null || publicationYear.isBlank() ? "b.d.w" : publicationYear;
    }

    private String cleanISBN(String isbn) { //

        return isbn.replaceAll("[ -]", "");
    }
}
