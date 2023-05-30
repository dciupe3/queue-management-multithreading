package businessLogic;

import Model.Client;
import Model.Server;
import businessLogic.strategy.SelectionPolicy;
import controller.Controller;
import view.IdServiceClass;
import view.SimulationFrame;
import view.StartedSimulationFrame;

import java.io.*;
import java.util.*;

public class SimulationManager extends Thread{

    public int timeLimit = 60; //timpul la care se termina simularea
    public int maxServiceTime = 4;
    public int minServiceTime = 2;
    public int maxArrivalTime = 30;
    public int minArrivalTime = 2;
    public int numberOfServers = 3; //nr de cozi
    public int numberOfClients = 4;
    public int maxNrOfClients;

    public Controller controller;

    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

    public File file = new File("file.txt");
    public PrintWriter writer = null;

    private Scheduler scheduler;

    private SimulationFrame simulationFrame;
    private StartedSimulationFrame startedSimulationFrame;

    private ArrayList<Client> generatedClients;

    private int peakHour = 0;
    private double averageWaitingTime = 0;
    private double averageServiceTime = 0;

    public SimulationManager(){
        openFile();

        //generatedClients = new ArrayList<>(numberOfClients);

        //scheduler = new Scheduler(numberOfServers, numberOfClients);
        //scheduler.changeStrategy(selectionPolicy);  --se face in update scheduler

        simulationFrame = new SimulationFrame();
        startedSimulationFrame = new StartedSimulationFrame();
        controller = new Controller(simulationFrame, this, startedSimulationFrame);
        simulationFrame.open();

        //generateNRandomClients(); --se face de la buton
    }

    public void generateNRandomClients(){

        for(int i = 0; i < numberOfClients; i++){
            Client client = new Client(i, 0, 0);
            Random random = new Random();
            Random random1 = new Random();
            client.setArrivalTime(random.nextInt(maxArrivalTime - minArrivalTime) + minArrivalTime);
            client.setServiceTime(random1.nextInt(maxServiceTime - minServiceTime) + minServiceTime);
            writer.print("(" + client.getId() + ", " + client.getServiceTime() + ", " + client.getArrivalTime() + "); ");
            //System.out.println("(" + client.getId() + ", " + client.getServiceTime() + ", " + client.getArrivalTime() + ") ");
            generatedClients.add(client);
        }
        generatedClients.sort(new Comparator<Client>() {
            @Override
            public int compare(Client o1, Client o2) {
                return o1.getArrivalTime() - o2.getArrivalTime();
            }
        });

        writer.println("\nClientii sortati in functie de arrival time: ");
        for (Client client : generatedClients) {
            writer.print("(" + client.getId() + ", " + client.getServiceTime() + ", " + client.getArrivalTime() + "); ");
        }

    }

