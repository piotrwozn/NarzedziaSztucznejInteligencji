
/*
 * Autor: Piotr Woźnicki
 * Numer studenta: so0139
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    /**
     * menu programu
     */
    public static void menu() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> trainingData;
        int l;

        int ans;
        boolean gate = true;

        while (gate) {
            System.out.println("Chcesz wpisac dane recznie -> 1");
            System.out.println("Chcesz abym pobral dane testowe z pliku iris_test.txt -> 2");
            System.out.println("Wylaczyc program -> 3");
            ans = scanner.nextInt();

            trainingData = Data.fileReader("iris_training.txt");
            String[] lineData = trainingData.get(0).trim().split("\t");
            l = lineData.length - 1;

            if (ans == 1) {
                writingData(l);
            } else if (ans == 2) {
                Perceptron perceptron = new Perceptron(l);
                perceptron.start();
            } else {
                gate = false;
            }
        }
    }

    /**
     * Funkcja pobierająca dane od użytkownika
     * @param l - liczba parametrów w jednej linii pliku
     */
    private static void writingData(int l) {
        Scanner scanner = new Scanner(System.in);
        double[] data = new double[l];
        boolean gate = true;

        while (gate) {
            System.out.println("Wpisz " + l + " dane");
            System.out.println("Jezeli chcesz zamknąc program napisz -1");
            data[0] = scanner.nextDouble();

            if (data[0] == -1) {
                gate = false;
            } else {
                for (int i = 1; i < data.length; i++) {
                    data[i] = scanner.nextDouble();
                }
            }
            Perceptron perceptron = new Perceptron(l, data);
            perceptron.start();
        }
    }
}