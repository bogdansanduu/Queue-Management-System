package ro.tuc.tp.BusinessLogic;

import ro.tuc.tp.Model.Server;
import ro.tuc.tp.Model.Task;

import java.util.List;

public interface Strategy {
    void addTask(List<Server> servers, Task t);
}
