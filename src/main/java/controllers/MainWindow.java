package controllers;

import client.RequestManager;
import client.UserSession;
import clientserverdata.Reply;
import cmd.Command;
import cmd.CommandUpdate;
import cmd.Registerable;
import consolehandler.Outputer;
import consolehandler.TableController;
import consolehandler.cmdLists.StdCommandList;
import controllers.data.FxProduct;
import controllers.data.TableFiller;
import exceptions.NotUniqueFullName;
import exceptions.TooLargeFullName;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import productdata.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;

public class MainWindow {

    boolean editingMode  = false;

    @FXML
    private TableView<FxProduct> table;

    @FXML
    private TableColumn<FxProduct, Long> idCol;

    @FXML
    private TableColumn<FxProduct, String> keyCol;

    @FXML
    private TableColumn<FxProduct, String> nameCol;

    @FXML
    private TableColumn<FxProduct, Double> xCol;

    @FXML
    private TableColumn<FxProduct, Integer> yCol;

    @FXML
    private TableColumn<FxProduct, LocalDateTime> creationCol;

    @FXML
    private TableColumn<FxProduct, Float> priceCol;

    @FXML
    private TableColumn<FxProduct, String> unitCol;

    @FXML
    private TableColumn<FxProduct, String> manufacturerCol;

    @FXML
    private TableColumn<FxProduct, String> ownerCol;

    @FXML
    private TableColumn<FxProduct, String> fullName;

    @FXML
    private TableColumn<FxProduct, String> orgType;

    @FXML
    private TableColumn<FxProduct, String> orgStreet;

    @FXML
    private TableColumn<FxProduct, Long> orgX;

    @FXML
    private TableColumn<FxProduct, Integer> orgY;

    @FXML
    private TableColumn<FxProduct, Long> orgZ;

    @FXML
    private TableColumn<FxProduct, Integer> orhID;


