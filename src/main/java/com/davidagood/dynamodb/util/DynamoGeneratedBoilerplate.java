package com.davidagood.dynamodb.util;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.model.InternalServerErrorException;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputExceededException;
import com.amazonaws.services.dynamodbv2.model.RequestLimitExceededException;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DynamoGeneratedBoilerplate {

    public static void handleCommonErrors(Exception exception) {
        try {
            throw exception;
        } catch (InternalServerErrorException isee) {
            log.error("Internal Server Error, generally safe to retry with exponential back-off. Error: {}",
                isee.getErrorMessage());
        } catch (RequestLimitExceededException rlee) {
            log.error("Throughput exceeds the current throughput limit for your account, " +
                "increase account level throughput before " +
                "retrying. Error: {}", rlee.getErrorMessage());
        } catch (ProvisionedThroughputExceededException ptee) {
            log.error("Request rate is too high. If you're using a custom retry strategy make sure to retry " +
                "with exponential back-off. " +
                "Otherwise consider reducing frequency of requests or increasing provisioned capacity for " +
                "your table or secondary index. Error: " +
                ptee.getErrorMessage());
        } catch (ResourceNotFoundException rnfe) {
            log.error("One of the tables was not found, verify table exists before retrying. Error: {}",
                rnfe.getErrorMessage());
        } catch (AmazonServiceException ase) {
            log.error("An AmazonServiceException occurred, indicates that the request was correctly " +
                "transmitted to the DynamoDB " +
                "service, but for some reason, the service was not able to process it, and returned an error " +
                "response instead. Investigate and " +
                "configure retry strategy. Error type: " + ase.getErrorType() + ". Error message: " +
                ase.getErrorMessage());
        } catch (AmazonClientException ace) {
            log.error("An AmazonClientException occurred, indicates that the client was unable to get a " +
                "response from DynamoDB " +
                "service, or the client was unable to parse the response from the service. Investigate and " +
                "configure retry strategy. " +
                "Error: {}", ace.getMessage());
        } catch (Exception e) {
            log.error("An exception occurred, investigate and configure retry strategy. Error: {}", e.getMessage());
        }
    }
}
