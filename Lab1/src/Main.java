import Models.CorrelationResult;
import Models.DataError;
import Models.DataTicket;
import Models.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


/**
 * Created by IntelliJ IDEA
 * Date: 01.09.2021
 * Time: 10:47 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class Main {
    // separator for CSV files
    private static final String COL_SEPARATOR = ";";

    // timeTracked indicates if time parameter should be added to responses
    private static boolean timeTracked = false;

    // start time for program
    private static Long startTime;

    // list that contains the two files to be compared
    private static final List<File> files = new ArrayList<>();

    // Module that returns result or error back to the user
    private static Logger logger;

    public static void main(String[] args) {
        // check for time args
        checkTimeArg(args);

        // time measurement <START>
        startTime = System.nanoTime();

        // check for correct arg counts
        DataError argError = validateArgCount(args);
        if (argError != null) {
            logger = new Logger(argError,System.nanoTime()-startTime,timeTracked);
            logger.log();
        }

        // check if files exist
        DataError fileErrors = validateFilesIn(args);
        if (fileErrors != null) {
            logger = new Logger(fileErrors,System.nanoTime()-startTime, timeTracked);
            logger.log();
        }

        processFiles();
    }

    // validations have been passed, main work process
    private static void processFiles() {
        final List<DataTicket> data = fileToDataTicket(files.get(0),files.get(1));

        CorrelationResult xCorrelationResult = new CorrelationResult();
        CorrelationResult yCorrelationResult = new CorrelationResult();
        CorrelationResult xyCorrelationResult = new CorrelationResult();

        Thread thread1 = new Thread(new CorrelationRunnable(data,DataTicket::getCloseX,xCorrelationResult));
        Thread thread2 = new Thread(new CorrelationRunnable(data,DataTicket::getCloseY,yCorrelationResult));
        Thread thread3 = new Thread(new CorrelationRunnable(data,ticket -> ticket.getCloseY()*ticket.getCloseX(),xyCorrelationResult));
        thread1.start();
        thread2.start();
        thread3.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            logger = new Logger(new DataError(DataError.ERROR,"Threads were interrupted: "+e.getLocalizedMessage()),
                    System.nanoTime()-startTime,
                    timeTracked);
            logger.log();
        }
        // ** correlation calculation **
        double numerator = xyCorrelationResult.getMean() - xCorrelationResult.getMean() * yCorrelationResult.getMean();
        double denominator = xCorrelationResult.getStandardDeviation() * yCorrelationResult.getStandardDeviation();
        double correlation = numerator / denominator;

        logger = new Logger(correlation,System.nanoTime()-startTime,timeTracked);
        logger.log();
    }

    /**
     * Reads the file provided and convert them to dataTicket
     * @param file1 the X file to be read
     * @param file2 the Y file to be read
     * @return a list of Models.DataTicket where each Models.DataTicket is a daily data and this list is sorted
     * in ascending order
     */
    private static List<DataTicket> fileToDataTicket(File file1, File file2) {
        List<DataTicket> result = new ArrayList<>();
        HashMap<Long,Double> tempStore = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file1));
            int dateCol = 0;
            int closeCol = 0;

            String[] header = br.readLine().split(COL_SEPARATOR);

            for (int i = 0; i < header.length; i++) {
                String colTitle = header[i].trim();
                if (colTitle.trim().contains("DATE")) {
                    dateCol = i;
                } else if (colTitle.trim().contains("CLOSE")) {
                    closeCol = i;
                }
            }

            // make sure dateCol and closeCol are contained in the CSV file
            if (dateCol == 0 || closeCol == 0) {
                DataError err = new DataError(DataError.ERROR, "Error reading cols from csv");
                logger = new Logger(err,System.nanoTime()-startTime,timeTracked);
            }

            String line;
            while((line = br.readLine()) != null) {
                String[] arr = line.split(COL_SEPARATOR);
                long date = Long.parseLong(arr[dateCol]);
                Double close = Double.parseDouble(arr[closeCol]);
                tempStore.put(date,close);
            }
            br = new BufferedReader(new FileReader(file2));
            // by passing the header
            br.readLine();

            while((line = br.readLine()) != null) {
                String[] arr = line.split(COL_SEPARATOR);
                long date = Long.parseLong(arr[dateCol]);
                Double close = Double.parseDouble(arr[closeCol]);
                if (!tempStore.containsKey(date)) {
                    DataError err = new DataError(DataError.ERROR, "Could not find data with matching dates in files");
                    logger = new Logger(err,System.nanoTime()-startTime,timeTracked);
                    logger.log();
                } else {
                    result.add(new DataTicket(date,tempStore.get(date),close));
                }
            }
            Collections.sort(result);
            return result;
        } catch (Exception e) {
            System.out.println(e);
            DataError err = new DataError(DataError.ERROR,
                    "Failed to parse files to DataTicket");
            logger = new Logger(err,System.nanoTime()-startTime,timeTracked);
            logger.log();
        }
        return null;
    }

    // checks if user wants to track the time
    private static void checkTimeArg(String[] args){
        for(String arg: args) {
            if (arg.trim().equals("-t")) {
                timeTracked = true;
                break;
            }
        }
    }

    // checks if the number of arguments are correct
    private static DataError validateArgCount(String[] args){
        if ((args.length == 3 && timeTracked) || (!timeTracked && args.length == 2)) {
            return null;
        } else {
            return new DataError(DataError.INCORRECT_ARGS_NUMBER,
                    "Incorrect number of arguments. "+args.length+" argument provided");
        }
    }

    // check if parameters passed are actual files else returns DateError
    private static DataError validateFilesIn(String[] args) {
        for (String arg: args) {
            if (arg.trim().equals("-t")) {
                continue ;
            }
            File f = new File(arg);
            if (f.exists()) {
                files.add(f);
            } else {
                return new DataError(DataError.FILE_NOT_FOUND,
                        "File with name "+arg+" not found");
            }
        }
        // we do not have two files for comparison
        if (files.size() != 2) {
            return new DataError(DataError.ERROR,
                    "Incomplete number of files. File count: "+files.size());
        }
        return null;
    }
}
