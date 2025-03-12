package br.mateuslemos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
public class Orders {

    private Long id;
    private String description;
    private Double value;
    private String status;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

}
