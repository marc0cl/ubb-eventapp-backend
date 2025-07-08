package com.ubb.eventappbackend.repository;

import com.ubb.eventappbackend.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("""
            select t from Token t inner join t.user u
            where u.id = :userId and (t.expired = false or t.revoked = false)
            """)
    List<Token> findAllValidTokenByUser(String userId);

    Optional<Token> findByAccessToken(String token);
}
