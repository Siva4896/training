package com.objectfrontier.training.java.jdbc.test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.objectfrontier.training.java.jdbc.exception.AppException;
import com.objectfrontier.training.java.jdbc.exception.ExceptionCode;
import com.objectfrontier.training.java.jdbc.servlet.HttpMethod;
import com.objectfrontier.training.java.jdbc.servlet.RequestHelper;

public class PersonIntegration {

    String baseUrl = "http://localhost:8080/webservices.1.0.1/person";

    /* Before Class */

    @BeforeClass
    public void setup() throws Exception {

        String filePath = "D:/tools/jetty-distribution-9.3.6.v20151106/start.jar"; //where your jar is located.
        Runtime.getRuntime().exec("java -jar" + filePath);
    }

    /* Before Test */

    @BeforeTest
    private void init() throws IOException {

        try {
            RequestHelper.setBaseUrl(baseUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Positive testcase for getInitial of Person */

    @Test(dataProvider = "testGetInitialPositive")
    private void testRead_positive(long personId, String expectedInitial) {

        try {
            String retrivedInitial = new RequestHelper().setMethod(HttpMethod.GET)
                                                       .setSecured(false)
                                                       .requestString("/initial?id=" + personId);
            Assert.assertEquals(retrivedInitial, expectedInitial, "Success!");
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /* Dataprovider for positive testcase for getInitial of Person */

    @DataProvider(name = "testGetInitialPositive")
    private Object[][] testReadPositiveValues() {

        long personId = 1;
        String expectedInitial = "BM";
        return new Object[][] {
                                { personId, expectedInitial }
        };
    }

    /* Negative testcase for getInitial of Person */

    @Test(dataProvider = "testGetInitialNegative")
    private void testRead_negative(long personId, List<ExceptionCode> expectedError) throws SQLException, IOException {

        try {
            String retrivedPerson = new RequestHelper().setMethod(HttpMethod.GET)
                                                       .setSecured(false)
                                                       .requestString("/initial?id=" + personId);
            Assert.fail(String.format("Person read failed!"));
    } catch (Exception e) {
        if (e instanceof AppException) {
            Assert.assertEquals(((AppException) e).getErrorList(), expectedError, "Person read failed due to" + expectedError + "!");
            }
        }
    }

    /* Dataprovider for negative testcase for getInitial of Person */

    @DataProvider(name = "testGetInitialNegative")
    private Object[][] testReadNegativeValues() {

        long personId = 33;
        List<ExceptionCode> errorList = new ArrayList<>();
        errorList.add(ExceptionCode.ID_NOT_FOUND);
        return new Object[][] {
                                { personId, errorList }
        };
    }
}
