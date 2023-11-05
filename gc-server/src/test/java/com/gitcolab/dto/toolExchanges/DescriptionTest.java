package com.gitcolab.dto.toolExchanges;


import com.gitcolab.dto.toolExchanges.Content;
import com.gitcolab.dto.toolExchanges.ContentItem;
import com.gitcolab.dto.toolExchanges.Description;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class DescriptionTest {

    @Test
    public void testNoArgsConstructor() {
        
        Description description = new Description();

        
        
        assertEquals(null, description.getContent());
        assertEquals(null, description.getType());
        assertEquals(0, description.getVersion());
    }

    @Test
    public void testAllArgsConstructor() {
        // Arrange
        ContentItem[] items = {
                new ContentItem("item1", "type1"),
                new ContentItem("item2", "type2"),
                new ContentItem("item3", "type3")
        };
        Content[] content = {
                new Content(items, "type1"),
                new Content(items, "type2"),
                new Content(items, "type3")
        };
        String type = "type1";
        int version = 1;

        
        Description description = new Description(content, type, version);

        
        
        assertArrayEquals(content, description.getContent());
        assertEquals(type, description.getType());
        assertEquals(version, description.getVersion());
    }

    @Test
    public void testEquality() {
        // Arrange
        ContentItem[] items1 = {
                new ContentItem("item1", "type1"),
                new ContentItem("item2", "type2"),
                new ContentItem("item3", "type3")
        };
        Content[] content1 = {
                new Content(items1, "type1"),
                new Content(items1, "type2"),
                new Content(items1, "type3")
        };
        String type1 = "type1";
        int version1 = 1;

        ContentItem[] items2 = {
                new ContentItem("item1", "type1"),
                new ContentItem("item2", "type2"),
                new ContentItem("item3", "type3")
        };
        Content[] content2 = {
                new Content(items2, "type1"),
                new Content(items2, "type2"),
                new Content(items2, "type3")
        };
        String type2 = "type1";
        int version2 = 1;

        ContentItem[] items3 = {
                new ContentItem("item1", "type1"),
                new ContentItem("item2", "type2")
        };
        Content[] content3 = {
                new Content(items3, "type1"),
                new Content(items3, "type2")
        };
        String type3 = "type2";
        int version3 = 2;

        
        Description description1 = new Description(content1, type1, version1);
        Description description2 = new Description(content2, type2, version2);
        Description description3 = new Description(content3, type3, version3);

        
        assertEquals(description1, description2); // Two descriptions with the same content, type, and version are considered equal
        assertNotEquals(description1, description3); // Two descriptions with different content are not equal
        assertNotEquals(description2, description3); // Two descriptions with different type and version are not equal
    }
}
