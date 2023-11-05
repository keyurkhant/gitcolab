package com.gitcolab.dto.toolExchanges.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAccessTokenRequest {
    String grant_type;
    String client_id;
    String client_secret;
    String code;
    String redirect_uri;
}
