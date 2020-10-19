package cmd;

import client.UserSession;
import consolehandler.Outputer;
import consolehandler.TableController;
import productdata.Product;
import productdata.ReaderProductBuilder;

import javax.jws.soap.SOAPBinding;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;

/**
 * Update whole element with given id
 *
 *
 */

public class CommandUpdate implements Command, Preparable{

    private static final long serialVersionUID = 1337000016L;

    /**
     * Iterates through all elements of collection and update element with given id
     *
     *
     */

    Product product;
    private String password;
    private String login;

    public CommandUpdate() {
    }

    public CommandUpdate(Product product){
        this.product = product;
    }

    @Override
    public String execute(String[] args) {

        if (product == null){
            prepare(args);
        }
        else {
            try {
                if (args == null || args[0] == null) {
                    return (Outputer.getString("EnterId"));
                }
                int counter = 0;
                Iterator<Map.Entry<String, Product>> it = TableController.getCurrentTable().getSet().iterator();
                int i = Integer.parseInt(args[0]);
                while (it.hasNext()) {
                    Map.Entry<String, Product> map = it.next();
                    if (map.getValue().getId() == i) {
                        counter++;
                        TableController.getCurrentTable().replace(map.getKey(), product);
                    }
                }
                if (counter == 0) {
                    return (Outputer.getString("NoSuchId"));
                }
            } catch (NumberFormatException e) {
                return (Outputer.getString("ArgIsNumber"));
            }
        }
        return "Element updated";
    }

    /**
     * get name of command
     *
     * @return String
     */

    @Override
    public String toString() {
        return Outputer.getString("update_id");
    }

    @Override
    public void prepare(String[] args) {
        login = UserSession.getLogin();
        password = UserSession.getPassword();
        if (product == null) {
        }
    }
}
