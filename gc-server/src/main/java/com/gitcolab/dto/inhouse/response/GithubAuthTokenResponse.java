package com.gitcolab.dto.inhouse.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GithubAuthTokenResponse {
    private String token;
    private String type;

}
