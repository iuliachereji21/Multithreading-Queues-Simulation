package org.ro.tuc.pt.model;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class Queue implements Runnable
{
    private int queueID;
    private LinkedBlockingQueue<Client> clients;
    private AtomicInteger waitingPeriod;
    private int sumOfWaitingTimes; //sum of waiting times of all the clients that got into this queue

    public Queue(int queueID)
    {
        clients = new LinkedBlockingQueue<>();
        waitingPeriod= new AtomicInteger();
        waitingPeriod.set(0);
        this.queueID=queueID;
        sumOfWaitingTimes=0;
    }

    public void addClient (Client newClient)
    {
        clients.add(newClient);
        waitingPeriod.addAndGet(newClient.getServiceTime());
        sumOfWaitingTimes+=waitingPeriod.get();
    }

    public int getSumOfWaitingTimes() {return sumOfWaitingTimes; }
    public int getNrClientsInTheQueue() { return clients.size(); }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public String toString()
    {
        String str="Queue "+queueID+": ";
        if(clients.isEmpty())
        {
            str=str+"closed";
            return str;
        }
        Iterator<Client> iterator = clients.iterator();
        while(iterator.hasNext())
        {
            str=str+iterator.next().toString()+"; ";
        }
        return str;
    }

    public LinkedBlockingQueue<Client> getClients()
    {
        return clients;
    }

    @Override
    public void run() {
        while(true)
        {
            Client next=clients.peek();
            if (next!=null) //there is a client
            {
                try
                {
                    for(int i=0;i< next.getServiceTime();i++)
                    {
                        sleep(1000);
                        waitingPeriod.addAndGet(-1);
                    }
                    clients.poll();
                }
                catch (InterruptedException e) { }
            }
        }
    }
}
