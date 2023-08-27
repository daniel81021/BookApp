package com.example.BookApp.product.enums;

public enum Rating {

    VERY_GOOD("5"),
    VERY_GOOD_LESS("5-"),
    GOOD_MORE("4+"),
    GOOD("4"),
    GOOD_LESS("4-"),
    SUFFICIENT("3");

    public final String value;


    Rating(String value) { //

        this.value = value;
    }
}
