package com.davidagood.dynamodb.alternatives;

import static java.util.Objects.isNull;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import java.util.Map;

public class GetItemAsRawDynamoJSON {

    public static void main(String[] args) {

        var dynamo = AmazonDynamoDBClientBuilder.defaultClient();

        var keyMap = Map.of("PK", new AttributeValue("customer1"), "SK", new AttributeValue("A_RECORD"));

        var getItemRequest = new GetItemRequest()
            .withTableName("data")
            .withKey(keyMap);

        try {
            var getItemResult = dynamo.getItem(getItemRequest);
            var attributeNameValueMap = getItemResult.getItem();

            if (isNull(attributeNameValueMap)) {
                System.out.format("No item found with the key %s!\n", keyMap);
                return;
            }

            attributeNameValueMap.forEach((key1, value) -> System.out.format("%s: %s\n", key1, value));

        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }
}
