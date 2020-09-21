package com.davidagood.dynamodb.repository;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.davidagood.dynamodb.model.Account;
import com.davidagood.dynamodb.model.Customer;
import com.davidagood.dynamodb.util.DynamoGeneratedBoilerplate;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BankingDataRepository {

    private final DynamoDBMapper mapper;

    public BankingDataRepository(AmazonDynamoDB dynamoDB) {
        this.mapper = new DynamoDBMapper(dynamoDB);
    }

    public Customer findCustomerById(String customerId) {
        try {
            var queryExpression = new DynamoDBQueryExpression<Customer>()
                .withKeyConditionExpression("#attributeKey = :attributeValue and SK = :sortValue")
                .withExpressionAttributeNames(Map.of("#attributeKey", "PK"))
                .withExpressionAttributeValues(Map.of(":attributeValue",
                    new AttributeValue(customerId), ":sortValue", new AttributeValue(Customer.A_RECORD)));

            var customerQueryResult = mapper.query(Customer.class, queryExpression);
            return customerQueryResult.isEmpty() ? null : customerQueryResult.get(0);

        } catch (Exception e) {
            DynamoGeneratedBoilerplate.handleCommonErrors(e);
            throw e;
        }
    }

    public void saveCustomer(Customer customer) {
        log.info("Started - Saving customer with customerId={}", customer.getId());
        mapper.save(customer);
        log.info("Completed - Saving customer with customerId={}", customer.getId());
    }

    public Account findAccountById(String accountId) {
        try {
            var queryExpression = new DynamoDBQueryExpression<Account>()
                .withIndexName("GSI1")
                .withConsistentRead(false)
                .withKeyConditionExpression("#attributeKey = :attributeValue and begins_with(GSISK, :gsisk)")
                .withExpressionAttributeNames(Map.of("#attributeKey", "GSI1PK"))
                .withExpressionAttributeValues(Map.of(":attributeValue",
                    new AttributeValue(accountId), ":gsisk", new AttributeValue("A#")));

            var accountQueryResult = mapper.query(Account.class, queryExpression);
            return accountQueryResult.isEmpty() ? null : accountQueryResult.get(0);

        } catch (Exception e) {
            DynamoGeneratedBoilerplate.handleCommonErrors(e);
            throw e;
        }
    }

    public void saveAccount(Account account) {
        log.info("Started - Saving account with accountId={}", account.getId());
        mapper.save(account);
        log.info("Completed - Saving account with accountId={}", account.getId());
    }
}
