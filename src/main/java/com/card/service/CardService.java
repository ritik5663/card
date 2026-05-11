package com.card.service;

import com.card.entity.Card;
import com.card.repository.CardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CardService {
    
    @Autowired
    private CardRepository cardRepository;
    
    public Card createCard(Card card) {
        if (cardRepository.existsByCardNumber(card.getCardNumber())) {
            throw new IllegalArgumentException("Card with number " + card.getCardNumber() + " already exists");
        }
        return cardRepository.save(card);
    }
    
    public Card getCardById(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Card with id " + id + " not found"));
    }
    
    public Card getCardByNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new EntityNotFoundException("Card with number " + cardNumber + " not found"));
    }
    
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }
    
    public List<Card> getCardsByCardholderName(String cardholderName) {
        return cardRepository.findByCardholderNameContainingIgnoreCase(cardholderName);
    }
    
    public List<Card> getCardsByType(Card.CardType cardType) {
        return cardRepository.findByCardType(cardType);
    }
    
    public List<Card> getActiveCards() {
        return cardRepository.findByIsActive(true);
    }
    
    public List<Card> getInactiveCards() {
        return cardRepository.findByIsActive(false);
    }
    
    public List<Card> getActiveCardsByCardholderName(String cardholderName) {
        return cardRepository.findActiveCardsByCardholderName(cardholderName);
    }
    
    public List<Card> getActiveCardsByType(Card.CardType cardType) {
        return cardRepository.findActiveCardsByType(cardType);
    }
    
    public Card updateCard(Long id, Card cardDetails) {
        Card existingCard = getCardById(id);
        
        if (!existingCard.getCardNumber().equals(cardDetails.getCardNumber()) && 
            cardRepository.existsByCardNumber(cardDetails.getCardNumber())) {
            throw new IllegalArgumentException("Card with number " + cardDetails.getCardNumber() + " already exists");
        }
        
        existingCard.setCardNumber(cardDetails.getCardNumber());
        existingCard.setCardholderName(cardDetails.getCardholderName());
        existingCard.setExpiryDate(cardDetails.getExpiryDate());
        existingCard.setCvv(cardDetails.getCvv());
        existingCard.setCardType(cardDetails.getCardType());
        existingCard.setIsActive(cardDetails.getIsActive());
        existingCard.setCreditLimit(cardDetails.getCreditLimit());
        existingCard.setCurrentBalance(cardDetails.getCurrentBalance());
        
        return cardRepository.save(existingCard);
    }
    
    public Card updateCardStatus(Long id, Boolean isActive) {
        Card card = getCardById(id);
        card.setIsActive(isActive);
        return cardRepository.save(card);
    }
    
    public Card updateCardBalance(Long id, Double newBalance) {
        Card card = getCardById(id);
        if (card.getCreditLimit() != null && newBalance > card.getCreditLimit()) {
            throw new IllegalArgumentException("Balance cannot exceed credit limit");
        }
        card.setCurrentBalance(newBalance);
        return cardRepository.save(card);
    }
    
    public void deleteCard(Long id) {
        Card card = getCardById(id);
        cardRepository.delete(card);
    }
    
    public void deactivateCard(Long id) {
        Card card = getCardById(id);
        card.setIsActive(false);
        cardRepository.save(card);
    }
    
    public boolean isCardActive(String cardNumber) {
        Optional<Card> card = cardRepository.findByCardNumber(cardNumber);
        return card.map(Card::getIsActive).orElse(false);
    }
    
    public long getActiveCardCount(String cardholderName) {
        return cardRepository.countActiveCardsByCardholderName(cardholderName);
    }
}
