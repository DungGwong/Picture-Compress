package hundred;
public class Map {
    int red, green, blue;
    double value;
    int rgb;
    public Map() {
        this.setRgb(0);
        this.setValue(0,0);
        this.setBlue(0);
        this.setRed(0);
        this.setGreen(0);
    }
    public void setRgb(int rgb) {
        this.rgb=rgb;
    }
    public int getRgb() {
        return this.rgb;
    }
    public void setRed(int red) {
        this.red = red;
    }

    public int getRed() {
        return this.red;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getGreen() {
        return this.green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getBlue() {
        return this.blue;
    }

    public void setValue(double value_x, double value_y) {
        this.value = Math.sqrt(value_x + value_y);
    }

    public double getValue() {
        return value;
    }
}
