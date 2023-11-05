package com.gitcolab.dto.toolExchanges.response;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponse {
    private String self;
    private String id;
    private String key;
    private String name;

    public static ProjectResponse fromJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(json, ProjectResponse.class);
    }
    public String toString() {
        return new Gson().toJson(this);
    }
}
