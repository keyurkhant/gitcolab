package com.gitcolab.dto.toolExchanges.request;

import com.gitcolab.dto.toolExchanges.*;
import com.gitcolab.dto.toolExchanges.request.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CreateIssueRequestTest {

    @Test
    public void testCreateIssueRequestNoArgsConstructor() {
        
        CreateIssueRequest createIssueRequest = new CreateIssueRequest();

        
        
        assertEquals(null, createIssueRequest.getFields());
    }

    @Test
    public void testCreateIssueRequestAllArgsConstructor() {
        // Arrange
        ContentItem[] contentItems = {
                new ContentItem("Sample text", "Type1"),
                new ContentItem("Another text", "Type2")
        };
        Content[] contents={
                new Content(contentItems,"Type1")
        };

        Description description = new Description(contents, "Type1", 1);
        IssueType issueType = new IssueType("Bug");
        Reporter reporter = new Reporter("R123");
        Project project = new Project("P456");
        String summary = "Sample summary";

        Fields fields = new Fields(description, issueType, project, reporter, summary);

        
        CreateIssueRequest createIssueRequest = new CreateIssueRequest(fields);

        
        
        assertEquals(fields, createIssueRequest.getFields());
    }

    @Test
    public void testCreateIssueRequestEquality() {
        // Arrange
        ContentItem[] contentItems = {
                new ContentItem("Sample text", "Type1"),
                new ContentItem("Another text", "Type2")
        };
        Content[] contents={
                new Content(contentItems,"Type1")
        };

        Description description = new Description(contents, "Type1", 1);
        IssueType issueType = new IssueType("Bug");
        Reporter reporter = new Reporter("R123");
        Project project = new Project("P456");
        String summary = "Sample summary";

        Description description2 = new Description(contents, "Type1", 1);
        IssueType issueType2 = new IssueType("Bug");
        Reporter reporter2 = new Reporter("R123");
        Project project2 = new Project("P456");
        String summary2 = "Sample summary";

        
        Fields fields1 = new Fields(description, issueType, project, reporter, summary);

        ContentItem[] contentItems2 = {
                new ContentItem("Sample text", "Type1"),
                new ContentItem("Another text", "Type2")
        };


        Fields fields2 = new Fields(description2, issueType2, project2, reporter2, summary2);

        ContentItem[] contentItems3 = {
                new ContentItem("Different text", "Type3")
        };
        Content[] contents3={
                new Content(contentItems3,"Type3")
        };
        Description description3 = new Description(contents3, "Type2", 2);
        IssueType issueType3 = new IssueType("Feature");
        Reporter reporter3 = new Reporter("R987");
        Project project3 = new Project("P789");
        String summary3 = "Another summary";

        Fields fields3 = new Fields(description3, issueType3, project3, reporter3, summary3);

        
        CreateIssueRequest createIssueRequest1 = new CreateIssueRequest(fields1);
        CreateIssueRequest createIssueRequest2 = new CreateIssueRequest(fields2);
        CreateIssueRequest createIssueRequest3 = new CreateIssueRequest(fields3);

        
        assertEquals(createIssueRequest1, createIssueRequest2); // Two CreateIssueRequest objects with the same fields are considered equal
        assertNotEquals(createIssueRequest1, createIssueRequest3); // Two CreateIssueRequest objects with different fields are not equal
        assertNotEquals(createIssueRequest2, createIssueRequest3); // Two CreateIssueRequest objects with different fields are not equal
    }
}
