package Bank;

import Tasks.Task;

import java.util.ArrayList;

public class BuildingATMThread extends Thread{
    ATM atm = new ATM();
    ArrayList<Task> tasks = null;
    ArrayList<Object> results = null;
    Handler handler = null;
    @Override
    public void run() {
        while (true){
            if (tasks != null && tasks.size() != 0){
                while (tasks.size() != 0){
                    tasks.get(0).setATM(atm);
                    Object obj = null;
                    try {
                        obj = tasks.get(0).run();
                        results.add(obj);
                    } catch (Exception e) {
                        results.add(e);
                    }
                    tasks.remove(0);
                }
                handler.done();
            }
            else if (tasks != null && tasks.size() == 0){
                tasks = null;
            }
        }
    }
}