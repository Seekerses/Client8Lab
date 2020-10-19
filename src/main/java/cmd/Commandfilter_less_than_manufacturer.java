package cmd;

import consolehandler.Outputer;
import consolehandler.TableController;
import productdata.Product;

import java.util.Iterator;
import java.util.Map;

/**
 * get elements which have lower manufacturer id than one's given
 *
 *
 */

public class Commandfilter_less_than_manufacturer implements Command {
    /**
     * counter will control a situation when all elements have higher manufacturer id than one's given
     */
    private int counter;
    private static final long serialVersionUID = 1337000006L;

    @Override
    public String execute(String[] args) {
        try {
            counter = 0;
            Iterator<Map.Entry<String, Product>> it = TableController.getCurrentTable().getSet().iterator();
            int i = Integer.parseInt(args[0]);
            StringBuilder stringBuilder = new StringBuilder("");
            while (it.hasNext()) {
                Map.Entry<String, Product> map = it.next();
                if (map.getValue().getManufacturer().getId() < i) {
                    counter++;
                    stringBuilder.append(TableController.getCurrentTable().get(map.getKey()).toString());
                }
            }
            if (counter == 0) {
                return (Outputer.getString("NoSuchElements"));
            }
            return stringBuilder.toString();
        }catch(NumberFormatException e){
            return (Outputer.getString("ArgIsNumber"));
        }
    }

    /**
     * get name of command
     *
     * @return String
     */

    @Override
    public String toString() {
        return Outputer.getString("filter_less_than_manufacturer");
    }
}
