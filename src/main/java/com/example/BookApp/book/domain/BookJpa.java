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
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Year;
import java.util.HashSet;
import java.util.Set;

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
    private Set<AuthorJpa> authors = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private PublisherJpa publisher;

    @Column(name = "place_of_publication", nullable = false)
    @NotBlank(message = CommonMessageConstants.NOT_BLANK_MSG)
    @Size(max = 32, message = CommonMessageConstants.VALUE_TOO_LONG)
    @Length(min = 2, message = CommonMessageConstants.VALUE_TOO_SHORT)
    private String city;

    @Column(name = "year_of_publication")
    private Year publictaionYear;

    @Column(name = "publication_number")
    @Min(1)
    private Integer publicationNumber;

    @Column(name = "isbn_number", nullable = false)
    @NotBlank(message = CommonMessageConstants.NOT_BLANK_MSG)
    @Size(max = 13, message = CommonMessageConstants.VALUE_TOO_LONG)
    @Length(min = 8, message = CommonMessageConstants.VALUE_TOO_SHORT)
    private String isbn;

    @Column(name = "cover", nullable = false)
    @Enumerated(EnumType.STRING)
    private CoverType cover;

    @Embedded
    private Audit audit = new Audit();

    @Version
    private Long version;

    @PrePersist
    private void prePersistForEntity() {
        city = city == null ? "b.m.w." : city;
        publictaionYear = publictaionYear == null ? Year.of(1899) : publictaionYear;
        isbn = cleanISBN(isbn);
    }

    private String cleanISBN(String isbn) {
        return isbn.replaceAll("[ -]", "");
    }
}
