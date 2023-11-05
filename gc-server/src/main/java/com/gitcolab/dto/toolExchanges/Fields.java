package com.gitcolab.dto.toolExchanges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fields {
        private Description description;
        private IssueType issuetype;
        private Project project;
        private Reporter reporter;
        private String summary;
}
