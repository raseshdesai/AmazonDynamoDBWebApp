package dynamodb.demo;

import com.amazonaws.services.dynamodbv2.model.*;
import dynamodb.demo.util.AWSUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 *  Created by Rasesh Desai on 6/20/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = {"classpath:spring-mvc-dynamo-db-demo-app-servlet.xml"})
public class AwsDynamoDBStandaloneClient_IT {

    private static final String MY_FAVORITE_MOVIES_TABLE = "my-favorite-movies-table";
    private static final String MOVIE_TABLE_HASH_KEY = "name";

    private static final String PRODUCT_DEMO_TABLE = "product-demo-table-for-integration-tests";
    private static final String PRODUCT_TABLE_HASH_KEY = "varietyName";
    private static final String PRODUCT_TABLE_RANGE_KEY = "crop";
    private static final String PRODUCT_TABLE_RM_COLUMN = "rm";
    private static final String PRODUCT_TABLE_COUNTRY_COLUMN = "country";

    @Autowired
    private AWSUtil awsUtil;

    @Test
    public void insertData(){
        String moviename = "Rocky6";
        addMovie(moviename);
        System.out.println("Movie successfully added");
    }

    @Test
    public void retrieveItem(){
        String countryName = getCountry("dkc_1", "corn");
        assertEquals("US", countryName);
    }

    @Test
    public void retrieveMultipleItems(){
        assertEquals(3, printAllProducts("dkc_1"));
    }

    @Test
    public void retrieveMultipleItemsWithFilter(){
        assertEquals(2, printAllProductsWithFilter("dkc_1", "10"));
    }

    @Test
    public void retrieveMultipleItemsWithLimit(){
        assertEquals(2, printAllProductsWithLimit("dkc_1", 2));
    }

    private int printAllProductsWithLimit(String varietyName, int limit) {
        Map<String, Condition> keyConditions = new HashMap<String, Condition>();
        keyConditions.put(PRODUCT_TABLE_HASH_KEY, new Condition().withComparisonOperator(ComparisonOperator.EQ).withAttributeValueList(new AttributeValue(varietyName)));

        QueryRequest queryRequest = new QueryRequest(PRODUCT_DEMO_TABLE).withKeyConditions(keyConditions).withLimit(limit).withScanIndexForward(false);

        QueryResult result = awsUtil.getAWSClient().query(queryRequest);
        for (Map<String, AttributeValue> item : result.getItems()) {
            System.out.println(item);
        }
        return result.getCount();
    }

    private int printAllProductsWithFilter(String varietyName, String relativeMaturityLessThanValue) {
        Map<String, Condition> keyConditions = new HashMap<String, Condition>();
        keyConditions.put(PRODUCT_TABLE_HASH_KEY, new Condition().withComparisonOperator(ComparisonOperator.EQ).withAttributeValueList(new AttributeValue(varietyName)));

        Map<String, Condition> queryFilterConditions = new HashMap<String, Condition>();
        queryFilterConditions.put(PRODUCT_TABLE_RM_COLUMN, new Condition().withComparisonOperator(ComparisonOperator.LT).withAttributeValueList(new AttributeValue().withN(relativeMaturityLessThanValue)));

        QueryRequest queryRequest = new QueryRequest(PRODUCT_DEMO_TABLE).withKeyConditions(keyConditions).withQueryFilter(queryFilterConditions);

        QueryResult result = awsUtil.getAWSClient().query(queryRequest);
        for (Map<String, AttributeValue> item : result.getItems()) {
            System.out.println(item);
        }
        return result.getCount();
    }

    private int printAllProducts(String varietyName) {
        Map<String, Condition> keyConditions = new HashMap<String, Condition>();
        keyConditions.put(PRODUCT_TABLE_HASH_KEY, new Condition().withComparisonOperator(ComparisonOperator.EQ).withAttributeValueList(new AttributeValue(varietyName)));

        QueryRequest queryRequest = new QueryRequest(PRODUCT_DEMO_TABLE).withKeyConditions(keyConditions);

        QueryResult result = awsUtil.getAWSClient().query(queryRequest);
        for (Map<String, AttributeValue> item : result.getItems()) {
            System.out.println(item);
        }
        return result.getCount();
    }

    private String getCountry(String varietyName, String crop) {
        Map.Entry<String, AttributeValue> hashKey = new AbstractMap.SimpleEntry<String, AttributeValue>(PRODUCT_TABLE_HASH_KEY, new AttributeValue(varietyName));
        Map.Entry<String, AttributeValue> rangeKey = new AbstractMap.SimpleEntry<String, AttributeValue>(PRODUCT_TABLE_RANGE_KEY, new AttributeValue(crop));
        GetItemRequest getItemRequest = new GetItemRequest().withTableName(PRODUCT_DEMO_TABLE).withKey(hashKey, rangeKey);
        GetItemResult item = awsUtil.getAWSClient().getItem(getItemRequest);
        return item.getItem().get(PRODUCT_TABLE_COUNTRY_COLUMN).getS();
    }

    private void addMovie(String movieName){
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put(MOVIE_TABLE_HASH_KEY, new AttributeValue().withS(movieName));
        PutItemRequest itemRequest = new PutItemRequest().withTableName(MY_FAVORITE_MOVIES_TABLE).withItem(item);
        awsUtil.getAWSClient().putItem(itemRequest);
    }
}
