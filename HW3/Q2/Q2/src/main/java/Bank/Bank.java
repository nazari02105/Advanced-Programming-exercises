package Bank;

import Tasks.Task;

import java.util.ArrayList;

public class Bank {
    ArrayList<BuildingATMThread> allThreads = new ArrayList<>();

    public Bank(int AtmCount) {
        for (int i = 0; i<AtmCount; ++i){
            BuildingATMThread buildingATMThread = new BuildingATMThread();
            buildingATMThread.start();
            allThreads.add(buildingATMThread);
        }
    }

    public synchronized ArrayList<Object> runATM(ArrayList<Task> tasks, Handler handler) {
        ArrayList<Object> results = new ArrayList<>();
        boolean condition = true;
        while (condition){
            for (BuildingATMThread allThread : allThreads) {
                if (allThread.tasks == null) {
                    allThread.tasks = tasks;
                    allThread.handler = handler;
                    allThread.results = results;
                    condition = false;
                    break;
                }
            }
        }
        return results;
    }

}