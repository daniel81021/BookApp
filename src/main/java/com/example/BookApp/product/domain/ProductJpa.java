package com.example.BookApp.product.domain;

import com.example.BookApp.book.domain.BookJpa;
import com.example.BookApp.common.Audit;
import com.example.BookApp.common.CommonMessageConstants;
import com.example.BookApp.product.enums.Condition;
import com.example.BookApp.product.enums.Rating;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class ProductJpa {

    private static final Double FREE = 0.0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookJpa book;

    @Column(name = "product_name", nullable = false)
    @NotBlank(message = CommonMessageConstants.NOT_BLANK_MSG)
    @Size(max = 50, message = CommonMessageConstants.VALUE_TOO_LONG)
    @Length(min = 2, message = CommonMessageConstants.VALUE_TOO_SHORT)
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Condition condition;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Column(name = "BUY_PRICE")
    private Double buyPrice;

    @Embedded
    private Audit audit = new Audit();

    @Version
    private Long version;

    @PrePersist
    private void prePersistForEntity() { //

        title = title == null || title.isBlank() ? this.book.getTitle() : title; //
        buyPrice = buyPrice == null ? FREE : buyPrice;
        rating = rating == null ? Rating.GOOD : rating;
        condition = condition == null ? Condition.USED : condition;
    }

    @PreUpdate
    private void preUpdateForEntity() {
    }


}
