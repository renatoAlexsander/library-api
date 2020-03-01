package com.estudo.library.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MessageException {

    private List<String> errors = new ArrayList<>();

    public MessageException(List<String> errors) {
        this.errors = errors;
    }
}
