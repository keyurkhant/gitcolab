package com.gitcolab.dto.toolExchanges;


import com.gitcolab.dto.toolExchanges.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FieldsTest {
    

    @Test
    public void testFieldsNoArgsConstructor() {
        
        Fields fields = new Fields();

        
        
        assertNull(fields.getDescription());
        assertNull(fields.getIssuetype());
        assertNull(fields.getProject());
        assertNull(fields.getReporter());
        assertNull(fields.getSummary());
    }

    @Test
    public void testFieldsAllArgsConstructor() {
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

        
        
        assertEquals(description, fields.getDescription());
        assertEquals(issueType, fields.getIssuetype());
        assertEquals(project, fields.getProject());
        assertEquals(reporter, fields.getReporter());
        assertEquals(summary, fields.getSummary());
    }

    @Test
    public void testFieldsEquality() {
        // Arrange
        ContentItem[] contentItems1 = {
                new ContentItem("Sample text", "Type1"),
                new ContentItem("Another text", "Type2")
        };
        Content[] contents={
                new Content(contentItems1,"Type1")
        };
        Description description1 = new Description(contents, "Type1", 1);
        IssueType issueType1 = new IssueType("Bug");
        Reporter reporter1 = new Reporter("R123");
        Project project1 = new Project("P456");
        String summary1 = "Sample summary";

        ContentItem[] contentItems2 = {
                new ContentItem("Sample text", "Type1"),
                new ContentItem("Another text", "Type2")
        };
        Content[] contents2={
                new Content(contentItems2,"Type1")
        };
        Description description2 = new Description(contents2, "Type1", 1);
        IssueType issueType2 = new IssueType("Bug");
        Reporter reporter2 = new Reporter("R123");
        Project project2 = new Project("P456");
        String summary2 = "Sample summary";

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

        
        Fields fields1 = new Fields(description1, issueType1, project1, reporter1, summary1);
        Fields fields2 = new Fields(description2, issueType2, project2, reporter2, summary2);
        Fields fields3 = new Fields(description3, issueType3, project3, reporter3, summary3);

        
        assertEquals(fields1, fields2); // Two Fields objects with the same fields are considered equal
        assertNotEquals(fields1, fields3); // Two Fields objects with different fields are not equal
        assertNotEquals(fields2, fields3); // Two Fields objects with different fields are not equal
    }
}
