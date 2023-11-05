package com.gitcolab.dto.inhouse.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class SendProjectRequest {
    @NotBlank
    private String projectOwner;

    @NotBlank
    private String repositoryName;

}
