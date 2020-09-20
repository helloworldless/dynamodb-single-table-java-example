package com.davidagood.dynamodb.repository;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.davidagood.dynamodb.util.DynamoGeneratedBoilerplate;
import com.davidagood.dynamodb.model.Account;
import com.davidagood.dynamodb.model.Customer;
import com.davidagood.dynamodb.model.ItemBase;
import com.davidagood.dynamodb.model.ModelConverter;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BankingDataRepository {

    private final DynamoDBMapper mapper;
    private final ModelConverter converter;

    public BankingDataRepository(AmazonDynamoDB dynamoDB) {
        this.mapper = new DynamoDBMapper(dynamoDB);
        this.converter = new ModelConverter();
    }

    public Customer findCustomerById(String customerId) {
        try {
            DynamoDBQueryExpression<ItemBase> expression = new DynamoDBQueryExpression<ItemBase>()
                .withKeyConditionExpression("#attributeKey = :attributeValue and SK = :sortValue")
                .withExpressionAttributeNames(Map.of("#attributeKey", "PK"))
                .withExpressionAttributeValues(Map.of(":attributeValue",
                    new AttributeValue(customerId), ":sortValue", new AttributeValue(Customer.A_RECORD)));

            PaginatedQueryList<ItemBase> itemBases = mapper.query(ItemBase.class, expression);

            return converter.convertCustomer(itemBases).orElse(null);
        } catch (Exception e) {
            DynamoGeneratedBoilerplate.handleCommonErrors(e);
            throw e;
        }
    }

    public void saveCustomer(Customer customer) {
        log.info("Started - Saving customer with customerId={}", customer.getId());
        var customerAsBase = converter.convertCustomerToBase(customer);
        mapper.save(customerAsBase);
        log.info("Completed - Saving customer with customerId={}", customer.getId());
    }

    public Account findAccountById(String accountId) {
        try {
            DynamoDBQueryExpression<ItemBase> expression = new DynamoDBQueryExpression<ItemBase>()
                .withIndexName("GSI1")
                .withConsistentRead(false)
                .withKeyConditionExpression("#attributeKey = :attributeValue and begins_with(GSISK, :gsisk)")
                .withExpressionAttributeNames(Map.of("#attributeKey", "GSI1PK"))
                .withExpressionAttributeValues(Map.of(":attributeValue",
                    new AttributeValue(accountId), ":gsisk", new AttributeValue("A#")));

            PaginatedQueryList<ItemBase> itemBases = mapper.query(ItemBase.class, expression);

            return converter.convertAccount(itemBases).orElse(null);
        } catch (Exception e) {
            DynamoGeneratedBoilerplate.handleCommonErrors(e);
            throw e;
        }
    }

}
