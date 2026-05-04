import java.util.function.DoubleBinaryOperator;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity<?> other)) return false;

        if (this.unit.getClass() != other.unit.getClass())
            return false;

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return Math.abs(base1 - base2) < 0.0001;
    }

    public Quantity<U> convertTo(U targetUnit) {
        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);
        return new Quantity<>(converted, targetUnit);
    }

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        validate(other, targetUnit, true);
        double baseResult = perform(other, ArithmeticOperation.ADD);
        double result = targetUnit.convertFromBaseUnit(baseResult);
        return new Quantity<>(round(result), targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validate(other, targetUnit, true);
        double baseResult = perform(other, ArithmeticOperation.SUBTRACT);
        double result = targetUnit.convertFromBaseUnit(baseResult);
        return new Quantity<>(round(result), targetUnit);
    }

    public double divide(Quantity<U> other) {
        validate(other, null, false);
        return perform(other, ArithmeticOperation.DIVIDE);
    }

    private void validate(Quantity<U> other, U targetUnit, boolean needTarget) {

        if (other == null)
            throw new IllegalArgumentException("Other quantity is null");

        if (unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Different measurement categories");

        if (!Double.isFinite(value) || !Double.isFinite(other.value))
            throw new IllegalArgumentException("Invalid numeric value");

        if (needTarget && targetUnit == null)
            throw new IllegalArgumentException("Target unit required");
    }

    private double perform(Quantity<U> other, ArithmeticOperation op) {

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        if (op == ArithmeticOperation.DIVIDE && base2 == 0)
            throw new ArithmeticException("Divide by zero");

        return op.compute(base1, base2);
    }

    private enum ArithmeticOperation {

        ADD((a, b) -> a + b),
        SUBTRACT((a, b) -> a - b),
        DIVIDE((a, b) -> a / b);

        private final DoubleBinaryOperator op;

        ArithmeticOperation(DoubleBinaryOperator op) {
            this.op = op;
        }

        public double compute(double a, double b) {
            return op.applyAsDouble(a, b);
        }
    }

    private double round(double val) {
        return Math.round(val * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return value + " " + unit.getUnitName();
    }
}