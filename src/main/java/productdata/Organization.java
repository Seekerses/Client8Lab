package productdata;
import consolehandler.Outputer;
import controllers.data.FxOrganization;
import exceptions.NotUniqueFullName;
import exceptions.TooLargeFullName;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

/**
 * Class that represents a organization that produces a Product
 */
public class Organization implements Serializable {

    private static int orgId;
    private static final long serialVersionUID = 1337000020L;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String fullName; //Длина строки не должна быть больше 1404, Значение этого поля должно быть уникальным, Поле не может быть null
    private OrganizationType type; //Поле может быть null
    private Address postalAddress; //Поле может быть null

    /**
     * Standard constructor
     * @param id It uses for setting id of Organization when reading a table from files
     * @param name Name of Org
     * @param fullName Full Name of Org, that must be unique
     * @param type Type of Org
     * @param postalAddress Address of Org
     * @throws NullPointerException If you are trying to create an Organization with null name or fullName
     * @throws TooLargeFullName If you are trying to create an Organization with full name that a longer than 1404 symbols
     * @throws NotUniqueFullName If you are trying to create an Organization with a full name that already exist
     */
    public Organization(Integer id,String name, String fullName, OrganizationType type, Address postalAddress) throws NullPointerException,TooLargeFullName,NotUniqueFullName{
        if (name == null || fullName == null) throw new NullPointerException();
        if (fullName.length() >1404) throw new TooLargeFullName();
        if (UniqueController.check(fullName)) throw new NotUniqueFullName();
        if (id == null) {
            orgId++;
            this.id = orgId;
        }
        else{
            this.id = id;
        }
        this.name = name;
        this.fullName = fullName;
        this.type = type;
        this.postalAddress = postalAddress;
        UniqueController.put(this.fullName,this);
    }

    /**
     * Id getter
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Name getter
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * FullName getter
     * @return full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Address getter
     * @return Address
     */
    public Address getPostalAddress() {
        return postalAddress;
    }

    /**
     * Type getter
     * @return Type
     */
    public OrganizationType getType(){
        return type;
    }

    @Override
    public String toString(){
        return ("ID : " + id + Outputer.getString("OrgName") + name
                + Outputer.getString("OrgFullName") + fullName + Outputer.getString("OrgType") + (type == null ? Outputer.getString("NoValue") : type.toString())
                + Outputer.getString("OrgAddress") + (postalAddress == null ? Outputer.getString("NoValue") : postalAddress.toString()));
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof Organization){
            return ((Organization) obj).fullName.equals(fullName)
                    && ((Organization) obj).name.equals(name)
                    && ((Organization) obj).type.equals(type)
                    && ((Organization) obj).postalAddress.equals(postalAddress);
        }
        else return false;
    }

    public static void setOrgId(int value){
        orgId = value;
    }

    public FxOrganization getFxOrganization(){
        return new FxOrganization(new SimpleIntegerProperty(id),
                new SimpleStringProperty(name),
                new SimpleStringProperty(fullName),
                new SimpleObjectProperty<>(type),
                new SimpleObjectProperty<>(postalAddress == null ? null : postalAddress.getFxAdress()));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public void setPostalAddress(Address postalAddress) {
        this.postalAddress = postalAddress;
    }
}