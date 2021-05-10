package com.battaglia.simulatingprocesses;

import static com.battaglia.simulatingprocesses.ProcessScheduling.processData;
import static com.battaglia.simulatingprocesses.ProcessScheduling.isProcessDataEmpty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Utilities {

    /**
     * Reads the input file ("process_scheduling_input.txt") using a scanner object, parses the data, and adds the information into the processData array list
     * The data in the input file is already sorted based on arrival time so no additional sorting operations need to take place
     */
    public static void fileInput() {
        try {
            Scanner inputScanner = new Scanner (new File("process_scheduling_input.txt"));

            while (inputScanner.hasNext()) {
                String line = inputScanner.nextLine();
                String[] splitLine = line.split(" ");

                int PID = Integer.parseInt(splitLine[0]);
                int priority = Integer.parseInt(splitLine[1]);
                int arrivalTime = Integer.parseInt(splitLine[3]);
                int DURATION = Integer.parseInt(splitLine[2]);

                Process process = new Process(PID, priority, arrivalTime, DURATION);
                processData.add(process);
            }

            //processData is now populated, change flag to false
            isProcessDataEmpty = false;

            for (Process p : processData) {
                System.out.println(p);
            }

        }  catch (IOException ex) {
            System.out.print("File not found: ");
            ex.printStackTrace();
        }
    }

    /**
     * Creates the file output stream for writing the simulation results to a .txt file
     * @return outputStream - used to redirect System.out.println to "process_scheduling_output.txt"
     */
    public static FileOutputStream outputStream() throws FileNotFoundException {
        File output = new File("process_scheduling_output.txt");
        return new FileOutputStream(output);
    }
}

