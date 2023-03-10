package com.epf.rentmanager.exception;

import java.sql.SQLException;

public class DaoException extends Exception{

    public DaoException(SQLException e) {
    }
    public DaoException(String msg) {
        super(msg);
    }
    public DaoException(){}
}
