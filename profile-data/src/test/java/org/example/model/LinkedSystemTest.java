package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkedSystemTest {

    @Test
    public void testLinkedSystem() {
        LinkedSystem linkedSystem = new LinkedSystem();

        linkedSystem.setVehicleId("KA123456");

        assertEquals("KA123456", linkedSystem.getVehicleId());
        assertEquals("LinkedSystem{vehicleId='KA123456'}", linkedSystem.toString());
    }
}
