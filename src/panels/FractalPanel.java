package panels;

import managers.FractalManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;

public class FractalPanel extends JPanel {
    private Point2D.Double startPoint = null;
    private boolean drawFractal = false;
    private final FractalManager fractalManager;
    private int iterations;
    private double lineSize;
    private double zoomFactor = 1.0;
    private Point lastMousePosition = null;
    private double offsetX = 0;
    private double offsetY = 0;

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

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastMousePosition = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                lastMousePosition = null;
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (lastMousePosition != null) {
                    double deltaX = e.getX() - lastMousePosition.getX();
                    double deltaY = e.getY() - lastMousePosition.getY();
                    offsetX += deltaX;
                    offsetY += deltaY;
                    lastMousePosition = e.getPoint();
                    repaint();
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
            int x = (int) Math.round(startPoint.x + offsetX);
            int y = (int) Math.round(startPoint.y + offsetY);

            g.setColor(Color.BLUE);
            fractalManager.drawStartPoint(g, x, y);

            if (drawFractal) {
                fractalManager.drawDragonFractal(g, x, y, lineSize * zoomFactor, iterations);
            }

            String zoomString = String.format("Zoom: %.0f%%", zoomFactor * 100);
            g.setColor(Color.BLACK);
            g.drawString(zoomString, getWidth() - 100, 20);
        }
    }

    public static JScrollPane getScrollablePanel(FractalPanel fractalPanel) {
        JScrollPane scrollPane = new JScrollPane(fractalPanel);
        scrollPane.setPreferredSize(new Dimension(800, 800));
        return scrollPane;
    }
}
