import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Fractal graphics!");
        initializeUI(frame);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private static void initializeUI(JFrame frame) {
        initializeInputPanel(frame);
    }

    private static void initializeInputPanel(JFrame frame) {
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addStartPoint = new JButton("Add start point");
        JButton repaintFractal = new JButton("Repaint fractal");
        JLabel lineSizeLabel = new JLabel("Line size:");
        JTextField lineSizeField = new JTextField(5);
        JButton clearPanel = new JButton("Clear");
        JButton drawDragon = new JButton("Draw dragon fractal");

        inputPanel.add(addStartPoint);
        inputPanel.add(repaintFractal);
        inputPanel.add(lineSizeLabel);
        inputPanel.add(lineSizeField);
        inputPanel.add(clearPanel);
        inputPanel.add(drawDragon);

        frame.add(inputPanel);
    }
}