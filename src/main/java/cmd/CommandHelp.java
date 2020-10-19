package cmd;

import consolehandler.Outputer;

import java.util.Arrays;

/**
 * Get info about all commands
 *
 *
 */

public class CommandHelp implements Command, Local {

    private static final long serialVersionUID = 1337000008L;

    @Override
    public String execute(String[] args){
        try {
            if (args.length == 1) {
                return (Outputer.getString("ZeroArgs"));
            }
        }catch (NullPointerException e){
            return (Outputer.getString("AllCommandsDescription"));
        }
        return null;
    }

    /**
     * get name of command
     *
     * @return String
     */

    public String toString(){
        return "help";
    }
}