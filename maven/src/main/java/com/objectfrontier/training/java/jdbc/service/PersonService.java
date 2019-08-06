package com.objectfrontier.training.java.jdbc.service;

import static com.objectfrontier.training.java.jdbc.statements.PersonStatements.CREATE_QUERY;
import static com.objectfrontier.training.java.jdbc.statements.PersonStatements.DELETE_QUERY;
import static com.objectfrontier.training.java.jdbc.statements.PersonStatements.GET_ADDRESS_ID;
import static com.objectfrontier.training.java.jdbc.statements.PersonStatements.READ_ALL_QUERY;
import static com.objectfrontier.training.java.jdbc.statements.PersonStatements.READ_QUERY;
import static com.objectfrontier.training.java.jdbc.statements.PersonStatements.UPDATE_QUERY;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.objectfrontier.training.java.jdbc.AppException;
import com.objectfrontier.training.java.jdbc.ExceptionCode;
import com.objectfrontier.training.java.jdbc.model.Address;
import com.objectfrontier.training.java.jdbc.model.Person;

public class PersonService {

    public Person create(Connection connection, Person person) throws AppException, SQLException {

        if (connection != null) {

            AddressService addressService = new AddressService();
            person.setAddress(addressService.create(connection, person.getAddress()));

            PreparedStatement createPersonQuery = connection.prepareStatement(CREATE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            createPersonQuery.setString(1, person.getFirstName());
            createPersonQuery.setString(2, person.getLastName());
            createPersonQuery.setString(3, person.getEmail());
            createPersonQuery.setLong(4, person.getAddress().getId());
            try {
                String[] dob = person.getBirthDate().split("-");
                String birthDate = dob[2] + "-" + dob[1] + "-" + dob[0];
                createPersonQuery.setDate(5, Date.valueOf(birthDate));
            } catch (Exception e) {
                throw new AppException(ExceptionCode.INVALID_DATE_FORMAT);
            }
            createPersonQuery.setTimestamp(6, person.getCreatedDate());

            if (person.getId() == 0) {
                if ((person.getCreatedDate() == null) || ((person.getCreatedDate()).equals(null))) {
                    throw new AppException(ExceptionCode.CREATED_DATE_EMPTY);
                }
                validatePerson(connection, person);
                createPersonQuery.executeUpdate();
            } else {
                throw new AppException(ExceptionCode.ID_AUTOINCREMENT);
            }
        }
        return person;
    }

    public Person read(Connection connection, long personId, boolean includeAddress) throws SQLException, AppException {

        Person retrievedPerson = new Person();

        if (connection != null) {

            PersonService personService = new PersonService();

            if (includeAddress) {

                AddressService addressService = new AddressService();
                retrievedPerson = personService.readPerson(connection, personId);
                retrievedPerson.setAddress(addressService.read(connection, retrievedPerson.getAddress().getId()));
            } else {
                retrievedPerson = personService.readPerson(connection, personId);
            }
            return retrievedPerson;
        } else {
            return null;
        }
    }

    public Person readPerson(Connection connection, long personId) throws SQLException, AppException {

        Address address = new Address();
        Person person = new Person();

        PreparedStatement readPersonQuery = connection.prepareStatement(READ_QUERY);
        readPersonQuery.setLong(1, personId);
        ResultSet result = readPersonQuery.executeQuery();

        if (result.next() != false) {
            person.setFirstName(result.getString("first_name"));
            person.setLastName(result.getString("last_name"));
            person.setEmail(result.getString("email"));
            address.setId(result.getLong("address_id"));
            person.setAddress(address);
            person.setBirthDate(result.getString("birth_date"));
            person.setCreatedDate(result.getTimestamp("created_date"));
            readPersonQuery.close();
        } else {
            readPersonQuery.close();
            throw new AppException(ExceptionCode.ID_NOT_FOUND);
        }
        return person;
    }

    public List<Person> readAllPerson(Connection connection) throws SQLException {

        List<Person> personDetails = new ArrayList<>();
        AddressService addressService = new AddressService();

        PreparedStatement readAllPersonQuery = connection.prepareStatement(READ_ALL_QUERY);
        ResultSet result = readAllPersonQuery.executeQuery();

        while (result.next()) {

            Person retrievedPerson = new Person();
            Address address = new Address();
            retrievedPerson.setId(result.getLong("id"));
            retrievedPerson.setFirstName(result.getString("first_name"));
            retrievedPerson.setLastName(result.getString("last_name"));
            retrievedPerson.setEmail(result.getString("email"));
            retrievedPerson.setBirthDate(result.getString("birth_date"));
            address.setId(result.getLong("address_id"));
            retrievedPerson.setAddress(address);
            retrievedPerson.setCreatedDate(result.getTimestamp("created_date"));
            retrievedPerson.setAddress(addressService.read(connection, retrievedPerson.getAddress().getId()));
            personDetails.add(retrievedPerson);
        }
        readAllPersonQuery.close();
        return personDetails;
    }

    public boolean update(Connection connection, Person person) throws SQLException, AppException {

        if (connection != null) {

            AddressService addressService = new AddressService();
            boolean isValidPerson = false;

            PreparedStatement getAddressQuery = connection.prepareStatement(GET_ADDRESS_ID);
            getAddressQuery.setLong(1, person.getId());
            ResultSet createdAddress = getAddressQuery.executeQuery();

            if (createdAddress.next()) {
                person.getAddress().setId(createdAddress.getLong(1));
            }

            PreparedStatement updatePersonQuery = connection.prepareStatement(UPDATE_QUERY);
            updatePersonQuery.setString(1, person.getFirstName());
            updatePersonQuery.setString(2, person.getLastName());
            updatePersonQuery.setString(3, person.getEmail());
            updatePersonQuery.setLong(4, person.getAddress().getId());
            try {
                String[] dob = person.getBirthDate().split("-");
                String birthDate = dob[2] + "-" + dob[1] + "-" + dob[0];
                updatePersonQuery.setDate(5, Date.valueOf(birthDate));
            } catch (Exception e) {
                throw new AppException(ExceptionCode.INVALID_DATE_FORMAT);
            }
            updatePersonQuery.setLong(6, person.getId());
            addressService.update(connection, person.getAddress());

            List<Person> personDetails = readAllPerson(connection);
            for (Person personDetail : personDetails) {
                if (personDetail.getId() == person.getId()) {
                    isValidPerson = true;
                }
            }

            if (isValidPerson) {
                validatePerson(connection, person);
                updatePersonQuery.executeUpdate();
                getAddressQuery.close();
                updatePersonQuery.close();
                return true;
            } else {
                updatePersonQuery.close();
                throw new AppException(ExceptionCode.ID_NOT_FOUND);
            }
        } else {
            return false;
        }
    }

    public boolean delete(Connection connection, Person person) throws SQLException, AppException {

        PreparedStatement deletePersonQuery = connection.prepareStatement(DELETE_QUERY);
        deletePersonQuery.setLong(1, person.getId());
        int result = deletePersonQuery.executeUpdate();

        if (result == 1) {
//            AddressService addressService = new AddressService();
//            addressService.delete(connection, person.getAddress());
            deletePersonQuery.close();
            return true;
        } else {
            deletePersonQuery.close();
            throw new AppException(ExceptionCode.ID_NOT_FOUND);
        }
    }

    public boolean validatePerson(Connection connection, Person person) throws SQLException {

        List<Person> personDetails = readAllPerson(connection);

        if (person.getFirstName() == person.getLastName()) {
            throw new AppException(ExceptionCode.SAME_FIRST_NAME_LAST_NAME);
        } else if ((person.getFirstName().trim().length() <= 0) || ((person.getFirstName()).equals(null))) {
            throw new AppException(ExceptionCode.FIRST_NAME_EMPTY);
        } else if ((person.getLastName().trim().length() <= 0) || ((person.getLastName()).equals(null))) {
            throw new AppException(ExceptionCode.LAST_NAME_EMPTY);
        } else if ((person.getEmail().trim().length() <= 0) || ((person.getEmail()).equals(null))) {
            throw new AppException(ExceptionCode.EMAIL_EMPTY);
        } else if ((person.getBirthDate().trim().length() <= 0) || ((person.getBirthDate()).equals(null))) {
            throw new AppException(ExceptionCode.BIRTH_DATE_EMPTY);
        }

        for (Person personDetail : personDetails) {
            if ((personDetail.getEmail()).equals(person.getEmail())) {
                throw new AppException(ExceptionCode.EMAIL_ADDRESS_DUPLICATE);
            }
        }

//        StringBuilder query = new StringBuilder().append("SELECT Count(*)      ")
//                                                 .append("FROM service_person  ")
//                                                 .append("WHERE first_name = ? ")
//                                                 .append("AND last_name = ?    ");
//        PreparedStatement duplicateUserQuery = connection.prepareStatement(query.toString());
//        duplicateUserQuery.setString(1, '"' + person.getFirstName() + '"');
//        duplicateUserQuery.setString(2, '"' + person.getLastName() + '"');
//        ResultSet result = duplicateUserQuery.executeQuery();
//        if (result.next()) {
//            System.out.println(result.getInt(1) + "siva");
//        }

        return true;
    }
}
