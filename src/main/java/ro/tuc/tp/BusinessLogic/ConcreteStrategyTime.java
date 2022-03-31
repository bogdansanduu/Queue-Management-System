package ro.tuc.tp.BusinessLogic;

import ro.tuc.tp.Model.Server;
import ro.tuc.tp.Model.Task;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {

    @Override
    public synchronized void addTask(List<Server> servers, Task t) {
        Server toAddServer = servers.get(0);
        int min = servers.get(0).getWaitingPeriod().get();

        for (Server server : servers) {
            int waitingPeriod = server.getWaitingPeriod().get();
            if (waitingPeriod <= min) {
                min = waitingPeriod;
                toAddServer = server;
            }
        }
        toAddServer.addTask(t);
    }
}