    @FXML
    public void initialize(){
        table.setEditable(true);
        table.setItems(TableController.getFxProducts());

        idCol.setCellValueFactory(new PropertyValueFactory<>(Outputer.getString("id")));

        keyCol.setCellValueFactory(new PropertyValueFactory<>(Outputer.getString("key")));

        nameCol.setCellValueFactory(new PropertyValueFactory<>(Outputer.getString("name")));
        nameCol.setCellFactory(TextFieldTableCell.<FxProduct>forTableColumn());
        nameCol.setOnEditCommit((TableColumn.CellEditEvent<FxProduct, String> event) -> {
            TablePosition<FxProduct, String> pos = event.getTablePosition();

            int row = pos.getRow();
            Product product = event.getTableView().getItems().get(row).getOriginal();
            product.setName(event.getNewValue());

            CommandUpdate cmd = new CommandUpdate(product);
            RequestManager.makePreparedRequest(cmd);

        });

        xCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FxProduct, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<FxProduct, Double> param) {
                return param.getValue().getCoordinates().xProperty().asObject();
            }
        });
        xCol.setCellFactory(new Callback<TableColumn<FxProduct, Double>, TableCell<FxProduct, Double>>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TextFieldTableCell(new StringConverter() {
                    @Override
                    public String toString(Object object) {
                        return object.toString();
                    }

                    @Override
                    public Object fromString(String string) {
                        Double number = 0D;
                        try {
                            number = Double.valueOf(string);
                        }
                        catch (Exception e){

                        }
                        return number;
                    }
                });
            }
        });
        xCol.setOnEditCommit((TableColumn.CellEditEvent<FxProduct, Double> event) -> {
            TablePosition<FxProduct, Double> pos = event.getTablePosition();

            int row = pos.getRow();
            Product product = event.getTableView().getItems().get(row).getOriginal();
            product.getCoordinates().setX(event.getNewValue());

            CommandUpdate cmd = new CommandUpdate(product);
            RequestManager.makePreparedRequest(cmd);

        });


        yCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FxProduct, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<FxProduct, Integer> param) {
                return param.getValue().getCoordinates().yProperty().asObject();
            }
        });
        yCol.setCellFactory(new Callback<TableColumn<FxProduct, Integer>, TableCell<FxProduct, Integer>>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TextFieldTableCell(new StringConverter() {
                    @Override
                    public String toString(Object object) {
                        return object.toString();
                    }

                    @Override
                    public Object fromString(String string) {
                        Integer number = 0;
                        try {
                            number = Integer.valueOf(string);
                        }
                        catch (Exception e){

                        }
                        return number;
                    }
                });
            }
        });
        yCol.setOnEditCommit((TableColumn.CellEditEvent<FxProduct, Integer> event) -> {
            TablePosition<FxProduct, Integer> pos = event.getTablePosition();

            int row = pos.getRow();
            Product product = event.getTableView().getItems().get(row).getOriginal();
            product.getCoordinates().setY(event.getNewValue());

            CommandUpdate cmd = new CommandUpdate(product);
            RequestManager.makePreparedRequest(cmd);

        });

        creationCol.setCellValueFactory(new PropertyValueFactory<>(Outputer.getString("creationDate")));

        priceCol.setCellValueFactory(new PropertyValueFactory<>(Outputer.getString("price")));
        priceCol.setCellFactory(new Callback<TableColumn<FxProduct, Float>, TableCell<FxProduct, Float>>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TextFieldTableCell(new StringConverter() {
                    @Override
                    public String toString(Object object) {
                        return object.toString();
                    }

                    @Override
                    public Object fromString(String string) {
                        Float number = 0F;
                        try {
                            number = Float.valueOf(string);
                        }
                        catch (Exception e){

                        }
                        return number;
                    }
                });
            }
        });
        priceCol.setOnEditCommit((TableColumn.CellEditEvent<FxProduct, Float> event) -> {
            TablePosition<FxProduct, Float> pos = event.getTablePosition();

            int row = pos.getRow();
            Product product = event.getTableView().getItems().get(row).getOriginal();
            product.setPrice(event.getNewValue());

            CommandUpdate cmd = new CommandUpdate(product);
            RequestManager.makePreparedRequest(cmd);

        });

        unitCol.setCellValueFactory(new PropertyValueFactory<>(Outputer.getString("unitOfMeasure")));
        unitCol.setCellFactory(ComboBoxTableCell.forTableColumn(
                Outputer.getString("KILOGRAMS"),
                Outputer.getString("CENTIMETERS"),
                Outputer.getString("PCS"),
                Outputer.getString("LITERS"),
                Outputer.getString("MILLILITERS")));
        unitCol.setOnEditCommit((TableColumn.CellEditEvent<FxProduct, String> event) -> {
            TablePosition<FxProduct, String> pos = event.getTablePosition();

            int row = pos.getRow();
            Product product = event.getTableView().getItems().get(row).getOriginal();
            product.setUnitOfMeasure(UnitOfMeasure.valueOf(event.getNewValue()));

            CommandUpdate cmd = new CommandUpdate(product);
            RequestManager.makePreparedRequest(cmd);

        });

        manufacturerCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FxProduct, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<FxProduct, String> param) {
                        if (param.getValue().getManufacturer() != null) {
                            return param.getValue().getManufacturer().nameProperty();
                        }
                        else {
                            return new SimpleStringProperty(Outputer.getString("<undefined>"));
                        }
                    }
                });
        manufacturerCol.setCellFactory(TextFieldTableCell.forTableColumn());
        manufacturerCol.setOnEditCommit((TableColumn.CellEditEvent<FxProduct, String> event) -> {

            TablePosition<FxProduct, String> pos = event.getTablePosition();

            int row = pos.getRow();
            Product product = event.getTableView().getItems().get(row).getOriginal();
            if (product.getManufacturer() != null){
                product.getManufacturer().setName(event.getNewValue());
                CommandUpdate cmd = new CommandUpdate(product);
                RequestManager.makePreparedRequest(cmd);
            }
            else {
                if(organizationFullName == null) {
                    table.applyCss();
                    table.layout();
                    Platform.runLater(() -> table.edit(event.getTablePosition().getRow(), fullName));
                    createOrganization(event.getNewValue(), null,product);

                }
                else {
                    createOrganization(event.getNewValue(), organizationFullName,product);
                }
            }
        });

        manufacturerCol.setOnEditCancel((TableColumn.CellEditEvent<FxProduct, String> event) ->{
            nully();
        });


        ownerCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FxProduct, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FxProduct, String> param) {
                if (param.getValue().getOwner().getUsername() != null) {
                    return new SimpleStringProperty(param.getValue().getOwner().getUsername());
                } else return new SimpleStringProperty(Outputer.getString("<undefined>"));
            }
        });
        fullName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FxProduct, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FxProduct, String> param) {
                if (param.getValue().getManufacturer() != null) {
                    return param.getValue().getManufacturer().fullNameProperty();
                }
                else {
                    return new SimpleStringProperty(Outputer.getString("<undefined>"));
                }
            }
        });
        fullName.setCellFactory(TextFieldTableCell.forTableColumn());
        fullName.setOnEditCommit((TableColumn.CellEditEvent<FxProduct, String> event) -> {


            TablePosition<FxProduct, String> pos = event.getTablePosition();

            int row = pos.getRow();
            Product product = event.getTableView().getItems().get(row).getOriginal();
            if (product.getManufacturer() != null){
                product.getManufacturer().setFullName(event.getNewValue());
                CommandUpdate cmd = new CommandUpdate(product);
                RequestManager.makePreparedRequest(cmd);
            }
            else {

                if(organizationName == null) {
                    createOrganization(null, event.getNewValue(),product);
                    table.layout();
                    table.edit(event.getTablePosition().getRow(), manufacturerCol);
                }
                else {
                    createOrganization(organizationName, event.getNewValue(),product);
                }
            }
        });
        fullName.setOnEditCancel((TableColumn.CellEditEvent<FxProduct, String> event) ->{
            nully();
        });

        orgType.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FxProduct, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FxProduct, String> param) {
                if (param.getValue().getManufacturer() != null) {
                    return param.getValue().getManufacturer().typeProperty().asString();
                }
                else {
                    return new SimpleObjectProperty<OrganizationType>().asString();
                }
            }
        });
        orgType.setCellFactory(ComboBoxTableCell.forTableColumn(Outputer.getString("PUBLIC"),Outputer.getString("TRUST"),Outputer.getString("PRIVATE_LIMITED_COMPANY")));

        orgType.setOnEditCommit((TableColumn.CellEditEvent<FxProduct, String> event) -> {

            if (event.getRowValue().getOriginal().getManufacturer() == null){
                organizationType = OrganizationType.valueOf(event.getNewValue());
                Platform.runLater(() -> {
                    table.edit(event.getTablePosition().getRow(), manufacturerCol);
                });
            }
            else {
                event.getRowValue().getOriginal().getManufacturer().setFullName(event.getNewValue());
                CommandUpdate cmd = new CommandUpdate(event.getRowValue().getOriginal());
                RequestManager.makePreparedRequest(cmd);
            }
        });
        orgType.setOnEditCancel((TableColumn.CellEditEvent<FxProduct, String> event) ->{
            nully();
        });

        orgStreet.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FxProduct, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FxProduct, String> param) {
                if (param.getValue().getManufacturer() != null && param.getValue().getManufacturer().getPostalAddress() != null) {
                    return param.getValue().getManufacturer().getPostalAddress().streetProperty();
                } else {
                    return new SimpleStringProperty(Outputer.getString("undefined"));
                }
            }
        });
        orgStreet.setCellFactory(TextFieldTableCell.forTableColumn());
        orgStreet.setOnEditCommit((TableColumn.CellEditEvent<FxProduct, String> event) -> {
            if (event.getRowValue().getOriginal().getManufacturer() == null){
                createAddress(event.getNewValue(),event.getRowValue().getOriginal());
                Platform.runLater(() -> {
                    table.edit(event.getTablePosition().getRow(), manufacturerCol);
                });
            }
            else {
                event.getRowValue().getOriginal().getManufacturer().setPostalAddress(new Address(event.getNewValue(),
                        event.getRowValue().getOriginal().getManufacturer().getPostalAddress() == null ? null :
                                event.getRowValue().getOriginal().getManufacturer().getPostalAddress().getTown()));
                CommandUpdate cmd = new CommandUpdate(event.getRowValue().getOriginal());
                RequestManager.makePreparedRequest(cmd);
            }
        });
        orgStreet.setOnEditCancel((TableColumn.CellEditEvent<FxProduct, String> event) ->{
            nully();
        });

        orgX.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FxProduct, Long>, ObservableValue<Long>>() {
            @Override
            public ObservableValue<Long> call(TableColumn.CellDataFeatures<FxProduct, Long> param) {
                if (param.getValue().getManufacturer() != null
                        && param.getValue().getManufacturer().getPostalAddress() != null
                        && param.getValue().getManufacturer().getPostalAddress().getTown() != null){
                    return param.getValue().getManufacturer().getPostalAddress().getTown().xProperty().asObject();
                }
                else {
                    return new SimpleLongProperty().asObject();
                }
            }
        });
        orgX.setCellFactory(new Callback<TableColumn<FxProduct, Long>, TableCell<FxProduct, Long>>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TextFieldTableCell(new StringConverter() {
                    @Override
                    public String toString(Object object) {
                        return object.toString();
                    }

                    @Override
                    public Object fromString(String string) {
                        Long number = 0L;
                        try {
                            number = Long.valueOf(string);
                        }
                        catch (Exception e){

                        }
                        return number;
                    }
                });
            }
        });
        orgX.setOnEditCommit((TableColumn.CellEditEvent<FxProduct, Long> event) -> {
            if (event.getRowValue().getOriginal().getManufacturer() == null
                    || event.getRowValue().getOriginal().getManufacturer().getPostalAddress() == null
                    || event.getRowValue().getOriginal().getManufacturer().getPostalAddress().getTown() == null){
                createLocation(event.getNewValue(),ycoordintate,zcoordinate,event.getRowValue().getOriginal());
                if (zcoordinate != null){
                    Platform.runLater(() -> {
                        table.edit(event.getTablePosition().getRow(), orgStreet);
                    });
                }
                else {
                    Platform.runLater(() -> {
                        table.edit(event.getTablePosition().getRow(), orgZ);
                    });
                }
            }
            else {
                event.getRowValue().getOriginal().getManufacturer().getPostalAddress().getTown().setX(event.getNewValue());
                CommandUpdate cmd = new CommandUpdate(event.getRowValue().getOriginal());
                RequestManager.makePreparedRequest(cmd);
            }
        });
        orgX.setOnEditCancel((TableColumn.CellEditEvent<FxProduct, Long> event) ->{
            nully();
        });

        orgY.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FxProduct, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<FxProduct, Integer> param) {
                if (param.getValue().getManufacturer() != null
                        && param.getValue().getManufacturer().getPostalAddress() != null
                        && param.getValue().getManufacturer().getPostalAddress().getTown() != null){
                    return param.getValue().getManufacturer().getPostalAddress().getTown().yProperty().asObject();
                }
                else {
                    return new SimpleIntegerProperty().asObject();
                }
            }
        });
        orgY.setCellFactory(new Callback<TableColumn<FxProduct, Integer>, TableCell<FxProduct, Integer>>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TextFieldTableCell(new StringConverter() {
                    @Override
                    public String toString(Object object) {
                        return object.toString();
                    }

                    @Override
                    public Object fromString(String string) {
                        Integer number = 0;
                        try {
                            number = Integer.valueOf(string);
                        }
                        catch (Exception e){

                        }
                        return number;
                    }
                });
            }
        });
        orgY.setOnEditCommit((TableColumn.CellEditEvent<FxProduct, Integer> event) -> {
            if (event.getRowValue().getOriginal().getManufacturer() == null
                    || event.getRowValue().getOriginal().getManufacturer().getPostalAddress() == null
                    || event.getRowValue().getOriginal().getManufacturer().getPostalAddress().getTown() == null){
                createLocation(xcoordinate,event.getNewValue(),zcoordinate,event.getRowValue().getOriginal());
                Platform.runLater(() -> {
                    table.edit(event.getTablePosition().getRow(), orgStreet);
                });
            }
            else {
                event.getRowValue().getOriginal().getManufacturer().getPostalAddress().getTown().setY(event.getNewValue());
                CommandUpdate cmd = new CommandUpdate(event.getRowValue().getOriginal());
                RequestManager.makePreparedRequest(cmd);
            }
        });
        orgY.setOnEditCancel((TableColumn.CellEditEvent<FxProduct, Integer> event) ->{
            nully();
        });

        orgZ.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FxProduct, Long>, ObservableValue<Long>>() {
            @Override
            public ObservableValue<Long> call(TableColumn.CellDataFeatures<FxProduct, Long> param) {
                if (param.getValue().getManufacturer() != null
                        && param.getValue().getManufacturer().getPostalAddress() != null
                        && param.getValue().getManufacturer().getPostalAddress().getTown() != null){
                    return param.getValue().getManufacturer().getPostalAddress().getTown().zProperty().asObject();
                }
                else {
                    return new SimpleLongProperty().asObject();
                }
            }
        });
        orgZ.setCellFactory(new Callback<TableColumn<FxProduct, Long>, TableCell<FxProduct, Long>>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TextFieldTableCell(new StringConverter() {
                    @Override
                    public String toString(Object object) {
                        return object.toString();
                    }

                    @Override
                    public Object fromString(String string) {
                        Long number = 0L;
                        try {
                            number = Long.valueOf(string);
                        }
                        catch (Exception e){

                        }
                        return number;
                    }
                });
            }
        });
        orgZ.setOnEditCommit((TableColumn.CellEditEvent<FxProduct, Long> event) -> {
            if (event.getRowValue().getOriginal().getManufacturer() == null
                    || event.getRowValue().getOriginal().getManufacturer().getPostalAddress() == null
                    || event.getRowValue().getOriginal().getManufacturer().getPostalAddress().getTown() == null){
                createLocation(xcoordinate,ycoordintate,event.getNewValue(),event.getRowValue().getOriginal());
                if (xcoordinate != null){
                    Platform.runLater(() -> {
                        table.edit(event.getTablePosition().getRow(), orgStreet);
                    });
                }
                else {
                    Platform.runLater(() -> {
                        table.edit(event.getTablePosition().getRow(), orgX);
                    });
                }
            }
            else {
                event.getRowValue().getOriginal().getManufacturer().getPostalAddress().getTown().setZ(event.getNewValue());
                CommandUpdate cmd = new CommandUpdate(event.getRowValue().getOriginal());
                RequestManager.makePreparedRequest(cmd);
            }
        });
        orgZ.setOnEditCancel((TableColumn.CellEditEvent<FxProduct, Long> event) ->{
            nully();
        });

        orhID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FxProduct, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<FxProduct, Integer> param) {
                if (param.getValue().getManufacturer() != null){
                    return param.getValue().getManufacturer().idProperty().asObject();
                }
                else {
                    return new SimpleIntegerProperty().asObject();
                }
            }
        });

        TableFiller.fill();
        updateVisual();
        table.getItems().addListener(new ListChangeListener<FxProduct>() {
            @Override
            public void onChanged(Change<? extends FxProduct> c) {
                Platform.runLater(() ->{
                    updateVisual();
                });

            }
        });

        ObservableList<String> commands = FXCollections.observableArrayList(Outputer.getString("help"),Outputer.getString("insert"), Outputer.getString("remove_key"),
                Outputer.getString("clear"), Outputer.getString("execute_script"), Outputer.getString("exit"), Outputer.getString("filter_less_than_manufacturer"), Outputer.getString("replace_if_greater"),
                Outputer.getString("info"), Outputer.getString("group_counting_by_coordinates"), Outputer.getString("show"), Outputer.getString("history"),Outputer.getString("min_by_name"),Outputer.getString("remove_lower"), Outputer.getString("login"), Outputer.getString("register"));
        commandInput.setItems(commands);

        usernameText.setText(UserSession.getLogin());
    }

    private String organizationName = null;
    private String organizationFullName = null;
    private OrganizationType organizationType = null;
    private Address address = null;
    private String street = null;
    private Location town = null;
    private Long xcoordinate = null;
    private Integer ycoordintate = null;
    private Long zcoordinate = null;

    void nully(){
        organizationFullName = null;
        organizationName = null;
        organizationType = null;
        address = null;
        street = null;
        town = null;
        xcoordinate = null;
        ycoordintate = null;
        zcoordinate = null;
        TableFiller.fill();
    }

    void createOrganization(String name, String fullName, Product product){
        if (organizationName == null || organizationFullName == null){
            organizationName = name;
            organizationFullName = fullName;
            System.out.println( organizationFullName + " " + organizationName);
        }
        if (organizationName != null && organizationFullName != null){
            try {
                Organization org = new Organization(null,organizationName,organizationFullName,organizationType,address);
                product.setManufacturer(org);
                CommandUpdate cmd = new CommandUpdate(product);
                RequestManager.makePreparedRequest(cmd);
            } catch (TooLargeFullName | NotUniqueFullName tooLargeFullName) {
                TableFiller.fill();
            }

        }
    }

    void createAddress(String street, Product product){
        this.street = street;
        this.address = new Address(street, town);
        if (product.getManufacturer() != null){
            product.getManufacturer().setPostalAddress(address);
            CommandUpdate cmd = new CommandUpdate(product);
            RequestManager.makePreparedRequest(cmd);
        }
    }

    void createLocation(Long x, Integer y, Long z, Product product){
        if ( x == null || z == null){
            xcoordinate = x;
            zcoordinate = z;
            ycoordintate = y;
        }
        if (xcoordinate != null && zcoordinate != null){

            town = new Location(xcoordinate,ycoordintate == null ? 0: ycoordintate,zcoordinate);
            if (product.getManufacturer() != null && product.getManufacturer().getPostalAddress() != null){
                product.getManufacturer().getPostalAddress().setTown(town);
                CommandUpdate cmd = new CommandUpdate(product);
                RequestManager.makePreparedRequest(cmd);
            }
        }
    }

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane visualBox;

    private HashMap<TextArea, FxProduct> map;

    public void updateVisual(){

        int i = 0;
        int j = 0;
        map = new HashMap<>();
        visualBox.getChildren().clear();
        visualBox.gridLinesVisibleProperty().setValue(true);
        visualBox.getRowConstraints().add(new RowConstraints(100));
        Iterator<FxProduct> iterator = table.getItems().iterator();
        FxProduct product = null;
        while(iterator.hasNext()){
            if (i > 2){
                visualBox.getRowConstraints().add(new RowConstraints(100));
                j++;
                i = 0;
            }
            product = iterator.next();
            Tooltip tooltip = new Tooltip();
            double code = 0;
                    for (byte a : product.getOriginal().getOwner().getUsername().getBytes()){
                        code += (double) a;
                    }
            code = code / (product.getOriginal().getOwner().getUsername().getBytes().length * 127);
            ;
            tooltip.setText(product.getOriginal().toString());
            TextArea area = new TextArea();
            area.setTooltip(tooltip);
            area.setEditable(false);
            map.put(area,product);
            area.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    FxProduct product1 = map.get((TextArea) event.getSource());
                    System.out.println(product1.toString());
                    table.scrollTo(map.get((TextArea) event.getSource()));
                    table.getSelectionModel().select(product1);
                    System.out.println(table.getItems().indexOf(product1));
                    table.requestFocus();
                }
            });
            visualBox.add(area,i,j);
            Canvas canvas = new Canvas(40,40);

            GraphicsContext ctx = canvas.getGraphicsContext2D();
            ctx.setStroke(Color.color(code,code,code));
            ctx.beginPath();
            ctx.setLineWidth(15);
            ctx.moveTo(product.getCoordinates().getX(),0);
            ctx.lineTo(product.getCoordinates().getY(),0);
            ctx.moveTo(product.getCoordinates().getX(),0);
            ctx.stroke();
            visualBox.add(canvas,i,j);
            i++;
        }


    }

    @FXML
    private ComboBox<String> commandInput;

    @FXML
    private TextField argumentsInput;

    @FXML
    private Button sendButton;

    @FXML
    private TextArea resultOutput;

    @FXML
    private Button addButton;

    @FXML
    private Text usernameText;


    @FXML
    void executeCommand(ActionEvent event) {

        Command cmd = StdCommandList.getCommand(commandInput.getValue());
        String[] args = argumentsInput.getText().split(" ");
        Reply result = null;
        if ("".equals(argumentsInput.getText())) args = null;
        try {
            result = RequestManager.makeRequest(cmd,args);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        resultOutput.setText(result != null ? result.getAnswer() : null);

        if(cmd instanceof Registerable){
            if ("Approved".equals((result != null ? result.getAnswer().split(",") : new String[0])[0])){
                usernameText.setText(UserSession.getLogin());
            }
        }
    }

    @FXML
    void addProduct(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/fxmls/addDialog.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Lab 7 Reg");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
