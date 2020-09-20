package com.davidagood.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import lombok.extern.slf4j.Slf4j;
import java.time.ZonedDateTime;
import java.util.Optional;

@Slf4j
public class ModelConverter {

    public Optional<Customer> convertCustomer(PaginatedQueryList<ItemBase> itemBases) {
        int size = itemBases.size();
        if (size == 0) {
            return Optional.empty();
        }
        if (size == 1) {
            var itemBase = itemBases.get(0);
            if (!itemBase.getType().equalsIgnoreCase(Customer.TYPE)) {
                throw new IllegalArgumentException(String.format("Expected Customer to have type=%s " +
                    "but type was type=%s", Customer.TYPE, itemBase.getType()));
            }
            var customer = Customer.builder()
                .id(itemBase.getPk())
                .firstName(itemBase.getFirstName())
                .lastName(itemBase.getLastName())
                .build();
            return Optional.of(customer);
        }

        log.error("This should never happen; Found size={} items", size);
        return Optional.empty();
    }

    public Optional<Account> convertAccount(PaginatedQueryList<ItemBase> itemBases) {
        int size = itemBases.size();
        if (size == 0) {
            return Optional.empty();
        }
        if (size == 1) {
            var itemBase = itemBases.get(0);
            if (!itemBase.getType().equalsIgnoreCase(Account.TYPE)) {
                throw new IllegalArgumentException(String.format("Expected Account to have type=%s " +
                    "but type was type=%s", Account.TYPE, itemBase.getType()));
            }
            var account = Account.builder()
                .id(itemBase.getGsipk1())
                .name(itemBase.getAccountName())
                .lastAccessTime(ZonedDateTime.parse(itemBase.getGsisk().split("#")[1]))
                .build();
            return Optional.of(account);
        }

        log.error("This should never happen; Found size={} items", size);
        return Optional.empty();
    }

    public ItemBase convertCustomerToBase(Customer customer) {
        return ItemBase.builder()
            .pk(customer.getId())
            .sk(Customer.A_RECORD)
            .type(Customer.TYPE)
            .firstName(customer.getFirstName())
            .lastName(customer.getLastName())
            .build();
    }

}
