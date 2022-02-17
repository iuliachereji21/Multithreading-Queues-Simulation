package org.ro.tuc.pt.controller;

import org.ro.tuc.pt.model.SimulationManager;
import org.ro.tuc.pt.view.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private View view;
    private SimulationManager simulationManager;
    private Thread mainThread;

    public Controller(View view) {
        this.view = view;
        int nrButtons = view.getNrButtons();
        for (int j = 0; j < nrButtons; j++)
            view.addButtonListener(new ButtonsListener(), j);
    }

    class ButtonsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==view.buttons.get(0)){ //save
                int input[] = new int[7]; int i=0;
                try {
                    while(i<7) { //read from user interface the 7 fields
                        input[i]=Integer.parseInt(view.fields.get(i).getText());
                        view.setWrongLabelVisible(false,i);
                        i++; }
                    if(input[4]<input[3]) {
                        view.setWrongLabelVisible(true,3);
                        view.setWrongLabelVisible(true,4);
                        i=8; }
                    if(input[6]<input[5]) {
                        view.setWrongLabelVisible(true,6);
                        view.setWrongLabelVisible(true,5);
                        i=8; } } catch(NumberFormatException ex) { view.setWrongLabelVisible(true,i); }
                if(i==7){ //all ok
                    if(mainThread!=null && mainThread.isAlive())
                        view.setWrongLabelVisible(true,7);
                    else {
                        view.setWrongLabelVisible(false,7);
                        simulationManager=new SimulationManager(view, input[0], input[1], input[2], input[3], input[4], input[5], input[6]);
                        view.clientsWaitingDisplay.setText(simulationManager.waitingClientsToString());
                        view.queueDisplay.setText(simulationManager.queuesStatusToString()); } }
                view.revalidate(); view.repaint(); }
            else if(e.getSource()==view.buttons.get(1)){ //start
                if(mainThread!=null && mainThread.isAlive()) view.setWrongLabelVisible(true,7); //there is a thread running
                else {
                    view.setWrongLabelVisible(false,7);
                    mainThread= new Thread(simulationManager);
                    mainThread.start(); } view.revalidate(); view.repaint(); } }
    }
}
