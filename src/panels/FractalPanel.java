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
    private boolean drawDragonFractal = false;
    private boolean drawJuliaFractal = false;
    private final FractalManager fractalManager;
    private int iterations;
    private double lineSize;
    private double zoomFactor = 1.0;
    private Point lastMousePosition = null;
    private double offsetX = 0;
    private double offsetY = 0;
    private Color startColor;
    private Color endColor;
    private double cRe;
    private double cIm;

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
        drawDragonFractal = false;
        drawJuliaFractal = false;
        repaint();
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public void setLineSize(double lineSize) {
        this.lineSize = lineSize;
    }

    public void setGradientColors(Color start, Color end) {
        this.startColor = start;
        this.endColor = end;
    }

    public void setC(double cRe, double cIm) {
        this.cRe = cRe;
        this.cIm = cIm;
    }

    public void drawDragonFractal() {
        if (startPoint != null) {
            drawDragonFractal = true;
            repaint();
        }
    }

    public void drawJuliaFractal() {
        if (startPoint != null) {
            drawJuliaFractal = true;
            repaint();
        }
    }

    public void clearPanel() {
        startPoint = null;
        drawDragonFractal = false;
        drawJuliaFractal = false;
        repaint();
    }

    public void clearOnlyPanel() {
        drawDragonFractal = false;
        drawJuliaFractal = false;
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

            if (drawDragonFractal) {
                fractalManager.drawDragonFractal(g, x, y, lineSize * zoomFactor, iterations, startColor, endColor);
            }

            if (drawJuliaFractal) {
                fractalManager.drawJuliaFractal(g, getWidth(), getHeight(), iterations, zoomFactor, cRe, cIm, startColor, endColor);
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
