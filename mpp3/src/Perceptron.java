public class Perceptron {
    private double[] weights;

    public Perceptron() {
        // Initialize the perceptron weights
        weights = new double[26];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Math.random() * 2 - 1;
        }
        normalizeWeights();
    }

    public double learn(Observation observation, String language, double learningRate) {
        double[] inputs = observation.getAttributes();
        double desiredOutput = observation.getLanguage().equals(language) ? 1 : -1;
        double actualOutput = evaluate(observation);
        double error = desiredOutput - actualOutput;

        // Update the weights using the delta rule
        for (int i = 0; i < weights.length; i++) {
            weights[i] += learningRate * error * inputs[i];
        }
        normalizeWeights();

        return error;
    }

    public double evaluate(Observation observation) {
        double net = 0;
        double[] inputs = observation.getAttributes();

        for (int i = 0; i < weights.length; i++) {
            net += inputs[i] * weights[i];
        }

        return net;
    }

    private void normalizeWeights() {
        double norm = 0;
        for (double weight : weights) {
            norm += weight * weight;
        }
        norm = Math.sqrt(norm);

        for (int i = 0; i < weights.length; i++) {
            weights[i] /= norm;
        }
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }
}
