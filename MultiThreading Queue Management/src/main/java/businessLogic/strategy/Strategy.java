package businessLogic.strategy;

import Model.Client;
import Model.Server;

import java.util.ArrayList;

public interface Strategy {

    public void addClient(ArrayList<Server> servers, Client client);
}
