package org.example.model;

/**
 * Represents a profile data entity with attributes such as email, phone, address, and vehicle ID.
 * This class provides getter and setter methods for each attribute and overrides the `toString` method
 * for a string representation of the object.
 */
public class ProfileData {
    /**
     * The email address of the profile.
     */
    private String email;

    /**
     * The phone number of the profile.
     */
    private String phone;

    /**
     * The address of the profile.
     */
    private String address;

    /**
     * The vehicle ID associated with the profile.
     */
    private String vehicleId;

    /**
     * Gets the email address of the profile.
     *
     * @return the email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the profile.
     *
     * @param email the email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number of the profile.
     *
     * @return the phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the profile.
     *
     * @param phone the phone number to set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the address of the profile.
     *
     * @return the address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the profile.
     *
     * @param address the address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the vehicle ID associated with the profile.
     *
     * @return the vehicle ID.
     */
    public String getVehicleId() {
        return vehicleId;
    }

    /**
     * Sets the vehicle ID associated with the profile.
     *
     * @param vehicleId the vehicle ID to set.
     */
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * Returns a string representation of the ProfileData object.
     *
     * @return a string containing the email, phone, address, and vehicle ID.
     */
    @Override
    public String toString() {
        return "ProfileData{" +
                "email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                '}';
    }
}