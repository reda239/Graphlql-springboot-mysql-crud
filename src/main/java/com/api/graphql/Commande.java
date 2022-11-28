package com.api.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

record Commande(@JsonProperty("id") @Id Long id, @JsonProperty("id_client") Long idClient) {
}
