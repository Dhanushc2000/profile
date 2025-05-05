package org.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * AWS Lambda handler for processing profile data and storing it in an RDS database.
 * This class implements the RequestHandler interface to handle Lambda function requests.
 */
public class ProfileLambdaHandler implements RequestHandler<ProfileData, String> {

    /**
     * Handles the Lambda function request.
     *
     * @param profileData ProfileData objects to be processed.
     * @param context     AWS Lambda context object.
     * @return A string indicating the result of the operation.
     */
    @Override
    public String handleRequest(ProfileData profileData, Context context) {
            try {
                // Validate input
                if (profileData.getEmail() == null || profileData.getEmail().isEmpty()) {
                    return "Invalid input: Email is required.";
                } else if (profileData.getPhone() == null || profileData.getPhone().isEmpty()) {
                    return "Invalid input: Phone number is required.";
                } else if (profileData.getAddress() == null || profileData.getAddress().isEmpty()) {
                    return "Invalid input: Address is required.";
                } else if (profileData.getVehicleId() == null || profileData.getVehicleId().isEmpty()) {
                    return "Invalid input: Vehicle ID is required.";
                }

                // Store data in RDS
                storeProfileData(profileData);
                context.getLogger().log("Profile data stored successfully: " + profileData);

                // Storing the vehicle data in linked_system table in RDS
                storeLinkedSystemData(profileData.getVehicleId());
                context.getLogger().log("LinkedSystem data stored successfully for vehicle ID: " + profileData.getVehicleId());

                return "Profile and LinkedSystem data stored successfully.";
            } catch (Exception e) {
                context.getLogger().log("Error: " + e.getMessage());
                return "Failed to process profile data.";
            }
    }

    /**
     * Stores profile data in the RDS database.
     *
     * @param profileData The ProfileData object to be stored.
     * @throws Exception If an error occurs while storing the data.
     */
    private void storeProfileData(ProfileData profileData) throws Exception {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC driver

            String jdbcUrl = System.getenv("RDS_JDBC_URL"); // Retrieve RDS endpoint and database name from environment variables
            String username = System.getenv("RDS_USERNAME"); // Retrieve RDS username from environment variables
            String password = System.getenv("RDS_PASSWORD"); // Retrieve RDS password from environment variables

            String insertQuery = "INSERT INTO profiles (email, phone, address, vehicle_id) VALUES (?, ?, ?, ?)";

            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, profileData.getEmail());
                preparedStatement.setString(2, profileData.getPhone());
                preparedStatement.setString(3, profileData.getAddress());
                preparedStatement.setString(4, profileData.getVehicleId());
                preparedStatement.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            throw new Exception("MySQL JDBC Driver not found.", e);
        } catch (Exception e) {
            throw new Exception("Error while storing profile data: " + e.getMessage(), e);
        }
    }

    /**
     * Stores linked system data in the RDS database.
     *
     * @param vehicleId The vehicle ID to be stored.
     * @throws Exception If an error occurs while storing the data.
     */
    private void storeLinkedSystemData(String vehicleId) throws Exception {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC driver

            String jdbcUrl = System.getenv("RDS_JDBC_URL"); // Retrieve RDS endpoint and database name from environment variables
            String username = System.getenv("RDS_USERNAME"); // Retrieve RDS username from environment variables
            String password = System.getenv("RDS_PASSWORD"); // Retrieve RDS password from environment variables

            String insertQuery = "INSERT INTO linked_system (vehicle_id) VALUES (?)";

            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, vehicleId);
                preparedStatement.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            throw new Exception("MySQL JDBC Driver not found.", e);
        } catch (Exception e) {
            throw new Exception("Error while storing linked system data: " + e.getMessage(), e);
        }
    }
}