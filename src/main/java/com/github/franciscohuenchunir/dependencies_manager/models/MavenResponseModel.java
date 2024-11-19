package com.github.franciscohuenchunir.dependencies_manager.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MavenResponseModel(
        @JsonProperty("response") Response response
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Response(
            @JsonProperty("docs") List<MavenArtifact> docs
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record MavenArtifact(
            @JsonProperty("id") String id,
            @JsonProperty("g") String groupId,
            @JsonProperty("a") String artifactId,
            @JsonProperty("latestVersion") String latestVersion
    ) {
    }
}