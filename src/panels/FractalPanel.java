package panels;

import managers.FractalManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class FractalPanel extends JPanel {
    private Point2D.Double startPoint = null;
    private boolean drawFractal = false;
    private final FractalManager fractalManager;
    private int iterations;

    public FractalPanel() {
        this.fractalManager = new FractalManager();
        setBackground(Color.WHITE);
    }

    public void setStartPoint(double x, double y) {
        startPoint = new Point2D.Double(x, y);
        drawFractal = false;
        repaint();
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public void drawDragonFractal() {
        if (startPoint != null) {
            drawFractal = true;
            repaint();
        }
    }

    public void clearPanel() {
        startPoint = null;
        drawFractal = false;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (startPoint != null) {
            g.setColor(Color.BLUE);
            int x = (int) Math.round(startPoint.x);
            int y = (int) Math.round(startPoint.y);
            fractalManager.drawStartPoint(g, x, y);

            if (drawFractal) {
                fractalManager.drawDragonFractal(g, x, y, iterations);
            }
        }
    }
}