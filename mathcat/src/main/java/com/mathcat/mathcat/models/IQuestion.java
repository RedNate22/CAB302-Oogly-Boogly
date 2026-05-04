package com.mathcat.mathcat.models;

/**
 * Defines the contract for a math question presented to the user. Implementations are responsible
 * for generating operands, computing the answer, and formatting the question text.
 */
public interface IQuestion {
    /**
     * @return the formatted question string built from operands (e.g., "What is the sum of 4 and
     *         7?").
     */
    public String getText();

    /**
     * @return the correctly calculated answer, to be compared with the User's answer.
     */
    public int getAnswer();

    /**
     * @return the {@link QuestionType} of the {@link Question}, e.g.,
     *         {@link QuestionType#MULTIPLICATION}.
     */
    public QuestionType getType();

    /**
     * @return the {@link Difficulty} of the {@link Question}, e.g., {@link Difficulty#HARD}.
     */
    public Difficulty getDifficulty();

    /**
     * @return the operands of the {@link Question} as an array. The no. of operands can be 2 or
     *         more.
     */
    public int[] getOperands();
}
