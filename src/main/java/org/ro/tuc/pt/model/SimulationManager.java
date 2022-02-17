package org.ro.tuc.pt.model;

import org.ro.tuc.pt.view.View;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static java.lang.Thread.sleep;

public class SimulationManager implements Runnable{
    private int nrClients;
    private int nrQueues;
    private int simulationTime;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int minServiceTime;
    private int maxServiceTime;
    private int currentTime;
    private double averageServiceTime;

    private Scheduler scheduler;
    private View view;
    private ArrayList<Client> clients;
    private File logEventsFile = new File("LogEvents.txt");

    public SimulationManager(View view, int nrClients,  int nrQueues, int simulationTime, int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime)
    {
        this.view=view;
        this.nrClients=nrClients;
        this.nrQueues=nrQueues;
        this.simulationTime=simulationTime;
        this.minArrivalTime=minArrivalTime;
        this.maxArrivalTime=maxArrivalTime;
        this.minServiceTime=minServiceTime;
        this.maxServiceTime=maxServiceTime;

        scheduler=new Scheduler(nrQueues);
        clients=generateRandomClients();
        currentTime=0;
    }

    private ArrayList<Client> generateRandomClients()
    {
        ArrayList<Client> clientList=new ArrayList<Client>();
        Random random = new Random();
        int arriveTime, serviceTime;
        averageServiceTime=0;
        for(int i=1;i<=nrClients;i++)
        {
            arriveTime= random.nextInt(maxArrivalTime+1-minArrivalTime)+minArrivalTime;
            serviceTime= random.nextInt(maxServiceTime+1-minServiceTime)+minServiceTime;
            averageServiceTime+=serviceTime;
            clientList.add(new Client(i,arriveTime,serviceTime));
        }
        averageServiceTime=averageServiceTime/nrClients;
        return clientList;
    }

    public String waitingClientsToString()
    {
        String str="Time "+currentTime+"\nWaiting clients: ";
        Iterator iterator = clients.iterator();
        while(iterator.hasNext())
        {
            str=str+iterator.next().toString()+"; ";
        }
        return str;
    }

    private void writeToLogEventFile(String str, boolean append)
    {
        try
        {
            FileWriter fw = new FileWriter(logEventsFile,append);
            PrintWriter pw = new PrintWriter(fw);
            pw.append(str);
            pw.close();
        }
        catch (IOException e)
        {
        }
    }

    private String finalDisplay(int peakTime, int maxNrClientsInTheQueues)
    {
        ArrayList<Queue> queues = scheduler.getQueues();
        String str = waitingClientsToString();  str=str+ "\nAverage waiting time: ";
        double avrNumber=0;
        Iterator<Queue> iterator = queues.iterator();
        while(iterator.hasNext())
            avrNumber+=iterator.next().getSumOfWaitingTimes();
        avrNumber=avrNumber/nrClients;
        String avr=""+avrNumber;
        int point=avr.indexOf('.');
        if(avrNumber == (int)avrNumber)
            avr=avr.substring(0,point);
        else if(avr.length()>point+3)
            avr=avr.substring(0,point+3);
        str = str + avr + "\nAverage service time: ";
        String avrS=""+ averageServiceTime;
        point=avrS.indexOf('.');
        if(averageServiceTime == (int)averageServiceTime)
            avrS=avrS.substring(0,point);
        else if(avrS.length()>point+3)
            avrS=avrS.substring(0,point+3);
        str = str + avrS + "\nPeak hour: "+peakTime+"\nClients in the queues at peak hour: "+maxNrClientsInTheQueues;
        view.clientsWaitingDisplay.setText(str);
        return str;
    }

    public String queuesStatusToString()
    {
        return scheduler.toString();
    }

    @Override
    public void run() {
        ArrayList<Queue> queues = scheduler.getQueues(); Iterator<Queue> iterator = queues.iterator();
        while(iterator.hasNext()) new Thread(iterator.next()).start();
        Iterator<Client> clientIterator; Client client; int peakTime=-1, maxNrClientsInTheQueues=0; String waitingClients, queuesString;
        writeToLogEventFile("", false);
        while(currentTime<simulationTime) {
            clientIterator=clients.iterator();
            while(clientIterator.hasNext()) {
                client=clientIterator.next();
                if(client.getArrivalTime()==currentTime) {
                    scheduler.allocateClientToQueue(client);
                    clientIterator.remove(); } }
            waitingClients=waitingClientsToString();
            queuesString=scheduler.toString();
            view.clientsWaitingDisplay.setText(waitingClients);
            view.queueDisplay.setText(queuesString);
            writeToLogEventFile(waitingClients+"\n"+queuesString+"\n",true);
            int nrClientsInTheQueues= scheduler.getNrClientsInTheQueues();
            if(nrClientsInTheQueues>maxNrClientsInTheQueues) {
                maxNrClientsInTheQueues=nrClientsInTheQueues;
                peakTime=currentTime;
            }
            currentTime++;
            try { sleep(1000); }
            catch (InterruptedException ex) { }
        }
        String str=finalDisplay(peakTime,maxNrClientsInTheQueues);
        view.clientsWaitingDisplay.setText(str);
        writeToLogEventFile(str,true);
    }
}
