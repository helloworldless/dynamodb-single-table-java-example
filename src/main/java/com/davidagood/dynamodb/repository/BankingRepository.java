package com.davidagood.dynamodb.repository;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.davidagood.dynamodb.model.Account;
import com.davidagood.dynamodb.model.Customer;
import com.davidagood.dynamodb.util.DynamoGeneratedBoilerplate;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BankingRepository {

    private final AmazonDynamoDB dynamo;
    private final DynamoDBMapper mapper;

    public BankingRepository(AmazonDynamoDB dynamo) {
        this.dynamo = dynamo;
        this.mapper = new DynamoDBMapper(dynamo);
    }

    public Customer findCustomerById(String customerId) {
        try {
            return mapper.load(Customer.class, customerId, Customer.A_RECORD);
        } catch (Exception e) {
            DynamoGeneratedBoilerplate.logCommonErrors(e);
            throw e;
        }
    }

    public long incrementAndGet() {
        var updateItemRequest = new UpdateItemRequest()
            .withTableName("data")
            .withKey(Map.of("PK", new AttributeValue("PLACEHOLDER"), "SK", new AttributeValue("PLACEHOLDER")))
            .withUpdateExpression("SET #count = #count + :increment")
            .withExpressionAttributeNames(Map.of("#count", "count"))
            .withExpressionAttributeValues(Map.of(":increment", new AttributeValue().withN("1")))
            .withReturnValues(ReturnValue.UPDATED_NEW)
            .withReturnConsumedCapacity(ReturnConsumedCapacity.INDEXES);
        var updateItemResult = dynamo.updateItem(updateItemRequest);
        log.info("{}", updateItemResult.getConsumedCapacity());
        return Long.parseLong(updateItemResult.getAttributes().get("count").getN());
    }

    public void updateAccount(Account account) {
        try {
            log.info("Started - Updating account with accountId={}", account.getId());
            var dynamoDBMapperConfig = new DynamoDBMapperConfig.Builder()
                // This is the default but including here for clarity
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE)
                .build();
            mapper.save(account, dynamoDBMapperConfig);
            log.info("Completed - Updating account with accountId={}", account.getId());
        } catch (Exception e) {
            DynamoGeneratedBoilerplate.logCommonErrors(e);
            throw e;
        }
    }

    public void insertCustomer(Customer customer) {
        try {
            log.info("Started - Inserting customer with customerId={}", customer.getId());
            var saveIfNotExistsExpression = new DynamoDBSaveExpression()
                .withExpectedEntry("PK", new ExpectedAttributeValue(false))
                .withExpectedEntry("SK", new ExpectedAttributeValue(false));
            mapper.save(customer, saveIfNotExistsExpression);
            log.info("Completed - Inserting customer with customerId={}", customer.getId());
        } catch (ConditionalCheckFailedException ce) {
            throw new UniquePrimaryKeyConstraintViolationException("Failed to insert customer because an item with " +
                "this PK and SK already exists; " + customer);
        } catch (Exception e) {
            DynamoGeneratedBoilerplate.logCommonErrors(e);
            throw e;
        }
    }

    public Account findAccountById(String accountId) {
        try {
            var queryExpression = new DynamoDBQueryExpression<Account>()
                .withIndexName("GSI1")
                .withConsistentRead(false)
                .withKeyConditionExpression("#accountId = :accountId and begins_with(GSISK, :GSISK)")
                .withExpressionAttributeNames(Map.of("#accountId", "GSI1PK"))
                .withExpressionAttributeValues(Map.of(":accountId", new AttributeValue(accountId),
                    ":GSISK", new AttributeValue("A#")));
            var accountQueryResult = mapper.query(Account.class, queryExpression);
            return accountQueryResult.isEmpty() ? null : accountQueryResult.get(0);
        } catch (Exception e) {
            DynamoGeneratedBoilerplate.logCommonErrors(e);
            throw e;
        }
    }

    public void insertAccount(Account account) {
        try {
            log.info("Started - Inserting account with accountId={}", account.getId());
            var saveIfNotExistsExpression = new DynamoDBSaveExpression()
                .withExpectedEntry("PK", new ExpectedAttributeValue(false))
                .withExpectedEntry("SK", new ExpectedAttributeValue(false));
            mapper.save(account, saveIfNotExistsExpression);
            log.info("Completed - Inserting account with accountId={}", account.getId());
        } catch (ConditionalCheckFailedException ce) {
            throw new UniquePrimaryKeyConstraintViolationException("Failed to insert account because an item with " +
                "this PK and SK already exists; " + account);
        } catch (Exception e) {
            DynamoGeneratedBoilerplate.logCommonErrors(e);
            throw e;
        }
    }
}
