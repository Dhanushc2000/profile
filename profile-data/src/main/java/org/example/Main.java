package org.example;

import com.amazonaws.services.lambda.runtime.Context;

import java.util.Collections;

public class Main {

    /**
     * The main method to test the ProfileLambdaHandler.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Create an instance of ProfileLambdaHandler
        ProfileLambdaHandler handler = new ProfileLambdaHandler();

        // Create a sample ProfileData object
        ProfileData profileData = new ProfileData();
        profileData.setEmail("test@example.com");
        profileData.setPhone("123-456-7890");
        profileData.setAddress("123 Main St");
        profileData.setVehicleId("ABC123");

        // Create a mock Context (you can use a library or a simple implementation)
        Context context = new MockContext();

        // Call the handleRequest method
        String result = handler.handleRequest(Collections.singletonList(profileData), context);

        // Print the result
        System.out.println("Result: " + result);
    }
}
