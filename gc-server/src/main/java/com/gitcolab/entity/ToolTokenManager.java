package com.gitcolab.entity;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ToolTokenManager {
    private long id;
    private EnumIntegrationType type;
    private String token;
    private long userId;

    private String typeString;

    public ToolTokenManager(EnumIntegrationType type, String token, long userId) {
        this.type = type;
        this.token = token;
        this.userId = userId;

        this.typeString = type.toString();
    }
}
