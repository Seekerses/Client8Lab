package cmd;
import consolehandler.Outputer;
import consolehandler.TableController;

/**
 * gives info about date of collection's creation, collection's size and collection's type
 *
 *
 */

public class CommandInfo implements Command {

    private static final long serialVersionUID = 1337000010L;

    @Override
    public String execute(String[] args) {
        try {
            if (args.length == 1) {
                return (Outputer.getString("ZeroArgs"));
            }
        }catch (NullPointerException e) {
            return ("Size of collection: " + TableController.getCurrentTable().getSize() + "\n"
                    + "Type of collection: " + TableController.getCurrentTable().getType() + "\n" +
                    "Date of creation: " + TableController.getCurrentTable().getCreationDate().toString() + "\nCommand complete...");
        }
        return null;
    }

    /**
     * get name of command
     *
     * @return String
     */

    public String toString(){
        return Outputer.getString("info");
    }
}
