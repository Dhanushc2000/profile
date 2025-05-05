package org.example.service;


import org.example.exception.ProfileDataException;
import org.example.model.ProfileData;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mockStatic;

public class ProfileServiceTest {

    @Test
    public void testStoreProfileData_Exception() {
        // Arrange
        ProfileData profileData = new ProfileData();
        profileData.setEmail("test@example.com");
        profileData.setPhone("1234567890");
        profileData.setAddress("123 Test Street");
        profileData.setVehicleId("V12345");

        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenThrow(new RuntimeException("Database connection error"));

            // Act & Assert
            assertThrows(ProfileDataException.class, () -> ProfileService.storeProfileData(profileData));
        }
    }
}
