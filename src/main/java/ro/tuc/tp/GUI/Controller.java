package ro.tuc.tp.GUI;

import ro.tuc.tp.BusinessLogic.SelectionPolicy;
import ro.tuc.tp.BusinessLogic.SimulationManager;
import ro.tuc.tp.Utils.OutputWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Controller implements ActionListener {
    private SimulationFrame view;

    public Controller(SimulationFrame view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String queuesNr = view.getQueuesNr().getText();
        String clientsNr = view.getClientsNr().getText();
        String minAT = view.getMinAT().getText();
        String maxAT = view.getMaxAT().getText();
        String minST = view.getMinST().getText();
        String maxST = view.getMaxST().getText();
        String maxSimTime = view.getMaxSimulation().getText();
        switch (command) {
            case "START":
                if ((queuesNr.matches("[0-9]+") && queuesNr.length() >= 1) &&
                        (clientsNr.matches("[0-9]+") && clientsNr.length() >= 1) &&
                        (minAT.matches("[0-9]+") && minAT.length() >= 1) &&
                        (maxAT.matches("[0-9]+") && maxAT.length() >= 1) &&
                        (minST.matches("[0-9]+") && minST.length() >= 1) &&
                        (maxST.matches("[0-9]+") && maxST.length() >= 1) &&
                        (maxSimTime.matches("[0-9]+") && maxSimTime.length() >= 1)) {
                    int maxSTI = Integer.parseInt(maxST);
                    int minSTI = Integer.parseInt(minST);
                    int maxATI = Integer.parseInt(maxAT);
                    int minATI = Integer.parseInt(minAT);
                    int queuesNrI = Integer.parseInt(queuesNr);
                    int clientsNrI = Integer.parseInt(clientsNr);
                    int maxSimTimeI = Integer.parseInt(maxSimTime);
                    if (maxSTI >= minSTI && maxATI >= minATI) {
                        SelectionPolicy policy = SelectionPolicy.SHORTEST_TIME;
                        if ((view.getStrategy().getSelectedItem()).equals("Time"))
                            policy = SelectionPolicy.SHORTEST_TIME;
                        if ((view.getStrategy().getSelectedItem()).equals("Queue"))
                            policy = SelectionPolicy.SHORTEST_QUEUE;
                        this.start(maxSTI, minSTI, maxATI, minATI, clientsNrI, queuesNrI, policy, this.view, maxSimTimeI);
                    } else
                        view.getResultLive().setText("Wrong input, check min max intervals");
                } else
                    view.getResultLive().setText("Wrong input");
                break;
            case "RESET":
                view.getMaxSimulation().setText("");
                view.getQueuesNr().setText("");
                view.getClientsNr().setText("");
                view.getMinAT().setText("");
                view.getMaxAT().setText("");
                view.getMinST().setText("");
                view.getMaxST().setText("");
                view.getResultLive().setText("");
                break;
        }
    }

    private void start(int maxSTI, int minSTI, int maxATI, int minATI, int clientsNrI, int queuesNrI, SelectionPolicy selectionPolicy, SimulationFrame view, int maxSimTime) {
        SwingWorker<Void, String> worker = new SwingWorker<>() {
            SimulationManager simulationManager;

            @Override
            protected Void doInBackground() {
                simulationManager = new SimulationManager(
                        maxSTI, minSTI, maxATI, minATI, clientsNrI, queuesNrI, maxSimTime, selectionPolicy, view);
//                Thread t = new Thread(simulationManager);
//                t.start();
                simulationManager.run();
                OutputWriter.writeOutput(simulationManager.getOutput());
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                String stepView = chunks.get(chunks.size() - 1);
                view.getResultLive().setText("<html>" + stepView.replaceAll("\n", "<br/>") + "</html>");
            }
        };
        worker.execute();
    }

}
