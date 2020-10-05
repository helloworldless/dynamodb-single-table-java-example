package com.davidagood.dynamodb.model;

import static java.util.Objects.nonNull;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
public class Account {

    public static final String TYPE = "ACCOUNT";
    public static final String A_PREFIX = "A#";

    @Getter(onMethod_ = {@DynamoDBHashKey(attributeName = "PK")})
    String customerId;

    @Getter(onMethod_ = {@DynamoDBRangeKey(attributeName = "SK")})
    String id;

    @Getter(onMethod_ = {@DynamoDBAttribute(attributeName = "GSISK")})
    @DynamoDBTypeConverted(converter = ZonedDateTimeConverter.class)
    ZonedDateTime lastAccessTime;

    @Getter(onMethod_ = {@DynamoDBAttribute(attributeName = "accountName")})
    String name;

    @DynamoDBAttribute(attributeName = "GSI1PK")
    public String getGsi1pk() {
        return this.getId();
    }

    public void setGsi1pk(String gsi1pk) {
        if (nonNull(this.id) && !this.id.equals(gsi1pk)) {
            throw new IllegalArgumentException(String.format("Expected SK=%s to match GSI1PK=%s", this.id, gsi1pk));
        }
        // No need to set it otherwise since getter returns id field
    }

    @DynamoDBAttribute
    public String getType() {
        return TYPE;
    }

    public void setType(String type) {
        if (!TYPE.equalsIgnoreCase(type)) {
            throw new IllegalArgumentException("Expected Account to have type ACCOUNT, but type was: " + type);
        }
        // No need to set it otherwise since getter returns static field
    }

    public static class ZonedDateTimeConverter implements DynamoDBTypeConverter<String, ZonedDateTime> {

        @Override
        public String convert(ZonedDateTime object) {
            return A_PREFIX + object.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }

        @Override
        public ZonedDateTime unconvert(String object) {
            return ZonedDateTime.parse(object.split(A_PREFIX)[1]);
        }
    }

}
