package productdata;
import consolehandler.Outputer;
import controllers.data.FxCoordinates;
import exceptions.InvalidYCoordinate;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.Serializable;

/**
 * Class that represents a coordinates of a Product
 */
public class Coordinates implements Serializable {
    private static final long serialVersionUID = 1337000018L;
    private double x;
    private Integer y; //Значение поля должно быть больше -150, Поле не может быть null

    /**
     * Standard constructor
     * @param x x coordinate
     * @param y y coordinate
     * @throws NullPointerException If you are trying to create a Coordinate with a null Y argument
     * @throws InvalidYCoordinate If you are trying to create a Coordinate with an Y argument less than -150
     */
    public Coordinates(double x, Integer y) throws NullPointerException, InvalidYCoordinate {
        if (y == null) throw new NullPointerException();
        if (y < -150 ) throw new InvalidYCoordinate();
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return (" x = " + Outputer.getNumber(x) + " y = " + Outputer.getNumber(y));
    }

    /**
     * Returns a fields of object in format for saving
     * @return String in in format for saving
     */
    public String output(){
        return Outputer.getNumber(x) + ";" + Outputer.getNumber(y);
    }

    /**
     *  Getter x
     * @return x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Getter y
     * @return y coordinate
     */
    public Integer getY() {
        return y;
    }

    public FxCoordinates getFxCoordinates(){
        return new FxCoordinates(new SimpleDoubleProperty(x),
                new SimpleIntegerProperty(y));
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}