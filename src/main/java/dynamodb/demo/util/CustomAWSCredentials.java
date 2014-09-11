package dynamodb.demo.util;

import com.amazonaws.auth.AWSCredentials;

import java.util.Properties;

/**
 *  Created by Rasesh Desai on 6/5/14.
 */

public class CustomAWSCredentials  implements AWSCredentials{

    private Properties awsCredentialProperties;

    public CustomAWSCredentials(Properties awsCredentialProperties) {
        this.awsCredentialProperties = awsCredentialProperties;
    }

    @Override
    public String getAWSAccessKeyId() {
        return awsCredentialProperties.getProperty("accessKey");
    }

    @Override
    public String getAWSSecretKey() {
        return awsCredentialProperties.getProperty("secretKey");
    }
}
