package com.poc.chainofresponsability.domain;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Error {

    private String field;
    private String message;
    private String code;
}
