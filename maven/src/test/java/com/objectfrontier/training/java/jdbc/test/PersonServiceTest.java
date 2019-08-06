package com.objectfrontier.training.java.jdbc.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.objectfrontier.training.java.jdbc.AppException;
import com.objectfrontier.training.java.jdbc.model.Address;
import com.objectfrontier.training.java.jdbc.model.Person;
import com.objectfrontier.training.java.jdbc.service.ConnectionService;
import com.objectfrontier.training.java.jdbc.service.PersonService;

public class PersonServiceTest {

    private PersonService personService = new PersonService();
    Connection connection = null;

    private static final String INPUTS_MSG = "INPUTS: given = %s, expected = %s.";
    private static final String ASSERT_FAIL_MSG = " expected:<%s> but was:<%s>";

    /* Positive testcase for create Person */

    @Test(dataProvider = "testCreatePositive", priority = 1, enabled = true)
    private void testCreatePositive(Person givenPerson, Person expectedPerson) throws SQLException, IOException, ClassNotFoundException {

        try {
            connection = ConnectionService.getConnection();
            System.out.println(connection);
            Person createdPerson = personService.create(connection, givenPerson);
            Assert.assertEquals(createdPerson, expectedPerson, "Success!");
//            ConnectionService.manageTransaction(connection, true);
        } catch (AppException e) {
            Assert.fail(e.getErrorMessage());
        }
        connection.close();
    }

    /* Dataprovider for positive testcase for create Person */

