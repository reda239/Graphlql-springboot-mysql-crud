package com.api.graphql;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

interface ClientRepository extends ReactiveCrudRepository<Client, Long> {
    Flux<Client> findByNom(String nom);

}
