package ro.tuc.tp.BusinessLogic;

import ro.tuc.tp.Model.Server;
import ro.tuc.tp.Model.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy {

    @Override
    public synchronized void addTask(List<Server> servers, Task t) {
        Server toAddServer = servers.get(0);
        int minLengthQueue = servers.get(0).getTasks().size();

        for (Server server : servers) {
            int lengthQueue = server.getTasks().size();
            if (lengthQueue <= minLengthQueue) {
                minLengthQueue = lengthQueue;
                toAddServer = server;
            }
        }

        toAddServer.addTask(t);
    }

}
