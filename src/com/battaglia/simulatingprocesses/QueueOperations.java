package com.battaglia.simulatingprocesses;

import static com.battaglia.simulatingprocesses.ProcessScheduling.currentTime;
import static com.battaglia.simulatingprocesses.ProcessScheduling.processData;
import static com.battaglia.simulatingprocesses.ProcessScheduling.processPriorityQueue;
import static com.battaglia.simulatingprocesses.ProcessScheduling.totalWaitTime;
import static com.battaglia.simulatingprocesses.ProcessScheduling.waitTimeAvg;
import static com.battaglia.simulatingprocesses.ProcessScheduling.running;

public class QueueOperations {
    /**
     * Loops through processData and adds processData[0] to processPriorityQueue if processData[0]'s
     * arrival time is less than or equal to the current time.
     */
    public static void addToQueue() {
        for (int i = 0; i <= processData.size() - 1; i++){
            //can just take the first element from processData each time because we are removing from the front
            if (processData.get(0).getARRIVALTIME() <= currentTime) {
                processPriorityQueue.add(processData.get(0));
                processData.remove(0);
            }
        }
    }

    /**
     * Removes the process that has the lowest priority from processPriorityQueue to simulate process "execution"
     */
    public static void execute() {
        running = true;

        //create a new process variable that will be used in the execution process
        Process executable = new Process();
        int lowestPriority = 999;

        //find the process that has the lowest priority in the queue
        for (Process process: processPriorityQueue) {
            if (process.getPriority() < lowestPriority) {

                //reassign lowestPriority to the lowest priority seen so far
                lowestPriority = process.getPriority();

                //assign executable to the process with the lowest priority we've seen so far
                executable = process;
            }
        }

        //"execute" executable aka remove it from the queue
        processPriorityQueue.remove(executable);

        //the time executable was removed from the queue
        int atTime = currentTime;

        //update current time with the duration of the process that executed
        currentTime += executable.getDURATION();

        //update total wait time with the wait time of executable
        totalWaitTime += executable.getWaiting();

        //and set executable's total wait to totalWaitTime
        executable.setTotalWaiting(totalWaitTime);

        waitTimeAvg.add(executable.getWaiting());

        System.out.println("Process removed from queue is: id = " + executable.getPID()+ ", at time " + atTime + ", wait time = " + executable.getWaiting() +
                            " Total wait time = " + executable.getTotalWaiting());
        System.out.println("Process id = " + executable.getPID() +
                            "\n\t\tPriority = " + executable.getPriority() +
                            "\n\t\tArrival = " + executable.getARRIVALTIME() +
                            "\n\t\tDuration = " + executable.getDURATION());
        System.out.println("Process " + executable.getPID() + " finished at time " +  currentTime + "\n");
        //System.out.println("\n");
    }
}
