/*
 * Autor: Piotr Woźnicki
 * Numer studenta: so0139
 */

import java.util.ArrayList;
import java.util.List;

public class Perceptron {
    ArrayList<String> trainingData;
    ArrayList<String> testData;
    int l;
    double[] data;
    double[] weights;
    double learningRate = 0.1;

    /**
     * Konstruktor wywoływany, gdy pobieramy dane testowe z pliku
     *
     * @param l - liczba elementów w jednej linii
     */
    public Perceptron(int l) {
        this.trainingData = Data.fileReader("iris_training.txt");
        this.testData = Data.fileReader("iris_test.txt");
        this.l = l;
        this.weights = new double[l];
        for (int i = 0; i < l; i++) {
            this.weights[i] = 0;
        }
    }

    /**
     * Konstruktor wywoływany, gdy pobieramy dane z klawiatury
     *
     * @param l    - liczba elementów w jednej linii
     * @param data - dane podane przez użytkownika
     */
    public Perceptron(int l, double[] data) {
        this.trainingData = Data.fileReader("iris_training.txt");
        this.l = l;
        this.data = data;
        this.weights = new double[l];
        for (int i = 0; i < l; i++) {
            this.weights[i] = 0;
        }
    }

    /**
     * Funkcja startująca, która sprawdza, czy dane są z pliku, czy od użytkownika
     */
    public void start() {
        if (testData == null) {
            fromWrite();
        } else {
            fromFile();
        }
    }

    /**
     * Funkcja, która ma na celu trenowanie, a potem testowanie danych z klawiatury
     */
    private void fromWrite() {
        startTraining();
        //zamiana tablicy, w której są dane podane przez użytkownika na listę
        ArrayList<Double> tmp = new ArrayList<>();
        for (double datum : data) {
            tmp.add(datum);
        }
        checkFlower(tmp);
    }

    /**
     * Funkcja, która ma na celu trenowanie, a potem testowanie danych z klawiatury
     */
    private void fromFile() {
        startTraining();
        //zmienienie listy String, w której były linijki na Listę list double z wartościami
        ArrayList<List<Double>> testDataParsed = Data.parseData(testData);
        test(testDataParsed);
    }

    /**
     * Funkcja rozpoczynająca trenowanie perceptronu
     */
    private void startTraining() {
        //zmienienie listy String, w której były linijki na Listę list double z wartościami
        ArrayList<List<Double>> trainDataParsed = Data.parseData(trainingData);
        train(trainDataParsed, 1000);
    }

    /**
     * Funkcja trenująca perceptron
     * @param trainingData - lista list double, w której są podane parametry każda wewnętrzna lista zawiera parametry z
     *                     jednej linii (z pliku iris_training.txt)
     * @param epochs - liczba epok uczenia się danego perceptronu
     */
    public void train(List<List<Double>> trainingData, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (List<Double> dataPoint : trainingData) {
                int target = (int) Math.round(dataPoint.get(l));
                int guess = this.guess(dataPoint.subList(0, l));
                //rozpoczynamy algorytm delta
                //liczymy decyzje prawidłową-decyzje faktyczną
                int delta = target - guess;

                for (int i = 0; i < this.weights.length; i++) {
                    double input = dataPoint.get(i);
                    //zmieniamy wagi stosując wzór algorytmu delta, czyli W' = W + (d-y)aX
                    this.weights[i] += this.learningRate * delta * input;
                }
                //koniec algorytmu delta
            }
        }
    }

    /**
     * Funkcja ma na celu stwierdzenie po nauce perceptronu czy jest to Iris setosa, czy inny kwiat
     * @param inputs - lista parametrów
     * @return - zwraca 0 lub 1 w zależności, do jakiego kwiatu pasowały parametry
     */
    public int guess(List<Double> inputs) {
        double sum = 0;
        for (int i = 0; i < this.weights.length; i++) {
            sum += inputs.get(i) * this.weights[i];
        }
        return (sum > 0) ? 1 : 0;
    }

    /**
     * Funkcja testująca dane z pliku
     * @param testData - lista list double, w której są podane parametry każda wewnętrzna lista zawiera parametry z
     *           jednej linii (z pliku iris_test.txt)
     */
    public void test(List<List<Double>> testData) {
        int correctPredictions = 0;
        int setosa = 0;
        int another = 0;
        //sprawdzanie, czy wynik był poprawny, czy nie
        for (List<Double> dataPoint : testData) {
            int target = (int) Math.round(dataPoint.get(l));
            int guess = guess(dataPoint.subList(0, l));
            if (guess == target) {
                correctPredictions++;
            }
            if (guess == 0) {
                setosa++;
            }
            if (guess == 1) {
                another++;
            }
        }

        //wypisanie wyników
        System.out.println();
        System.out.println("Liczba iris setosa: " + setosa);
        System.out.println("Liczba innych kwiatów: " + another);
        System.out.println();
        System.out.println("Prawidłowo saklasyfikowane : " + correctPredictions + " na " + testData.size());
        System.out.println();
        double accuracy = (double) correctPredictions / testData.size() * 100;
        System.out.println("Accuracy: " + accuracy + " %");
        System.out.println();
    }

    /**
     * Funkcja testująca dane od użytkownika
     * @param testData - dane podane przez użytkownika podane w liście
     */
    public void checkFlower(List<Double> testData) {

        int guess = guess(testData.subList(0, l));
        if (guess == 0) {
            System.out.println("iris setosa");
        } else {
            System.out.println("inny kwiat");
        }
    }

}