package com.davidagood.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DynamoDBTable(tableName = "data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemBase {

    @Getter(onMethod_ = {@DynamoDBHashKey(attributeName = "PK")})
    String pk;
    @Getter(onMethod_ = {@DynamoDBHashKey(attributeName = "SK")})
    String sk;
    @Getter(onMethod_ = {@DynamoDBAttribute(attributeName = "GSI1PK")})
    String gsipk1;
    @Getter(onMethod_ = {@DynamoDBAttribute(attributeName = "GSISK")})
    String gsisk;
    @Getter(onMethod_ = {@DynamoDBAttribute})
    String type;
    @Getter(onMethod_ = {@DynamoDBAttribute})
    String firstName;
    @Getter(onMethod_ = {@DynamoDBAttribute})
    String lastName;
    @Getter(onMethod_ = {@DynamoDBAttribute})
    String accountName;

}
