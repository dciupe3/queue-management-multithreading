package businessLogic.strategy;

import Model.Client;
import Model.Server;

import java.util.ArrayList;

public class ConcreteStrategyTime implements Strategy{
    @Override
    public void addClient(ArrayList<Server> servers, Client client) {
        Server minTimeServer = new Server();
        minTimeServer.setWaitingPeriod(1000000000);
        for (Server server : servers) {
            if(server.getWaitingPeriod() < minTimeServer.getWaitingPeriod()){
                minTimeServer = server;
            }
        }
        //System.out.println(client.getId() + " " + minTimeServer.getIdServer());
        minTimeServer.addClient(client);
    }
}
