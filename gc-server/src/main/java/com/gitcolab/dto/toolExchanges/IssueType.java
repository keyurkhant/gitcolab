package com.gitcolab.dto.toolExchanges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueType {
    private String id;
    private String name;

    public IssueType(String id) {
        this.id = id;
    }
}