package cmd;
import client.UserSession;
import consolehandler.Outputer;
import consolehandler.TableController;

/**
 * remove all elements from the collection
 *
 *
 */

public class CommandClear implements Command, Preparable {

    private String password;
    private String login;

    private static final long serialVersionUID = 1337000002L;

    @Override
    public String execute(String[] args) {
        if (password == null || login == null){
            prepare(args);
            execute(args);
        }
        try {
            if (args.length == 1) {
                return (Outputer.getString("ZeroArgs"));
            }
        }catch (NullPointerException e) {
            if (TableController.getCurrentTable().getSize() == 0) {
                return (Outputer.getString("EmptyCollection"));
            } else {
                TableController.getCurrentTable().clear();
                return (Outputer.getString("CollectionCleared"));
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
        return Outputer.getString("clear");
    }

    @Override
    public void prepare(String[] args) {
        login = UserSession.getLogin();
        password = UserSession.getPassword();
    }
}
