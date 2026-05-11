package com.card;

import com.card.entity.Card;
import com.card.service.CardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CardControllerIntegrationTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private CardService cardService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        cardService = Mockito.mock(CardService.class);
        objectMapper = new ObjectMapper();
        
        com.card.controller.CardController controller = new com.card.controller.CardController();
        // Use reflection to inject the mocked service
        try {
            java.lang.reflect.Field serviceField = com.card.controller.CardController.class.getDeclaredField("cardService");
            serviceField.setAccessible(true);
            serviceField.set(controller, cardService);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject mocked service", e);
        }
        
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testCreateCard() throws Exception {
        Card card = new Card();
        card.setCardNumber("1234567890123456");
        card.setCardholderName("John Doe");
        card.setExpiryDate("12/25");
        card.setCvv("123");
        card.setCardType(Card.CardType.CREDIT);
        card.setCreditLimit(5000.0);
        card.setCurrentBalance(0.0);

        Card savedCard = new Card();
        savedCard.setId(1L);
        savedCard.setCardNumber(card.getCardNumber());
        savedCard.setCardholderName(card.getCardholderName());
        savedCard.setExpiryDate(card.getExpiryDate());
        savedCard.setCvv(card.getCvv());
        savedCard.setCardType(card.getCardType());
        savedCard.setCreditLimit(card.getCreditLimit());
        savedCard.setCurrentBalance(card.getCurrentBalance());

        when(cardService.createCard(any(Card.class))).thenReturn(savedCard);

        mockMvc.perform(post("/api/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(card)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cardNumber").value("1234567890123456"))
                .andExpect(jsonPath("$.cardholderName").value("John Doe"))
                .andExpect(jsonPath("$.cardType").value("CREDIT"));
    }

    @Test
    public void testGetCardById() throws Exception {
        Card card = new Card();
        card.setId(1L);
        card.setCardNumber("9876543210987654");
        card.setCardholderName("Jane Smith");
        card.setExpiryDate("08/24");
        card.setCvv("456");
        card.setCardType(Card.CardType.DEBIT);

        when(cardService.getCardById(1L)).thenReturn(card);

        mockMvc.perform(get("/api/cards/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNumber").value("9876543210987654"))
                .andExpect(jsonPath("$.cardholderName").value("Jane Smith"));
    }

    @Test
    public void testGetAllCards() throws Exception {
        Card card1 = new Card();
        card1.setId(1L);
        card1.setCardNumber("1111111111111111");
        card1.setCardholderName("User One");
        card1.setCardType(Card.CardType.CREDIT);

        Card card2 = new Card();
        card2.setId(2L);
        card2.setCardNumber("2222222222222222");
        card2.setCardholderName("User Two");
        card2.setCardType(Card.CardType.DEBIT);

        when(cardService.getAllCards()).thenReturn(Arrays.asList(card1, card2));

        mockMvc.perform(get("/api/cards"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testUpdateCard() throws Exception {
        Card existingCard = new Card();
        existingCard.setId(1L);
        existingCard.setCardNumber("1111222233334444");
        existingCard.setCardholderName("Original Name");
        existingCard.setExpiryDate("06/23");
        existingCard.setCvv("789");
        existingCard.setCardType(Card.CardType.PREPAID);

        Card updatedCard = new Card();
        updatedCard.setId(1L);
        updatedCard.setCardNumber("1111222233334444");
        updatedCard.setCardholderName("Updated Name");
        updatedCard.setExpiryDate("06/23");
        updatedCard.setCvv("789");
        updatedCard.setCardType(Card.CardType.PREPAID);

        when(cardService.updateCard(anyLong(), any(Card.class))).thenReturn(updatedCard);

        mockMvc.perform(put("/api/cards/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCard)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardholderName").value("Updated Name"));
    }

    @Test
    public void testDeleteCard() throws Exception {
        Mockito.doNothing().when(cardService).deleteCard(1L);

        mockMvc.perform(delete("/api/cards/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testCreateCardWithInvalidData() throws Exception {
        Card card = new Card();
        card.setCardNumber("123");
        card.setCardholderName("");
        card.setExpiryDate("invalid");
        card.setCvv("12");
        card.setCardType(null);

        mockMvc.perform(post("/api/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(card)))
                .andExpect(status().isBadRequest());
    }
}
