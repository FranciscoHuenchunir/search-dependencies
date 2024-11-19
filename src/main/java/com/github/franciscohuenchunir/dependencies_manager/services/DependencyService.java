package com.github.franciscohuenchunir.dependencies_manager.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.franciscohuenchunir.dependencies_manager.models.DependencyModel;
import com.github.franciscohuenchunir.dependencies_manager.models.MavenResponseModel;
import com.github.franciscohuenchunir.dependencies_manager.repository.IDependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DependencyService implements IDependency<List<DependencyModel>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DependencyService.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String MAVEN_DEP_FOLDER = System.getProperty("user.home") + "/.dep-repo";
    private static final Path PATH = Path.of(MAVEN_DEP_FOLDER);
    private static final String DEPENDENCY_JSON = "/dependencies.json";

    private List<DependencyModel> jsonDependencies = null;

    @Override
    public List<DependencyModel> searchDependency(final String artifact, final String group){

        return jsonDependencies.stream()
                .filter(item ->
                        item.artifactId().contains(artifact) ||
                        item.groupId().contains(group))
                .toList();
    }

    @Override
    public List<DependencyModel> searchDependency(String artifact) {
        return jsonDependencies.stream()
                .filter(item ->
                        item.artifactId().contains(artifact))
                .toList();
    }

    public void loadDependenciesAndGroups(){
        try {
            File file = new File(PATH+DEPENDENCY_JSON);jsonDependencies = new ArrayList<>(
                    Arrays.asList(OBJECT_MAPPER.readValue(file, DependencyModel[].class))
            );
        }catch (IOException ex){
            LOGGER.error(ex.getMessage());
        }
    }

    public List<DependencyModel> getDependencies() {
        return jsonDependencies;
    }

    public void saveDependencies(final List<DependencyModel> model) {
        try {
            File file = new File(PATH+DEPENDENCY_JSON);
            OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(file, model);
        }catch (IOException ex){
            LOGGER.error(ex.getMessage());
        }
    }
}
