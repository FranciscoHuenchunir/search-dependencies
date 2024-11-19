package com.github.franciscohuenchunir.dependencies_manager.services;

import com.github.franciscohuenchunir.dependencies_manager.models.MavenResponseModel;
import com.github.franciscohuenchunir.dependencies_manager.repository.IDependency;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ResponseMavenService implements IDependency<Optional<MavenResponseModel>> {                                              // a:spring-*
    private static final String URL_MAVEN = "https://search.maven.org/solrsearch/select?wt=json&q=";

    @Override
    public Optional<MavenResponseModel> searchDependency(String artifact, String group) {
        String params = String.format("a:%s* AND g:%s*", artifact, group);

        String url = String.format(URL_MAVEN, params);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<MavenResponseModel> response = restTemplate.getForEntity(url, MavenResponseModel.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<MavenResponseModel> searchDependency(final String artifact) {
        String params = String.format("a:%s*", artifact);
        String url = String.format(URL_MAVEN, params);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<MavenResponseModel> response = restTemplate.getForEntity(url, MavenResponseModel.class);
        return Optional.ofNullable(response.getBody());
    }

}
