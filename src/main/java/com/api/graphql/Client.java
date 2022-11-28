package com.api.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

record Client(@JsonProperty("id") @Id Long id, @JsonProperty("nom") String nom, @JsonProperty("prenom") String prenom) {
}
