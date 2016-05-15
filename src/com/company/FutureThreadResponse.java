package com.company;

import java.util.ArrayList;

/**
 * Created by sharad.singh on 15/05/16.
 */
public class FutureThreadResponse {

    private boolean found;
    private Long lineNumber;
    private Integer totalCount;
    private ArrayList<Integer> lineOffsetList = new ArrayList<Integer>();


    public FutureThreadResponse withFound(boolean found) {
        this.found = found;
        return this;
    }

    public FutureThreadResponse withLineNumber(Long lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    public FutureThreadResponse withTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public FutureThreadResponse withLineOffSet(ArrayList<Integer> lineOffSet) {
        this.lineOffsetList = lineOffSet;
        return this;
    }

    public boolean getFound(){
        return this.found ;
    }

    public Long getLineNumber(){
        return this.lineNumber ;
    }
    public Integer getTotalCount(){
        return this.totalCount ;
    }
    public ArrayList<Integer> getLineOffsetList(){
        return this.lineOffsetList ;
    }


}
