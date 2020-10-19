package cmd;
import client.UserSession;
import consolehandler.Initializer;
import consolehandler.Outputer;
import consolehandler.TableController;
import productdata.Product;
import productdata.ReaderProductBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * get name of command
 *
 *
 */

public class CommandAdd implements Command, Preparable, Serializable {


    private String login;
    private String password;

    Product product;
    String key;
    private static final long serialVersionUID = 1337000000L;

    public CommandAdd(){
    }

    public CommandAdd(Product product, String key){
        this.product = product;
        this.key = key;
    }

    /**
     * insert product to hashtable
     *
     * @param args is key to new product
     */
    @Override
    public String execute(String[] args) {
        if (product == null || key == null){
            prepare(args);
            execute(args);
        }
        else {
            TableController.getCurrentTable().put(key, product);
            return (Outputer.getString("InsertionСomplete"));
        }
        return null;
    }

    /**
     * get name of command
     *
     * @return String
     */

    @Override
    public String toString() {
        return Outputer.getString("insert");
    }

    @Override
    public void prepare(String[] args) {
        login = UserSession.getLogin();
        password = UserSession.getPassword();
        if (product == null) {
            if (args == null) {
                String key;
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                try {
                    do {
                        System.out.println(Outputer.getString("Enterproductkey"));
                        key = reader.readLine();
                        if (key == null) System.out.println(Outputer.getString("ErrorNullKey"));
                    } while (key == null);
                    this.key = key;
                    this.product = ReaderProductBuilder.buildProduct(reader);
                } catch (Exception e) {
                    System.out.println(Outputer.getString("NullKeyException"));
                }
            } else {
                this.product = Initializer.build(args);
                this.key = args[0];
            }
        }
    }

}