package managers;

import java.awt.*;

public class FractalManager {
    public void drawStartPoint(Graphics g, int x, int y) {
        g.fillOval(x - 4, y - 4, 8, 8);
    }

    public void drawDragonFractal(Graphics g, int x, int y, double size, int depth, Color startColor, Color endColor) {
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

        drawLine(g, x, y, size, currentDirection.toString(), startColor, endColor);
    }

    public void drawJuliaFractal(Graphics g, int width, int height, int maxIteration, double zoom, double cRe, double cIm, Color startColor, Color endColor) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double zx = 1.5 * (x - (double) width / 2) / (0.5 * zoom * width);
                double zy = (y - (double) height / 2) / (0.5 * zoom * height);
                int iteration = 0;

                while (zx * zx + zy * zy < 4 && iteration < maxIteration) {
                    double temp = zx * zx - zy * zy + cRe;
                    zy = 2.0 * zx * zy + cIm;
                    zx = temp;
                    iteration++;
                }

                float ratio = (float) iteration / maxIteration;

                int r = (int) (startColor.getRed() * (1 - ratio) + endColor.getRed() * ratio);
                int gValue = (int) (startColor.getGreen() * (1 - ratio) + endColor.getGreen() * ratio);
                int b = (int) (startColor.getBlue() * (1 - ratio) + endColor.getBlue() * ratio);

                g.setColor(new Color(r, gValue, b));
                g.drawRect(x, y, 1, 1);
            }
        }
    }

    private void drawLine(Graphics g, int x, int y, double size, String direction, Color startColor, Color endColor) {
        int currentX = x;
        int currentY = y;
        int angle = 0;

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
