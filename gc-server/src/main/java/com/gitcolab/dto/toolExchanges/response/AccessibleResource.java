package com.gitcolab.dto.toolExchanges.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccessibleResource {
    String id;
    String url;
    String name;
    String avatarUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessibleResource that = (AccessibleResource) o;
        return Objects.equals(id, that.id) ;
    }

   }
