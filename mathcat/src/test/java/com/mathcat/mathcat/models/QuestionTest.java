package com.mathcat.mathcat.models;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class QuestionTest {

    @Nested
    class Addition {
        @Test
        void twoOperandsAnswer() {
            assertEquals(8, new Question(QuestionType.ADDITION, Difficulty.EASY, new int[]{3, 5}).getAnswer());
        }

        @Test
        void twoOperandsText() {
            assertEquals("3 + 5 = ?", new Question(QuestionType.ADDITION, Difficulty.EASY, new int[]{3, 5}).getText());
        }

        @Test
        void threeOperandsAnswer() {
            assertEquals(10, new Question(QuestionType.ADDITION, Difficulty.HARD, new int[]{3, 5, 2}).getAnswer());
        }

        @Test
        void threeOperandsText() {
            assertEquals("3 + 5 + 2 = ?", new Question(QuestionType.ADDITION, Difficulty.HARD, new int[]{3, 5, 2}).getText());
        }
    }

    @Nested
    class Subtraction {
        @Test
        void twoOperandsAnswer() {
            assertEquals(5, new Question(QuestionType.SUBTRACTION, Difficulty.EASY, new int[]{9, 4}).getAnswer());
        }

        @Test
        void twoOperandsText() {
            assertEquals("9 - 4 = ?", new Question(QuestionType.SUBTRACTION, Difficulty.EASY, new int[]{9, 4}).getText());
        }

        @Test
        void threeOperandsAnswer() {
            assertEquals(41, new Question(QuestionType.SUBTRACTION, Difficulty.HARD, new int[]{95, 23, 31}).getAnswer());
        }
    }

    @Nested
    class Multiplication {
        @Test
        void twoOperandsAnswer() {
            assertEquals(12, new Question(QuestionType.MULTIPLICATION, Difficulty.EASY, new int[]{3, 4}).getAnswer());
        }

        @Test
        void twoOperandsText() {
            assertEquals("3 x 4 = ?", new Question(QuestionType.MULTIPLICATION, Difficulty.EASY, new int[]{3, 4}).getText());
        }

        @Test
        void threeOperandsAnswer() {
            assertEquals(24, new Question(QuestionType.MULTIPLICATION, Difficulty.HARD, new int[]{2, 3, 4}).getAnswer());
        }
    }

    @Nested
    class Getters {
        @Test
        void getType() {
            assertEquals(QuestionType.ADDITION, new Question(QuestionType.ADDITION, Difficulty.EASY, new int[]{1, 2}).getType());
        }

        @Test
        void getDifficulty() {
            assertEquals(Difficulty.HARD, new Question(QuestionType.ADDITION, Difficulty.HARD, new int[]{1, 2}).getDifficulty());
        }

        @Test
        void getOperands() {
            int[] operands = {3, 5};
            assertArrayEquals(operands, new Question(QuestionType.ADDITION, Difficulty.EASY, operands).getOperands());
        }
    }

    @Nested
    class InvalidOperands {
        @Test
        void tooFewOperands() {
            assertThrows(IllegalArgumentException.class, () ->
                    new Question(QuestionType.ADDITION, Difficulty.EASY, new int[]{5}));
        }

        @Test
        void tooManyOperands() {
            assertThrows(IllegalArgumentException.class, () ->
                    new Question(QuestionType.ADDITION, Difficulty.EASY, new int[]{1, 2, 3, 4, 5, 6, 7}));
        }
    }
}
