package com.mathcat.mathcat.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class QuestionServiceTest {
    private QuestionService service;

    @BeforeEach
    void setUp() {
        service = new QuestionService();
    }

    @Nested
    class NextQuestion {
        @Test
        void returnsNonNull() {
            assertNotNull(service.nextQuestion(1));
        }

        @Test
        void rebuildsQueueWhenExhausted() {
            for (int i = 0; i < 31; i++) {
                assertNotNull(service.nextQuestion(1));
            }
        }

        @Test
        void worksAcrossAllLevels() {
            for (int level = 1; level <= 10; level++) {
                assertNotNull(service.nextQuestion(level));
            }
        }

    }
}
