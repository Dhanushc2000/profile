package org.example.service;

import org.example.exception.ProfileDataException;
import org.example.model.ProfileData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProfileServiceTest {

    @Test
    public void testStoreProfileData_ConfigFileNotFound() {
        // Arrange

        ProfileData profileData = new ProfileData();
        deleteConfigFile();

        ProfileService profileService = new ProfileService();

        // Act & Assert
        ProfileDataException exception = assertThrows(ProfileDataException.class, () -> {
            profileService.storeProfileData(profileData);
        });
        assertEquals("Configuration file not found: src\\main\\resources\\config.properties (The system cannot find the file specified)", exception.getMessage());
    }

    @Test
    public void testStoreLinkedSystem_ConfigFileNotFound() {
        // Arrange
        deleteConfigFile();

        // Act & Assert
        ProfileDataException exception = assertThrows(ProfileDataException.class, () -> {
            ProfileService.storeLinkedSystemData("V12345");
        });
        assertEquals("Configuration file not found: src\\main\\resources\\config.properties (The system cannot find the file specified)", exception.getMessage());
    }

    private void deleteConfigFile() {
        java.io.File file = new java.io.File("src/main/resources/config.properties");
        if (file.exists()) {
            file.delete();
        }
    }
}