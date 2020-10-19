package productdata;
import clientserverdata.User;
import consolehandler.Outputer;
import consolehandler.TableController;
import controllers.data.FxProduct;
import exceptions.NegativePrice;
import javafx.beans.property.*;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Product implements Serializable {

    private static long idCounter;
    private static final long serialVersionUID = 1337000022L;
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float price; //Поле может быть null, Значение поля должно быть больше 0
    private UnitOfMeasure unitOfMeasure; //Поле не может быть null
    private Organization manufacturer; //Поле может быть null
    private User owner;

    /**
     * Standart Product constructor
     * @param id It uses for setting id of Product when reading a table from files
     * @param name Name of Product
     * @param coordinates Coordinates of Product
     * @param price Price of Product
     * @param unitOfMeasure Units of measure of Product
     * @param organization Organization that produces a Product
     * @param data It uses for setting creation date of Product when reading a table from files
     * @throws NullPointerException If you are trying to create a Product with null name, coordinates or units of measure arguments
     * @throws NegativePrice If you are trying to create a Product with negative price
     */
    public Product(Long id,String name, Coordinates coordinates, Float price, UnitOfMeasure unitOfMeasure, Organization organization,LocalDateTime data) throws NullPointerException,NegativePrice {
        if (id == null ) {
            idCounter++;
            this.id = idCounter;
        }
        else
        {
            this.id = id;
        }
        if (name == null || coordinates == null || unitOfMeasure == null) throw new NullPointerException();
        if (price != null && price < 0) throw new NegativePrice();
        this.name = name;
        this.price = price;
        this.coordinates = coordinates;
        if (data == null) {
            creationDate = LocalDateTime.now();
        }
        else {
            creationDate = data;
        }
        this.unitOfMeasure = unitOfMeasure;
        this.manufacturer = organization;
    }

    /**
     * Returns a fields value in format for saving
     * @return String in format for saving
     */
    public String out(){
        return id.toString() + ";" + name + ";" + coordinates.output() + ";" +
                (manufacturer == null ? ";;;;;;;":manufacturer.getId() + ";" +
                        (manufacturer.getPostalAddress() == null ? ";;;" : manufacturer.getPostalAddress().toString())
                        + ";" + manufacturer.getName() + ";" + manufacturer.getFullName() + ";"
                        + (manufacturer.getType() == null?"":manufacturer.getType().toString())) + ";" +
                unitOfMeasure.toString() + ";" + Outputer.getDate(creationDate) + ";" + (price == null ? "":Outputer.getNumber(price));
    }

    /**
     * Name getter
     * @return Name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return ("ID : " + id + " \n" + Outputer.getString("ProductName") + name + " \n" + Outputer.getString("Coordinates")
                + coordinates.toString() + " \n" + Outputer.getString("CreationDate") + Outputer.getDate(creationDate)
                + " \n" + Outputer.getString("Price") + (price == null ? Outputer.getString("NoValue"):Outputer.getNumber(price))+ " \n" + Outputer.getString("UnitOfMeasure")
                + unitOfMeasure.toString() + " \n" + Outputer.getString("Manufacturer") + (manufacturer == null ? Outputer.getString("NoValue") : manufacturer.toString()));
    }

    /**
     * Id getter
     * @return Id
     */
    public Long getId() {
        return id;
    }

    /**
     * Organization getter
     * @return Organization
     */
    public Organization getManufacturer() {
        return manufacturer;
    }

    /**
     * Price getter
     * @return Price
     */
    public Float getPrice() {
        return price;
    }

    /**
     * Coordinates getter
     * @return Coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Creation Date getter
     * @return Creation Date
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * UnitsOfMeasure getter
     * @return Units of Measure
     */
    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public int compareTo(Product product) {
        return id.intValue() - product.getId().intValue();
    }

    public static  void setIdCounter(int value){
        idCounter = value;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public FxProduct getFxProduct(){
        String key = TableController.getCurrentTable().getKeyByValue(this);
        if(key == null){
            key = "";
        }
        return new FxProduct(new SimpleStringProperty(key),
                new SimpleLongProperty(id),
                new SimpleStringProperty(name),
                new SimpleObjectProperty<controllers.data.FxCoordinates>(coordinates.getFxCoordinates()),
                new SimpleObjectProperty<LocalDateTime>(creationDate),
                new SimpleFloatProperty(price == null ? 0 : price),
                new SimpleObjectProperty<UnitOfMeasure>(unitOfMeasure),
                new SimpleObjectProperty<controllers.data.FxOrganization>(manufacturer == null ? null : manufacturer.getFxOrganization()),
                new SimpleObjectProperty<User>(owner), this);
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public void setManufacturer(Organization manufacturer) {
        this.manufacturer = manufacturer;
    }
}