    @DataProvider(name = "testCreatePositive", parallel = true)
    private Object[][] testCreate_positiveValues() throws IOException, URISyntaxException {

      Address givenAddress = new Address("Peelamedu", "Coimbatore", 641004);
      Person givenPerson = new Person("Ranjith", "R", "ranjithraj@gmail.com", "04-04-1996", Timestamp.from(Instant.now()), givenAddress);
      Person expectedPerson = givenPerson;
      return new Object[][] {
                              { givenPerson, expectedPerson }
      };
//        Object[][] parsedFile = new CSVReader().fileParse();
//        return parsedFile;
    }

//    /* Negative testcase for create Person */
//
//    @Test(dataProvider = "testCreateNegative", priority = 2, enabled = true)
//    private void testCreate_negative(Person givenPerson, String expectedError) throws SQLException, IOException {
//
//        try {
//            connection = ConnectionService.getConnection();
//            Person createdPerson = personService.create(connection, givenPerson);
//            Assert.fail(String.format(INPUTS_MSG, createdPerson.getFirstName(), givenPerson.getFirstName()) +
//                        String.format(INPUTS_MSG, createdPerson.getLastName(), givenPerson.getLastName()) +
//                        String.format(INPUTS_MSG, createdPerson.getEmail(), givenPerson.getEmail()) +
//                        String.format(INPUTS_MSG, createdPerson.getBirthDate(), givenPerson.getBirthDate()) +
//                        String.format(INPUTS_MSG, createdPerson.getAddress(), givenPerson.getAddress()) +
//                        String.format(ASSERT_FAIL_MSG, givenPerson.getId(), createdPerson.getId()));
//        } catch (AppException e) {
//            Assert.assertEquals(e.getErrorMessage(), expectedError, "Person creation failed!");
////            ConnectionService.manageTransaction(connection, false);
//        }
//        connection.close();
//    }
//
//    /* Dataprovider for negative testcase for create Person */
//
//    @DataProvider(name = "testCreateNegative")
//    private Object[][] testCreateNegativeValues() {
//
//        Address givenAddress1 = new Address("", "Chennai", 600096);
//        Address givenAddress2 = new Address("Thuraipakkam", "", 600096);
//        Address givenAddress3 = new Address("Thuraipakkam", "Chennai", 0);
//        Address givenAddress4 = new Address("Thuraipakkam", "Chennai", 600096);
//        Address givenAddress5 = new Address("Thuraipakkam", "Chennai", 600096);
//        Address givenAddress6 = new Address("Thuraipakkam", "Chennai", 600096);
//        Address givenAddress7 = new Address("Thuraipakkam", "Chennai", 600096);
//        Address givenAddress8 = new Address("Thuraipakkam", "Chennai", 600096);
//        Address givenAddress9 = new Address("Thuraipakkam", "Chennai", 600096);
//        Address givenAddress10 = new Address("Thuraipakkam", "Chennai", 600096);
//        Address givenAddress11 = new Address("Thuraipakkam", "Chennai", 600096);
//        Person givenPerson1 = new Person("Siva", "Ravi", "sivaravi@gmail.com", "2-10-1996", Timestamp.from(Instant.now()), givenAddress1);
//        Person givenPerson2 = new Person("Siva", "Siva", "sivaravi@gmail.com", "3-10-1996", Timestamp.from(Instant.now()), givenAddress2);
//        Person givenPerson3 = new Person("Siva", "Siva", "sivaravi@gmail.com", "4-10-1996", Timestamp.from(Instant.now()), givenAddress3);
//        Person givenPerson4 = new Person("", "Siva", "sivaravi@gmail.com", "5-10-1996", Timestamp.from(Instant.now()), givenAddress4);
//        Person givenPerson5 = new Person("Siva", "", "sivaravi@gmail.com", "6-10-1996", Timestamp.from(Instant.now()), givenAddress5);
//        Person givenPerson6 = new Person("Siva", "Siva", "", "7-10-1996", Timestamp.from(Instant.now()), givenAddress6);
//        Person givenPerson7 = new Person("Siva", "Siva", "sivaravi@gmail.com", "", Timestamp.from(Instant.now()), givenAddress7);
//        Person givenPerson8 = new Person("Siva", "Siva", "sivaravi2@gmail.com", "8-10-1996", Timestamp.from(Instant.now()), givenAddress8);
//        Person givenPerson9 = new Person("Siva", "Siva", "sivaravi@gmail.com", "9-10-1996", Timestamp.from(Instant.now()), givenAddress9);
//        Person givenPerson10 = new Person("Siva", "Siva", "sivaravi@gmail.com", "10-10-1996", null, givenAddress10);
//        Person givenPerson11 = new Person("Siva", "Ravi", "sivaravi@gmail.com", "1996-10-22", Timestamp.from(Instant.now()), givenAddress11);
//        String expectedError1 = "Street should not be empty";
//        String expectedError2 = "City should not be empty";
//        String expectedError3 = "Postal code should not be empty";
//        String expectedError4 = "First name should not be empty";
//        String expectedError5 = "Last name should not be empty";
//        String expectedError6 = "Email address should not be empty";
//        String expectedError7 = "Invalid date format";
//        String expectedError8 = "Duplicate Email address entry";
//        String expectedError9 = "First name and last name should not be same";
//        String expectedError10 = "Created date should not be empty";
//        return new Object[][] {
//                                { givenPerson1, expectedError1 },
//                                { givenPerson2, expectedError2 },
//                                { givenPerson3, expectedError3 },
//                                { givenPerson4, expectedError4 },
//                                { givenPerson5, expectedError5 },
//                                { givenPerson6, expectedError6 },
//                                { givenPerson7, expectedError7 },
//                                { givenPerson8, expectedError8 },
//                                { givenPerson9, expectedError9 },
//                                { givenPerson10, expectedError10 },
//                                { givenPerson11, expectedError7 }
//        };
//    }
//
//    /* Positive testcase for update Person */
//
//    @Test(dataProvider = "testUpdatePositive", priority = 5, enabled = true)
//    private void testUpdate_positive(Person givenPerson, Person expectedPerson) throws SQLException, IOException {
//
//        try {
//            connection = ConnectionService.getConnection();
//            boolean createdPerson = personService.update(connection, givenPerson);
//            Assert.assertEquals(createdPerson, true, "Success!");
//        } catch (AppException e) {
//            Assert.fail(e.getErrorMessage());
//        }
//        connection.close();
//    }
//
//    /* Dataprovider for positive testcase for update Person */
//
//    @DataProvider(name = "testUpdatePositive")
//    private Object[][] testUpdatePositiveValues() {
//
//        Address givenAddress = new Address(2, "Saravanampatti", "Coimbatore", 641102);
//        Person givenPerson = new Person(2, "Balaji", "Mohanan", "balajimohanan@gmail.com", "15-08-1996", givenAddress);
//        Person expectedPerson = givenPerson;
//        return new Object[][] {
//                                { givenPerson, expectedPerson }
//        };
//    }
//
//    /* Negative testcase for update Person */
//
//    @Test(dataProvider = "testUpdateNegative", priority = 6, enabled = true)
//    private void testUpdate_negative(Person givenPerson, String expectedError) throws SQLException, IOException {
//
//        try {
//            connection = ConnectionService.getConnection();
//            boolean updatedPerson = personService.update(connection, givenPerson);
//            Assert.fail(String.format(INPUTS_MSG, updatedPerson, true, "Testcase failed!") +
//                        String.format(ASSERT_FAIL_MSG, givenPerson.getId(), updatedPerson));
//        } catch (AppException e) {
//            Assert.assertEquals(e.getErrorMessage(), expectedError, "Person updation failed!");
//        }
//        connection.close();
//    }
//
//    /* Dataprovider for negative testcase for update Person */
//
//    @DataProvider(name = "testUpdateNegative")
//    private Object[][] testUpdateNegativeValues() {
//
//        Address givenAddress1 = new Address("Ammapet", "Salem", 636003);
//        Address givenAddress2 = new Address("", "Salem", 0);
//        Address givenAddress3 = new Address("Ammapet", "", 0);
//        Address givenAddress4 = new Address("Ammapet", "Salem", 0);
//        Person givenPerson1 = new Person(5, "Sriram", "Narayanan", "sriram@gmail.com", "15-08-1996", givenAddress1);
//        Person givenPerson2 = new Person(2, "Sriram", "Narayanan", "sriram@gmail.com", "15-08-1996", givenAddress2);
//        Person givenPerson3 = new Person(2, "Sriram", "Narayanan", "sriram@gmail.com", "15-08-1996", givenAddress3);
//        Person givenPerson4 = new Person(2, "Sriram", "Narayanan", "sriram@gmail.com", "15-08-1996", givenAddress4);
//        Person givenPerson5 = new Person(2, "", "Narayanan", "sriram@gmail.com", "15-08-1996", givenAddress1);
//        Person givenPerson6 = new Person(2, "Sriram", "", "sriram@gmail.com", "15-08-1996", givenAddress1);
//        Person givenPerson7 = new Person(2, "Sriram", "Narayanan", "", "15-08-1996", givenAddress1);
//        Person givenPerson8 = new Person(2, "Sriram", "Narayanan", "sriram@gmail.com", "", givenAddress1);
//        Person givenPerson9 = new Person(2, "Sriram", "Narayanan", "sriram@gmail.com", "1996-08-16", givenAddress1);
//        Person givenPerson10 = new Person(2, "Sriram", "Narayanan", "sriram2@gmail.com", "15-08-1996", givenAddress1);
//        Person givenPerson11 = new Person(2, "Sriram", "Sriram", "sriram@gmail.com", "12-06-2000", givenAddress1);
//        String expectedError1 = "No entry found in the given ID";
//        String expectedError2 = "Street should not be empty";
//        String expectedError3 = "City should not be empty";
//        String expectedError4 = "Postal code should not be empty";
//        String expectedError5 = "First name should not be empty";
//        String expectedError6 = "Last name should not be empty";
//        String expectedError7 = "Email address should not be empty";
//        String expectedError8 = "Invalid date format";
//        String expectedError9 = "Duplicate Email address entry";
//        String expectedError10 = "First name and last name should not be same";
//        return new Object[][] {
//                                { givenPerson1, expectedError1 },
//                                { givenPerson2, expectedError2 },
//                                { givenPerson3, expectedError3 },
//                                { givenPerson4, expectedError4 },
//                                { givenPerson5, expectedError5 },
//                                { givenPerson6, expectedError6 },
//                                { givenPerson7, expectedError7 },
//                                { givenPerson8, expectedError8 },
//                                { givenPerson9, expectedError8 },
//                                { givenPerson10, expectedError9 },
//                                { givenPerson11, expectedError10 }
//        };
//    }
//
//    /* Positive testcase for read Person */
//
//    @Test(dataProvider = "testReadPositive", priority = 3, enabled = true)
//    private void testRead_positive(long personId) throws SQLException, IOException {
//
//        try {
//            connection = ConnectionService.getConnection();
//            Person retrivedPerson = personService.read(connection, personId, true);
//            Assert.assertEquals(retrivedPerson.getEmail(), "sriram2@gmail.com", "Success!");
//        } catch (AppException e) {
//            Assert.fail(e.getErrorMessage());
//        }
//        connection.close();
//    }
//
//    /* Dataprovider for positive testcase for read Person */
//
//    @DataProvider(name = "testReadPositive")
//    private Object[][] testReadPositiveValues() {
//
//        long personId = 2;
//        return new Object[][] {
//                                { personId }
//        };
//    }
//
//    /* Negative testcase for read Person */
//
//    @Test(dataProvider = "testReadNegative", priority = 4, enabled = true)
//    private void testRead_negative(long personId, String expectedError) throws SQLException, IOException {
//
//        try {
//            connection = ConnectionService.getConnection();
//            Person retrivedPerson = personService.read(connection, personId, true);
//            Assert.fail(String.format(INPUTS_MSG, retrivedPerson.getFirstName(), "Testcase failed!"));
//        } catch (AppException e) {
//            Assert.assertEquals(e.getErrorMessage(), expectedError, "Person read failed!");
//        }
//        connection.close();
//    }
//
//    /* Dataprovider for negative testcase for read Person */
//
//    @DataProvider(name = "testReadNegative")
//    private Object[][] testReadNegativeValues() {
//
//        long personId = 15;
//        String expectedError = "No entry found in the given ID";
//        return new Object[][] {
//                                { personId, expectedError }
//        };
//    }
//
//    /* Positive testcase for delete Person */
//
//    @Test(dataProvider = "testDeletePositive", priority = 7, enabled = false)
//    private void testDelete_positive(Person person) throws SQLException, IOException {
//
//        try {
//            connection = ConnectionService.getConnection();
//            boolean personDetails = personService.delete(connection, person);
//            Assert.assertEquals(personDetails, true, "Success!");
//        } catch (AppException e) {
//            Assert.fail(String.format(INPUTS_MSG, person.getId(), 2) +
//                    String.format(ASSERT_FAIL_MSG, 2, e.getErrorMessage()));
//        }
//        connection.close();
//    }
//
//    /* Dataprovider for positive testcase for delete Person */
//
//    @DataProvider(name = "testDeletePositive")
//    private Object[][] testDeletePositiveValues() {
//
//        Person givenPerson = new Person(2);
//        return new Object[][] {
//                                { givenPerson }
//        };
//    }
//
//    /* Positive testcase for delete Person */
//
//    @Test(dataProvider = "testDeleteNegative", priority = 8, enabled = true)
//    private void testDelete_negative(Person person, String experctedError) throws SQLException, IOException {
//
//        try {
//            connection = ConnectionService.getConnection();
//            boolean personDetails = personService.delete(connection, person);
//            Assert.fail(String.format(INPUTS_MSG, personDetails, 4) +
//                    String.format(ASSERT_FAIL_MSG, 4, experctedError));
//        } catch (AppException e) {
//            Assert.assertEquals(e.getErrorMessage(), experctedError, "Success!");
//        }
//        connection.close();
//    }
//
//    /* Dataprovider for positive testcase for delete Person */
//
//    @DataProvider(name = "testDeleteNegative")
//    private Object[][] testDeleteNegativeValues() {
//
//        Person givenPerson = new Person(15);
//        String expectedError = "No entry found in the given ID";
//        return new Object[][] {
//                                { givenPerson, expectedError }
//        };
//    }
//
////    @AfterClass
////    private void removeData() throws SQLException, IOException {
////
////        Connection con = ConnectionService.getConnection();
////
////        con.createStatement().execute("DELETE from service_person");
////        con.createStatement().execute("ALTER TABLE service_person AUTO_INCREMENT = 1");
////        con.createStatement().execute("DELETE from service_address");
////        con.createStatement().execute("ALTER TABLE service_address AUTO_INCREMENT = 1");
//////        ConnectionService.manageTransaction(con, true);
////        con.close();
////    }
}
