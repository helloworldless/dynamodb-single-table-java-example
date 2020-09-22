# DynamoDB Java Example

Examples of working with DynamoDB (single table design) using the Java AWS SDK.

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
20:06:04.121 [main] INFO com.davidagood.dynamodb.App - Finding customer by customerId=customer1
20:06:04.780 [main] INFO com.davidagood.dynamodb.App - Customer(id=customer1, firstName=Sam, lastName=Jones)
20:06:04.809 [main] INFO com.davidagood.dynamodb.App - Finding account by accountId=account1
20:06:04.899 [main] INFO com.davidagood.dynamodb.App - Account(customerId=customer1, id=account1, lastAccessTime=2020-09-21T00:30:12.596143Z, name=My First Account)
20:06:04.921 [main] INFO com.davidagood.dynamodb.repository.BankingRepository - Started - Saving account with accountId=account1
20:06:04.977 [main] INFO com.davidagood.dynamodb.repository.BankingRepository - Completed - Saving account with accountId=account1
20:06:04.977 [main] INFO com.davidagood.dynamodb.repository.BankingRepository - Started - Saving customer with customerId=3db19ebc-256b-4011-be0e-59a34bb54ceb
20:06:05.028 [main] INFO com.davidagood.dynamodb.repository.BankingRepository - Completed - Saving customer with customerId=3db19ebc-256b-4011-be0e-59a34bb54ceb
20:06:05.028 [main] INFO com.davidagood.dynamodb.repository.BankingRepository - Started - Saving account with accountId=cf2b1907-a12d-4841-98d9-31596400b295
20:06:05.080 [main] INFO com.davidagood.dynamodb.repository.BankingRepository - Completed - Saving account with accountId=cf2b1907-a12d-4841-98d9-31596400b295
```
