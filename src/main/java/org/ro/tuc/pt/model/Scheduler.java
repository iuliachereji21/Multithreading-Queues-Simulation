package org.ro.tuc.pt.model;

import java.util.ArrayList;
import java.util.Iterator;

public class Scheduler {
    private ArrayList<Queue> queues;
    private int nrQueues;

    public Scheduler(int nrQueues)
    {
        this.nrQueues=nrQueues;
        queues=new ArrayList<Queue>();
        for(int i=0;i<nrQueues;i++)
            queues.add(new Queue(i+1));
    }

    public void allocateClientToQueue(Client client)
    {
        int i=0,min=0;
        while(i<nrQueues)
        {
            if(queues.get(i).getWaitingPeriod().get()<queues.get(min).getWaitingPeriod().get())
                min=i;
            i++;
        }
        queues.get(min).addClient(client);
    }

    public ArrayList<Queue> getQueues()
    {
        return queues;
    }


    public int getNrClientsInTheQueues()
    {
        Iterator<Queue> iterator = queues.iterator();
        int sum=0;
        while(iterator.hasNext())
        {
            sum += iterator.next().getNrClientsInTheQueue();
        }
        return sum;
    }

    public String toString()
    {
        String str="";
        Iterator<Queue> iterator = queues.iterator();
        while(iterator.hasNext())
        {
            str=str+iterator.next().toString()+"\n";
        }
        return str;
    }
}
