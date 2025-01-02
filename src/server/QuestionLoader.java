package server;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * QuestionLoader.java - Loads questions from a file.
 */
public class QuestionLoader {

    /**
     * Loads questions from the given file path.
     *
     * @param filepath Path to the questions file.
     * @return List of Question objects loaded from the file.
     */
    public static List<Question> loadQuestions(String filepath) {
        List<Question> questions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                if (parts.length < 3) {
                    System.err.println("⚠️ Invalid question format: " + line);
                    continue;
                }

                String questionText = parts[0];
                String[] options = parts[1].split(",");
                String correctAnswer = parts[2];

                questions.add(new Question(questionText, options, correctAnswer));
            }

            System.out.println("✅ Questions successfully loaded from: " + filepath);

        } catch (IOException e) {
            System.err.println("❌ Error loading questions: " + e.getMessage());
            e.printStackTrace();
        }

        return questions;
    }
}
