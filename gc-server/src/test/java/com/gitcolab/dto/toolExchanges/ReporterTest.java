package com.gitcolab.dto.toolExchanges;

import static org.junit.jupiter.api.Assertions.*;

import com.gitcolab.dto.toolExchanges.Reporter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReporterTest {

    @Test
    public void testNoArgsConstructor() {
        
        Reporter reporter = new Reporter();

        
        
        assertEquals(null, reporter.getId());
    }

    @Test
    public void testAllArgsConstructor() {
        
        String id = "R123";

        
        Reporter reporter = new Reporter(id);

        
        
        assertEquals(id, reporter.getId());
    }

    @Test
    public void testEquality() {
        
        String id1 = "R123";
        String id2 = "R123";
        String id3 = "R456";

        
        Reporter reporter1 = new Reporter(id1);
        Reporter reporter2 = new Reporter(id2);
        Reporter reporter3 = new Reporter(id3);

        
        assertEquals(reporter1, reporter2); // Two reporters with the same id are considered equal
        assertNotEquals(reporter1, reporter3); // Two reporters with different ids are not equal
        assertNotEquals(reporter2, reporter3); // Two reporters with different ids are not equal
    }
}
