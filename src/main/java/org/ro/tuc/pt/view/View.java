package org.ro.tuc.pt.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Graphics;

public class View extends JFrame {
    private int nrButtons;
    private int nrFields;
    private int nrWrongLabels;
    public ArrayList<JButton> buttons;
    public ArrayList<JTextField> fields;
    private ArrayList<JLabel> wrongLabels;
    public JTextArea queueDisplay;
    public JTextArea clientsWaitingDisplay;

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.drawLine(566,50,566,870);
        g.drawLine(1275,50,1275,870);
    }

    public View(String title, int x, int y, int width, int height)
    {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(x,y,width,height);
        this.setResizable(false);

        JPanel onlyPanel = new JPanel();
        onlyPanel.setLayout(null);
        this.add(onlyPanel);
        onlyPanel.setBackground(Color.BLACK);
        onlyPanel.setBounds(0,0,width, height);

        nrButtons=2;
        nrFields=7;
        nrWrongLabels=8;
        int nrLabels = 8;
        int labelHeight=25;
        int wrongLabelHeight=20;
        int fieldWidth=480, fieldHeight = 50;

        JLabel[] labels = new JLabel[nrLabels];
        for(int i = 0; i< nrLabels; i++)
        {
            labels[i]=new JLabel();
            labels[i].setFont(new Font("TimesRoman",20,labelHeight));
            labels[i].setForeground(Color.WHITE);
            onlyPanel.add(labels[i]);
        }
        labels[0].setText("SETUP");
        labels[0].setBounds(50,20,100,labelHeight);
        labels[1].setText("Number of clients");
        labels[1].setBounds(50,85,500,labelHeight);
        labels[2].setText("Number of queues");
        labels[2].setBounds(50,190,500,labelHeight);
        labels[3].setText("Simulation time (seconds)");
        labels[3].setBounds(50,295,500,labelHeight);
        labels[4].setText("Min arrival time");
        labels[4].setBounds(50,400,180,labelHeight);
        labels[5].setText("Max arrival time");
        labels[5].setBounds(290,400,180,labelHeight);
        labels[6].setText("Min service time");
        labels[6].setBounds(50,505,500,labelHeight);
        labels[7].setText("Max service time");
        labels[7].setBounds(290,505,500,labelHeight);

        wrongLabels= new ArrayList<JLabel>();
        for(int i=0;i<nrWrongLabels;i++)
        {
            JLabel label=new JLabel();
            label.setFont(new Font("TimesRoman",20,wrongLabelHeight));
            label.setForeground(Color.RED);
            wrongLabels.add(label);
            onlyPanel.add(label);
            label.setVisible(false);
        }
        wrongLabels.get(0).setText("*invalid number of clients");
        wrongLabels.get(0).setBounds(50,150,250,fieldHeight);
        wrongLabels.get(1).setText("*invalid number of queues");
        wrongLabels.get(1).setBounds(50,255,250,fieldHeight);
        wrongLabels.get(2).setText("*invalid simulation time");
        wrongLabels.get(2).setBounds(50,360,250,fieldHeight);
        wrongLabels.get(3).setText("*invalid min arrival time");
        wrongLabels.get(3).setBounds(50,465,250,fieldHeight);
        wrongLabels.get(4).setText("*invalid max arrival time");
        wrongLabels.get(4).setBounds(290,465,250,fieldHeight);
        wrongLabels.get(5).setText("*invalid min service time");
        wrongLabels.get(5).setBounds(50,570,250,fieldHeight);
        wrongLabels.get(6).setText("*invalid max service time");
        wrongLabels.get(6).setBounds(290,570,250,fieldHeight);
        wrongLabels.get(7).setText("*please wait for the current simulation to end");
        wrongLabels.get(7).setBounds(130,740,400,fieldHeight);

        fields = new ArrayList<JTextField>();

        for(int i=0;i<nrFields;i++)
        {
            JTextField field=new JTextField();
            field.setFont(new Font("TimesRoman",20,labelHeight));
            field.setForeground(Color.WHITE);
            field.setOpaque(false);
            field.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.WHITE));
            fields.add(field);
            onlyPanel.add(field);
        }

        fields.get(0).setBounds(50,110,fieldWidth,fieldHeight);
        fields.get(1).setBounds(50,215,fieldWidth,fieldHeight);
        fields.get(2).setBounds(50,320,fieldWidth,fieldHeight);
        fields.get(3).setBounds(50,425,200,fieldHeight);
        fields.get(4).setBounds(290,425,200,fieldHeight);
        fields.get(5).setBounds(50,530,200,fieldHeight);
        fields.get(6).setBounds(290,530,200,fieldHeight);


        buttons = new ArrayList<JButton>();
        for(int i=0;i<nrButtons;i++)
        {
            JButton button=new JButton();
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("TimesRoman",20,20));
            buttons.add(button);
            onlyPanel.add(button);
        }

        buttons.get(0).setText("SAVE");
        buttons.get(0).setBounds(350,700, 150, 50);
        buttons.get(1).setText("START");
        buttons.get(1).setBounds(580, 20, 150, 50);

        Border border = BorderFactory.createEmptyBorder();

        JPanel scrollPanel = new JPanel();
        scrollPanel.setBackground(Color.BLACK);
        scrollPanel.setBounds(570,120,680, 700);
        scrollPanel.setBorder(border);
        onlyPanel.add(scrollPanel);

        queueDisplay= new JTextArea(10,30);
        queueDisplay.setBorder(border);
        queueDisplay.setFont(new Font("TimesRoman",20,25));
        queueDisplay.setText("");
        queueDisplay.setLineWrap(true);
        queueDisplay.setWrapStyleWord(true);
        queueDisplay.setForeground(Color.WHITE);
        queueDisplay.setBackground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(queueDisplay, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(680,700));
        scrollPane.setBorder(border);
        scrollPanel.add(scrollPane);

        JPanel scrollPanel2 = new JPanel();
        scrollPanel2.setBackground(Color.BLACK);
        scrollPanel2.setBounds(1280,120,400, 700);
        scrollPanel2.setBorder(border);
        onlyPanel.add(scrollPanel2);

        clientsWaitingDisplay= new JTextArea(10,30);
        clientsWaitingDisplay.setBorder(border);
        clientsWaitingDisplay.setFont(new Font("TimesRoman",20,25));
        clientsWaitingDisplay.setText("");
        clientsWaitingDisplay.setLineWrap(true);
        clientsWaitingDisplay.setWrapStyleWord(true);
        clientsWaitingDisplay.setForeground(Color.WHITE);
        clientsWaitingDisplay.setBackground(Color.BLACK);

        JScrollPane scrollPane2 = new JScrollPane(clientsWaitingDisplay, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane2.setPreferredSize(new Dimension(400,700));
        scrollPane2.setBorder(border);
        scrollPanel2.add(scrollPane2);
    }


    public void addButtonListener(ActionListener listener, int nrOfTheButton)
    {
        if(nrOfTheButton<nrButtons)
            buttons.get(nrOfTheButton).addActionListener(listener);
    }

    public int getNrButtons() {
        return nrButtons;
    }


    public void setWrongLabelVisible (boolean visible, int nrOfTheLabel)
    {
        if(nrOfTheLabel<nrWrongLabels)
            wrongLabels.get(nrOfTheLabel).setVisible(visible);
    }
}
