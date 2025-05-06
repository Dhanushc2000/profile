package org.example.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.example.model.LinkedSystem;
import org.example.model.ProfileData;
import org.example.service.ProfileService;

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

            // Storing Profile data in RDS
            ProfileService.storeProfileData(profileData);
            context.getLogger().log("Profile data stored successfully: " + profileData);

            // Storing the vehicle data in linked_system table in RDS
            LinkedSystem linkedSystem = new LinkedSystem();
            linkedSystem.setVehicleId(profileData.getVehicleId());
            ProfileService.storeLinkedSystemData(linkedSystem.getVehicleId());
            context.getLogger().log("LinkedSystem data stored successfully for vehicle ID: " + linkedSystem.getVehicleId());

            return "Profile and LinkedSystem data stored successfully.";
        } catch (Exception e) {
            context.getLogger().log("Error processing profile data: " + e.getMessage());
            return "Failed to process profile data.";
        }
    }
}