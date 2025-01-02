package server;

/**
 * Question.java - Represents a quiz question with options and a correct answer.
 */
public class Question {
    private String question;
    private String[] options;
    private String correctAnswer;

    /**
     * Constructor to initialize a Question object.
     *
     * @param question      The question text.
     * @param options       An array of possible answer options.
     * @param correctAnswer The correct answer to the question.
     */
    public Question(String question, String[] options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    /**
     * Gets the question text.
     *
     * @return The question.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Gets the options for the question.
     *
     * @return An array of options.
     */
    public String[] getOptions() {
        return options;
    }

    /**
     * Gets the correct answer.
     *
     * @return The correct answer as a string.
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Validates if the given answer matches the correct answer.
     *
     * @param answer The answer provided by the user.
     * @return true if the answer is correct, false otherwise.
     */
    public boolean isCorrect(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }

    @Override
    public String toString() {
        return "Question: " + question + "\nOptions: " + String.join(", ", options);
    }
}
