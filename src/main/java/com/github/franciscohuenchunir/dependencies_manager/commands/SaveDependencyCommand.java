package com.github.franciscohuenchunir.dependencies_manager.commands;

import com.github.franciscohuenchunir.dependencies_manager.models.DependencyModel;
import com.github.franciscohuenchunir.dependencies_manager.services.DependencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;
import java.util.List;

@ShellComponent
@ShellCommandGroup(value = "Add Dependencies")
public class SaveDependencyCommand {
    private final DependencyService service;
    @Autowired
    public SaveDependencyCommand(DependencyService service) {
        this.service = service;
    }

    @ShellMethod(key = {"save dependency", "save -d"}, group = "Add Dependencies")
    void saveDependency() throws IOException {
        List<DependencyModel> model = service.getDependencies();
        model.add(new DependencyModel(
                "org.",
                "spring-",
                List.of("3."),
                "compile"
        ));
        service.saveDependencies(model);
        service.loadDependenciesAndGroups();
    }

}
