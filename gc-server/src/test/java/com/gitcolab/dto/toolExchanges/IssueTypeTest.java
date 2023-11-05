package com.gitcolab.dto.toolExchanges;

import com.gitcolab.dto.toolExchanges.IssueType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class IssueTypeTest {

    @Test
    public void testNoArgsConstructor() {
        
        IssueType issueType = new IssueType();


        
        assertEquals(null, issueType.getId());
    }

    @Test
    public void testAllArgsConstructor() {
        
        String id = "12345";

        
        IssueType issueType = new IssueType(id);

      
        
        assertEquals(id, issueType.getId());
    }

    @Test
    public void testEquality() {
        
        String id1 = "12345";
        String id2 = "12345";
        String id3 = "54321";

        
        IssueType issueType1 = new IssueType(id1);
        IssueType issueType2 = new IssueType(id2);
        IssueType issueType3 = new IssueType(id3);

        
        assertEquals(issueType1, issueType2); // Two issue types with the same id are considered equal
        assertNotEquals(issueType1, issueType3); // Two issue types with different ids are not equal
        assertNotEquals(issueType2, issueType3); // Two issue types with different ids are not equal
    }
}
