package src.elements;

public class CreatorColor {

    private final int MAX_COLOR_CODE = 255;

    private int red;
    private int green;
    private int blue;

    CreatorColor() {
        red = (int) Math.random() * MAX_COLOR_CODE;
        green = (int) Math.random() * MAX_COLOR_CODE;
        blue = (int) Math.random() * MAX_COLOR_CODE;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
}
