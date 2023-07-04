package com.example.tamozhpenies.clientSum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientSumRepo extends JpaRepository<ClientSum, Long> {
    @Query("SELECT c FROM ClientSum c WHERE c.user = :userId")
    List<ClientSum> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM ClientSum c WHERE c.user.username = :username")
    List<ClientSum> findAllByUsername(@Param("username") String username);
}
