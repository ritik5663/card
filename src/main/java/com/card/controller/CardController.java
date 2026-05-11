package com.card.controller;

import com.card.entity.Card;
import com.card.service.CardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@CrossOrigin(origins = "*")
public class CardController {
    
    @Autowired
    private CardService cardService;
    
    @PostMapping
    public ResponseEntity<Card> createCard(@Valid @RequestBody Card card) {
        Card createdCard = cardService.createCard(card);
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable Long id) {
        Card card = cardService.getCardById(id);
        return ResponseEntity.ok(card);
    }
    
    @GetMapping("/number/{cardNumber}")
    public ResponseEntity<Card> getCardByNumber(@PathVariable String cardNumber) {
        Card card = cardService.getCardByNumber(cardNumber);
        return ResponseEntity.ok(card);
    }
    
    @GetMapping
    public ResponseEntity<List<Card>> getAllCards() {
        List<Card> cards = cardService.getAllCards();
        return ResponseEntity.ok(cards);
    }
    
    @GetMapping("/cardholder/{cardholderName}")
    public ResponseEntity<List<Card>> getCardsByCardholderName(@PathVariable String cardholderName) {
        List<Card> cards = cardService.getCardsByCardholderName(cardholderName);
        return ResponseEntity.ok(cards);
    }
    
    @GetMapping("/type/{cardType}")
    public ResponseEntity<List<Card>> getCardsByType(@PathVariable Card.CardType cardType) {
        List<Card> cards = cardService.getCardsByType(cardType);
        return ResponseEntity.ok(cards);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Card>> getActiveCards() {
        List<Card> cards = cardService.getActiveCards();
        return ResponseEntity.ok(cards);
    }
    
    @GetMapping("/inactive")
    public ResponseEntity<List<Card>> getInactiveCards() {
        List<Card> cards = cardService.getInactiveCards();
        return ResponseEntity.ok(cards);
    }
    
    @GetMapping("/active/cardholder/{cardholderName}")
    public ResponseEntity<List<Card>> getActiveCardsByCardholderName(@PathVariable String cardholderName) {
        List<Card> cards = cardService.getActiveCardsByCardholderName(cardholderName);
        return ResponseEntity.ok(cards);
    }
    
    @GetMapping("/active/type/{cardType}")
    public ResponseEntity<List<Card>> getActiveCardsByType(@PathVariable Card.CardType cardType) {
        List<Card> cards = cardService.getActiveCardsByType(cardType);
        return ResponseEntity.ok(cards);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Card> updateCard(@PathVariable Long id, @Valid @RequestBody Card cardDetails) {
        Card updatedCard = cardService.updateCard(id, cardDetails);
        return ResponseEntity.ok(updatedCard);
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<Card> updateCardStatus(@PathVariable Long id, @RequestParam Boolean isActive) {
        Card updatedCard = cardService.updateCardStatus(id, isActive);
        return ResponseEntity.ok(updatedCard);
    }
    
    @PatchMapping("/{id}/balance")
    public ResponseEntity<Card> updateCardBalance(@PathVariable Long id, @RequestParam Double newBalance) {
        Card updatedCard = cardService.updateCardBalance(id, newBalance);
        return ResponseEntity.ok(updatedCard);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateCard(@PathVariable Long id) {
        cardService.deactivateCard(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/check/{cardNumber}")
    public ResponseEntity<Boolean> isCardActive(@PathVariable String cardNumber) {
        boolean isActive = cardService.isCardActive(cardNumber);
        return ResponseEntity.ok(isActive);
    }
    
    @GetMapping("/count/{cardholderName}")
    public ResponseEntity<Long> getActiveCardCount(@PathVariable String cardholderName) {
        long count = cardService.getActiveCardCount(cardholderName);
        return ResponseEntity.ok(count);
    }
}
