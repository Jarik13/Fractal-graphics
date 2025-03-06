package managers;

import java.awt.*;

public class FractalManager {
    private static final int ITERATIONS = 4;
    private static final int LINE_SIZE = 35;

    public void drawStartPoint(Graphics g, int x, int y) {
        g.fillOval(x - 4, y - 4, 8, 8);
    }

    public void drawDragonFractal(Graphics g, int x, int y) {
        drawDragonFractal(g, x, y, LINE_SIZE, ITERATIONS);
    }

    private void drawDragonFractal(Graphics g, int x, int y, int size, int depth) {
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

    private void drawLine(Graphics g, int x, int y, int size, String direction) {
        int currentX = x;
        int currentY = y;
        int angle = 0;

        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.CYAN, Color.MAGENTA, Color.PINK, Color.YELLOW};
        int colorIndex = 0;

        for (char c : direction.toCharArray()) {
            if (c == 'F') {
                int newX = currentX + (int) (size * Math.cos(Math.toRadians(angle)));
                int newY = currentY + (int) (size * Math.sin(Math.toRadians(angle)));

                g.setColor(colors[colorIndex % colors.length]);
                g.drawLine(currentX, currentY, newX, newY);

                currentX = newX;
                currentY = newY;
            } else if (c == '+') {
                angle += 90;
            } else if (c == '-') {
                angle -= 90;
            }
        }
    }
}
