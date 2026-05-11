package com.card;

import com.card.entity.Card;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
public class CardControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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
        card.setCardNumber("9876543210987654");
        card.setCardholderName("Jane Smith");
        card.setExpiryDate("08/24");
        card.setCvv("456");
        card.setCardType(Card.CardType.DEBIT);

        String response = mockMvc.perform(post("/api/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(card)))
                .andReturn().getResponse().getContentAsString();

        Card createdCard = objectMapper.readValue(response, Card.class);
        Long cardId = createdCard.getId();

        mockMvc.perform(get("/api/cards/" + cardId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNumber").value("9876543210987654"))
                .andExpect(jsonPath("$.cardholderName").value("Jane Smith"));
    }

    @Test
    public void testGetAllCards() throws Exception {
        mockMvc.perform(get("/api/cards"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testUpdateCard() throws Exception {
        Card card = new Card();
        card.setCardNumber("1111222233334444");
        card.setCardholderName("Original Name");
        card.setExpiryDate("06/23");
        card.setCvv("789");
        card.setCardType(Card.CardType.PREPAID);

        String response = mockMvc.perform(post("/api/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(card)))
                .andReturn().getResponse().getContentAsString();

        Card createdCard = objectMapper.readValue(response, Card.class);
        Long cardId = createdCard.getId();

        createdCard.setCardholderName("Updated Name");

        mockMvc.perform(put("/api/cards/" + cardId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createdCard)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardholderName").value("Updated Name"));
    }

    @Test
    public void testDeleteCard() throws Exception {
        Card card = new Card();
        card.setCardNumber("5555666677778888");
        card.setCardholderName("To Be Deleted");
        card.setExpiryDate("03/26");
        card.setCvv("999");
        card.setCardType(Card.CardType.CREDIT);

        String response = mockMvc.perform(post("/api/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(card)))
                .andReturn().getResponse().getContentAsString();

        Card createdCard = objectMapper.readValue(response, Card.class);
        Long cardId = createdCard.getId();

        mockMvc.perform(delete("/api/cards/" + cardId))
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
