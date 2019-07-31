package com.filesystem.controller;

import javax.persistence.Entity;

public class NumberRequest {

    private String numbers;

    public NumberRequest() {
    }

    public NumberRequest(String numbers) {
        this.numbers = numbers;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}
