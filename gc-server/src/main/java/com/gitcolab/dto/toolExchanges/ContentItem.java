package com.gitcolab.dto.toolExchanges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentItem {
    private String text;
    private String type;
}
