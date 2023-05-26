import java.nio.file.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static ArrayList<Observation> test, training;
    public HashMap<String, Perceptron> identifiers;

    public Main() {
        // Parse directories with training and test data
        test = parseData("data/test");
        training = parseData("data/training");

        // Initialize the perceptrons for each language
        identifiers = setIdentifiers();
    }

    public void trainModel() {
        // Train the perceptrons using the identity activation function
        double learningRate = 0.1;
        double errorThreshold = 0.000001;
        int maxEpochs = 1000000;

        for (int epoch = 0; epoch < maxEpochs; epoch++) {
            double maxError = 0;

            for (Observation trainingObs : training) {
                for (HashMap.Entry<String, Perceptron> entry : identifiers.entrySet()) {
                    double error = entry.getValue().learn(trainingObs, entry.getKey(), learningRate);
                    maxError = Math.max(maxError, Math.abs(error));
                }
            }

            if (maxError < errorThreshold) {
                break;
            }
        }

        // Test the perceptrons with test data
        for (Observation testObs : test) {
            double maxOutput = Double.NEGATIVE_INFINITY;
            String predictedLanguage = null;

            for (HashMap.Entry<String, Perceptron> entry : identifiers.entrySet()) {
                double output = entry.getValue().evaluate(testObs);
                if (output > maxOutput) {
                    maxOutput = output;
                    predictedLanguage = entry.getKey();
                }
            }

            System.out.println("Predicted: " + predictedLanguage + " | Actual: " + testObs.getLanguage());
        }
    }

    public String classify(Observation observation) {
        double maxOutput = Double.NEGATIVE_INFINITY;
        String predictedLanguage = null;

        for (HashMap.Entry<String, Perceptron> entry : identifiers.entrySet()) {
            double output = entry.getValue().evaluate(observation);
            if (output > maxOutput) {
                maxOutput = output;
                predictedLanguage = entry.getKey();
            }
        }

        return predictedLanguage;
    }


    private static HashMap<String, Perceptron> setIdentifiers() {
        HashMap<String, Perceptron> identifiers = new HashMap<>();
        for (Observation observation : training) {
            identifiers.put(observation.getLanguage(), new Perceptron());
        }
        return identifiers;
    }

    private static ArrayList<Observation> parseData(String dataPath) {
        ArrayList<Observation> data = new ArrayList<>();

        try {
            Path path = Paths.get(dataPath);
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);

            for (Path languageDir : directoryStream) {
                if (Files.isDirectory(languageDir)) {
                    String language = languageDir.getFileName().toString();
                    DirectoryStream<Path> languageStream = Files.newDirectoryStream(languageDir);
                    for (Path file : languageStream) {
                        String text = Files.readString(file);
                        data.add(new Observation(language, text));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
