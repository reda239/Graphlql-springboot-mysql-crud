package com.api.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;


@SpringBootApplication
@EnableR2dbcRepositories
public class GraphQlApplication {

    public static void main(String[] args) {

        SpringApplication.run(GraphQlApplication.class, args);

    }

}

@Controller
class ClientGraphqlController{

    private  final  ClientRepository repository;

    ClientGraphqlController(ClientRepository repository){
        this.repository = repository;
    }
    @QueryMapping
    Flux<Client> clientByNom(@Argument String nom){
        return  this.repository.findByNom(nom);
    }

    @QueryMapping
    Flux<Client> clients(){
        return this.repository.findAll();
    }
    @SchemaMapping(typeName = "Client")
    Flux<Commande> commandes(Client client){
        var commandes = new ArrayList<Commande>();
        for (var id=1;id<=(Math.random()*100);id++){
            commandes.add(new Commande((long) id,client.id()));
        }
        //data fetchers
        return  Flux.fromIterable(commandes);
    }
    @MutationMapping
    Mono<Client> ajouterClient(@Argument String nom,@Argument String prenom){
        return this.repository.save(new Client(null,nom,prenom));
    }
    @MutationMapping
    Mono<Void> supprimerClient(@Argument Long id){

        return  this.repository.deleteById(id);
    }

}


interface  ClientRepository extends ReactiveCrudRepository<Client,Long>{
   Flux<Client> findByNom(String nom);

}
record Commande(@JsonProperty("id") @Id Long id,@JsonProperty("id_client")  Long idClient){}
record Client(@JsonProperty("id") @Id Long id,@JsonProperty("nom") String nom,@JsonProperty("prenom") String prenom){}