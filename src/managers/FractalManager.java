package managers;

import java.awt.*;

public class FractalManager {
    public void drawStartPoint(Graphics g, int x, int y) {
        g.fillOval(x - 4, y - 4, 8, 8);
    }

    public void drawDragonFractal(Graphics g, int x, int y, double size, int depth) {
        String direction = "X";
        StringBuilder currentDirection = new StringBuilder(direction);

        for (int i = 0; i < depth; i++) {
            StringBuilder nextDirection = new StringBuilder();
            for (char c : currentDirection.toString().toCharArray()) {
                if (c == 'X') {
                    nextDirection.append("X+YF+");
                } else if (c == 'Y') {
                    nextDirection.append("-FX-Y");
                } else {
                    nextDirection.append(c);
                }
            }
            currentDirection = nextDirection;
        }

        drawLine(g, x, y, size, currentDirection.toString());
    }

    private void drawLine(Graphics g, int x, int y, double size, String direction) {
        int currentX = x;
        int currentY = y;
        int angle = 0;

        Color startColor = new Color(255, 215, 0);
        Color endColor = new Color(0, 128, 0);

        int totalSteps = (int) direction.chars().filter(ch -> ch == 'F').count();
        int step = 0;

        for (char c : direction.toCharArray()) {
            if (c == 'F') {
                int newX = currentX + (int) (size * Math.cos(Math.toRadians(angle)));
                int newY = currentY + (int) (size * Math.sin(Math.toRadians(angle)));

                float ratio = (float) step / totalSteps;
                int r = (int) (startColor.getRed() * (1 - ratio) + endColor.getRed() * ratio);
                int gValue = (int) (startColor.getGreen() * (1 - ratio) + endColor.getGreen() * ratio);
                int b = (int) (startColor.getBlue() * (1 - ratio) + endColor.getBlue() * ratio);
                g.setColor(new Color(r, gValue, b));

                g.drawLine(currentX, currentY, newX, newY);

                currentX = newX;
                currentY = newY;
                step++;
            } else if (c == '+') {
                angle += 90;
            } else if (c == '-') {
                angle -= 90;
            }
        }
    }
}
