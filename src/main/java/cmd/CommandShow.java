package cmd;
import consolehandler.Outputer;
import consolehandler.TableController;

/**
 * show all elements in String format
 *
 *
 */

public class CommandShow implements Command {

    private static final long serialVersionUID = 1337000015L;

    @Override
    public String execute(String[] args) {
        try {
            if (args.length == 1) {
                return (Outputer.getString("ZeroArgs"));
            }
        }catch (NullPointerException e) {
            if (TableController.getCurrentTable().getSize() == 0) {
                return (Outputer.getString("ShowEmptyCollection"));
            } else {
                return TableController.getCurrentTable().show();
            }
        }
        return null;
    }

    /**
     * get name of command
     *
     * @return String
     */

    public String toString(){
        return Outputer.getString("show");
    }
}
