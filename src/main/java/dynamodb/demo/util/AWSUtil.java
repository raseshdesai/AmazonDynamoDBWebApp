package dynamodb.demo.util;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

/**
 *  Created by Rasesh Desai on 9/10/14.
 */
public interface AWSUtil {

    AmazonDynamoDBClient getAWSClient();
}
