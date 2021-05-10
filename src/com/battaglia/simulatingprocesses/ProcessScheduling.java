package com.battaglia.simulatingprocesses;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class ProcessScheduling {

    //initialize instance variables
    static ArrayList<Process> processData = new ArrayList<>();
    static PriorityQueue<Process> processPriorityQueue = new PriorityQueue<>(new ProcessComparator());
    static List<Integer> waitTimeAvg = new ArrayList<>();

    static int currentTime = 0;
    static int maxWaitTime = 30;
    static float totalWaitTime = 0;
    static int finalProcessArrival;
    static boolean running = false;
    static boolean isProcessDataEmpty = true; //processData is currently empty, so set to true


    public static void main(String[] args) {
        try {
            FileOutputStream outputStream = Utilities.outputStream();
            System.setOut(new PrintStream(outputStream));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        //Read data from file
        Utilities.fileInput();

        //time of last process's arrival. Since input file is sorted based on arrival time, we can just get the last element's arrival time
        finalProcessArrival = processData.get(processData.size() - 1).getARRIVALTIME();

        //default wait time is 30 executions of the while loop below
        System.out.println("\nMaximum wait time = " + maxWaitTime + "\n");

        //this while loop is the main driver of the program
        //it will continue to execute while processData or processPriorityQueue are not empty
        //after each execution, "time" increments so this loop serves as the program's timekeeper
        while (!processData.isEmpty() || !processPriorityQueue.isEmpty()) {

            //call addToQueue() and insert process into queue based on current time and the arrival time of each process
            QueueOperations.addToQueue();

            //Update waiting time of elements in the queue
            for (Process pQ : processPriorityQueue) {
                int waitTime = currentTime - pQ.getARRIVALTIME();
                pQ.setWaiting(waitTime);
            }

            if (!processPriorityQueue.isEmpty()) {
                //if processData has been completely emptied into processPriorityQueue, note the time and add it to the output file
                if (processData.isEmpty() && !isProcessDataEmpty) {
                    isProcessDataEmpty = true;
                    System.out.println("D becomes empty at time " + finalProcessArrival + "\n\n");
                }

                //If there are processes in processPriorityQueue that have been waiting longer than the max wait time (default 30),
                //decrease those processes' priorities by 1. This will help reduce process starvation as more processes enter the queue
                if (currentTime > 10) { //currentTime is compared to the arrival time of the first process
                    System.out.println("Update priority:");
                    for (Process process : processPriorityQueue) {
                        if (process.getWaiting() >= maxWaitTime && process.getPriority() != 1) { //priority cannot go below 1
                            int currentPriority = process.getPriority();
                            System.out.println("PID = " + process.getPID() + ", wait time = " + process.getWaiting() + ", current priority = " + process.getPriority());
                            process.setPriority(currentPriority - 1);
                            System.out.println("PID = " + process.getPID() + ", new priority = " + process.getPriority());
                        }
                    }
                    System.out.println("\n");
                }

                if (!running) {
                    //"executes" (removes) the process with the lowest priority from the queue
                    QueueOperations.execute();
                    running = false;
                }

            } else {
                currentTime += 1; //increase time by one
            }
        }

        System.out.println("Total wait time = " + totalWaitTime);

        //Calculate average waiting time
        int sum = 0;
        for (int i : waitTimeAvg) {
            sum += i;
        }
        System.out.println("Average wait time = " + sum / (float) waitTimeAvg.size());
    }
}