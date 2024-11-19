package com.github.franciscohuenchunir.dependencies_manager.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface IDependency<T> {
    T searchDependency(final String artifact, final String group);
    T searchDependency(final String artifact);
}
