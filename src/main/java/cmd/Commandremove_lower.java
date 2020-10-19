package cmd;
import consolehandler.Outputer;
import consolehandler.TableController;
import productdata.Product;

import java.util.*;

/**
 * delete all elements with lower id than one's given
 *
 * @author Alexandr
 */

public class Commandremove_lower implements Command{

    private static final long serialVersionUID = 1337000013L;

    /**
     * Iterates through hashtable and remove all elements with id lower than one's given
     *
     * @param args , gives id from input
     */

    @Override
    public String execute(String[] args) {
        try {
            Iterator<Map.Entry<String, Product>> it = TableController.getCurrentTable().getSet().iterator();
            int i = Integer.parseInt(args[0]);
            while (it.hasNext()) {
                Map.Entry<String, Product> map = it.next();
                if (map.getValue().getId() < i) {
                    it.remove();//against ConcurrentModificationException
                    TableController.getCurrentTable().remove(map.getKey());
                    return (Outputer.getString("LowerIdRemoved"));
                }
            }
        }catch (NumberFormatException e){
            return (Outputer.getString("ArgIsNumber"));
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
        return Outputer.getString("remove_lower");
    }
}
