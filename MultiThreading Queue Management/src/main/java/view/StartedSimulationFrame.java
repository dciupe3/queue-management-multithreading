package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.CharArrayWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class StartedSimulationFrame extends JFrame {

    JPanel mainPanel = new JPanel();
    JPanel panel = new JPanel();
    JScrollPane scrollPane;


    private int nrOfQueues;
    private int maxnrOfClients;
    private ArrayList<Integer> nrClients = new ArrayList<Integer>();
    private HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

    ArrayList<ArrayList<IdServiceClass>> serverClients = new ArrayList<>();

    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;

    private JProgressBar progressBar;

    public JTextField averageWaitingTimeJTextField = new JTextField("Average waiting time = ");
    public JTextField averageServiceTimeJTextField = new JTextField("Average service time = ");
    public JTextField peakHourJTextField = new JTextField("Peak hour = ");
    public JLabel time = new JLabel();

    public StartedSimulationFrame(){
        setSize(800, 800);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));
        panel = new JPanel();
        scrollPane = new JScrollPane(panel);

        //partea de jos cu textbox
        panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1, 2));
        panel3 = new JPanel();
        panel3.setLayout(new GridLayout(3,1));
        panel3.add(averageWaitingTimeJTextField);
        panel3.add(averageServiceTimeJTextField);
        panel3.add(peakHourJTextField);
        panel4 = new JPanel();
        //panel4.setLayout(new GridLayout(2, 1));

        progressBar = new JProgressBar(0,0,60);
        panel4.add(progressBar);
        //panel4.add(time);

        panel2.add(panel3);
        panel2.add(panel4);


        mainPanel.add(scrollPane);
        mainPanel.add(panel2);
        this.add(mainPanel);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void updateFrame(){

        panel.removeAll();

        for(int i = 0; i  < nrOfQueues; i++){
            JPanel p = new JPanel();
            p.setLayout(new GridLayout(maxnrOfClients + 1, 1));
            p.setBorder(new EmptyBorder(5, 5, 5, 5));
            Integer integer = Integer.valueOf(i);
            JLabel l = new JLabel("Queue " + integer.toString());
            //trebuie aliniat la centru
            p.add(l);

            for(int j = 0; j < nrClients.get(i).intValue(); j++){
                JButton b = new JButton("[" + serverClients.get(i).get(j).id_ + "]   :   " + serverClients.get(i).get(j).serviceTime_);
                if(j == 0){
                    if(serverClients.get(i).get(j).serviceTime_ == 1)
                        b.setBackground(Color.RED);
                    else
                        b.setBackground(Color.GREEN);
                }
                else
                {
                    b.setBackground(Color.YELLOW);
                }
                p.add(b);
            }
            panel.add(p);
        }

        revalidate();
        repaint();
    }

    public void updateProgressBar(int currentTime, int maxTime){
        progressBar.setMaximum(maxTime);
        progressBar.setString("Time " + currentTime);
        progressBar.setStringPainted(true);
        progressBar.setString("Current time = " + currentTime);
        progressBar.setValue(currentTime);
    }

    public void updateData(int nrOfQueues, int maxnrOfClients, ArrayList<Integer> nrClients, ArrayList<ArrayList<IdServiceClass>> serverClients){
        this.nrOfQueues = nrOfQueues;
        this.maxnrOfClients = maxnrOfClients;
        this.nrClients = nrClients;
        panel.setLayout(new GridLayout(1, nrOfQueues));
        //System.out.println(this.nrOfQueues + " " + this.maxnrOfClients + " " + this.nrClients + "\n\n");
        this.serverClients = serverClients;
    }

    public void open(){
        this.setTitle("Simulation");
        this.setVisible(true);
    }

}
