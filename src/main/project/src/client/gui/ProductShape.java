package src.client.gui;

import src.elements.Product;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class ProductShape {
    private int radius = 10;

    private double countdownX;
    private double countdownY;

    private double drawingX;
    private double drawingY;

    private final int LAMBDA = 15;

    private Shape circle;
    private Product product;

    ProductShape(Product product,  double countdownX, double countdownY) {
        this.countdownX = countdownX;
        this.countdownY = countdownY;
        drawingX = toDrawingX(product.getCoordinates().getX());
        drawingY = toDrawingY(product.getCoordinates().getY());
        circle = new Ellipse2D.Double(drawingX, drawingY, radius, radius);
        this.product = product;
    }

    private double toDrawingX(double x) {
        return countdownX + x * LAMBDA;
    }

    private double toDrawingY(double y) {
        return countdownY - y * LAMBDA;
    }

    boolean intersectsWithCursor(Point point) {
        double x = point.getX();
        double y = point.getY();
        return circle.contains(x, y);
    }

    Shape getShape() {
        return circle;
    }

    String[] getInfo() {
        return new String[] {"Имя Продукта = " + product.getName() + "\n",
                "Цена Продукта = " + product.getPrice() + "\n",
                "Владелец = " + product.getOwner().getName() + "\n",
                "Создатель = " + product.getHost()};
    }

    public void setRadius(int r) {
        radius = r;
        circle = new Ellipse2D.Double(drawingX, drawingY, radius, radius);
    }

    public int getRadius() {
        return radius;
    }

    public double getDrawingX() {
        return drawingX;
    }

    public double getDrawingY() {
        return drawingY;
    }

    public int getId() {
        return product.getId();
    }

    public Color getColor() {return product.getColor();}

    public String getHost() {
        return product.getHost();
    }

    @Override
    public boolean equals(Object obj) {
        return (product.getId() == ((ProductShape)obj).getId());
    }
}
