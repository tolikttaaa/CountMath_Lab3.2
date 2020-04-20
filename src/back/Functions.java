package back;

public enum Functions implements DerivativeFunc {
    FUNCTION_1 {
        @Override
        public double get(double argument) {
            return Math.pow(argument, 3) - argument + 4;
        }

        @Override
        public Interval[] getNotAllowedScope() {
            return new Interval[0];
        }

        @Override
        public String toString() {
            return "y = x^3 - x + 4";
        }
    },
    FUNCTION_2 {
        @Override
        public double get(double argument) {
            return Math.cos(argument) / (Math.pow(argument, 2) + 1);
        }

        @Override
        public Interval[] getNotAllowedScope() {
            return new Interval[0];
        }

        @Override
        public String toString() {
            return "y = cos(x)/(x^2 + 1)";
        }
    },
    FUNCTION_3 {
        @Override
        public double get(double argument) {
            return Math.sin(argument);
        }

        @Override
        public Interval[] getNotAllowedScope() {
            return new Interval[0];
        }

        @Override
        public String toString() {
            return "y = sin(x)";
        }
    }
}
