package com.icrn.substitutes.Exceptions;

public class SchedulingException extends Exception {
    public SchedulingException(String message){
        super(message);
    }
    public SchedulingException(){
        super();
    }
    public SchedulingException(Exception e){
        super(e);
    }
}
