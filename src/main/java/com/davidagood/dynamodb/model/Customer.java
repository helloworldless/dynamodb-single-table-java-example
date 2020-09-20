package com.davidagood.dynamodb.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Customer {
    public static final String A_RECORD = "A_RECORD";
    public static final String TYPE = "CUSTOMER";

    String id;
    String firstName;
    String lastName;
}
