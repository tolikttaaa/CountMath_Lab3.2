package back.exception;

public class NoSolutionException extends Exception{
    public NoSolutionException() {
        super("Can't solve nonlinear equation at these bounds!");
    }

    public NoSolutionException(String inCase) {
        super("Can't solve nonlinear equation at these bounds!\nThe reason is: " + inCase);
    }
}
