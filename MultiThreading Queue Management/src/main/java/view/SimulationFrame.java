package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SimulationFrame extends JFrame {
    private JTextField nrOfClientsJTextField = new JTextField();
    private JTextField nrOfQueuesJTextField = new JTextField();
    private JTextField simulationTimeJTextField = new JTextField();
    private JTextField minArrivalTimeJTextField = new JTextField();
    private JTextField maxArrivalTimeJTextField = new JTextField();
    private JTextField minServiceTimeJTextField = new JTextField();
    private JTextField maxServiceTimeJTextField = new JTextField();
    private JButton generateDataJButton = new JButton("GENERATE DATA");
    private JButton startSimulationJButton = new JButton("START SIMULATION");

    private JLabel nrOfClientsJLabel = new JLabel("number of clients");
    private JLabel nrOfQueuesJLabel = new JLabel("number of queues");
    private JLabel simulationTimeJLabel = new JLabel("simulation time");
    private JLabel minArrivalTimeJLabel = new JLabel("min arrival time");
    private JLabel maxArrivalTimeJLabel = new JLabel("max arrival time");
    private JLabel minServiceTimeJLabel = new JLabel("min service time");
    private JLabel maxServiceTimeJLabel = new JLabel("max service time");

    public SimulationFrame(){

        startSimulationJButton.setEnabled(false);

        setSize(500, 500);

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new GridLayout(2, 1));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(7, 2));

        textPanel.add(nrOfClientsJTextField);
        textPanel.add(nrOfClientsJLabel);
        textPanel.add(nrOfQueuesJTextField);
        textPanel.add(nrOfQueuesJLabel);
        textPanel.add(simulationTimeJTextField);
        textPanel.add(simulationTimeJLabel);
        textPanel.add(minArrivalTimeJTextField);
        textPanel.add(minArrivalTimeJLabel);
        textPanel.add(maxArrivalTimeJTextField);
        textPanel.add(maxArrivalTimeJLabel);
        textPanel.add(minServiceTimeJTextField);
        textPanel.add(minServiceTimeJLabel);
        textPanel.add(maxServiceTimeJTextField);
        textPanel.add(maxServiceTimeJLabel);

        mainPanel.add(textPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2));

        buttonPanel.add(generateDataJButton);
        buttonPanel.add(startSimulationJButton);

        mainPanel.add(buttonPanel);

        this.add(mainPanel);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void open(){
        this.setTitle("First frame");
        this.setVisible(true);
    }

    public void addGenerateButtonListener(ActionListener listener){
        generateDataJButton.addActionListener(listener);
    }

    public void addStartButtonListener(ActionListener listener){
        startSimulationJButton.addActionListener(listener);
    }

    public void setStartButtonEnable(){
        startSimulationJButton.setEnabled(true);
    }

    public int getNrOfClients(){
        Integer integer = Integer.valueOf(nrOfClientsJTextField.getText());
        return integer.intValue();
    }

    public int getNrOfQueues(){
        Integer integer = Integer.valueOf(nrOfQueuesJTextField.getText());
        return integer.intValue();
    }

    public int getSimulationTime(){
        Integer integer = Integer.valueOf(simulationTimeJTextField.getText());
        return integer.intValue();
    }

    public int getMinArrivalTime(){
        Integer integer = Integer.valueOf(minArrivalTimeJTextField.getText());
        return integer.intValue();
    }

    public int getMaxArrivalTime(){
        Integer integer = Integer.valueOf(maxArrivalTimeJTextField.getText());
        return integer.intValue();
    }

    public int getMinServiceTime(){
        Integer integer = Integer.valueOf(minServiceTimeJTextField.getText());
        return integer.intValue();
    }

    public int getMaxServiceTime(){
        Integer integer = Integer.valueOf(maxServiceTimeJTextField.getText());
        return integer.intValue();
    }
}
