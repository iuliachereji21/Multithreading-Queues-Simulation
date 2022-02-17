package org.ro.tuc.pt;

import org.ro.tuc.pt.controller.Controller;
import org.ro.tuc.pt.view.View;

import java.awt.*;
public class App
{
    public static void main( String[] args )
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        View myView= new View("Queue simulation", ((int) screenSize.getWidth()/17), ((int) screenSize.getHeight()/17),1700, 900);
        myView.setVisible(true);
        Controller c = new Controller(myView);
    }
}