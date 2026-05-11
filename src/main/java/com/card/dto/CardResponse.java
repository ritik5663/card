package com.card.dto;

import com.card.entity.Card;
import java.time.LocalDateTime;

public class CardResponse {
    
    private Long id;
    private String cardNumber;
    private String cardholderName;
    private String expiryDate;
    private Card.CardType cardType;
    private Boolean isActive;
    private Double creditLimit;
    private Double currentBalance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public CardResponse() {}
    
    public CardResponse(Card card) {
        this.id = card.getId();
        this.cardNumber = maskCardNumber(card.getCardNumber());
        this.cardholderName = card.getCardholderName();
        this.expiryDate = card.getExpiryDate();
        this.cardType = card.getCardType();
        this.isActive = card.getIsActive();
        this.creditLimit = card.getCreditLimit();
        this.currentBalance = card.getCurrentBalance();
        this.createdAt = card.getCreatedAt();
        this.updatedAt = card.getUpdatedAt();
    }
    
    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() != 16) {
            return cardNumber;
        }
        return "****-****-****-" + cardNumber.substring(12);
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCardNumber() {
        return cardNumber;
    }
    
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public String getCardholderName() {
        return cardholderName;
    }
    
    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }
    
    public String getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public Card.CardType getCardType() {
        return cardType;
    }
    
    public void setCardType(Card.CardType cardType) {
        this.cardType = cardType;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public Double getCreditLimit() {
        return creditLimit;
    }
    
    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }
    
    public Double getCurrentBalance() {
        return currentBalance;
    }
    
    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
