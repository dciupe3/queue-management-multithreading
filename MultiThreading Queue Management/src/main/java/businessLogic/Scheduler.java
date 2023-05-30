package businessLogic;

import Model.Client;
import Model.Server;
import businessLogic.strategy.ConcreteStrategyQueue;
import businessLogic.strategy.ConcreteStrategyTime;
import businessLogic.strategy.SelectionPolicy;
import businessLogic.strategy.Strategy;

import java.util.ArrayList;

public class Scheduler {
    private ArrayList<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer){
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;

        changeStrategy(SelectionPolicy.SHORTEST_TIME);
        servers = new ArrayList<Server>(maxNoServers);

        for(int i = 0; i < maxNoServers; i++){
            Server server = new Server(maxTasksPerServer, i);
            servers.add(server);
            server.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ConcreteStrategyQueue();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME){
            strategy = new ConcreteStrategyTime();
        }

    }

    public void dispatchTask(Client client){
        strategy.addClient(servers, client);
    }

    public double getAverageWaitingTime(){
        double averageWaitingTime = 0;
        int nr_clients = 0;
        for(Server s : servers){
            averageWaitingTime += s.getTotalWaitingTimeServer();
            nr_clients += s.getNrOfFinishedClients();
        }

        averageWaitingTime = averageWaitingTime / nr_clients;

        return averageWaitingTime;
    }

    public double getAverageServiceTime() {
        double averageServiceTime = 0;
        int nr_clients = 0;
        for(Server s : servers){
            averageServiceTime += s.getTotalServiceTime();
            nr_clients += s.getNrOfFinishedClients();
        }

        averageServiceTime = averageServiceTime / nr_clients;

        return averageServiceTime;
    }

    public int getTotalWaitingPeriod(){
        int totalWaitingPeriod = 0;
        for(Server s : servers){
            totalWaitingPeriod += s.getWaitingPeriod();
        }
        return totalWaitingPeriod;
    }

    public ArrayList<Server> getServers(){
        return servers;
    }

}
