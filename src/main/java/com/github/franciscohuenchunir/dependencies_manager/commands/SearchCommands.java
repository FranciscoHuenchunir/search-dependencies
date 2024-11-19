package com.github.franciscohuenchunir.dependencies_manager.commands;

import com.github.franciscohuenchunir.dependencies_manager.models.DependencyModel;
import com.github.franciscohuenchunir.dependencies_manager.models.MavenResponseModel;
import com.github.franciscohuenchunir.dependencies_manager.services.DependencyService;
import com.github.franciscohuenchunir.dependencies_manager.services.ResponseMavenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ShellComponent
@ShellCommandGroup(value = "Search Dependencies")
public class SearchCommands {
    private final DependencyService dependencyService;
    private final ResponseMavenService responseMavenService;

    @Autowired
    public SearchCommands(DependencyService dependencyService, ResponseMavenService responseMavenService) {
        this.dependencyService = dependencyService;
        this.responseMavenService = responseMavenService;
    }

    // volver la respuesta asincrona
    @ShellMethod(value = "Search for dependencies in Maven Central or the local JSON file.")
    String search(
            @ShellOption(defaultValue = "", help = "Artifact name to search for (e.g., 'spring-boot-starter').") String artifact,
            @ShellOption(defaultValue = "", help = "Group ID to search for (e.g., 'org.springframework').") String group)
    {
        List<DependencyModel> model = new ArrayList<>();
        if(!artifact.isBlank() && group.isBlank()){
            Optional<MavenResponseModel> response = responseMavenService.searchDependency(artifact);

            for(var item : response.get().response().docs()){
                model.add(new DependencyModel(item.groupId(), item.artifactId(), List.of(item.latestVersion()), "compile" ));

            }

            return model.toString();
        }
        return dependencyService.searchDependency(artifact, group).toString();
    }
    @ShellMethod(key = {"search flow", "search -f"}, value = "Searches dependencies by flow")
    String searchByFlow() {
        return responseMavenService.searchDependency("spring").toString();
    }
}
