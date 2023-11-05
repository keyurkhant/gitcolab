package com.gitcolab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    private long id;
    private User user;
    private String token;
    private Instant expiryDate;
}
