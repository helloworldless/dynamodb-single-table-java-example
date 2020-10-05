package com.davidagood.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.davidagood.dynamodb.model.Account;
import com.davidagood.dynamodb.model.Customer;
import com.davidagood.dynamodb.repository.BankingRepository;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
    private static final String REGION = "us-east-1";

    public static void main(String[] args) {
        var app = new App();
        // app.runMapperExamples();
        app.runDirectApiExamples();
    }

    private final BankingRepository bankingRepository;

    public App() {
        var dynamo = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();
        this.bankingRepository = new BankingRepository(dynamo);
    }

    public void runMapperExamples() {
        var customerId = "customer1";
        log.info("Finding customer by customerId={}", customerId);
        var customer = this.bankingRepository.findCustomerById(customerId);
        log.info("{}", customer);

        String accountId = "account1";
        log.info("Finding account by accountId={}", accountId);
        var account = this.bankingRepository.findAccountById(accountId);
        log.info("{}", account);

        account.setLastAccessTime(ZonedDateTime.now(ZoneId.of("UTC")));
        this.bankingRepository.updateAccount(account);

        var jessSmithId = UUID.randomUUID().toString();
        var jessSmith = Customer.builder()
            .id(jessSmithId)
            .firstName("Jessica")
            .lastName("Smith")
            .build();

        this.bankingRepository.insertCustomer(jessSmith);

        var secondAccount = Account.builder()
            .id(UUID.randomUUID().toString())
            .customerId(jessSmithId)
            .name("Another account with access time")
            .lastAccessTime(ZonedDateTime.now(ZoneId.of("UTC")))
            .build();

        this.bankingRepository.insertAccount(secondAccount);
    }

    public void runDirectApiExamples() {
        log.info("Incrementing and getting new count");
        long newCount = this.bankingRepository.incrementAndGet();
        log.info("New count={}", newCount);

    }

}
