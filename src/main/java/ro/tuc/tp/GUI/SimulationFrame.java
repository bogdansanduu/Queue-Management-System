package ro.tuc.tp.GUI;

import javax.swing.*;
import java.awt.*;

public class SimulationFrame extends JFrame {
    private JPanel contentPanel;
    private JPanel inputPanel;
    private JPanel resultPanel;
    private JLabel nrQueues;
    private JLabel nrClients;
    private JLabel minArrivalTime;
    private JLabel maxArrivalTime;
    private JLabel minServiceTime;
    private JLabel maxServiceTime;
    private JLabel resultLiveLabel;
    private JLabel resultLive;
    private JLabel maxSimulationLabel;
    private JTextField maxSimulation;
    private JTextField queuesNr;
    private JTextField clientsNr;
    private JTextField minAT;
    private JTextField maxAT;
    private JTextField minST;
    private JTextField maxST;
    private JButton start;
    private JButton resetInput;
    private Controller controller = new Controller(this);
    private JComboBox strategy;

    public SimulationFrame(String name) {
        super(name);
        this.prepareGUI();
    }

    public void prepareGUI() {
        this.setSize(500, 700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPanel = new JPanel(new GridLayout(2, 1));
        this.prepareInputPanel();
        this.prepareResultPanel();
        this.setContentPane(this.contentPanel);
    }

    private void prepareInputPanel() {
        this.inputPanel = new JPanel();
        this.inputPanel.setLayout(new GridLayout(9, 2));
        this.nrQueues = new JLabel("Number of queues", JLabel.LEFT);
        this.nrClients = new JLabel("Number of clients", JLabel.LEFT);
        this.minArrivalTime = new JLabel("Min arrival time", JLabel.LEFT);
        this.maxArrivalTime = new JLabel("Max arrival time", JLabel.LEFT);
        this.minServiceTime = new JLabel("Min service time", JLabel.LEFT);
        this.maxServiceTime = new JLabel("Max service time", JLabel.LEFT);
        this.maxSimulationLabel = new JLabel("Max simulation time", JLabel.LEFT);
        this.queuesNr = new JTextField();
        this.clientsNr = new JTextField();
        this.minAT = new JTextField();
        this.maxAT = new JTextField();
        this.minST = new JTextField();
        this.maxST = new JTextField();
        this.maxSimulation = new JTextField();
        this.start = new JButton("Start");
        this.start.setActionCommand("START");
        this.start.addActionListener(this.controller);
        this.resetInput = new JButton("Reset Input");
        this.resetInput.setActionCommand("RESET");
        this.resetInput.addActionListener(this.controller);
        this.strategy = new JComboBox(new String[]{"Time", "Queue"});
        this.strategy.setSelectedIndex(0);
        this.strategy.addActionListener(this.controller);
        this.inputPanel.add(this.maxSimulationLabel);
        this.inputPanel.add(this.maxSimulation);
        this.inputPanel.add(this.nrQueues);
        this.inputPanel.add(this.queuesNr);
        this.inputPanel.add(this.nrClients);
        this.inputPanel.add(this.clientsNr);
        this.inputPanel.add(this.minArrivalTime);
        this.inputPanel.add(this.minAT);
        this.inputPanel.add(this.maxArrivalTime);
        this.inputPanel.add(this.maxAT);
        this.inputPanel.add(this.minServiceTime);
        this.inputPanel.add(this.minST);
        this.inputPanel.add(this.maxServiceTime);
        this.inputPanel.add(this.maxST);
        this.inputPanel.add(this.start);
        this.inputPanel.add(this.resetInput);
        this.inputPanel.add(this.strategy);
        this.contentPanel.add(inputPanel);
    }

    private void prepareResultPanel() {
        this.resultPanel = new JPanel();
        this.resultPanel.setLayout(new GridLayout(2, 1));
        this.resultLiveLabel = new JLabel("Queue Management Live", JLabel.CENTER);
        this.resultLive = new JLabel("", JLabel.CENTER);
        this.resultPanel.add(this.resultLiveLabel);
        this.resultPanel.add(this.resultLive);
        this.contentPanel.add(resultPanel);
    }

    public JTextField getQueuesNr() {
        return queuesNr;
    }

    public JTextField getClientsNr() {
        return clientsNr;
    }

    public JTextField getMinAT() {
        return minAT;
    }

    public JTextField getMaxAT() {
        return maxAT;
    }

    public JTextField getMinST() {
        return minST;
    }

    public JTextField getMaxST() {
        return maxST;
    }

    public JLabel getResultLive() {
        return resultLive;
    }

    public JComboBox getStrategy() {
        return strategy;
    }

    public JTextField getMaxSimulation() {
        return maxSimulation;
    }
}
