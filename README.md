# DynamoDB Java Example

Examples of working with DynamoDB using the Java AWS SDK.

Also uses [DynamoDBMapper](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBMapper.html).

Data model based on [DynamoDB Office Hours | Online banking service model with Rick Houlihan
](https://www.twitch.tv/videos/689452191)

See data model JSON in `resources/DataModelingBanking.json`. This can be imported to
 [NoSQL Workbench for DynamoDB](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/workbench.html)
  and committed to AWS.

## How to Run the Example Code

1. Make sure you have your AWS credentials configured
1. Run `./gradlew run`

Example output:

```text
20:02:09.807 [main] INFO com.davidagood.dynamodb.App - Finding customer by customerId=customer1
20:02:10.322 [main] INFO com.davidagood.dynamodb.App - Customer(id=customer1, firstName=Sam, lastName=Jones)
20:02:10.332 [main] INFO com.davidagood.dynamodb.App - Finding account by accountId=account1
20:02:10.404 [main] INFO com.davidagood.dynamodb.App - Account(id=account1, name=My First Account, lastAccessTime=2020-09-15T12:00Z)
20:02:10.406 [main] INFO com.davidagood.dynamodb.repository.BankingDataRepository - Started - Saving customer with customerId=customer2
20:02:10.466 [main] INFO com.davidagood.dynamodb.repository.BankingDataRepository - Completed - Saving customer with customerId=customer2

```
