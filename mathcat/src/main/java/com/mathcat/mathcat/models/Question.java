package com.mathcat.mathcat.models;

/**
 * Represents a math question generated from a set of operands, {@link QuestionType}, and a
 * {@link Difficulty}. Computes the answer and builds the question text at construction time.
 */
public final class Question implements IQuestion {
    private final String questionText;
    private final QuestionType questionType;
    private final Difficulty difficulty;
    private final int[] operands;
    private final int answer;

    private static final String ADDITION = " + ";
    private static final String SUBTRACTION = " - ";
    private static final String MULTIPLICATION = " x ";
    private static final String QUESTION_END = " = ?";

    /**
     * Constructs a math question from the given type, difficulty, and operands. Computes the answer
     * and formats the question text at construction time.
     * 
     * @param questionType the aritmetic operation for this question
     * @param difficulty the difficulty level
     * @param operands the numbers used, between 2 and 6 inclusive
     * @throws IllegalArgumentException if operands length is outside of the valid range
     */
    public Question(QuestionType questionType, Difficulty difficulty, int[] operands) {
        if (operands.length < 2 || operands.length > 6) {
            throw new IllegalArgumentException(
                    "A question must contain between 2 and 6 operands (inclusive).");
        }

        String connector;
        int calculatedAnswer = operands[0];

        switch (questionType) {
            case ADDITION:
                connector = ADDITION;
                for (int i = 1; i < operands.length; i++) {
                    calculatedAnswer += operands[i];
                }
                break;

            case SUBTRACTION:
                connector = SUBTRACTION;
                for (int i = 1; i < operands.length; i++) {
                    calculatedAnswer -= operands[i];
                }
                break;

            case MULTIPLICATION:
                connector = MULTIPLICATION;
                for (int i = 1; i < operands.length; i++) {
                    calculatedAnswer *= operands[i];
                }
                break;

            default:
                throw new IllegalArgumentException("This Question Type is not defined.");
        }


        String questionBuilder = operands[0] + connector + operands[1]; // e.g. "5" + " + " + "2"
        for (int i = 2; i < operands.length; i++) {
            // Add any additional operands (if > 2 operands total)
            questionBuilder += connector + operands[i]; // e.g. "5 + 2" + " + " + "9"
        }

        this.questionType = questionType;
        this.difficulty = difficulty;
        this.operands = operands;
        this.questionText = questionBuilder + QUESTION_END; // e.g. "5 + 2 + 9" + "?"
        this.answer = calculatedAnswer;
    }

    /** @return the formatted question string, e.g. "5 + 2 + 9 = ?" */
    @Override
    public String getText() {
        return questionText;
    }

    /** @return the correct answer to the question. */
    @Override
    public int getAnswer() {
        return answer;
    }

    /** @return the arithmetic type of this question. */
    @Override
    public QuestionType getType() {
        return questionType;
    }

    /** @return the difficulty level of this question. */
    @Override
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /** @return the operands used in this question. */
    @Override
    public int[] getOperands() {
        return operands;
    }
}
