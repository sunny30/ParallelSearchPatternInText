package com.company;


import java.io.IOException;
import java.io.RandomAccessFile;
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
    private Long startOffset ;
    private Long endOffset ;
    private RandomAccessFile randomAccessFile ;
    public Integer size ;
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


        byte [] b = new byte[size]  ;
        Line = getLineFromRandomAccess(randomAccessFile,startOffset,size,b) ;
        //randomAccessFile.seek(startOffset);
        //randomAccessFile.read(b,0,size) ;
        //Line = new String(b,"UTF-8") ;
        //offset list with in line
        boolean found = false;

        //POJO for find the instance of all Pattern in the line.
        while (lastIndex != -1) {

            lastIndex = Line.indexOf(Pattern, lastIndex);

            if (lastIndex != -1) {
                count++;
                found = true;
        //        lineOffset.add(lastIndex);
                lastIndex += Pattern.length();
            }

        }

        FutureThreadResponse threadResponse = new FutureThreadResponse();

        //Builder pattern for threadResponse object of FutureThreadResponse class
        threadResponse = threadResponse.withFound(found).withTotalCount(count);

        //log statement of thread's finding here are the details of multiple thread and their output
        if(logEnabled>0)
            System.out.println(Thread.currentThread() + "log: " + lineNumber + " " + count);

        return threadResponse;
    }

    public MyCallable withStartOffset(Long startOffset){
        this.startOffset = startOffset ;
        return this ;
    }

    public MyCallable withEndOffset(Long endOffset){
        this.endOffset = endOffset ;
        return this ;
    }
    public MyCallable withRandomAccessFile(RandomAccessFile randomAccessFile){
        this.randomAccessFile = randomAccessFile ;
        return this ;
    }

    public MyCallable withSize(Integer size){
        this.size = size;
        return this ;
    }

    public String getLineFromRandomAccess(RandomAccessFile randomAccessFile,long startOffset,Integer size,byte[] b)throws IOException{
        String Line ;
        synchronized (randomAccessFile){
            randomAccessFile.seek(startOffset);
            randomAccessFile.read(b,0,size) ;
            Line = new String(b,"UTF-8") ;
        }
        return Line ;
    }

}
