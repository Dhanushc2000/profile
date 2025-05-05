package org.example.service;

import org.example.exception.ProfileDataException;
import org.example.model.ProfileData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ProfileService {

    public ProfileService() {
        // Private constructor to prevent instantiation
    }

    public static void storeProfileData(ProfileData profileData) throws ProfileDataException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = System.getenv("RDS_JDBC_URL");
            String username = System.getenv("RDS_USERNAME");
            String password = System.getenv("RDS_PASSWORD");

            String insertQuery = "INSERT INTO profiles (email, phone, address, vehicle_id) VALUES (?, ?, ?, ?)";

            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, profileData.getEmail());
                preparedStatement.setString(2, profileData.getPhone());
                preparedStatement.setString(3, profileData.getAddress());
                preparedStatement.setString(4, profileData.getVehicleId());
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            throw new ProfileDataException("Error while storing profile data: " + e.getMessage(), e);
        }
    }

    public void storeLinkedSystemData(String vehicleId) throws ProfileDataException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = System.getenv("RDS_JDBC_URL");
            String username = System.getenv("RDS_USERNAME");
            String password = System.getenv("RDS_PASSWORD");

            String insertQuery = "INSERT INTO linked_system (vehicle_id) VALUES (?)";

            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, vehicleId);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            throw new ProfileDataException("Error while storing linked system data: " + e.getMessage(), e);
        }
    }
}