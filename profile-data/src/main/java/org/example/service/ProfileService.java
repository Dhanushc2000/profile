package org.example.service;

import org.example.exception.ProfileDataException;
import org.example.model.ProfileData;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Service class for handling profile data operations.
 * This class provides methods to store profile data in an RDS database.
 */
public class ProfileService {

    /**
     * Stores the profile data in the RDS database.
     *
     * @param profileData The `ProfileData` object containing the profile information to be stored.
     * @throws ProfileDataException If an error occurs while storing the profile data in the database.
     */
    public static void storeProfileData(ProfileData profileData) throws ProfileDataException{
        final Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(input);
            String jdbcUrl = properties.getProperty("RDS_JDBC_URL");
            String username = properties.getProperty("RDS_USERNAME");
            String password = properties.getProperty("RDS_PASSWORD");

            String insertQuery = "INSERT INTO profiles (email, phone, address, vehicle_id) VALUES (?, ?, ?, ?)";

            try(Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, profileData.getEmail());
                preparedStatement.setString(2, profileData.getPhone());
                preparedStatement.setString(3, profileData.getAddress());
                preparedStatement.setString(4, profileData.getVehicleId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ProfileDataException("SQL error while storing profile data: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new ProfileDataException("Configuration file not found: " + e.getMessage(), e);
        }
    }

    /**
     * Stores the vehicle ID in the linked_system table in the RDS database.
     *
     * @param vehicleId The vehicle ID to be stored.
     * @throws ProfileDataException If an error occurs while storing the linked system data in the database.
     */
    public static void storeLinkedSystemData(String vehicleId) throws ProfileDataException {
        final Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(input);
            String jdbcUrl = properties.getProperty("RDS_JDBC_URL");
            String username = properties.getProperty("RDS_USERNAME");
            String password = properties.getProperty("RDS_PASSWORD");

            String insertQuery = "INSERT INTO linked_system (vehicle_id) VALUES (?)";

            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, vehicleId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ProfileDataException("SQL error while storing linked system data: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new ProfileDataException("Configuration file not found: " + e.getMessage(), e);
        }
    }
}
