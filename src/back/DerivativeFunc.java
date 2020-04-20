package back;

import back.exception.NotImplementedMethodException;

public interface DerivativeFunc extends Function{
    @Override
    default Function getDerivative() throws NotImplementedMethodException {
        throw new NotImplementedMethodException();
    }
}
