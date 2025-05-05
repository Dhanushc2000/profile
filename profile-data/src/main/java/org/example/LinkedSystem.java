package org.example;

/**
 * Represents a LinkedSystem entity with a vehicle ID.
 * This class provides getter and setter methods for the vehicle ID
 * and overrides the `toString` method for a string representation.
 */
public class LinkedSystem {

    /**
     * The vehicle ID associated with the LinkedSystem.
     */
    private String vehicleId;

    /**
     * Constructor to initialize the LinkedSystem object with a vehicle ID.
     *
     * @return vehicleId the vehicle ID to set.
     */
    public String getVehicleId() {
        return vehicleId;
    }

    /**
     * Sets the vehicle ID.
     *
     * @param vehicleId the vehicle ID to set.
     */
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * Returns a string representation of the LinkedSystem object.
     *
     * @return a string containing the vehicle ID.
     */
    @Override
    public String toString() {
        return "LinkedSystem{" +
                "vehicleId='" + vehicleId + '\'' +
                '}';
    }
}
