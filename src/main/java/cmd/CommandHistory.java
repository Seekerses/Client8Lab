package cmd;
import consolehandler.Outputer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * gives commands history
 *
 *
 */

public class CommandHistory implements Command{

    private static final long serialVersionUID = 1337000009L;

    private static List<String> history = new ArrayList<>();

    public void addCommand(String name) {
        history.add(name);
    }

    public String execute(String[] args) {
        try {
            if (args.length == 1) {
                return(Outputer.getString("ZeroArgs"));
            }
        }catch (NullPointerException e) {
            return String.valueOf((history.subList(Math.max(history.size() - 7, 0), history.size())));
        }
        return null;
    }

    /**
     * get name of command
     *
     * @return String
     */

    public String toString(){
        return Outputer.getString("history");
    }
}