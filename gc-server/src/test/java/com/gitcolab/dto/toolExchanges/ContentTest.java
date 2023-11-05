package com.gitcolab.dto.toolExchanges;


import com.gitcolab.dto.toolExchanges.Content;
import com.gitcolab.dto.toolExchanges.ContentItem;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ContentTest {

    @Test
    public void testNoArgsConstructor() {
        
        Content content = new Content();

        
        
        assertThrows(NullPointerException.class,()->{
            int length=content.getContent().length;
        });
//        assertThrows(NullPointerException.class,);
        assertEquals(null, content.getType());
    }

    @Test
    public void testAllArgsConstructor() {
        // Arrange
        ContentItem[] items = {
                new ContentItem("item1", "type1"),
                new ContentItem("item2", "type2"),
                new ContentItem("item3", "type3")
        };
        String type = "type1";

        
        Content content = new Content(items, type);

        
        
        assertArrayEquals(items, content.getContent());
        assertEquals(type, content.getType());
    }

    @Test
    public void testEquality() {
        // Arrange
        ContentItem[] items1 = {
                new ContentItem("item1", "type1"),
                new ContentItem("item2", "type2"),
                new ContentItem("item3", "type3")
        };
        String type1 = "type1";

        ContentItem[] items2 = {
                new ContentItem("item1", "type1"),
                new ContentItem("item2", "type2"),
                new ContentItem("item3", "type3")
        };
        String type2 = "type1";

        ContentItem[] items3 = {
                new ContentItem("item1", "type1"),
                new ContentItem("item2", "type2")
        };
        String type3 = "type2";

        
        Content content1 = new Content(items1, type1);
        Content content2 = new Content(items2, type2);
        Content content3 = new Content(items3, type3);

        
        assertEquals(content1, content2); // Two content objects with the same items and type are considered equal
        assertNotEquals(content1, content3); // Two content objects with different items are not equal
        assertNotEquals(content2, content3); // Two content objects with different type are not equal
    }
}
