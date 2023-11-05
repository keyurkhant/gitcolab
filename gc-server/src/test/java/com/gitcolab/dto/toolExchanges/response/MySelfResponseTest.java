package com.gitcolab.dto.toolExchanges.response;

import com.gitcolab.dto.toolExchanges.response.MySelfResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class MySelfResponseTest {

    @Test
    public void testAllArgsConstructor() {
        
        String self = "/users/12345";
        String accountId = "A123";
        String accountType = "Regular";
        String emailAddress = "user@example.com";
        String displayName = "Example User"; // Updated user name
        boolean active = true;

        
        MySelfResponse response = new MySelfResponse(self, accountId, accountType, emailAddress, displayName, active);

        
        
        assertEquals(self, response.getSelf());
        assertEquals(accountId, response.getAccountId());
        assertEquals(accountType, response.getAccountType());
        assertEquals(emailAddress, response.getEmailAddress());
        assertEquals(displayName, response.getDisplayName()); 
        assertEquals(active, response.isActive());
    }

    @Test
    public void testSetSelf() throws NoSuchFieldException, IllegalAccessException {
        
        MySelfResponse mySelfResponse = new MySelfResponse();
        String self = "https://example.com/self";

        
        mySelfResponse.setSelf(self);

        
        Field selfField = MySelfResponse.class.getDeclaredField("self");
        selfField.setAccessible(true);
        assertEquals(self, selfField.get(mySelfResponse));
    }

    @Test
    public void testSetAccountId() throws NoSuchFieldException, IllegalAccessException {
        
        MySelfResponse mySelfResponse = new MySelfResponse();
        String accountId = "12345";

        
        mySelfResponse.setAccountId(accountId);

        
        Field accountIdField = MySelfResponse.class.getDeclaredField("accountId");
        accountIdField.setAccessible(true);
        assertEquals(accountId, accountIdField.get(mySelfResponse));
    }

    @Test
    public void testSetAccountType() throws NoSuchFieldException, IllegalAccessException {
        
        MySelfResponse mySelfResponse = new MySelfResponse();
        String accountType = "atlassian";

        
        mySelfResponse.setAccountType(accountType);

        
        Field accountTypeField = MySelfResponse.class.getDeclaredField("accountType");
        accountTypeField.setAccessible(true);
        assertEquals(accountType, accountTypeField.get(mySelfResponse));
    }

}
