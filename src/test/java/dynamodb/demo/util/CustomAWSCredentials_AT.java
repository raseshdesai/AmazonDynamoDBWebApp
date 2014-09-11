package dynamodb.demo.util;

import dynamodb.demo.util.CustomAWSCredentials;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *  Created by Rasesh Desai on 6/5/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = {"classpath:spring-mvc-dynamo-db-demo-app-servlet.xml"})
public class CustomAWSCredentials_AT {

    @Autowired
    private CustomAWSCredentials customAWSCredentials;

    @Test
    public void readProperty() throws Exception {
        System.out.println("customAWSCredentials.getAWSAccessKeyId() = " + customAWSCredentials.getAWSAccessKeyId());
        System.out.println("customAWSCredentials.getAWSSecretKey() = " + customAWSCredentials.getAWSSecretKey());
    }
}
