package com.example.BookApp.book.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public enum CoverType {

    HARDCOVER("hard"),
    SOFT("soft"),
    SOFT_WITH_DUST_COVER("soft with dust cover"),
    HARDCOVER_WITH_DUST_COVER("hard with dust cover"),
    SOFT_WITH_WINGS("soft with appendix");

    public final String value;

    CoverType(String value) {
        this.value = value;
    }
}

