package pl.edu.uw.dsk.dev.wallboard.exceptions;

public class TechnicalException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public TechnicalException() {
        super();
    }

    public TechnicalException(String s) {
        super(s);
    }

    public TechnicalException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TechnicalException(Throwable throwable) {
        super(throwable);
    }
}
