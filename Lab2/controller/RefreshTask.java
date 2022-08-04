package controller;

import javafx.concurrent.Task;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import javafx.application.Platform;
import core.Launcher;

public class RefreshTask extends Task<Void> {
    @Override
    protected Void call() throws Exception {
        for (;;) {
            try {
                Thread.sleep((long) (60 * 1e3));
            } catch (InterruptedException e) {
                System.out.println("Encountered an interrupted exception");
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        FetchData.refreshData();
                        Launcher.refreshPane();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            FutureTask<Callable<Void>> futureTask = new FutureTask(new WatchTask());
            Platform.runLater(futureTask);
            try {
                futureTask.get();
            } catch (InterruptedException e) {
                System.out.println("Encountered an interrupted exception");
            } catch (ExecutionException e) {
                System.out.println("Encountered an execution exception");
            }
        }
    }
}
