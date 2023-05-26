public class Observation {
    private String language;
    private double[] attributes;

    public Observation(String language, String text) {
        this.language = language;
        this.attributes = countLetters(text);
    }

    private double[] countLetters(String text) {
        double[] attributes = new double[26];

        int totalLetters = 0;
        for (int i = 'a'; i <= 'z'; i++) {
            char letter = (char) i;
            int count = (int) text.toLowerCase().chars().filter(character -> character == letter).count();
            attributes[i - 'a'] = count;
            totalLetters += count;
        }

        for (int i = 0; i < attributes.length; i++) {
            attributes[i] /= totalLetters;
        }

        return attributes;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double[] getAttributes() {
        return attributes;
    }

    public void setAttributes(double[] attributes) {
        this.attributes = attributes;
    }
}
