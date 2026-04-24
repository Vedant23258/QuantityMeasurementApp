
public class UseCase8QuantityMeasurementApp {

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

        public QuantityLength convertTo(LengthUnit targetUnit) {
            double baseValue = unit.convertToBaseUnit(value);
            double convertedValue = targetUnit.convertFromBaseUnit(baseValue);
            return new QuantityLength(convertedValue, targetUnit);
        }

        public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
            if (other == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid operand or target unit");
            }
            double sumBase = unit.convertToBaseUnit(value) + other.unit.convertToBaseUnit(other.value);
            double sumTarget = targetUnit.convertFromBaseUnit(sumBase);
            return new QuantityLength(sumTarget, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityLength)) return false;
            QuantityLength other = (QuantityLength) obj;
            return Math.abs(unit.convertToBaseUnit(value) - other.unit.convertToBaseUnit(other.value)) < 1e-6;
        }

        @Override
        public String toString() {
            return "Quantity(" + Math.round(value * 1000.0) / 1000.0 + ", " + unit + ")";
        }
    }

    public static void main(String[] args) {
        System.out.println("Input: Quantity(1.0, FEET).convertTo(INCHES) → Output: " +
                new QuantityLength(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES));

        System.out.println("Input: Quantity(1.0, FEET).add(Quantity(12.0, INCHES), FEET) → Output: " +
                new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12.0, LengthUnit.INCHES), LengthUnit.FEET));

        System.out.println("Input: Quantity(36.0, INCHES).equals(Quantity(1.0, YARDS)) → Output: " +
                new QuantityLength(36.0, LengthUnit.INCHES).equals(new QuantityLength(1.0, LengthUnit.YARDS)));

        System.out.println("Input: Quantity(1.0, YARDS).add(Quantity(3.0, FEET), YARDS) → Output: " +
                new QuantityLength(1.0, LengthUnit.YARDS).add(new QuantityLength(3.0, LengthUnit.FEET), LengthUnit.YARDS));

        System.out.println("Input: Quantity(2.54, CENTIMETERS).convertTo(INCHES) → Output: " +
                new QuantityLength(2.54, LengthUnit.CENTIMETERS).convertTo(LengthUnit.INCHES));

        System.out.println("Input: Quantity(5.0, FEET).add(Quantity(0.0, INCHES), FEET) → Output: " +
                new QuantityLength(5.0, LengthUnit.FEET).add(new QuantityLength(0.0, LengthUnit.INCHES), LengthUnit.FEET));

        System.out.println("Input: LengthUnit.FEET.convertToBaseUnit(12.0) → Output: " +
                LengthUnit.FEET.convertToBaseUnit(12.0) + " (already in base unit)");

        System.out.println("Input: LengthUnit.INCHES.convertToBaseUnit(12.0) → Output: " +
                LengthUnit.INCHES.convertToBaseUnit(12.0) + " (converted to feet)");
    }
}
