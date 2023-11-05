package com.gitcolab.dto.toolExchanges.request;

import com.gitcolab.dto.toolExchanges.request.ProjectCreateRequest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectCreateRequestTest {

    @Test
    public void testDefaultConstructor() {
        ProjectCreateRequest request = new ProjectCreateRequest();
        Assert.assertEquals("UNASSIGNED", request.getAssigneeType());
        Assert.assertNull(request.getDescription());
        Assert.assertNull(request.getKey());
        Assert.assertNull(request.getLeadAccountId());
        Assert.assertNull(request.getName());
//        Assert.assertEquals("com.atlassian.jira-core-project-templates:jira-core-simplified-process-control", request.getProjectTemplateKey());
//        Assert.assertEquals("software", request.get());
//        Assert.assertEquals("http://atlassian.com", request.getUrl());
    }

    @Test
    public void testAllArgsConstructor() {
        String assigneeType = "ASSIGNED";
        String description = "Test description";
        String key = "TEST-123";
        String leadAccountId = "123456";
        String name = "Test Project";

        ProjectCreateRequest request = new ProjectCreateRequest(description, key, leadAccountId, name);
//        Assert.assertEquals(assigneeType, request.getAssigneeType());
        Assert.assertEquals(description, request.getDescription());
        Assert.assertEquals(key, request.getKey());
        Assert.assertEquals(leadAccountId, request.getLeadAccountId());
        Assert.assertEquals(name, request.getName());
//        Assert.assertEquals("com.atlassian.jira-core-project-templates:jira-core-simplified-process-control", );
//        Assert.assertEquals("software", request.getProjectTypeKey());
//        Assert.assertEquals("http://atlassian.com", request.getUrl());
    }

    @Test
    public void testSetters() {
        ProjectCreateRequest request = new ProjectCreateRequest();

//        request.setAssigneeType("ASSIGNED");
        request.setDescription("Test description");
        request.setKey("TEST-123");
        request.setLeadAccountId("123456");
        request.setName("Test Project");

//        Assert.assertEquals("ASSIGNED", request.getAssigneeType());
        Assert.assertEquals("Test description", request.getDescription());
        Assert.assertEquals("TEST-123", request.getKey());
        Assert.assertEquals("123456", request.getLeadAccountId());
        Assert.assertEquals("Test Project", request.getName());
//        Assert.assertEquals("com.atlassian.jira-core-project-templates:jira-core-simplified-process-control", request.getProjectTemplateKey());
//        Assert.assertEquals("software", request.getProjectTypeKey());
//        Assert.assertEquals("http://atlassian.com", request.getUrl());
    }



}
