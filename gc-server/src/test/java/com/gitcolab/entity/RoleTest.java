package com.gitcolab.entity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoleTest {
    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
    }

    @AfterEach
    void tearDown() {
        role = null;
    }

    @Test
    void testConstructorAndGetters() {
        Integer id = 1;
        EnumRole name = EnumRole.ROLE_ADMIN;

        role.setId(id);
        role.setName(name);

        Assertions.assertEquals(id, role.getId());
        Assertions.assertEquals(name, role.getName());
    }

    @Test
    void testSetters() {
        Integer id = 2;
        EnumRole name = EnumRole.ROLE_USER;

        role.setId(id);
        role.setName(name);

        Assertions.assertEquals(id, role.getId());
        Assertions.assertEquals(name, role.getName());
    }
}
