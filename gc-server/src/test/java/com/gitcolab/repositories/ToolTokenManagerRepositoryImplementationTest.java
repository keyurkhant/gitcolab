package com.gitcolab.repositories;

import com.gitcolab.dao.ToolTokenManagerDAO;
import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.ToolTokenManager;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
class ToolTokenManagerRepositoryImplementationTest {
    @InjectMocks
    private ToolTokenManagerRepositoryImplementation integrationRepository;

    @Mock
    private ToolTokenManagerDAO integrationDAO;

    @Test
    public void testSave() {
        ToolTokenManager integration = new ToolTokenManager();
        integration.setType(EnumIntegrationType.GITHUB);
        integration.setToken("some-token");
        integration.setUserId(456L);

        when(integrationDAO.save(integration)).thenReturn(1);

        int result = integrationRepository.save(integration);

        assertEquals(1, result);
        Mockito.verify(integrationDAO, times(1)).save(integration);
    }

    @Test
    public void testUpdate() {
        ToolTokenManager integration = new ToolTokenManager();
        integration.setType(EnumIntegrationType.GITHUB);
        integration.setToken("updated-token");
        integration.setUserId(456L);

        when(integrationDAO.update(integration)).thenReturn(1);

        int result = integrationRepository.update(integration);

        assertEquals(1, result);
        Mockito.verify(integrationDAO, times(1)).update(integration);
    }

    @Test
    public void testGetByEmail() {
        String email = "test@example.com";
        EnumIntegrationType integrationType = EnumIntegrationType.GITHUB;
        ToolTokenManager integration = new ToolTokenManager();
        integration.setType(integrationType);

        when(integrationDAO.getByEmail(email, integrationType)).thenReturn(Optional.of(integration));

        Optional<ToolTokenManager> result = integrationRepository.getByEmail(email, integrationType);

        assertTrue(result.isPresent());
        assertEquals(integrationType, result.get().getType());
        Mockito.verify(integrationDAO, times(1)).getByEmail(email, integrationType);
    }

    @Test
    public void testGetByEmail_NonExistingIntegration() {
        String email = "test@example.com";
        EnumIntegrationType integrationType = EnumIntegrationType.GITHUB;

        when(integrationDAO.getByEmail(email, integrationType)).thenReturn(Optional.empty());

        Optional<ToolTokenManager> result = integrationRepository.getByEmail(email, integrationType);

        assertFalse(result.isPresent());
        Mockito.verify(integrationDAO, times(1)).getByEmail(email, integrationType);
    }

//    @Test
//    public void testGetByUsername() {
////        String username = "testuser";
////
////        when(integrationDAO.getByUsername(username)).thenReturn(Optional.of(new Integration()));
////
////        Optional<Integration> result = integrationRepository.getByUsername(username);
//
//    }
}