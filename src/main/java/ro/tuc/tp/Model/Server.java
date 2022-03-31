package ro.tuc.tp.Model;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private volatile boolean exit = false;
    private int queueNr;
    private static int queueNrCounter = 1;
    private AtomicInteger totalWaitingTime;
    private AtomicInteger totalClients;
    private AtomicInteger totalServiceTime;

    public synchronized void addTask(Task newTask) {
        try {
            this.tasks.put(newTask);
            this.totalServiceTime.addAndGet(newTask.getServiceTime());
            if (this.waitingPeriod.intValue() != 0)
                this.totalWaitingTime.addAndGet(this.waitingPeriod.intValue() - 1);
            this.totalClients.incrementAndGet();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.waitingPeriod.addAndGet(newTask.getServiceTime());
    }

    public Server(int maxTasksPerServer) {
        this.tasks = new ArrayBlockingQueue<>(maxTasksPerServer);
        this.waitingPeriod = new AtomicInteger(0);
        this.queueNr = queueNrCounter++;
        this.totalWaitingTime = new AtomicInteger(0);
        this.totalClients = new AtomicInteger(0);
        this.totalServiceTime = new AtomicInteger(0);
    }

    @Override
    public void run() {
        while (!exit) {
            try {
                if (tasks.size() != 0) {
                    Task task = tasks.peek();
                    Thread.sleep(task.getServiceTime() * 1000);
                    this.tasks.take();
                }
                else
                    Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String displayTasks() {
        String outputs = "";
        System.out.print("Queue " + this.queueNr + ": ");
        outputs = outputs.concat("Queue " + this.queueNr + ": ");

        if (this.tasks.size() == 0) {
            System.out.println("closed");
            outputs = outputs.concat("closed\n");
        } else {
            for (Task task : this.tasks) {
                System.out.print(" (" + task.getID() + " " + task.getArrivalTime() + " " + task.getServiceTime() + ")");
                outputs = outputs.concat(" (" + task.getID() + " " + task.getArrivalTime() + " " + task.getServiceTime() + ")");
            }
            this.tasks.peek().decrementServiceTime();
            outputs = outputs.concat("\n");
            System.out.println();
        }
        return outputs;
    }

    public void stop() {
        exit = true;
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public AtomicInteger getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public AtomicInteger getTotalServiceTime() {
        return totalServiceTime;
    }

    public static void setQueueNrCounter(int queueNrCounter) {
        Server.queueNrCounter = queueNrCounter;
    }
}
