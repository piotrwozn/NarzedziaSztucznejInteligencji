/*
 * Autor: Piotr Woźnicki
 * Numer studenta: so0139
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Data {

    /**
     * Funkcja odczytująca dane z pliku
     * @param filePath - path pliku, z którego mamy pobrać dane
     * @return - zwraca listę stringów, w którym jeden element to jedna linijka
     */
    public static ArrayList<String> fileReader(String filePath) {
        ArrayList<String> data = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line;
            data = new ArrayList<>();

            while ((line = bufferedReader.readLine()) != null) {
                data.add(line);
            }

            bufferedReader.close();
        } catch (IOException ignored) {

        }
        return data;
    }

    /**
     * Funkcja zmieniająca listę stringów na listę list double
     * @param data - lista Stringów zawierająca linijki danych
     * @return - zwracamy listę list double, w której każdy element to osobny parametr
     */
    public static ArrayList<List<Double>> parseData(ArrayList<String> data) {
        ArrayList<List<Double>> parsedData = new ArrayList<>();

        for (String row : data) {
            String[] values = row.split("\t");
            List<Double> parsedRow = new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                values[i] = values[i].trim();
            }

            for (int i = 0; i < values.length; i++) {
                if(i == values.length - 1) {
                    if(values[i].equals("Iris-setosa")) {
                        parsedRow.add(0.0);
                    } else {
                        parsedRow.add(1.0);
                    }
                } else {
                    parsedRow.add(Double.parseDouble(values[i].replace(',','.')));
                }
            }

            parsedData.add(parsedRow);
        }

        return parsedData;
    }

}
