package Model;

public class Client {
    private int id;
    private int arrivalTime; //timpul pana intra intr-o coada
    private int serviceTime; //timpul care trece cand e in varful cozii
    private int waitingTime; //cat a stat in coada
    private int totalTImeSpentInQueue = 0;
    public int modifiedServiceTime;

    public Client(int id, int arrivalTime, int serviceTime){
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.waitingTime = 0;
        this.modifiedServiceTime = serviceTime;
    }

    public Client() {
        this.id = 0;
        this.arrivalTime = 0;
        this.waitingTime = 0;
        this.waitingTime = 0;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setArrivalTime(int arrivalTime){
        this.arrivalTime = arrivalTime;
    }

    public void setServiceTime(int serviceTime){
        this.serviceTime = serviceTime;
        this.modifiedServiceTime = serviceTime;
    }

    public void setWaitingTime(int waitingTime){
        this.waitingTime = waitingTime;
    }

    public void setTotalTImeSpentInQueue(int v){
        totalTImeSpentInQueue = v;
    }

    public int getId(){
        return id;
    }

    public int getArrivalTime(){
        return arrivalTime;
    }

    public int getServiceTime(){
        return serviceTime;
    }

    public int getWaitingTime(){
        return waitingTime;
    }

    public int getTotalTImeSpentInQueue(){
        return totalTImeSpentInQueue;
    }
}
