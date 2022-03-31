package ro.tuc.tp.BusinessLogic;

import ro.tuc.tp.GUI.SimulationFrame;
import ro.tuc.tp.Model.Server;
import ro.tuc.tp.Model.Task;
import ro.tuc.tp.Utils.TaskUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimulationManager implements Runnable {
    //UI DATA
    public int timeLimit;
    public int maxProcessingTime;
    public int minProcessingTime;
    public int maxArrivalTime;
    public int minArrivalTime;
    public int numberOfServers;
    public int numberOfClients;
    public SelectionPolicy selectionPolicy;//fa sa fie tot de la UI
//-----

    private Scheduler scheduler;
    private List<Task> generatedTasks;
    private List<String> output;
    private SimulationFrame view;


    public SimulationManager(int maxProcessingTime, int minProcessingTime, int maxArrivalTime, int minArrivalTime, int numberOfClients, int numberOfServers, int timeLimit, SelectionPolicy selectionPolicy, SimulationFrame view) {
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minArrivalTime = minArrivalTime;
        this.numberOfClients = numberOfClients;
        this.numberOfServers = numberOfServers;
        this.selectionPolicy = selectionPolicy;
        this.view = view;
        this.timeLimit = timeLimit;
        this.generatedTasks = new ArrayList<>();
        this.output = new ArrayList<>();
        generateNRandomTasks();
        this.scheduler = new Scheduler(1000, this.numberOfServers);
        this.scheduler.changeStrategy(selectionPolicy);
        for (Server server : scheduler.getServers()) {
            Server.setQueueNrCounter(1);
            Thread t = new Thread(server);
            t.start();
        }
    }

    public void generateNRandomTasks() {
        Task.setIDCounter(1);
        for (int i = 0; i < this.numberOfClients; i++) {
            int processingTime = TaskUtil.getRandomNumber(this.minProcessingTime, this.maxProcessingTime);
            int arrivalTime = TaskUtil.getRandomNumber(this.minArrivalTime, this.maxArrivalTime);
            Task task = new Task(arrivalTime, processingTime);
            this.generatedTasks.add(task);
        }
        Collections.sort(this.generatedTasks);
    }

    @Override
    public void run() {
        String stepView = "";
        int currentTime = 0;
        int peakHour = 0;
        int maxClients = 0;
        boolean noMoreTask = false;
        while (currentTime < this.timeLimit && !noMoreTask) {
            stepView = "";
            System.out.println("Time" + " " + currentTime);
            stepView = stepView.concat("Time" + " " + currentTime + "\n");
            List<Task> toDispatchTasks = new ArrayList<>();
            int currClients = 0;

            for (Task task : this.generatedTasks) {
                if (task.getArrivalTime() == currentTime) {
                    toDispatchTasks.add(task);
                    currClients++;
                }
            }

            if (currClients >= maxClients) {
                maxClients = currClients;
                peakHour = currentTime;
            }
            this.generatedTasks.removeAll(toDispatchTasks);

            for (Task task : toDispatchTasks) {
                scheduler.dispatchTask(task);
            }

            System.out.print("Waiting clients: ");
            stepView = stepView.concat("Waiting clients: ");

            for (Task task : this.generatedTasks) {
                System.out.print("(" + task.getID() + " " + task.getArrivalTime() + " " + task.getServiceTime() + ")");
                stepView = stepView.concat("(" + task.getID() + " " + task.getArrivalTime() + " " + task.getServiceTime() + ")");
            }

            stepView = stepView.concat("\n");
            System.out.println();
            int totalClients = 0;

            for (Server server : this.scheduler.getServers()) {
                String outputs = server.displayTasks();
                totalClients += server.getTasks().size();
                stepView = stepView.concat(outputs);
            }

            if (totalClients == 0 && this.generatedTasks.size() == 0) noMoreTask = true;
            currentTime++;
            this.output.add(stepView);
            this.view.getResultLive().setText("<html>" + stepView.replaceAll("\n", "<br/>") + "</html>");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        stepView = "";
        float totalWaiting = 0, totalService = 0;

        for (Server server : this.scheduler.getServers()) {
            totalWaiting += server.getTotalWaitingTime().intValue();
            totalService += server.getTotalServiceTime().intValue();
        }

        System.out.println("Average waiting time: " + totalWaiting / this.numberOfClients);
        System.out.println("Average service time: " + totalService / this.numberOfClients);
        System.out.println("Peak hour: " + peakHour);
        stepView = stepView.concat("Average waiting time: " + totalWaiting / this.numberOfClients + "\n");
        stepView = stepView.concat("Average service time: " + totalService / this.numberOfClients + "\n");
        stepView = stepView.concat("Peak hour: " + peakHour + "\n");

        for (Server server : this.scheduler.getServers()) {
            server.stop();
        }

        this.output.add(stepView);
        this.view.getResultLive().setText("<html>" + stepView.replaceAll("\n", "<br/>") + "</html>");
    }

    public List<String> getOutput() {
        return output;
    }
}
