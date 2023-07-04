package com.example.tamozhpenies.peni;

import com.example.tamozhpenies.clientSum.ClientSum;
import com.example.tamozhpenies.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PeniRepo extends JpaRepository<Peni, Long> {
    @Query("SELECT p FROM Peni p WHERE p.user = :userId")
    List<Peni> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT p FROM Peni p WHERE p.user.username = :username")
    List<Peni> findAllByUsername(@Param("username") String username);

    void deleteByUser(User user);
}
