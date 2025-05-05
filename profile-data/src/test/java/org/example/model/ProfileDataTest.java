package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileDataTest {

    @Test
    public void testProfileData() {
        ProfileData profileData = new ProfileData();

        profileData.setEmail("test@example.com");
        profileData.setPhone("1234567890");
        profileData.setAddress("123 Test Street");
        profileData.setVehicleId("V12345");

        assertEquals("test@example.com", profileData.getEmail());
        assertEquals("1234567890", profileData.getPhone());
        assertEquals("123 Test Street", profileData.getAddress());
        assertEquals("V12345", profileData.getVehicleId());
        String expected = "ProfileData{email='test@example.com', phone='1234567890', address='123 Test Street', vehicleId='V12345'}";
        assertEquals(expected, profileData.toString());
    }
}
