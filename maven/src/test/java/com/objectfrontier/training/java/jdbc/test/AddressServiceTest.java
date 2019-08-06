package com.objectfrontier.training.java.jdbc.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.objectfrontier.training.java.jdbc.AppException;
import com.objectfrontier.training.java.jdbc.model.Address;
import com.objectfrontier.training.java.jdbc.service.AddressService;
import com.objectfrontier.training.java.jdbc.service.ConnectionService;

public class AddressServiceTest {

    private AddressService addressService = new AddressService();
    private Connection connection = null;

    private static final String INPUTS_MSG = "INPUTS: given = %s, expected = %s.";
    private static final String ASSERT_FAIL_MSG = "Expected:<%s> but was:<%s>";

    /* Before Test */

    @BeforeTest
    private void createConnection() throws IOException, ClassNotFoundException {

        try {
            connection = ConnectionService.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* After Test */

    @AfterTest
    private void closeConnection() {

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* Positive testcase for create Address */

    @Test(dataProvider = "testCreatePositive", enabled = false)
    private void testCreate_positive(Address givenAddress, Address expectedAddress) throws SQLException {

        try {
            Address createdAddress = addressService.create(connection, givenAddress);
            Assert.assertEquals(createdAddress.getCity(), expectedAddress.getCity(), "Success!");
        } catch (AppException e) {
            Assert.fail(String.format(INPUTS_MSG, givenAddress.getStreet(), expectedAddress.getStreet()) +
                        String.format(INPUTS_MSG, givenAddress.getCity(), expectedAddress.getCity()) +
                        String.format(INPUTS_MSG, givenAddress.getPostalCode(), expectedAddress.getPostalCode()) +
                        String.format(ASSERT_FAIL_MSG, givenAddress.getId(), e.getErrorMessage()));
        }
    }

    /* Dataprovider for positive testcase for create Address */

    @DataProvider(name = "testCreatePositive")
    private Object[][] testCreatePositiveValues() {

        Address givenAddress = new Address("Ammapet", "Salem", 636003);
        Address expectedAddress = givenAddress;
        return new Object[][] {
                                { givenAddress, expectedAddress }
        };
    }
//
//    /* Negative testcase for create Address */
//
//    @Test(dataProvider = "testCreateNegative", enabled = false)
//    private void testCreate_negative(Address givenAddress, String expectedError) throws SQLException {
//
//        try {
//            Address createdAddress = addressService.create(connection, givenAddress);
//            Assert.fail(String.format(INPUTS_MSG, createdAddress.getStreet(), givenAddress.getStreet()) +
//                        String.format(INPUTS_MSG, createdAddress.getCity(), givenAddress.getCity()) +
//                        String.format(INPUTS_MSG, createdAddress.getPostalCode(), givenAddress.getPostalCode()) +
//                        String.format(ASSERT_FAIL_MSG, givenAddress.getId(), createdAddress.getId()));
//        } catch (AppException e) {
//            Assert.assertEquals(e.getErrorMessage(), expectedError, "Address creation failed!");
//        }
//    }
//
//    /* Dataprovider for negative testcase for create Address */
//
//    @DataProvider(name = "testCreateNegative")
//    private Object[][] testCreateNegativeValues() {
//
//        Address givenAddress1 = new Address("", "Salem", 636003);
//        Address givenAddress2 = new Address("Ammapet", "", 636003);
//        Address givenAddress3 = new Address("Ammapet", "Salem", 0);
//        String expectedError1 = "Street should not be empty";
//        String expectedError2 = "City should not be empty";
//        String expectedError3 = "Postal code should not be empty";
//        return new Object[][] {
//                                { givenAddress1, expectedError1 },
//                                { givenAddress2, expectedError2 },
//                                { givenAddress3, expectedError3 }
//        };
//    }
//
//    /* Positive testcase for update Address */
//
//    @Test(dataProvider = "testUpdatePositive", enabled = false)
//    private void testUpdate_positive(Address givenAddress, Address expectedAddress) throws SQLException {
//
//        try {
//            boolean createdAddress = addressService.update(connection, givenAddress);
//            Assert.assertEquals(createdAddress, true, "Success!");
//        } catch (AppException e) {
//            Assert.fail(String.format(INPUTS_MSG, givenAddress.getId(), expectedAddress.getId()) +
//                        String.format(INPUTS_MSG, givenAddress.getStreet(), expectedAddress.getStreet()) +
//                        String.format(INPUTS_MSG, givenAddress.getCity(), expectedAddress.getCity()) +
//                        String.format(INPUTS_MSG, givenAddress.getPostalCode(), expectedAddress.getPostalCode()) +
//                        String.format(ASSERT_FAIL_MSG, givenAddress.getId(), e.getErrorMessage()));
//        }
//    }
//
//    /* Dataprovider for positive testcase for update Address */
//
//    @DataProvider(name = "testUpdatePositive")
//    private Object[][] testUpdatePositiveValues() {
//
//        Address givenAddress = new Address(33, "Peelamedu", "Coimbatore", 641004);
//        Address expectedAddress = givenAddress;
//        return new Object[][] {
//                                {givenAddress, expectedAddress}
//        };
//    }
//
//    /* Negative testcase for update Address */
//
//    @Test(dataProvider = "testUpdateNegative", enabled = false)
//    private void testUpdate_negative(Address givenAddress, String expectedError) throws SQLException {
//
//        try {
//            boolean updatedAddress = addressService.update(connection, givenAddress);
//            Assert.fail(String.format(INPUTS_MSG, updatedAddress, true, "Testcase failed!") +
//                        String.format(ASSERT_FAIL_MSG, givenAddress.getId(), updatedAddress));
//        } catch (AppException e) {
//            Assert.assertEquals(e.getErrorMessage(), expectedError, "Address updation failed!");
//        }
//    }
//
//    /* Dataprovider for negative testcase for update Address */
//
//    @DataProvider(name = "testUpdateNegative")
//    private Object[][] testUpdateNegativeValues() {
//
//        Address givenAddress1 = new Address(34, "Ammapet", "Salem", 0);
//        Address givenAddress2 = new Address(33, "", "Salem", 0);
//        Address givenAddress3 = new Address(33, "Ammapet", "", 0);
//        Address givenAddress4 = new Address(33, "Ammapet", "Salem", 0);
//        String expectedError1 = "No entry found in the given ID";
//        String expectedError2 = "Street should not be empty";
//        String expectedError3 = "City should not be empty";
//        String expectedError4 = "Postal code should not be empty";
//        return new Object[][] {
//                                { givenAddress1, expectedError1 },
//                                { givenAddress2, expectedError2 },
//                                { givenAddress3, expectedError3 },
//                                { givenAddress4, expectedError4 }
//        };
//    }
//
//    /* Positive testcase for read Address */
//
//    @Test(dataProvider = "testReadPositive", enabled = false)
//    private void testRead_positive(long addressId) throws SQLException {
//
//        try {
//            Address retrivedAddress = addressService.read(connection, addressId);
//            Assert.assertEquals(retrivedAddress.getCity(), "Coimbatore", "Success!");
//        } catch (AppException e) {
//            Assert.fail(e.getErrorMessage());
//        }
//    }
//
//    /* Dataprovider for positive testcase for read Address */
//
//    @DataProvider(name = "testReadPositive")
//    private Object[][] testReadPositiveValues() {
//
//        long addressId = 33;
//        return new Object[][] {
//                                { addressId }
//        };
//    }
//
//    /* Negative testcase for read Address */
//
//    @Test(dataProvider = "testReadNegative", enabled = false)
//    private void testRead_negative(long addressId, String expectedError) throws SQLException {
//
//        try {
//            Address retrivedAddress = addressService.read(connection, addressId);
//            Assert.fail(String.format(INPUTS_MSG, retrivedAddress.getStreet(), "Testcase failed!") +
//                        String.format(ASSERT_FAIL_MSG, retrivedAddress.getCity(), retrivedAddress.getCity()));
//        } catch (AppException e) {
//            Assert.assertEquals(e.getErrorMessage(), expectedError, "Address read failed!");
//        }
//    }
//
//    /* Dataprovider for negative testcase for read Address */
//
//    @DataProvider(name = "testReadNegative")
//    private Object[][] testReadNegativeValues() {
//
//        long addressId = 34;
//        String expectedError = "No entry found in the given ID";
//        return new Object[][] {
//                                { addressId, expectedError }
//        };
//    }
//
////    @Test
////    private void testDelete_positive() throws SQLException {
////
////        Connection connection = ConnectionService.getConnection();
////        Address address = new Address();
////        address.setId(28);
////        testDelete_positive(connection, address);
////    }
////
////    private void testDelete_positive(Connection connection, Address address) throws SQLException {
////
////        try {
////
////            boolean deletedAddress = addressService.delete(connection, address);
////            Assert.assertEquals(deletedAddress, true, "Success!");
////        } catch (Exception e) {
////            Assert.fail(String.format(INPUTS_MSG, address.getCity(), address.getCity()) +
////                    String.format(ASSERT_FAIL_MSG, address.getId(), e.getMessage()));
////        }
////    }
////
////    @Test
////    private void testDelete_negative() throws SQLException {
////
////        Connection connection = ConnectionService.getConnection();
////        Address address = new Address();
////        address.setId(16);
////        String expectedError = "No address found in this ID " + address.getId();
////        testDelete_negative(connection, address, expectedError);
////    }
////
////    private void testDelete_negative(Connection connection, Address address, String expectedError) throws SQLException {
////
////        try {
////
////            boolean deletedAddress = addressService.delete(connection, address);
////            Assert.fail(String.format(INPUTS_MSG, deletedAddress, true) +
////                    String.format(ASSERT_FAIL_MSG, address.getId(), expectedError));
////        } catch (Exception e) {
////            Assert.assertEquals(e.getMessage(), expectedError, "Success!");
////        }
////    }
//
//    /*  => Property File
//        => Interface         */
//
//    @Test(priority = 4, enabled = true)
//    private void testSearch() throws SQLException {
//
//        try {
//            String[] columnName = new String[] {"street" , "city", "postalCode"};
//            addressService.search(connection, "salem", columnName);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
}
