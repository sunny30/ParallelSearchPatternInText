package com.company;


import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by sharad.singh on 15/05/16.
 */
public class MyCallable implements Callable<FutureThreadResponse> {

    private String Line;
    private String Pattern;
    private Long lineNumber;
    private Integer logEnabled ;

    //Using builder pattern
    public MyCallable withLine(String Line) {
        this.Line = Line;
        return this;
    }

    public MyCallable withPattern(String Pattern) {
        this.Pattern = Pattern;
        return this;
    }

    public MyCallable withLineNumber(Long lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    public MyCallable withLogEnabled(Integer logEnabled){
        this.logEnabled = logEnabled ;
        return this ;
    }

    //override the call method of Callable interface
    @Override
    public FutureThreadResponse call() throws Exception {
        int lastIndex = 0;
        int count = 0;
        //offset list with in line
        ArrayList<Integer> lineOffset = new ArrayList<Integer>();
        boolean found = false;

        //POJO for find the instance of all Pattern in the line.
        while (lastIndex != -1) {

            lastIndex = Line.indexOf(Pattern, lastIndex);

            if (lastIndex != -1) {
                count++;
                found = true;
                lineOffset.add(lastIndex);
                lastIndex += Pattern.length();
            }

        }

        FutureThreadResponse threadResponse = new FutureThreadResponse();

        //Builder pattern for threadResponse object of FutureThreadResponse class
        threadResponse = threadResponse.withFound(found).withLineNumber(lineNumber).
                withLineOffSet(lineOffset).withTotalCount(count);

        //log statement of thread's finding here are the details of multiple thread and their output
        if(logEnabled>0)
            System.out.println(Thread.currentThread() + "log: " + lineNumber + " " + count);

        return threadResponse;
    }
}
