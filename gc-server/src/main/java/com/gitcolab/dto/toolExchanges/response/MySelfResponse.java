package com.gitcolab.dto.toolExchanges.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MySelfResponse {
    private String self;
    private String accountId;
    private String accountType;
    private String emailAddress;
    private String displayName;
    private boolean active;
}
