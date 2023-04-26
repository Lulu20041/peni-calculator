package com.example.tamozhpenies.peni;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PeniRepo extends JpaRepository<Peni, Long> {
    @Query("SELECT p FROM Peni p WHERE p.user = :userId")
    List<Peni> findAllByUserId(@Param("userId") Long userId);
}
