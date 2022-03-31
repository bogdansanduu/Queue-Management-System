package ro.tuc.tp.Model;

public class Task implements Comparable<Task> {
    private static int IDCounter = 1;
    private int ID;
    private int arrivalTime;
    private int serviceTime;


    public Task(int arrivalTime, int serviceTime) {
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.ID = Task.IDCounter;
        Task.IDCounter++;
    }

    public int getID() {
        return ID;
    }

    public synchronized void decrementServiceTime() {
        this.serviceTime--;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    @Override
    public int compareTo(Task o) {
        if (this.arrivalTime >= o.getArrivalTime())
            return 1;
        else if (this.arrivalTime < o.getArrivalTime())
            return -1;
        else
            return 0;
    }

    public static void setIDCounter(int IDCounter) {
        Task.IDCounter = IDCounter;
    }
}
