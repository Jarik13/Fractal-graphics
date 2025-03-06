package panels;

import managers.FractalManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;

public class FractalPanel extends JPanel {
    private Point2D.Double startPoint = null;
    private boolean drawFractal = false;
    private final FractalManager fractalManager;
    private int iterations;
    private double lineSize;
    private double zoomFactor = 1.0;

    public FractalPanel() {
        this.fractalManager = new FractalManager();
        setBackground(Color.WHITE);

        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    zoomIn();
                } else {
                    zoomOut();
                }
            }
        });
    }

    public void setStartPoint(double x, double y) {
        startPoint = new Point2D.Double(x, y);
        drawFractal = false;
        repaint();
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public void setLineSize(double lineSize) {
        this.lineSize = lineSize;
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

    private void zoomIn() {
        zoomFactor *= 1.1;
        repaint();
    }

    private void zoomOut() {
        zoomFactor /= 1.1;
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
                fractalManager.drawDragonFractal(g, x, y, lineSize * zoomFactor, iterations);
            }
        }
    }

    public static JScrollPane getScrollablePanel(FractalPanel fractalPanel) {
        JScrollPane scrollPane = new JScrollPane(fractalPanel);
        scrollPane.setPreferredSize(new Dimension(800, 800));
        return scrollPane;
    }
}