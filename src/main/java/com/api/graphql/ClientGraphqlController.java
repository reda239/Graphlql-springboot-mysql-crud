package com.api.graphql;

import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.stream.Stream;

@Controller
class ClientGraphqlController {

    private final ClientRepository repository;

    ClientGraphqlController(ClientRepository repository) {
        this.repository = repository;
    }

    @QueryMapping
    Flux<Client> clientByNom(@Argument String nom) {
        return this.repository.findByNom(nom);
    }

    @QueryMapping
    Flux<Client> clients() {
        return this.repository.findAll();
    }

    @SchemaMapping(typeName = "Client")
    Flux<Commande> commandes(Client client) {
        var commandes = new ArrayList<Commande>();
        for (var id = 1; id <= (Math.random() * 100); id++) {
            commandes.add(new Commande((long) id, client.id()));
        }
        //data fetchers
        return Flux.fromIterable(commandes);
    }

    @MutationMapping
    Mono<Client> ajouterClient(@Argument String nom, @Argument String prenom) {
        return this.repository.save(new Client(null, nom, prenom));
    }

    @MutationMapping
    Mono<Void> supprimerClient(@Argument Long id) {

        return this.repository.deleteById(id);
    }
     @SubscriptionMapping
    Flux<ClientEvent> clientEvents(@Argument Long id){
        return  this.repository.findById(id).flatMapMany(client->{
            var stream = Stream.generate(()->new ClientEvent(client,
                    Math.random()>.5 ? ClientEventType.DELETED : ClientEventType.UPDATED));
            return  Flux.fromStream(stream);
        }).delayElements(Duration.ofSeconds(1))
                .take(10);
     }
}
