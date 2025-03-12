package br.mateuslemos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.mateuslemos.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> { 

}
