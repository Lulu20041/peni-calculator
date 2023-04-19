package com.example.tamozhpenies.clientSum;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientSumRepo extends JpaRepository<ClientSum, Long> {
    List<ClientSum> findByUserId(Long id);
}
