package dynamodb.demo.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  Created by Rasesh Desai on 9/10/14.
 */
public class AWSUtilImpl implements AWSUtil {

    @Autowired
    private AmazonDynamoDBClient dynamoDBClient;

    @Override
    public AmazonDynamoDBClient getAWSClient() {
        dynamoDBClient.setRegion(Region.getRegion(Regions.US_WEST_2));
        return dynamoDBClient;
    }
}
