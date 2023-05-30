package Model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Server extends Thread{
    private BlockingQueue<Client> clients;
    private int id = 0;
    private int waitingPeriod;
    public int noClients = 0;

    private int totalWaitingTimeServer = 0;
    private int nrOfFinishedClients = 0;
    private int totalServiceTime = 0;

    public Server(){
        clients = new ArrayBlockingQueue<>(100);
        noClients = clients.size();
        waitingPeriod = 0;
    }

    public Server(int maxNoClients, int id){
        clients = new ArrayBlockingQueue<>(maxNoClients);
        waitingPeriod = 0;
        this.id = id;
    }

    public void run() {
        while (true) {
            try {
                if(this.clients.size() > 0) {
                    Thread.sleep(clients.peek().getServiceTime() * 1000L);
                    Client clientOut = clients.take();
                    totalWaitingTimeServer += clientOut.getTotalTImeSpentInQueue();
                    waitingPeriod -= clientOut.getServiceTime();
                    totalServiceTime += clientOut.getServiceTime();
                    noClients--;
                    nrOfFinishedClients++;
                    System.out.println("Finished : " + clientOut.getId());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addClient(Client client){
        try {
            client.setTotalTImeSpentInQueue(waitingPeriod);
            clients.put(client);
            noClients++;
            waitingPeriod += client.getServiceTime();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getWaitingPeriod(){
        return waitingPeriod;
    }

    public void setWaitingPeriod(int waitingPeriod){
        this.waitingPeriod = waitingPeriod;
    }

    public int getIdServer(){
        return id;
    }

    public int getTotalWaitingTimeServer(){
        return totalWaitingTimeServer;
    }

    public int getNrOfFinishedClients() {
        return nrOfFinishedClients;
    }

    public int getTotalServiceTime() {
        return totalServiceTime;
    }

    public BlockingQueue<Client> getClients(){
        return clients;
    }

}
