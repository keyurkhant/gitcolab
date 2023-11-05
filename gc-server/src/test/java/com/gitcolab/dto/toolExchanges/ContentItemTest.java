package com.gitcolab.dto.toolExchanges;

import com.gitcolab.dto.toolExchanges.ContentItem;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContentItemTest {
    @Test
    public void testNoArgsConstructor() {
        
        ContentItem item = new ContentItem();

        
        
        assertEquals(null, item.getText());
        assertEquals(null, item.getType());
    }

    @Test
    public void testAllArgsConstructor() {
        
        String text = "This is some text.";
        String type = "Type1";

        
        ContentItem item = new ContentItem(text, type);

        
        
        assertEquals(text, item.getText());
        assertEquals(type, item.getType());
    }

    @Test
    public void testEquality() {
        
        String text1 = "Text 1";
        String type1 = "Type1";

        String text2 = "Text 2";
        String type2 = "Type2";

        
        ContentItem item1 = new ContentItem(text1, type1);
        ContentItem item2 = new ContentItem(text1, type1);
        ContentItem item3 = new ContentItem(text2, type2);

        
        assertEquals(item1, item2); // Two content items with the same text and type are considered equal
        assertNotEquals(item1, item3); // Two content items with different text are not equal
        assertNotEquals(item2, item3); // Two content items with different type are not equal
    }
}