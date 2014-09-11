package dynamodb.demo.controller;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import dynamodb.demo.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 *  Created by Rasesh Desai on 3/26/14.
 */
@Controller
public class ProductController {

    @Autowired
    private ProductDAO productDAO;

    @RequestMapping(value = "/addproduct", method = RequestMethod.GET)
    public String addProduct(@RequestParam(value = "productvariety", required = false) String productVariety, Model model) {
        if (null == productVariety) {
            throw new IllegalArgumentException("Product variety cannot be null");
        }
        productDAO.addProduct(productVariety);
        model.addAttribute("productvariety", productVariety);
        return "productconfirmation";
    }
}
