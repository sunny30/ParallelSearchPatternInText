package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.*;


public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException

    {
        long startTime = System.currentTimeMillis();
        int len = 1024 * 1024;
        // write your code here
        //System.out.println(args.length);
        try {
            if (args.length < 3)
                System.out.println("please follow the correct command line arguments from readme");
            else {

                File file = new File(args[0]);
                String pattern = args[1];
                Integer logEnabled = Integer.parseInt(args[3]);

                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                long fileLength = randomAccessFile.length();
        //        System.out.println(randomAccessFile.length());
                int numThreads = Integer.parseInt(args[2]);
                long perThreads = fileLength / numThreads;
                int sizeOfByte = 0;
                if (perThreads > len) {
                    sizeOfByte = len;
                } else {
                    sizeOfByte = (int) perThreads;
                }
                perThreads = Math.min(len, perThreads);
                ExecutorService executors = Executors.newFixedThreadPool(numThreads);
                ArrayList<Future<FutureThreadResponse>> futureArrayList = new ArrayList<Future<FutureThreadResponse>>();
                ArrayList<RandomAccessFile> accessFileArrayList = new ArrayList<RandomAccessFile>() ;
        //        System.out.println(perThreads);
                for (long i = 0; i < fileLength; i += perThreads) {
                    MyCallable myCallable = new MyCallable();
                  //  RandomAccessFile randomAccessFile1 = new RandomAccessFile(file, "rw");
                    if (i + perThreads >= fileLength) {
                        //RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "rw");
                        myCallable = myCallable.withStartOffset(i).withPattern(pattern).withRandomAccessFile(randomAccessFile).withSize((int) (fileLength - i)).withLogEnabled(logEnabled);
                        Future<FutureThreadResponse> result = executors.submit(myCallable) ;
                        futureArrayList.add(result);
                        break;
                    } else
                        myCallable = myCallable.withStartOffset(i).withPattern(pattern).withRandomAccessFile(randomAccessFile).withSize(sizeOfByte).withLogEnabled(logEnabled);
                    Future<FutureThreadResponse> result = executors.submit(myCallable);
                    futureArrayList.add(result);
                }
                int totalMatches = 0;

                for (Future<FutureThreadResponse> result : futureArrayList) {

                    totalMatches += result.get().getTotalCount();

                }
                System.out.println(totalMatches);

                executors.shutdown();
                long stopTime = System.currentTimeMillis();
                long elapsedTime = stopTime - startTime;

                System.out.println("Time taken by program is :" + elapsedTime + "millisseconds");

            }
/*
            //book name with full path
            String text = args[0];

            //pattern that we have to search with in that book
            String pattern = args[1];

            //number of threads with the
            int numThreads = Integer.parseInt(args[2]);
            int logEnabled = 0 ;
            if(args.length==4 && args[3]!=null)
                logEnabled = Integer.parseInt(args[3]);
           // System.out.println("logs:" + text + " pattern:" + pattern + " NumberOfThreads:" + numThreads);
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

                    if(Line !=null){
                        String tempLine ;
                        while (((tempLine = br.readLine())!=null) && Line.length()<len)
                            Line += tempLine ;
                    }

                    //waste of resource if we are assigning line to threads in which patterns can never exist .
                    if (Line.length() < pattern.length()) {
                        //read the next line
                        continue;
                    }
                    //log statement which lines is going attach to the current instance of callable
                    if(logEnabled>0)
                        System.out.println("Current line submitting to thread: " + lineNumber);

                    MyCallable myCallable = new MyCallable();
                    //applying builder pattern to the Mycallable instance ,that's why i havent written getter and setter
                    myCallable = myCallable.withLine(Line).withPattern(pattern).withLineNumber(lineNumber).withLogEnabled(logEnabled);


                    //submitting this instance to thread pool ,available thread will pick this one
                    Future<FutureThreadResponse> result = executors.submit(myCallable);

                    //adding the result collections of result
                    futureArrayList.add(result);


                }
                Long totalMatches = 0L;
                for (Future<FutureThreadResponse> result : futureArrayList)
                    totalMatches += (long) result.get().getTotalCount();
                //    if (result.get().getFound()) {

                        //result.get() is the magic it will make wait all the thread to stop the out put since it got completed
                        //formatting log output ,preparing Command line output.
                  //      totalMatches += (long) result.get().getTotalCount();
                    //    System.out.print("On the line-Number " + result.get().getLineNumber() + " " + "Total Count in this line: " + result.get().getTotalCount() + " offsets on this lines are ");
                      //  for (Integer offset : result.get().getLineOffsetList()) {
                        //    System.out.print(offset + " ");
                        //}

//                        System.out.println();
  //                  }
                //total Number of matches
                System.out.println(totalMatches);
                //Shutting down all the resources to thread
                executors.shutdown();*/

        } catch (IOException e) {
            //Execption handling
        } catch (Exception e) {
            //General Exception handling
        }
    }
}

//}
