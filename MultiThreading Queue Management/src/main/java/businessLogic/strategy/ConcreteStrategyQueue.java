package businessLogic.strategy;

import Model.Client;
import Model.Server;

import java.util.ArrayList;

public class ConcreteStrategyQueue implements Strategy {
    @Override
    public void addClient(ArrayList<Server> servers, Client client) {
        Server minServer = new Server();
        minServer.noClients = 100000000;

        for (Server server : servers) {
            if(server.noClients < minServer.noClients){
                minServer = server;
            }
        }
        minServer.addClient(client);
    }
}
