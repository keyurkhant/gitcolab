package com.gitcolab.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserInfoTest {

    @Test
    void testNoArgsConstructor() {
        UserInfo userInfo = new UserInfo();

        Assertions.assertNull(userInfo.getId());
        Assertions.assertNull(userInfo.getUsername());
        Assertions.assertNull(userInfo.getEmail());
    }

    @Test
    void testAllArgsConstructor() {
        Long id = 123L;
        String username = "manan123";
        String email = "manan@example.com";

        UserInfo userInfo = new UserInfo(id, username, email);

        Assertions.assertEquals(id, userInfo.getId());
        Assertions.assertEquals(username, userInfo.getUsername());
        Assertions.assertEquals(email, userInfo.getEmail());
    }

    // Add additional test cases if needed
}
