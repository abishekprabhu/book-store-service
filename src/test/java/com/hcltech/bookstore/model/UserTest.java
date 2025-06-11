package com.hcltech.bookstore.model;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        // User is abstract, so use a concrete subclass for testing
        class TestUser extends User {}

        TestUser user = new TestUser();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("Password@123");
        user.setRoles(Set.of("AUTHOR", "CUSTOMER"));

        assertEquals(1L, user.getId());
        assertEquals("testUser", user.getUsername());
        assertEquals("Password@123", user.getPassword());
        assertTrue(user.getRoles().contains("AUTHOR"));
        assertTrue(user.getRoles().contains("CUSTOMER"));
    }

    @Test
    void testAllArgsConstructor() {
        // User is abstract, so use a concrete subclass for testing
        class TestUser extends User {
            public TestUser(Long id, String username, String password, Set<String> roles) {
                super(id, username, password, roles);
            }
        }

        TestUser user = new TestUser(2L, "anotherUser", "Secret@456", Set.of("CUSTOMER"));

        assertEquals(2L, user.getId());
        assertEquals("anotherUser", user.getUsername());
        assertEquals("Secret@456", user.getPassword());
        assertEquals(Set.of("CUSTOMER"), user.getRoles());
    }

    @Test
    void testCustomConstructor() {
        // User is abstract, so use a concrete subclass for testing
        class TestUser extends User {
            public TestUser(String username, String password, Set<String> roles) {
                super(username, password, roles);
            }
        }

        TestUser user = new TestUser("user3", "Pass@789", Set.of("AUTHOR"));

        assertNull(user.getId());
        assertEquals("user3", user.getUsername());
        assertEquals("Pass@789", user.getPassword());
        assertEquals(Set.of("AUTHOR"), user.getRoles());
    }
}