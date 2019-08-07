// Define AddressService class for CRUD (create, read, readAll, update and delete) operations for Address using JDBC
  // -> use PreparedStatement appropriately

package com.objectfrontier.training.java.jdbc.service;

import static com.objectfrontier.training.java.jdbc.statements.AddressStatements.CREATE_QUERY;
import static com.objectfrontier.training.java.jdbc.statements.AddressStatements.DELETE_QUERY;
import static com.objectfrontier.training.java.jdbc.statements.AddressStatements.READ_ALL_QUERY;
import static com.objectfrontier.training.java.jdbc.statements.AddressStatements.READ_QUERY;
import static com.objectfrontier.training.java.jdbc.statements.AddressStatements.UPDATE_QUERY;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.objectfrontier.training.java.jdbc.AppException;
import com.objectfrontier.training.java.jdbc.ExceptionCode;
import com.objectfrontier.training.java.jdbc.model.Address;

public class AddressService {

    public Address create(Connection connection, Address address) throws SQLException, AppException {

//        Address createdAddress = new Address();
        int result;

        PreparedStatement createAddressQuery = connection.prepareStatement(CREATE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
        createAddressQuery.setString(1, address.getStreet());
        createAddressQuery.setString(2, address.getCity());
        createAddressQuery.setLong(3, address.getPostalCode());

        if (address.getId() == 0) {
            validateAddress(connection, address);
            result = createAddressQuery.executeUpdate();
        } else {
            throw new AppException(ExceptionCode.ID_AUTOINCREMENT);
        }

        if (result == 1) {
            ResultSet generatedAddress = createAddressQuery.getGeneratedKeys();
            generatedAddress.next();
            address.setId(generatedAddress.getLong(1));
//            createdAddress.setCity(address.getCity());
        }
        createAddressQuery.close();
        return address;
    }

    public Address read(Connection connection, long addressId) throws SQLException, AppException {

        Address address = new Address();
        PreparedStatement readAddressQuery = connection.prepareStatement(READ_QUERY);
        readAddressQuery.setLong(1, addressId);
        ResultSet result = readAddressQuery.executeQuery();

        if (result.next()) {
            address.setId(result.getLong("id"));
            address.setStreet(result.getString("street"));
            address.setCity(result.getString("city"));
            address.setPostalCode(result.getLong("postal_code"));
            readAddressQuery.close();
            return address;
        } else {
            readAddressQuery.close();
            throw new AppException(ExceptionCode.ID_NOT_FOUND);
        }
    }

    public List<Address> readAll(Connection connection) throws SQLException {

        List<Address> addressDetails = new ArrayList<>();

        PreparedStatement readAllAddressQuery = connection.prepareStatement(READ_ALL_QUERY);
        ResultSet result = readAllAddressQuery.executeQuery();

        while (result.next()) {

            Address retrievedAddress = new Address();
            retrievedAddress.setId(result.getLong("id"));
            retrievedAddress.setStreet(result.getString("street"));
            retrievedAddress.setCity(result.getString("city"));
            retrievedAddress.setPostalCode(result.getLong("postal_code"));
            addressDetails.add(retrievedAddress);
        }
        readAllAddressQuery.close();
        return addressDetails;
    }

    public boolean update(Connection connection, Address address) throws SQLException, AppException {

        boolean isValidAddress = false;

        PreparedStatement updateAddressQuery = connection.prepareStatement(UPDATE_QUERY);
        updateAddressQuery.setString(1, address.getStreet());
        updateAddressQuery.setString(2, address.getCity());
        updateAddressQuery.setLong(3, address.getPostalCode());
        updateAddressQuery.setLong(4, address.getId());

        List<Address> addressDetails = readAll(connection);
        for (Address addressDetail : addressDetails) {
            if (addressDetail.getId() == address.getId()) {
                isValidAddress = true;
            }
        }

        if (isValidAddress) {
            validateAddress(connection, address);
            updateAddressQuery.executeUpdate();
            updateAddressQuery.close();
            return true;
        } else {
            updateAddressQuery.close();
            throw new AppException(ExceptionCode.ID_NOT_FOUND);
        }
    }

    public boolean delete(Connection connection, Address address) throws SQLException, AppException {

        PreparedStatement deleteAddressQuery = connection.prepareStatement(DELETE_QUERY);
        deleteAddressQuery.setLong(1, address.getId());
        int result = deleteAddressQuery.executeUpdate();

        if (result == 1) {
            deleteAddressQuery.close();
            return true;
        } else {
            deleteAddressQuery.close();
            throw new AppException(ExceptionCode.ID_NOT_FOUND);
        }

    }

    public boolean validateAddress(Connection connection, Address address) throws SQLException {

        if ((address.getStreet().trim().length() <= 0) || ((address.getStreet()).equals(null))) {
            throw new AppException(ExceptionCode.STREET_NAME_EMPTY);
        } else if ((address.getCity().trim().length() <= 0) || ((address.getCity()).equals(null))) {
            throw new AppException(ExceptionCode.CITY_NAME_EMPTY);
        } else if ((address.getPostalCode() <= 0)) {
            throw new AppException(ExceptionCode.POSTAL_CODE_EMPTY);
        }
        return true;
    }

    public void search(Connection connection,String searchString, String[] column) throws SQLException {

        List<Address> addressDetails = new ArrayList<>();
        StringBuilder query = new StringBuilder().append("SELECT * FROM service_address ")
                                                 .append("WHERE                         ");

        for (int i = 0; i < column.length; i++) {

            if (i > 0) {
                query.append(" OR ");
            }

            switch (column[i]) {

            case "street" :
                query.append(" street LIKE ? ");
                break;
            case "city" :
                query.append(" city LIKE ? ");
                break;
            case "postalCode" :
                query.append(" postal_code LIKE ? ");
                break;
            }
        }

        if (column.length == 0) {

            query.append("street LIKE ?      ")
                 .append("OR                 ")
                 .append("city LIKE ?        ")
                 .append("OR                 ")
                 .append("postal_code LIKE ? ");
        }
        PreparedStatement searchQuery = connection.prepareStatement(query.toString());

        if (column.length > 0) {
            for (int i = 0; i < column.length; i++) {
                searchQuery.setString(i + 1, "%" + searchString + "%");
            }
        } else {
            searchQuery.setString(1, "%" + searchString + "%");
            searchQuery.setString(2, "%" + searchString + "%");
            searchQuery.setString(3, "%" + searchString + "%");
        }

        ResultSet result = searchQuery.executeQuery();

        while (result.next()) {

            Address retrievedAddress = new Address();
            retrievedAddress.setId(result.getLong("id"));
            retrievedAddress.setStreet(result.getString("street"));
            retrievedAddress.setCity(result.getString("city"));
            retrievedAddress.setPostalCode(result.getLong("postal_code"));
            addressDetails.add(retrievedAddress);
        }
        for (Address address : addressDetails) {
            System.out.println(address.getId() + " " + address.getStreet() + " " + address.getCity() + " " + address.getPostalCode());
        }
    }
}
