package com.mathcat.mathcat.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AIServiceTest {

    private AIService aiService;

    @BeforeEach
    void setup() {
        aiService = new AIService();
    }

    @Nested
    class GetHint {

        @Test
        void returnsAResponse() throws Exception {
            List<String[]> history = new ArrayList<>();
            String response = aiService.getHint("What is 2 + 2?", "4", "I don't know", history);
            assertNotNull(response);
            assertFalse(response.isEmpty());
        }

        @Test
        void responseIsNotBlank() throws Exception {
            List<String[]> history = new ArrayList<>();
            String response = aiService.getHint("What is 2 + 2?", "4", "I don't know", history);
            assertFalse(response.isBlank());
        }

        @Test
        void doesNotGiveDirectAnswer() throws Exception {
            List<String[]> history = new ArrayList<>();
            String response = aiService.getHint("What is 2 + 2?", "4", "I don't know", history);
            assertFalse(response.contains("4"));
        }

        @Test
        void worksWithEmptyHistory() throws Exception {
            List<String[]> history = new ArrayList<>();
            assertDoesNotThrow(() -> aiService.getHint("What is 2 + 2?", "4", "I don't know", history));
        }

        @Test
        void worksWithConversationHistory() throws Exception {
            List<String[]> history = new ArrayList<>();
            history.add(new String[] {"user", "I don't know"});
            history.add(new String[] {"assistant", "Think about what + means"});
            String response = aiService.getHint("What is 2 + 2?", "4", "addition?", history);
            assertNotNull(response);
            assertFalse(response.isEmpty());
        }
    }
}
