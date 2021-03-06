package client;

import clientserverdata.Reply;
import clientserverdata.Request;
import cmd.Command;
import cmd.Local;
import cmd.Preparable;
import cmd.Registerable;
import consolehandler.CommandController;
import consolehandler.CommandInterpreter;

import java.io.IOException;

public class RequestManager {

    public static Reply makeRequest(Command command, String[] args){

        Command cmd = null;
        try {
            cmd = command.getClass().newInstance();
        }
        catch (InstantiationException e){
            System.out.println("Command have not nullify constructor.");
        }
        catch (IllegalAccessException e){
            System.out.println("Why do we lost access to cmd ?");
        }
        if (command instanceof Local){
            try {
                String res = command.execute(args);
                if (res != null) {
                    return new Reply(null,res);
                }
            }
            catch (IOException e){
                System.out.println("Wrong arguments");
                return null;
            }
        }
        if (command instanceof Preparable){
            if (cmd != null) {
                ((Preparable) cmd).prepare(args);
            }
        }
        Reply result = ClientController.handleRequest(new Request(cmd, args, UserSession.getLogin(), UserSession.getPassword()));
        if (cmd instanceof Registerable && result != null){
            switch (result.getAnswer().split(",")[0]){
                case "Approved" :
                    UserSession.setLogin(result.getAnswer().split(",")[1]);
                    UserSession.setPassword(result.getAnswer().split(",")[2]);
                    System.out.println("You are logged in as " + UserSession.getLogin());
                    break;
                case "Wrong" :
                    System.out.println("Wrong login or password" + UserSession.getLogin());
                    break;
                case "Existed" :
                    System.out.println("User already exist, please use log in.");
                    break;
            }
            return result;
        }
        if (result != null) {
            if (result.getAnswer() != null) System.out.println(result.getAnswer());
            if (result.getProducts() != null){
                result.getProducts().forEach((k,v) -> {
                    if ((k == null)) {
                        System.out.print("");
                    } else {
                        System.out.println(v + " " + k);
                    }
                });
            }
            return result;
        }
        return  null;
    }

    public static Reply makePreparedRequest(Command command){
        if (command instanceof Preparable){
            ((Preparable) command).prepare(null);
        }
        Reply result = ClientController.handleRequest(new Request(command, null, UserSession.getLogin(), UserSession.getPassword()));
        if (command instanceof Registerable && result != null){
            switch (result.getAnswer().split(",")[0]){
                case "Approved" :
                    UserSession.setLogin(result.getAnswer().split(",")[1]);
                    UserSession.setPassword(result.getAnswer().split(",")[2]);
                    System.out.println("You are logged in as " + UserSession.getLogin());
                    break;
                case "Wrong" :
                    System.out.println("Wrong login or password" + UserSession.getLogin());
                    break;
                case "Existed" :
                    System.out.println("User already exist, please use log in.");
                    break;
            }
            return result;
        }
        if (result != null) {
            if (result.getAnswer() != null) System.out.println(result.getAnswer());
            if (result.getProducts() != null){
                result.getProducts().forEach((k,v) -> {
                    if ((k == null)) {
                        System.out.print("");
                    } else {
                        System.out.println(v + " " + k);
                    }
                });
            }
            return result;
        }
        return  null;
    }
}
