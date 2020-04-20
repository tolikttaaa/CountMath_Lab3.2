package back.exception;

public class NotImplementedSolutionException extends NotImplementedMethodException {
    public NotImplementedSolutionException() {
        super("Solution by this method does not implement yet!");
    }
}
