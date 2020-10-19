package cmd;

import client.ClientController;
import consolehandler.Outputer;

import java.io.IOException;

/**
 * break the programm
 *
 *
 */

public class CommandExit implements Command, Local{

    @Override
    public String execute(String[] args) throws IOException {
        try {
            if (args.length == 1) {
                System.out.println(Outputer.getString("ZeroArgs"));
            }
        }catch (NullPointerException e) {
            ClientController.getClientSocket().close();
            System.out.println(Outputer.getString("EndOfProgram"));
            System.exit(0);
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
        return Outputer.getString("exit");
    }
}
