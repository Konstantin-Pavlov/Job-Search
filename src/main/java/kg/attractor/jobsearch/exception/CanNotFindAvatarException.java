package kg.attractor.jobsearch.exception;

import java.util.NoSuchElementException;

public class CanNotFindAvatarException extends NoSuchElementException {
    public CanNotFindAvatarException(String msg) {
        super(msg);
    }
}
