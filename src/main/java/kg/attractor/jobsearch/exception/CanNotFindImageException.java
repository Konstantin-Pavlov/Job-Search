package kg.attractor.jobsearch.exception;

import java.util.NoSuchElementException;

public class CanNotFindImageException extends NoSuchElementException {
    public CanNotFindImageException(String msg) {
        super(msg);
    }
}
