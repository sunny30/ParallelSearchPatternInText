package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*;


public class Main {

    public static void main(String[] args)

    {
        // write your code here
        System.out.println(args.length);
        if (args.length < 3)
            System.out.println("please follow the correct command line arguments from readme");
        else {

            //book name with full path
            String text = args[0];

            //pattern that we have to search with in that book
            String pattern = args[1];

            //number of threads with the
            int numThreads = Integer.parseInt(args[2]);
            System.out.println("logs:" + text + " pattern:" + pattern + " NumberOfThreads:" + numThreads);
            try {
                //Steaming buffer reader
                BufferedReader br = new BufferedReader(new FileReader(text));

                //Requesting JVM for the thread banks of number numThreads
                ExecutorService executors = Executors.newFixedThreadPool(numThreads);

                String Line;
                Long lineNumber = 0L;
                //Creating arraylist of all the responses from callable interface
                ArrayList<Future<FutureThreadResponse>> futureArrayList = new ArrayList<Future<FutureThreadResponse>>();

                while ((Line = br.readLine()) != null) {
                    ++lineNumber;

                    //waste of resource if we are assigning line to threads in which patterns can never exist .
                    if (Line.length() < pattern.length()) {
                        //read the next line
                        continue;
                    }
                    //log statement which lines is going attach to the current instance of callable
                    System.out.println("Current line submitting to thread: " + lineNumber);

                    MyCallable myCallable = new MyCallable();
                    //applying builder pattern to the Mycallable instance ,that's why i havent written getter and setter
                    myCallable = myCallable.withLine(Line).withPattern(pattern).withLineNumber(lineNumber);

                    //submitting this instance to thread pool ,available thread will pick this one
                    Future<FutureThreadResponse> result = executors.submit(myCallable);

                    //adding the result collections of result
                    futureArrayList.add(result);


                }
                Long totalMatches = 0L;
                for (Future<FutureThreadResponse> result : futureArrayList)
                    if (result.get().getFound()) {

                        //result.get() is the magic it will make wait all the thread to stop the out put since it got completed
                        //formatting log output ,preparing Command line output.
                        totalMatches += (long) result.get().getTotalCount();
                        System.out.print("On the line-Number " + result.get().getLineNumber() + " " + "Total Count in this line: " + result.get().getTotalCount() + " offsets on this lines are ");
                        for (Integer offset : result.get().getLineOffsetList()) {
                            System.out.print(offset + " ");
                        }

                        System.out.println();
                    }
                //total Number of matches
                System.out.println(totalMatches);
                //Shutting down all the resources to thread
                executors.shutdown();

            } catch (IOException e) {
                //Execption handling
            } catch (Exception e) {
                //General Exception handling
            }
        }
    }

}
