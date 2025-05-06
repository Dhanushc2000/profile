package org.example.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.example.model.ProfileData;
import org.example.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ProfileLambdaHandlerTest {

    private ProfileLambdaHandler handler;

    @Mock
    private Context mockContext;

    @Mock
    private LambdaLogger mockLogger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new ProfileLambdaHandler();
        when(mockContext.getLogger()).thenReturn(mockLogger);
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

    @Test
    public void testHandleRequest_ExceptionHandling() {
        // Arrange
        ProfileData profileData = new ProfileData();
        profileData.setEmail("test@example.com");
        profileData.setPhone("123-456-7890");
        profileData.setAddress("123 Main St");
        profileData.setVehicleId("BH123456");

        // Mock ProfileService to throw an exception
        try (MockedStatic<ProfileService> mockedStatic = mockStatic(ProfileService.class)) {
            mockedStatic.when(() -> ProfileService.storeProfileData(Mockito.any(ProfileData.class)))
                    .thenThrow(new RuntimeException());
        }

        // Act
        String result = handler.handleRequest(profileData, mockContext);

        // Assert
        assertEquals("Failed to process profile data.", result);
    }

    @Test
    public void testHandleRequest_Success() throws Exception {
        // Arrange
        ProfileData profileData = new ProfileData();
        profileData.setEmail("test@example.com");
        profileData.setPhone("123-456-7890");
        profileData.setAddress("123 Main St");
        profileData.setVehicleId("BH123456");

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Mock DriverManager connection
        mockStatic(DriverManager.class);
        when(DriverManager.getConnection(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(mockConnection);
        // Create a valid config file
        createValidConfigFile();

        // Act
        ProfileService.storeProfileData(profileData);

        // Assert
        Mockito.verify(mockPreparedStatement, Mockito.times(1)).executeUpdate();
        ProfileService.storeProfileData(profileData);

        ProfileService.storeLinkedSystemData(profileData.getVehicleId());

        // Act
        String result = handler.handleRequest(profileData, mockContext);

        // Assert
        assertEquals("Profile and LinkedSystem data stored successfully.", result);
        verify(mockLogger).log("Profile data stored successfully: " + profileData);
        verify(mockLogger).log("LinkedSystem data stored successfully for vehicle ID: " + profileData.getVehicleId());
    }

    private void createValidConfigFile() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("RDS_JDBC_URL", "jdbc:mysql://localhost:3306/testdb");
        properties.setProperty("RDS_USERNAME", "testuser");
        properties.setProperty("RDS_PASSWORD", "testpassword");
        try (FileOutputStream output = new FileOutputStream("src/main/resources/config.properties")) {
            properties.store(output, null);
        }
    }
}
