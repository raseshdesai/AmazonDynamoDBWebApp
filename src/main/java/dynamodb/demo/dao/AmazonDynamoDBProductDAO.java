package dynamodb.demo.dao;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import dynamodb.demo.util.AWSUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 *  Created by Rasesh Desai on 9/9/14.
 */
public class AmazonDynamoDBProductDAO implements ProductDAO {

    public static String TABLE_NAME = "RD_PRODUCT_TABLE";
    public static final String HASH_KEY = "VARIETY_NAME";

    @Autowired
    private AWSUtil awsUtil;

    @Override
    public void addProduct(String productVariety) {
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put(HASH_KEY, new AttributeValue().withS(productVariety));
        PutItemRequest itemRequest = new PutItemRequest().withTableName(TABLE_NAME).withItem(item);
        awsUtil.getAWSClient().putItem(itemRequest);
    }
}
