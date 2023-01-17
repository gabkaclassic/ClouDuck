package org.gab.ClouDuck.exceptions;

public class InvalidSecretKeyException extends Exception {

    public InvalidSecretKeyException() {

        super("Secret key is not valid");
    }
}
