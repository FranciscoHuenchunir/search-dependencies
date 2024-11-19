package com.github.franciscohuenchunir.dependencies_manager.config;

import com.github.franciscohuenchunir.dependencies_manager.models.DependencyModel;
import com.github.franciscohuenchunir.dependencies_manager.services.DependencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.List;

@Configuration
public class LoadData {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadData.class);
    private static final String MAVEN_DEP_FOLDER = System.getProperty("user.home") + "/.dep-repo";

    private List<DependencyModel> data() {
        return List.of(
                new DependencyModel(
                        "org.springframework.boot",
                        "spring-boot-starter",
                        List.of("3.1.0"),
                        "compile"
                )
        );
    }
    @Bean
    CommandLineRunner initData(DependencyService service) {
        Path path = Path.of(MAVEN_DEP_FOLDER);
        try {
            if (Files.exists(path)) {
                service.loadDependenciesAndGroups();
                return args -> LOGGER.debug("load data");
            }
            Files.createDirectory(path, PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------")));

            service.saveDependencies(data());
            service.loadDependenciesAndGroups();

            return args -> LOGGER.debug("save directory");
        } catch (IOException ex) {
            return args -> LOGGER.error(ex.getMessage());
        }
    }
}