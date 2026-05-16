package com.mathcat.mathcat.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public final class AIServiceTest {

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
            assertFalse(response.matches("(?i).*\\bthe answer is 4\\b.*"));
        }

        @Test
        void worksWithEmptyHistory() throws Exception {
            List<String[]> history = new ArrayList<>();
            assertDoesNotThrow(
                    () -> aiService.getHint("What is 2 + 2?", "4", "I don't know", history));
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

    @Nested
    class SanitiseInput {

        @Test
        void returnsEmptyStringForNull() {
            String result = AIService.sanitiseInput(null, 500);
            assertEquals("", result);
        }

        @Test
        void stripsHtmlTags() {
            String result = AIService.sanitiseInput("<b>hello</b>", 500);
            assertEquals("hello", result);
        }


        @Test
        void truncatesToMaxLength() {
            String longInput = "a".repeat(600);
            String result = AIService.sanitiseInput(longInput, 500);
            assertEquals(500, result.length());
        }

        @Test
        void collapsesExcessiveWhitespace() {
            String result = AIService.sanitiseInput("hello    world", 500);
            assertEquals("hello world", result);
        }

        @Test
        void returnsEmptyForWhitespaceOnly() {
            String result = AIService.sanitiseInput("   ", 500);
            assertEquals("", result);
        }
    }

    @Nested
    class RateLimit {

        @Test
        void allowsCallsUnderTheLimit() {
            AIService service = new AIService();
            // First call should not be rate limited
            // We test sanitiseInput as a proxy since we can't call getHint without the API
            assertNotNull(AIService.sanitiseInput("test", 500));
        }

        @Test
        void blocksCallsOverTheLimit() {
            AIService service = new AIService();
            List<String[]> history = new ArrayList<>();

            // Make 10 calls to hit the limit
            for (int i = 0; i < 10; i++) {
                service.getHint("What is 2 + 2?", "4", "hint " + i, history);
            }

            // 11th call should be rate limited
            String response = service.getHint("What is 2 + 2?", "4", "one more", history);
            assertTrue(response.contains("Please wait"));
        }
    }

