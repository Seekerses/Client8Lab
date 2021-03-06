package cmd;
import client.UserSession;
import consolehandler.Outputer;
import consolehandler.TableController;

/**
 * removes element with given key
 *
 *
 */

public class CommandRemove implements Command {

    private String login = UserSession.getLogin();
    private String password = UserSession.getPassword();

    private static final long serialVersionUID = 1337000012L;

    @Override
    public String execute(String[] args) {
        int count = 0;
        for(String key : TableController.getCurrentTable().getKey()){
            if(key.equals(args[0])){
                count++;
            }
        }
        if(count==0){
            return ("No such key\nAvailable keys: " + TableController.getCurrentTable().getKey());
        }else{
            TableController.getCurrentTable().remove(args[0]);
            return (Outputer.getString("ElementRemoved"));
        }
    }

    /**
     * get name of command
     *
     * @return String
     */

    public String toString(){
        return Outputer.getString("remove_key");
    }
}