    public void openFile(){
        try {
            writer = new PrintWriter(file);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void updateData(int n1, int n2, int n3, int n4, int n5, int n6, int n7){
        numberOfServers = n1;
        numberOfClients = n2;
        timeLimit = n3;
        minServiceTime = n4;
        maxServiceTime = n5;
        minArrivalTime = n6;
        maxArrivalTime = n7;
        maxNrOfClients = numberOfClients;
    }

    public void updateScheduler(){
        generatedClients = new ArrayList<>(numberOfClients);
        scheduler = new Scheduler(numberOfServers, numberOfClients);
        scheduler.changeStrategy(selectionPolicy);
    }

    public void run(){
        System.out.println("Simulation time : " + timeLimit + "\nClientNr: " + numberOfClients + "\nQueuesNr: "
                + numberOfServers + "\nMinArrivalTime: " + minArrivalTime + "\nMaxArrivalTime: " + maxArrivalTime
                + "\nMinServiceTime" + minServiceTime + "\nMaxServiceTime: " + maxServiceTime + "\n");
        ArrayList<Client> removeList = new ArrayList<Client>();
        int currentTime = 0;
        int maxWaitingPeriod = 0;
        try{
            while(currentTime < timeLimit){
                writer.println("\n\nTime : " + currentTime);
                for (Client client : generatedClients) {
                    if(client.getArrivalTime() <= currentTime){
                        scheduler.dispatchTask(client);
                        removeList.add(client);
                        //generatedClients.remove(client); //nu e bine...il sterg dupa
                        numberOfClients--;
                    }
                    else{
                        break;
                    }
                }
                //stergem clientii din lista initiala
                for(Client client : removeList){
                    generatedClients.remove(client);
                }
                removeList.clear();

                //PEAK HOUR
                if(scheduler.getTotalWaitingPeriod() > maxWaitingPeriod){
                    maxWaitingPeriod = scheduler.getTotalWaitingPeriod();
                    peakHour = currentTime;
                }

                System.out.println("Time : " + currentTime);

                startedSimulationFrame.updateProgressBar(currentTime, timeLimit - 1);
                currentTime++;

                //scriere in fisier

                updateDataFromFrame();
                startedSimulationFrame.updateFrame();

                showWaitingClients();
                showQueues();


                if(isEverythingEmpty() && generatedClients.isEmpty()){
                    System.out.println("DONE");
                    break;
                }
                Thread.sleep(1000L);
            }

            averageWaitingTime = scheduler.getAverageWaitingTime();
            averageServiceTime = scheduler.getAverageServiceTime();

            startedSimulationFrame.averageWaitingTimeJTextField.setText("Average waiting time = " + averageWaitingTime);
            startedSimulationFrame.averageServiceTimeJTextField.setText("Average service time = " + averageServiceTime);
            startedSimulationFrame.peakHourJTextField.setText("Peak hour = " + peakHour);

            System.out.println("\n\nAverage waiting time : " + averageWaitingTime);
            writer.println("\n\nAverage waiting time : " + averageWaitingTime);
            System.out.println("Average service time : " + averageServiceTime);
            writer.println("Average service time : " + averageServiceTime);
            System.out.println("Peak hour : " + peakHour);
            writer.println("Peak hour : " + peakHour);
            writer.close();

        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void updateDataFromFrame(){

        ArrayList<Integer> nrClientsPerServer = new ArrayList<Integer>(numberOfServers);

        ArrayList<ArrayList<IdServiceClass>> serverClients = new ArrayList<>();
        for(Server s : scheduler.getServers()){
            Integer nr = Integer.valueOf(s.noClients);
            ArrayList<IdServiceClass> cl = new ArrayList<>();
            for(Client c : s.getClients()){
                cl.add(new IdServiceClass(c.getId(), c.modifiedServiceTime));
            }
            serverClients.add(cl);
            nrClientsPerServer.add(nr);
        }

        //System.out.println(numberOfServers + " " + maxNrOfClients + " " + nrClientsPerServer + "\n\n");

        startedSimulationFrame.updateData(numberOfServers, maxNrOfClients, nrClientsPerServer, serverClients);
    }

    private boolean isEverythingEmpty(){
        for(Server server : scheduler.getServers()){
            if(!server.getClients().isEmpty())
                return false;
        }
        return true;
    }

    private void showWaitingClients(){
        writer.print("Waiting clients: ");
        for(Client client : generatedClients){
            writer.print("(" + client.getId() + ", " + client.getServiceTime() + ", " + client.getArrivalTime() + "); ");
        }
    }

    private void showQueues(){
        for(Server server : scheduler.getServers()){
            writer.print("\nQueue " + server.getIdServer() + ": ");
            for(Client client : server.getClients()){
                writer.print("(" + client.getId() + ", " + client.modifiedServiceTime + ", " + client.getArrivalTime() + "); ");
            }
            if(server.getClients().size() > 0)
                server.getClients().peek().modifiedServiceTime--;
        }
    }

}
