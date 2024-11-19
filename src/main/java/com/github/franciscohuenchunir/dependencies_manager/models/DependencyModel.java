package com.github.franciscohuenchunir.dependencies_manager.models;

import java.util.List;

public record DependencyModel(
        String groupId,
        String artifactId,
        List<String> versions,
        String scopes){
}
