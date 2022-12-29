package org.gab.ClouDuck.exceptions;

import java.sql.Date;

public class ExpiredKeyException extends Exception {
    
    public ExpiredKeyException(Date date) {
        super(String.format("Period of key is deadline expired ( %s )", date.toString()));
    }
}
