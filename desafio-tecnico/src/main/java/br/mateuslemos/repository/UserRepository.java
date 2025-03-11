package br.mateuslemos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.mateuslemos.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
