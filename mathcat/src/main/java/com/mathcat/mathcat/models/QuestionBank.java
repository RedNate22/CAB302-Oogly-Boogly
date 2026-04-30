package com.mathcat.mathcat.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class QuestionBank {
        // Prevent instantiation
        private QuestionBank() {}

        public static List<IQuestion> getByDifficulty(Difficulty difficulty) {
                List<IQuestion> combined = new ArrayList<>();

                switch (difficulty) {
                        case EASY:
                                combined.addAll(Arrays.asList(EASY_ADDITION));
                                combined.addAll(Arrays.asList(EASY_SUBTRACTION));
                                combined.addAll(Arrays.asList(EASY_MULTIPLICATION));
                                break;

                        case MEDIUM:
                                combined.addAll(Arrays.asList(MEDIUM_ADDITION));
                                combined.addAll(Arrays.asList(MEDIUM_SUBTRACTION));
                                combined.addAll(Arrays.asList(MEDIUM_MULTIPLICATION));
                                break;

                        case HARD:
                                combined.addAll(Arrays.asList(HARD_ADDITION));
                                combined.addAll(Arrays.asList(HARD_SUBTRACTION));
                                combined.addAll(Arrays.asList(HARD_MULTIPLICATION));
                                break;
                }

                return combined;
        }

        // Need to keep this here to prevent formatters messing with my OCD - Nate
        // @formatter:off

        // EASY: 2 operands, 1 digit (1-9)
        private static final IQuestion[] EASY_ADDITION = {
                new Question(QuestionType.ADDITION, Difficulty.EASY, new int[] {3, 5}),
                new Question(QuestionType.ADDITION, Difficulty.EASY, new int[] {2, 7}),
                new Question(QuestionType.ADDITION, Difficulty.EASY, new int[] {1, 4}),
                new Question(QuestionType.ADDITION, Difficulty.EASY, new int[] {6, 3}),
                new Question(QuestionType.ADDITION, Difficulty.EASY, new int[] {4, 5}),
                new Question(QuestionType.ADDITION, Difficulty.EASY, new int[] {2, 8}),
                new Question(QuestionType.ADDITION, Difficulty.EASY, new int[] {7, 1}),
                new Question(QuestionType.ADDITION, Difficulty.EASY, new int[] {5, 4}),
                new Question(QuestionType.ADDITION, Difficulty.EASY, new int[] {3, 6}),
                new Question(QuestionType.ADDITION, Difficulty.EASY, new int[] {8, 1}),
        };

        private static final IQuestion[] EASY_SUBTRACTION = {
                new Question(QuestionType.SUBTRACTION, Difficulty.EASY, new int[] {9, 4}),
                new Question(QuestionType.SUBTRACTION, Difficulty.EASY, new int[] {7, 3}),
                new Question(QuestionType.SUBTRACTION, Difficulty.EASY, new int[] {8, 5}),
                new Question(QuestionType.SUBTRACTION, Difficulty.EASY, new int[] {6, 2}),
                new Question(QuestionType.SUBTRACTION, Difficulty.EASY, new int[] {5, 1}),
                new Question(QuestionType.SUBTRACTION, Difficulty.EASY, new int[] {9, 6}),
                new Question(QuestionType.SUBTRACTION, Difficulty.EASY, new int[] {7, 4}),
                new Question(QuestionType.SUBTRACTION, Difficulty.EASY, new int[] {8, 3}),
                new Question(QuestionType.SUBTRACTION, Difficulty.EASY, new int[] {6, 4}),
                new Question(QuestionType.SUBTRACTION, Difficulty.EASY, new int[] {9, 2}),
        };

        private static final IQuestion[] EASY_MULTIPLICATION = {
                new Question(QuestionType.MULTIPLICATION, Difficulty.EASY, new int[] {3, 4}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.EASY, new int[] {2, 6}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.EASY, new int[] {5, 3}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.EASY, new int[] {4, 4}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.EASY, new int[] {7, 2}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.EASY, new int[] {3, 6}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.EASY, new int[] {2, 9}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.EASY, new int[] {4, 5}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.EASY, new int[] {6, 3}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.EASY, new int[] {5, 5}),
        };

        // MEDIUM: 2 operands, 2 digits (10-99)
        private static final IQuestion[] MEDIUM_ADDITION = {
                new Question(QuestionType.ADDITION, Difficulty.MEDIUM, new int[] {23, 45}),
                new Question(QuestionType.ADDITION, Difficulty.MEDIUM, new int[] {31, 47}),
                new Question(QuestionType.ADDITION, Difficulty.MEDIUM, new int[] {15, 62}),
                new Question(QuestionType.ADDITION, Difficulty.MEDIUM, new int[] {44, 33}),
                new Question(QuestionType.ADDITION, Difficulty.MEDIUM, new int[] {27, 51}),
                new Question(QuestionType.ADDITION, Difficulty.MEDIUM, new int[] {38, 42}),
                new Question(QuestionType.ADDITION, Difficulty.MEDIUM, new int[] {56, 21}),
                new Question(QuestionType.ADDITION, Difficulty.MEDIUM, new int[] {19, 74}),
                new Question(QuestionType.ADDITION, Difficulty.MEDIUM, new int[] {62, 25}),
                new Question(QuestionType.ADDITION, Difficulty.MEDIUM, new int[] {47, 38}),
        };

        private static final IQuestion[] MEDIUM_SUBTRACTION = {
                new Question(QuestionType.SUBTRACTION, Difficulty.MEDIUM, new int[] {75, 32}),
                new Question(QuestionType.SUBTRACTION, Difficulty.MEDIUM, new int[] {84, 51}),
                new Question(QuestionType.SUBTRACTION, Difficulty.MEDIUM, new int[] {63, 27}),
                new Question(QuestionType.SUBTRACTION, Difficulty.MEDIUM, new int[] {92, 48}),
                new Question(QuestionType.SUBTRACTION, Difficulty.MEDIUM, new int[] {57, 23}),
                new Question(QuestionType.SUBTRACTION, Difficulty.MEDIUM, new int[] {81, 39}),
                new Question(QuestionType.SUBTRACTION, Difficulty.MEDIUM, new int[] {66, 41}),
                new Question(QuestionType.SUBTRACTION, Difficulty.MEDIUM, new int[] {78, 35}),
                new Question(QuestionType.SUBTRACTION, Difficulty.MEDIUM, new int[] {94, 52}),
                new Question(QuestionType.SUBTRACTION, Difficulty.MEDIUM, new int[] {83, 46}),
        };

        private static final IQuestion[] MEDIUM_MULTIPLICATION = {
                new Question(QuestionType.MULTIPLICATION, Difficulty.MEDIUM, new int[] {12, 11}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.MEDIUM, new int[] {15, 13}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.MEDIUM, new int[] {14, 12}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.MEDIUM, new int[] {11, 21}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.MEDIUM, new int[] {13, 14}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.MEDIUM, new int[] {16, 12}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.MEDIUM, new int[] {11, 18}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.MEDIUM, new int[] {15, 11}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.MEDIUM, new int[] {12, 13}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.MEDIUM, new int[] {21, 14}),
        };

        // HARD: 3 operands, 2 digits (10-99)
        private static final IQuestion[] HARD_ADDITION = {
                new Question(QuestionType.ADDITION, Difficulty.HARD, new int[] {23, 45, 67}),
                new Question(QuestionType.ADDITION, Difficulty.HARD, new int[] {31, 47, 52}),
                new Question(QuestionType.ADDITION, Difficulty.HARD, new int[] {15, 62, 43}),
                new Question(QuestionType.ADDITION, Difficulty.HARD, new int[] {44, 33, 21}),
                new Question(QuestionType.ADDITION, Difficulty.HARD, new int[] {27, 51, 38}),
                new Question(QuestionType.ADDITION, Difficulty.HARD, new int[] {38, 42, 19}),
                new Question(QuestionType.ADDITION, Difficulty.HARD, new int[] {56, 21, 73}),
                new Question(QuestionType.ADDITION, Difficulty.HARD, new int[] {19, 74, 36}),
                new Question(QuestionType.ADDITION, Difficulty.HARD, new int[] {62, 25, 48}),
                new Question(QuestionType.ADDITION, Difficulty.HARD, new int[] {47, 38, 61}),
        };

        private static final IQuestion[] HARD_SUBTRACTION = {
                new Question(QuestionType.SUBTRACTION, Difficulty.HARD, new int[] {95, 23, 31}),
                new Question(QuestionType.SUBTRACTION, Difficulty.HARD, new int[] {84, 15, 47}),
                new Question(QuestionType.SUBTRACTION, Difficulty.HARD, new int[] {77, 22, 35}),
                new Question(QuestionType.SUBTRACTION, Difficulty.HARD, new int[] {92, 31, 28}),
                new Question(QuestionType.SUBTRACTION, Difficulty.HARD, new int[] {88, 24, 41}),
                new Question(QuestionType.SUBTRACTION, Difficulty.HARD, new int[] {96, 37, 28}),
                new Question(QuestionType.SUBTRACTION, Difficulty.HARD, new int[] {75, 18, 32}),
                new Question(QuestionType.SUBTRACTION, Difficulty.HARD, new int[] {83, 29, 34}),
                new Question(QuestionType.SUBTRACTION, Difficulty.HARD, new int[] {91, 45, 22}),
                new Question(QuestionType.SUBTRACTION, Difficulty.HARD, new int[] {78, 31, 27}),
        };

        private static final IQuestion[] HARD_MULTIPLICATION = {
                new Question(QuestionType.MULTIPLICATION, Difficulty.HARD, new int[] {11, 12, 13}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.HARD, new int[] {11, 14, 15}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.HARD, new int[] {12, 11, 16}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.HARD, new int[] {13, 14, 11}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.HARD, new int[] {11, 13, 17}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.HARD, new int[] {12, 14, 15}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.HARD, new int[] {11, 15, 16}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.HARD, new int[] {13, 12, 14}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.HARD, new int[] {11, 16, 17}),
                new Question(QuestionType.MULTIPLICATION, Difficulty.HARD, new int[] {12, 13, 15}),
        };

        // @formatter:on
}
