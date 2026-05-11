package com.card.repository;

import com.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    
    Optional<Card> findByCardNumber(String cardNumber);
    
    List<Card> findByCardholderNameContainingIgnoreCase(String cardholderName);
    
    List<Card> findByCardType(Card.CardType cardType);
    
    List<Card> findByIsActive(Boolean isActive);
    
    @Query("SELECT c FROM Card c WHERE c.cardholderName = :cardholderName AND c.isActive = true")
    List<Card> findActiveCardsByCardholderName(@Param("cardholderName") String cardholderName);
    
    @Query("SELECT c FROM Card c WHERE c.cardType = :cardType AND c.isActive = true")
    List<Card> findActiveCardsByType(@Param("cardType") Card.CardType cardType);
    
    boolean existsByCardNumber(String cardNumber);
    
    @Query("SELECT COUNT(c) FROM Card c WHERE c.cardholderName = :cardholderName AND c.isActive = true")
    long countActiveCardsByCardholderName(@Param("cardholderName") String cardholderName);
}
