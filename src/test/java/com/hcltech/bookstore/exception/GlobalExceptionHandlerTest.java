package com.hcltech.bookstore.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;
    private WebRequest mockWebRequest;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
        mockWebRequest = mock(WebRequest.class);
        when(mockWebRequest.getDescription(false)).thenReturn("uri=/test/path");
    }

    @Test
    void handleCustomException() {
        String errorMessage = "This is a custom error message";
        CustomException ex = new CustomException(errorMessage);

        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleCustomException(ex, mockWebRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertInstanceOf(Map.class, responseEntity.getBody());

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();

        assertNotNull(responseBody.get("timestamp"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseBody.get("status"));
        assertEquals("ERROR", responseBody.get("error"));
        assertEquals(errorMessage, responseBody.get("message"));
        assertEquals("/test/path", responseBody.get("path"));
    }

    @Test
    void handleGlobalException() {
        String errorMessage = "Something went wrong internally";
        Exception ex = new Exception(errorMessage);

        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleGlobalException(ex, mockWebRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertInstanceOf(Map.class, responseEntity.getBody());

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();

        assertNotNull(responseBody.get("timestamp"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseBody.get("status"));
        assertEquals("INTERNAL_SERVER_ERROR", responseBody.get("error"));
        assertEquals(errorMessage, responseBody.get("message"));
        assertEquals("/test/path", responseBody.get("path"));
    }

    @Test
    void handleGlobalRuntimeException() {
        String errorMessage = "A generic runtime error occurred";
        RuntimeException ex = new RuntimeException(errorMessage);

        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleGlobalRuntimeException(ex, mockWebRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertInstanceOf(Map.class, responseEntity.getBody());

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();

        assertNotNull(responseBody.get("timestamp"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseBody.get("status"));
        assertEquals("INTERNAL_SERVER_ERROR", responseBody.get("error"));
        assertEquals(errorMessage, responseBody.get("message"));
        assertEquals("/test/path", responseBody.get("path"));
    }

    @Test
    void handleEntityNotFoundException() {
        String errorMessage = "User with ID 123 not found";
        EntityNotFoundException ex = new EntityNotFoundException(errorMessage);

        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleEntityNotFoundException(ex, mockWebRequest);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertInstanceOf(Map.class, responseEntity.getBody());

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();

        assertNotNull(responseBody.get("timestamp"));
        assertEquals(HttpStatus.NOT_FOUND.value(), responseBody.get("status"));
        assertEquals("Entity is not found", responseBody.get("error"));
        assertEquals(errorMessage, responseBody.get("message"));
        assertEquals("/test/path", responseBody.get("path"));
    }

    @Test
    void handleBookNotFoundException() {
        String errorMessage = "Book 'Effective Java' not found";
        BookNotFoundException ex = new BookNotFoundException(errorMessage);

        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleBookNotFoundException(ex, mockWebRequest);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertInstanceOf(Map.class, responseEntity.getBody());

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();

        assertNotNull(responseBody.get("timestamp"));
        assertEquals(HttpStatus.NOT_FOUND.value(), responseBody.get("status"));
        assertEquals("Book not found", responseBody.get("error"));
        assertEquals(errorMessage, responseBody.get("message"));
        assertEquals("/test/path", responseBody.get("path"));
    }

    @Test
    void handleAuthorNotFoundException() {
        String errorMessage = "Author 'Joshua Bloch' not found";
        AuthorNotFoundException ex = new AuthorNotFoundException(errorMessage);

        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleAuthorNotFoundException(ex, mockWebRequest);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertInstanceOf(Map.class, responseEntity.getBody());

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();

        assertNotNull(responseBody.get("timestamp"));
        assertEquals(HttpStatus.NOT_FOUND.value(), responseBody.get("status"));
        assertEquals("Author not found", responseBody.get("error"));
        assertEquals(errorMessage, responseBody.get("message"));
        assertEquals("/test/path", responseBody.get("path"));
    }

    @Test
    void handleUsernameNotFoundException() {
        String errorMessage = "User 'admin' does not exist";
        UsernameNotFoundException ex = new UsernameNotFoundException(errorMessage);

        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleUsernameNotFoundException(ex, mockWebRequest);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Map);

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();

        assertNotNull(responseBody.get("timestamp"));
        assertEquals(HttpStatus.FORBIDDEN.value(), responseBody.get("status"));
        assertEquals("You are not authorized", responseBody.get("error"));
        assertEquals(errorMessage, responseBody.get("message"));
        assertEquals("/test/path", responseBody.get("path"));
    }

    @Test
    void handleBadCredentialsException() {
        String errorMessage = "Incorrect password for user 'user1'";
        BadCredentialsException ex = new BadCredentialsException(errorMessage);

        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleBadCredentialsException(ex, mockWebRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Map);

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();

        assertNotNull(responseBody.get("timestamp"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseBody.get("status"));
        assertEquals("Check Username and Password", responseBody.get("error"));
        assertEquals(errorMessage, responseBody.get("message"));
        assertEquals("/test/path", responseBody.get("path"));
    }

    @Test
    void handleDuplicateEntityException() {
        String errorMessage = "Entity 'book' with title 'Spring in Action' already exists";
        DuplicateEntityException ex = new DuplicateEntityException(errorMessage);

        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleDuplicateEntityException(ex, mockWebRequest);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Map);

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();

        assertNotNull(responseBody.get("timestamp"));
        assertEquals(HttpStatus.CONFLICT.value(), responseBody.get("status"));
        assertEquals("CONFLICT", responseBody.get("error"));
        assertEquals(errorMessage, responseBody.get("message"));
        assertEquals("/test/path", responseBody.get("path"));
    }

    @Test
    void handleInsufficientStockException() {
        String errorMessage = "Not enough stock for item 'Pen', requested 10, available 5";
        InsufficientStockException ex = new InsufficientStockException(errorMessage);

        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleInsufficientStockException(ex, mockWebRequest);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Map);

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();

        assertNotNull(responseBody.get("timestamp"));
        assertEquals(HttpStatus.CONFLICT.value(), responseBody.get("status"));
        assertEquals("CONFLICT", responseBody.get("error"));
        assertEquals(errorMessage, responseBody.get("message"));
        assertEquals("/test/path", responseBody.get("path"));
    }
}
