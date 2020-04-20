package back;

public interface ConstantFunc extends Function {
    Function ZERO_FUNCTION = new Function() {
        @Override
        public double get(double argument) {
            return 0d;
        }

        @Override
        public Interval[] getNotAllowedScope() {
            return new Interval[0];
        }

        @Override
        public Function getDerivative() {
            return ZERO_FUNCTION;
        }
    };

    @Override
    default Interval[] getNotAllowedScope() {
        return new Interval[0];
    }

    @Override
    default Function getDerivative() {
        return ZERO_FUNCTION;
    }
}
