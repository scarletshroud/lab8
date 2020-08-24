package src.client.gui;

import src.elements.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class VisualisationPanel extends JPanel {

    private int width;
    private int height;

    private double countdownX;
    private double countdownY;

    private final int CELL_SIZE = 15;

    private ArrayList<ProductShape> staticShapes;

    private ArrayList<ProductShape> appearingShapes;
    private ArrayList<ProductShape> removingShapes;

    private CollectionTableModel collectionTableModel;

    private  boolean initializationPaint;

    VisualisationPanel(CollectionTableModel collectionTableModel) {
       this.collectionTableModel = collectionTableModel;
       staticShapes = new ArrayList<>();
       appearingShapes = new ArrayList<>();
       removingShapes = new ArrayList<>();
       initializationPaint = true;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(Color.LIGHT_GRAY);

        width = getWidth();
        height = getHeight();

        countdownX = width / 2;
        countdownY = height / 2;

        drawGrid(g);
        drawAxis(g);
        drawElements(g);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.GRAY);

        for(int x = width / 2; x < width; x += CELL_SIZE) {
            g.drawLine(x, 0, x, height);
        }

        for(int x = width / 2; x > 0; x -= CELL_SIZE) {
            g.drawLine(x, 0, x, height);
        }

        for(int y = height / 2; y < height; y += CELL_SIZE) {
            g.drawLine(0, y, width, y);
        }

        for(int y = height / 2; y > 0; y -= CELL_SIZE){
            g.drawLine(0, y, width, y);
        }
    }

    private void drawAxis(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine((int) countdownX, 0, (int) countdownX, height);
        g.drawLine(0, (int) countdownY, width, (int) countdownY);
    }

    private void drawElements(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        staticShapes.clear();

        for (int i = 0; i < collectionTableModel.getRowCount(); i++) {
            Product product = collectionTableModel.getRow(i);
            ProductShape shape = new ProductShape(product, countdownX, countdownY);
            staticShapes.add(shape);
        }

        for (ProductShape s : appearingShapes) {
            if (staticShapes.contains(s)) {
                staticShapes.remove(s);
            }
        }

        for (ProductShape s : staticShapes) {
            g2.setColor(s.getColor());
            g2.draw(s.getShape());
            g2.fill(s.getShape());
        }

        ArrayList<ProductShape> shapesToRemove = new ArrayList<>();

        for (ProductShape s : appearingShapes) {
            int r = s.getRadius() + 1;
            if (r < 11) {
                s.setRadius(r);
                g2.setColor(s.getColor());
                g2.draw(s.getShape());
                g2.fill(s.getShape());
            } else {
                staticShapes.add(s);
                shapesToRemove.add(s);
            }
        }

        for(ProductShape s : shapesToRemove) {
            if(appearingShapes.contains(s)) {
                appearingShapes.remove(s);
            }
        }

        shapesToRemove.clear();

        for (ProductShape s : removingShapes) {

            if (staticShapes.contains(s)) {
                staticShapes.remove(s);
            }

            int r = s.getRadius() - 1;

            if (r > 0) {
                s.setRadius(r);
                g2.setColor(s.getColor());
                g2.draw(s.getShape());
                g2.fill(s.getShape());

            } else {
                shapesToRemove.add(s);
            }
        }

        for(ProductShape s : shapesToRemove) {
            if(removingShapes.contains(s)) {
                removingShapes.remove(s);
            }
        }
    }

    synchronized public void updateShapesToDraw(boolean isOpened) {

      /*  if (initializationPaint) {

            for (int i = 0; i < collectionTableModel.getRowCount(); i++) {
                Product product = collectionTableModel.getRow(i);
                ProductShape shape = new ProductShape(product, countdownX, countdownY);
                staticShapes.add(shape);
            }

            if (collectionTableModel.getRowCount() > 0) {
                initializationPaint = false;
                repaint();
            }

        } else { */

            if (isOpened) {

                ArrayList<ProductShape> updatedShapes = new ArrayList<>();

                for (int i = 0; i < collectionTableModel.getRowCount(); i++) {
                    Product product = collectionTableModel.getRow(i);
                    ProductShape shape = new ProductShape(product, countdownX, countdownY);
                    updatedShapes.add(shape);
                }

                for (ProductShape s : updatedShapes) {
                    if (!staticShapes.contains(s)) {
                        s.setRadius(0);
                        appearingShapes.add(s);
                    }
                }

                for (ProductShape s : staticShapes) {
                    if (!updatedShapes.contains(s)) {
                        removingShapes.add(s);
                        System.out.println("added");
                    }
                }

                while (!removingShapes.isEmpty() || !appearingShapes.isEmpty()) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                    repaint();
                }
            }
        }

    void drawInfo(Point point) {
        for (ProductShape s: staticShapes) {
          if (s.intersectsWithCursor(point)) {
              JOptionPane.showMessageDialog(this, s.getInfo());
          }
        }
    }

    int getRowIndexByShape(Point point) {

        for (ProductShape s: staticShapes) {
            if (s.intersectsWithCursor(point)) {
                return collectionTableModel.getRowIndex(s.getId());
            }
        }
        return -1;
    }

    public double getCountdownX() {
        return countdownX;
    }

    public double getCountdownY() {
        return countdownY;
    }

    public void changeCountdown(double offsetX, double offsetY) {
        countdownX += offsetX;
        countdownY += offsetY;
    }
}
