
public class UseCase7QuantityMeasurementApp {

    public enum LengthUnit {
        FEET(12.0),
        INCHES(1.0),
        YARDS(36.0),
        CENTIMETERS(0.393701);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }
    }

    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid input for QuantityLength");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toBaseUnit() {
            return value * unit.getConversionFactor();
        }

        public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
            if (other == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid operand or target unit");
            }
            double sumInBase = this.toBaseUnit() + other.toBaseUnit();
            double sumInTarget = sumInBase / targetUnit.getConversionFactor();
            return new QuantityLength(sumInTarget, targetUnit);
        }

        @Override
        public String toString() {
            return "Quantity(" + Math.round(value * 1000.0) / 1000.0 + ", " + unit + ")";
        }
    }

    public static void main(String[] args) {
        System.out.println("Example Output of running the App:");

        System.out.println("Input: add(Quantity(1.0, FEET), Quantity(12.0, INCHES), FEET) → Output: " +
                new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12.0, LengthUnit.INCHES), LengthUnit.FEET));

        System.out.println("Input: add(Quantity(1.0, FEET), Quantity(12.0, INCHES), INCHES) → Output: " +
                new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12.0, LengthUnit.INCHES), LengthUnit.INCHES));

        System.out.println("Input: add(Quantity(1.0, FEET), Quantity(12.0, INCHES), YARDS) → Output: " +
                new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12.0, LengthUnit.INCHES), LengthUnit.YARDS));

        System.out.println("Input: add(Quantity(1.0, YARDS), Quantity(3.0, FEET), YARDS) → Output: " +
                new QuantityLength(1.0, LengthUnit.YARDS).add(new QuantityLength(3.0, LengthUnit.FEET), LengthUnit.YARDS));

        System.out.println("Input: add(Quantity(36.0, INCHES), Quantity(1.0, YARDS), FEET) → Output: " +
                new QuantityLength(36.0, LengthUnit.INCHES).add(new QuantityLength(1.0, LengthUnit.YARDS), LengthUnit.FEET));

        System.out.println("Input: add(Quantity(2.54, CENTIMETERS), Quantity(1.0, INCHES), CENTIMETERS) → Output: " +
                new QuantityLength(2.54, LengthUnit.CENTIMETERS).add(new QuantityLength(1.0, LengthUnit.INCHES), LengthUnit.CENTIMETERS));

        System.out.println("Input: add(Quantity(5.0, FEET), Quantity(0.0, INCHES), YARDS) → Output: " +
                new QuantityLength(5.0, LengthUnit.FEET).add(new QuantityLength(0.0, LengthUnit.INCHES), LengthUnit.YARDS));

        System.out.println("Input: add(Quantity(5.0, FEET), Quantity(-2.0, FEET), INCHES) → Output: " +
                new QuantityLength(5.0, LengthUnit.FEET).add(new QuantityLength(-2.0, LengthUnit.FEET), LengthUnit.INCHES));
    }
}
