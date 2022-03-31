package ro.tuc.tp.BusinessLogic;

import ro.tuc.tp.Model.Server;
import ro.tuc.tp.Model.Task;
import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private Strategy strategy = new ConcreteStrategyTime();

    public Scheduler(int maxTasksPerServer, int nrOfServers) {
        this.servers = new ArrayList<>();
        for (int i = 0; i < nrOfServers; i++) {
            this.servers.add(new Server(maxTasksPerServer));
        }
    }

    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ConcreteStrategyQueue();
        }
        if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }

    public synchronized void dispatchTask(Task task) {
        this.strategy.addTask(this.servers, task);
    }

    public List<Server> getServers() {
        return servers;
    }
}
