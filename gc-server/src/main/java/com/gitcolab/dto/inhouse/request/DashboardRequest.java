package com.gitcolab.dto.inhouse.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DashboardRequest {
    private String githubAuthToken;
    private String userId;
    private String username;
}
