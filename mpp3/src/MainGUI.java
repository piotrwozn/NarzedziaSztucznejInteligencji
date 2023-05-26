import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {
    private JFrame frame;
    private JTextArea inputTextArea;
    private JButton classifyButton;
    private JLabel resultLabel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainGUI mainGUI = new MainGUI();
                mainGUI.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MainGUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        inputTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(inputTextArea);
        scrollPane.setBounds(10, 11, 414, 185);
        frame.getContentPane().add(scrollPane);

        classifyButton = new JButton("Classify Language");
        classifyButton.setBounds(10, 207, 139, 23);
        frame.getContentPane().add(classifyButton);

        resultLabel = new JLabel("");
        resultLabel.setBounds(159, 211, 265, 14);
        frame.getContentPane().add(resultLabel);

        // Train the model
        Main main = new Main();
        main.trainModel();

        classifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputText = inputTextArea.getText();
                if (!inputText.isEmpty()) {
                    Observation obs = new Observation("", inputText);
                    String predictedLanguage = main.classify(obs);
                    resultLabel.setText("Predicted language: " + predictedLanguage);
                } else {
                    resultLabel.setText("Please enter text to classify.");
                }
            }
        });
    }
}
