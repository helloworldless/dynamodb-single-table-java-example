package com.davidagood.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DynamoDBTable(tableName = "data")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    public static final String A_RECORD = "A_RECORD";
    public static final String TYPE = "CUSTOMER";

    @Getter(onMethod_ = {@DynamoDBHashKey(attributeName = "PK")})
    String id;

    @DynamoDBRangeKey(attributeName = "SK")
    public String getSortKey() {
        return A_RECORD;
    }

    public void setSortKey(String sortKey) {
        if (!A_RECORD.equalsIgnoreCase(sortKey)) {
            throw new IllegalArgumentException("Expected Customer sort key of A_RECORD, but it was: " + sortKey);
        }
        // No need to set it otherwise since getter returns static field
    }

    @Getter(onMethod_ = {@DynamoDBAttribute})
    String firstName;

    @Getter(onMethod_ = {@DynamoDBAttribute})
    String lastName;

    @DynamoDBAttribute
    public String getType() {
        return TYPE;
    }

    public void setType(String type) {
        if (!TYPE.equalsIgnoreCase(type)) {
            throw new IllegalArgumentException("Expected Customer to have type CUSTOMER, but type was: " + type);
        }
        // No need to set it otherwise since getter returns static field
    }
}
