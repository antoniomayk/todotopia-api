package dev.antoniomayk.todotopia.api.common.repository;

import dev.antoniomayk.todotopia.api.common.entity.User;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactorCrudRepository<User, UUID> {

    Mono<User> findByEmail(@Nullable String email);

}
