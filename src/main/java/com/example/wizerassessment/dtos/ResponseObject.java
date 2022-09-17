package com.example.wizerassessment.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ResponseObject<M> {
    protected Boolean valid;
    protected String message;
    protected List<M> data;
}
