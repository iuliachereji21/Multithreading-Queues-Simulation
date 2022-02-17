package org.ro.tuc.pt.model;

public class Client {
    private int ID;
    private int arrivalTime;
    private int serviceTime;

    public Client(int ID, int arrivalTime, int serviceTime)
    {
        this.ID=ID;
        this.arrivalTime=arrivalTime;
        this.serviceTime=serviceTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public String toString()
    {
        return "("+ID+","+arrivalTime+","+serviceTime+")";
    }
}
