package com.gitcolab.dao;

import static org.junit.jupiter.api.Assertions.*;

import com.gitcolab.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class DAOTest<T> {

    private DAO<T> dao;
    private T entity;

    @BeforeEach
    void setUp() {
        dao = mock(DAO.class);
        entity = (T) mock(User.class);
    }

    @Test
    void testGet() {
        long id = 1L;
        when(dao.get(id)).thenReturn(Optional.of(entity));

        Optional<T> result = dao.get(id);

        assertTrue(result.isPresent());
        assertEquals(entity, result.get());

        verify(dao).get(id);
    }

    @Test
    void testGetNotFound() {
        long id = 1L;
        when(dao.get(id)).thenReturn(Optional.empty());

        Optional<T> result = dao.get(id);

        assertFalse(result.isPresent());

        verify(dao).get(id);
    }

    @Test
    void testSave() {
        when(dao.save(entity)).thenReturn(1);

        int result = dao.save(entity);

        assertEquals(1, result);

        verify(dao).save(entity);
    }

    @Test
    void testUpdate() {
        when(dao.update(entity)).thenReturn(1);

        int result = dao.update(entity);

        assertEquals(1, result);

        verify(dao).update(entity);
    }

    @Test
    void testDelete() {
        long id = 1L;

        assertDoesNotThrow(() -> dao.delete(id));

        verify(dao).delete(id);
    }
}

