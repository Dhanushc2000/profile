package org.example.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileDataExceptionTest {

    @Test
    public void testProfileDataExceptionWithMessage() {
        String errorMessage = "Profile data error occurred";
        ProfileDataException exception = new ProfileDataException(errorMessage, null);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testProfileDataExceptionWithMessageAndCause() {
        String errorMessage = "Profile data error occurred";
        Throwable cause = new RuntimeException("Root cause");
        ProfileDataException exception = new ProfileDataException(errorMessage, cause);

        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}