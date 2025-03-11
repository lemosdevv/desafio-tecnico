package br.mateuslemos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.mateuslemos.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
