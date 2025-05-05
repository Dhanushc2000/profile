package org.example.handler;

import com.amazonaws.services.lambda.runtime.Context;
import org.example.model.ProfileData;
import org.example.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileLambdaHandlerTest {

    private ProfileLambdaHandler handler;
    private Context mockContext;

    @BeforeEach
    public void setUp() {
        handler = new ProfileLambdaHandler();
        mockContext = Mockito.mock(Context.class);
    }

    @Test
    public void testHandleRequest_InvalidEmail() {
        // Arrange
        ProfileData profileData = new ProfileData();
        profileData.setEmail("");
        profileData.setPhone("123-456-7890");
        profileData.setAddress("123 Main St");
        profileData.setVehicleId("BH123456");

        // Act
        String result = handler.handleRequest(profileData, mockContext);

        // Assert
        assertEquals("Invalid input: Email is required.", result);
    }

    @Test
    public void testHandleRequest_InvalidPhone() {
        // Arrange
        ProfileData profileData = new ProfileData();
        profileData.setEmail("test@example.com");
        profileData.setPhone("");
        profileData.setAddress("123 Main St");
        profileData.setVehicleId("BH123456");

        // Act
        String result = handler.handleRequest(profileData, mockContext);

        // Assert
        assertEquals("Invalid input: Phone number is required.", result);
    }

    @Test
    public void testHandleRequest_InvalidAddress() {
        // Arrange
        ProfileData profileData = new ProfileData();
        profileData.setEmail("test@example.com");
        profileData.setPhone("123-456-7890");
        profileData.setAddress("");
        profileData.setVehicleId("BH123456");

        // Act
        String result = handler.handleRequest(profileData, mockContext);

        // Assert
        assertEquals("Invalid input: Address is required.", result);
    }

    @Test
    public void testHandleRequest_InvalidVehicleId() {
        // Arrange
        ProfileData profileData = new ProfileData();
        profileData.setEmail("test@example.com");
        profileData.setPhone("123-456-7890");
        profileData.setAddress("123 Main St");
        profileData.setVehicleId("");

        // Act
        String result = handler.handleRequest(profileData, mockContext);

        // Assert
        assertEquals("Invalid input: Vehicle ID is required.", result);
    }
}
