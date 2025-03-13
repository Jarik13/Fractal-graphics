import panels.FractalPanel;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static FractalPanel fractalPanel;
    private static Color startColor = new Color(255, 215, 0);
    private static Color endColor = new Color(0, 128, 0);

    public static void main(String[] args) {
        JFrame frame = new JFrame("Fractal graphics!");
        frame.setLayout(new BorderLayout());
        initializeUI(frame);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private static void initializeUI(JFrame frame) {
        initializeInputPanel(frame);
        initializeFractalPanel(frame);
    }

    private static void initializeInputPanel(JFrame frame) {
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addStartPoint = new JButton("Add start point");
        JLabel lineSizeLabel = new JLabel("Line size:");
        JTextField lineSizeField = new JTextField(5);
        JLabel iterationsLabel = new JLabel("Iterations:");
        JTextField iterationsField = new JTextField(5);
        JButton clearPanel = new JButton("Clear");
        JButton drawDragon = new JButton("Draw dragon fractal");

        JButton startColorButton = new JButton("Start Color");
        JButton endColorButton = new JButton("End Color");

        JLabel cRealLabel = new JLabel("C real:");
        JTextField cRealField = new JTextField(5);
        JLabel cImageLabel = new JLabel("C image:");
        JTextField cImageField = new JTextField(5);

        JButton drawJulia = new JButton("Draw Julia fractal");

        inputPanel.add(addStartPoint);
        inputPanel.add(lineSizeLabel);
        inputPanel.add(lineSizeField);
        inputPanel.add(iterationsLabel);
        inputPanel.add(iterationsField);
        inputPanel.add(startColorButton);
        inputPanel.add(endColorButton);
        inputPanel.add(clearPanel);
        inputPanel.add(drawDragon);
        inputPanel.add(cRealLabel);
        inputPanel.add(cRealField);
        inputPanel.add(cImageLabel);
        inputPanel.add(cImageField);
        inputPanel.add(drawJulia);

        frame.add(inputPanel, BorderLayout.NORTH);

        addStartPoint.addActionListener(e -> {
            int centerX = fractalPanel.getWidth() / 2;
            int centerY = fractalPanel.getHeight() / 2;
            fractalPanel.setStartPoint(centerX, centerY);
        });

        clearPanel.addActionListener(e -> {
            fractalPanel.clearPanel();
            iterationsField.setText("");
            lineSizeField.setText("");
            cRealField.setText("");
            cImageField.setText("");
        });

        drawDragon.addActionListener(e -> {
            try {
                fractalPanel.clearOnlyPanel();
                int iterations = Integer.parseInt(iterationsField.getText());
                double lineSize = Double.parseDouble(lineSizeField.getText());
                fractalPanel.setIterations(iterations);
                fractalPanel.setLineSize(lineSize);
                fractalPanel.setGradientColors(startColor, endColor);
                fractalPanel.drawDragonFractal();
            } catch (NumberFormatException ex) {
                showInputError(frame, iterationsField, lineSizeField);
            }
        });

        drawJulia.addActionListener(e -> {
            try {
                fractalPanel.clearOnlyPanel();
                int iterations = Integer.parseInt(iterationsField.getText());
                double cRe = Double.parseDouble(cRealField.getText());
                double cIm = Double.parseDouble(cImageField.getText());
                fractalPanel.setIterations(iterations);
                fractalPanel.setC(cRe, cIm);
                fractalPanel.setGradientColors(startColor, endColor);
                fractalPanel.drawJuliaFractal();
            } catch (NumberFormatException ex) {
                if (cRealField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number of real part of c.", "Invalid Real Part C", JOptionPane.ERROR_MESSAGE);
                } else if (cImageField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number of image part of c.", "Invalid Image Part C", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number of iterations.", "Invalid Iterations Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        startColorButton.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(frame, "Choose Start Color", startColor);
            if (selectedColor != null) {
                startColor = selectedColor;
            }
        });

        endColorButton.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(frame, "Choose End Color", endColor);
            if (selectedColor != null) {
                endColor = selectedColor;
            }
        });
    }

    private static void initializeFractalPanel(JFrame frame) {
        fractalPanel = new FractalPanel();
        JScrollPane scrollPane = FractalPanel.getScrollablePanel(fractalPanel);
        frame.add(scrollPane, BorderLayout.CENTER);
    }

    private static void showInputError(JFrame frame, JTextField iterationsField, JTextField lineSizeField) {
        if (iterationsField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid number of iterations.", "Invalid Iterations Input", JOptionPane.ERROR_MESSAGE);
        } else if (lineSizeField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid line size.", "Invalid Line Size Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}