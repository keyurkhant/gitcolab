package com.gitcolab.dto;

import static org.junit.jupiter.api.Assertions.*;
import com.gitcolab.dto.UserDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDTOTest {

    @Test
    void getUsername_ShouldReturnUsername() {
        // Arrange
        String expectedUsername = "username";
        UserDTO userDTO = new UserDTO(expectedUsername);

        // Act
        String actualUsername = userDTO.getUsername();

        // Assert
        assertEquals(expectedUsername, actualUsername);
    }

    @Test
    void setUsername_ShouldSetUsername() {
        // Arrange
        String initialUsername = "username";
        String newUsername = "newusername";
        UserDTO userDTO = new UserDTO(initialUsername);

        // Act
        userDTO.setUsername(newUsername);

        // Assert
        assertEquals(newUsername, userDTO.getUsername());
    }
}
