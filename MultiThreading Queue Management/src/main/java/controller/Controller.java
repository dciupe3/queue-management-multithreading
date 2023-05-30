package controller;

import businessLogic.SimulationManager;
import view.SimulationFrame;
import view.StartedSimulationFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private SimulationFrame simulationFrame;
    private SimulationManager simulationManager;
    StartedSimulationFrame startedSimulationFrame;

    public Controller(SimulationFrame simulationFrame, SimulationManager simulationManager, StartedSimulationFrame startedSimulationFrame){
        this.simulationFrame = simulationFrame;
        this.simulationManager = simulationManager;
        this.startedSimulationFrame = startedSimulationFrame;

        simulationFrame.addGenerateButtonListener(new GenerateListener());
        simulationFrame.addStartButtonListener(new StartListener());

    }

    class GenerateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                simulationManager.updateData(simulationFrame.getNrOfQueues(), simulationFrame.getNrOfClients(),
                        simulationFrame.getSimulationTime(), simulationFrame.getMinServiceTime(),
                        simulationFrame.getMaxServiceTime(), simulationFrame.getMinArrivalTime(),
                        simulationFrame.getMaxArrivalTime());
                simulationManager.updateScheduler();
                simulationManager.generateNRandomClients();
                simulationFrame.setStartButtonEnable();
            } catch (Exception exception){
                exception.printStackTrace();
            }
        }
    }

    class StartListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                simulationManager.start();
                startedSimulationFrame.open();
                //startedSimulationFrame.addPanels();
            } catch (Exception exception){
                exception.printStackTrace();
            }
        }
    }


}
