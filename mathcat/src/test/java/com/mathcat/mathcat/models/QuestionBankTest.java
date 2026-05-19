package com.mathcat.mathcat.models;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public final class QuestionBankTest {

    @Nested
    class GetByDifficulty {
        @Test
        void easyReturns30Questions() {
            assertEquals(30, QuestionBank.getByDifficulty(Difficulty.EASY).size());
        }

        @Test
        void mediumReturns30Questions() {
            assertEquals(30, QuestionBank.getByDifficulty(Difficulty.MEDIUM).size());
        }

        @Test
        void hardReturns30Questions() {
            assertEquals(30, QuestionBank.getByDifficulty(Difficulty.HARD).size());
        }

        @Test
        void easyQuestionsHaveCorrectDifficulty() {
            assertTrue(QuestionBank.getByDifficulty(Difficulty.EASY).stream()
                    .allMatch(q -> q.getDifficulty() == Difficulty.EASY));
        }

        @Test
        void mediumQuestionsHaveCorrectDifficulty() {
            assertTrue(QuestionBank.getByDifficulty(Difficulty.MEDIUM).stream()
                    .allMatch(q -> q.getDifficulty() == Difficulty.MEDIUM));
        }

        @Test
        void hardQuestionsHaveCorrectDifficulty() {
            assertTrue(QuestionBank.getByDifficulty(Difficulty.HARD).stream()
                    .allMatch(q -> q.getDifficulty() == Difficulty.HARD));
        }

        @Test
        void returnsMutableList() {
            // shuffle() calls set() on this list, so it must not be unmodifiable
            List<IQuestion> questions = QuestionBank.getByDifficulty(Difficulty.EASY);
            assertDoesNotThrow(() -> questions.set(0, questions.get(1)));
        }

        @Test
        void returnsIndependentListEachCall() {
            List<IQuestion> a = QuestionBank.getByDifficulty(Difficulty.EASY);
            List<IQuestion> b = QuestionBank.getByDifficulty(Difficulty.EASY);
            a.clear();
            assertEquals(30, b.size());
        }
    }
}
