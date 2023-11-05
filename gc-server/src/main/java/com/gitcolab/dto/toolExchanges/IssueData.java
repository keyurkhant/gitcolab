package com.gitcolab.dto.toolExchanges;

import com.gitcolab.dto.toolExchanges.AtlassianUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IssueData {
    private String url;
    private String title;
    private String body;
    private AtlassianUser user;
}
