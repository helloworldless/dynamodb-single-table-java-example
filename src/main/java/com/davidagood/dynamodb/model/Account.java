package com.davidagood.dynamodb.model;

import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder
public class Account {
    public static final String TYPE = "ACCOUNT";

    String id;
    String name;
    ZonedDateTime lastAccessTime;
}
