package com.card.dto;

import com.card.entity.Card;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CardRequest {
    
    @NotBlank(message = "Card number is required")
    @Size(min = 16, max = 16, message = "Card number must be exactly 16 digits")
    @Pattern(regexp = "\\d{16}", message = "Card number must contain only digits")
    private String cardNumber;
    
    @NotBlank(message = "Cardholder name is required")
    @Size(min = 2, max = 100, message = "Cardholder name must be between 2 and 100 characters")
    private String cardholderName;
    
    @NotBlank(message = "Expiry date is required")
    @Pattern(regexp = "(0[1-9]|1[0-2])/\\d{2}", message = "Expiry date must be in MM/YY format")
    private String expiryDate;
    
    @NotBlank(message = "CVV is required")
    @Size(min = 3, max = 4, message = "CVV must be 3 or 4 digits")
    @Pattern(regexp = "\\d{3,4}", message = "CVV must contain only digits")
    private String cvv;
    
    @NotNull(message = "Card type is required")
    private Card.CardType cardType;
    
    private Boolean isActive = true;
    
    private Double creditLimit;
    
    private Double currentBalance = 0.0;
    
    public CardRequest() {}
    
    public CardRequest(String cardNumber, String cardholderName, String expiryDate, String cvv, Card.CardType cardType) {
        this.cardNumber = cardNumber;
        this.cardholderName = cardholderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardType = cardType;
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
    
    public String getCvv() {
        return cvv;
    }
    
    public void setCvv(String cvv) {
        this.cvv = cvv;
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
    
    public Card toEntity() {
        Card card = new Card();
        card.setCardNumber(this.cardNumber);
        card.setCardholderName(this.cardholderName);
        card.setExpiryDate(this.expiryDate);
        card.setCvv(this.cvv);
        card.setCardType(this.cardType);
        card.setIsActive(this.isActive);
        card.setCreditLimit(this.creditLimit);
        card.setCurrentBalance(this.currentBalance);
        return card;
    }
}
