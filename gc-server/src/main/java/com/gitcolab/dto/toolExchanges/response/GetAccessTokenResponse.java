package com.gitcolab.dto.toolExchanges.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAccessTokenResponse {
    String access_token;
    String expires_in;
    String scope;
}
