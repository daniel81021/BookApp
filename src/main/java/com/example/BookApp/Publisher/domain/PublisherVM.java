package com.example.BookApp.publisher.domain;

import com.example.BookApp.common.CommonMessageConstants;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.jackson.JsonComponent;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonComponent
public class PublisherVM {

    private Long id;

    @NotNull(message = CommonMessageConstants.NOT_NULL)
    @NotBlank(message = CommonMessageConstants.NOT_BLANK_MSG)
    @Size(max = 32, message = CommonMessageConstants.VALUE_TOO_LONG)
    @Length(min = 2, message = CommonMessageConstants.VALUE_TOO_SHORT)
    private String name;

    private Long version;

}
