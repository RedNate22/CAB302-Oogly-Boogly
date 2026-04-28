package com.mathcat.mathcat.models;

public class Question implements IQuestion {
    private final String questionText;
    private final QuestionType questionType;
    private final Difficulty difficulty;
    private final int[] operands;

    private static final String QUESTION_START = "What is: ";
    private static final String ADDITION = " + ";
    private static final String SUBTRACTION = " - ";
    private static final String MULTIPLICATION = " x ";
    private static final String QUESTION_MARK = "?";

    public Question(QuestionType questionType, Difficulty difficulty, int[] operands) {
        if (operands.length < 2) {
            throw new IllegalArgumentException("A question must have at least 2 operands.");
        }

        String questionBuilder;
        String connector;

        switch (questionType) {
            case ADDITION:
                connector = ADDITION;
                break;
            case SUBTRACTION:
                connector = SUBTRACTION;
                break;
            case MULTIPLICATION:
                connector = MULTIPLICATION;
                break;
            default:
                throw new IllegalArgumentException("This Question Type is not defined.");
        }

        questionBuilder = QUESTION_START + operands[0] + connector + operands[1];
        for (int i = 2; i < operands.length; i++) {
            questionBuilder += connector + operands[i];
        }
        this.questionText = questionBuilder + QUESTION_MARK;

        this.questionType = questionType;
        this.difficulty = difficulty;
        this.operands = operands;
    }

    // TODO remove this
    // int[] operands = {4, 7};
    // Question q = new Question(QuestionType.ADDITION, Difficulty.LOW, new int[]{4, 7});

    @Override
    public String getText() {
        return questionText;
    }

    @Override
    public int getAnswer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAnswer'");
    }

    @Override
    public QuestionType getType() {
        return questionType;
    }

    @Override
    public Difficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public int[] getOperands() {
        return operands;
    }
}
