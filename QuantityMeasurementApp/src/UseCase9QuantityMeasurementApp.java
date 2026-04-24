public class UseCase9QuantityMeasurementApp {

    public enum WeightUnit {
        KILOGRAM(1.0),
        GRAM(0.001),
        POUND(0.453592);

        private final double toKilogramFactor;

        WeightUnit(double toKilogramFactor) {
            this.toKilogramFactor = toKilogramFactor;
        }

        public double convertToBaseUnit(double value) {
            return value * toKilogramFactor;
        }

        public double convertFromBaseUnit(double baseValue) {
            return baseValue / toKilogramFactor;
        }
    }

    public static class QuantityWeight {
        private final double value;
        private final WeightUnit unit;

        public QuantityWeight(double value, WeightUnit unit) {
            if (unit == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid input for QuantityWeight");
            }
            this.value = value;
            this.unit = unit;
        }

        public QuantityWeight convertTo(WeightUnit targetUnit) {
            double baseValue = unit.convertToBaseUnit(value);
            double convertedValue = targetUnit.convertFromBaseUnit(baseValue);
            return new QuantityWeight(convertedValue, targetUnit);
        }

        public QuantityWeight add(QuantityWeight other) {
            double sumBase = unit.convertToBaseUnit(value) + other.unit.convertToBaseUnit(other.value);
            double sumTarget = unit.convertFromBaseUnit(sumBase);
            return new QuantityWeight(sumTarget, unit);
        }

        public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
            double sumBase = unit.convertToBaseUnit(value) + other.unit.convertToBaseUnit(other.value);
            double sumTarget = targetUnit.convertFromBaseUnit(sumBase);
            return new QuantityWeight(sumTarget, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityWeight)) return false;
            QuantityWeight other = (QuantityWeight) obj;
            return Math.abs(unit.convertToBaseUnit(value) - other.unit.convertToBaseUnit(other.value)) < 1e-6;
        }

        @Override
        public String toString() {
            return "Quantity(" + Math.round(value * 100000.0) / 100000.0 + ", " + unit + ")";
        }
    }

    public static void main(String[] args) {
        System.out.println("Equality Comparisons:");
        System.out.println("Input: Quantity(1.0, KILOGRAM).equals(Quantity(1.0, KILOGRAM)) → Output: " +
                new QuantityWeight(1.0, WeightUnit.KILOGRAM).equals(new QuantityWeight(1.0, WeightUnit.KILOGRAM)));
        System.out.println("Input: Quantity(1.0, KILOGRAM).equals(Quantity(1000.0, GRAM)) → Output: " +
                new QuantityWeight(1.0, WeightUnit.KILOGRAM).equals(new QuantityWeight(1000.0, WeightUnit.GRAM)));
        System.out.println("Input: Quantity(2.0, POUND).equals(Quantity(2.0, POUND)) → Output: " +
                new QuantityWeight(2.0, WeightUnit.POUND).equals(new QuantityWeight(2.0, WeightUnit.POUND)));
        System.out.println("Input: Quantity(1.0, KILOGRAM).equals(Quantity(2.20462, POUND)) → Output: " +
                new QuantityWeight(1.0, WeightUnit.KILOGRAM).equals(new QuantityWeight(2.20462, WeightUnit.POUND)));
        System.out.println("Input: Quantity(500.0, GRAM).equals(Quantity(0.5, KILOGRAM)) → Output: " +
                new QuantityWeight(500.0, WeightUnit.GRAM).equals(new QuantityWeight(0.5, WeightUnit.KILOGRAM)));

        System.out.println("\nUnit Conversions:");
        System.out.println("Input: Quantity(1.0, KILOGRAM).convertTo(GRAM) → Output: " +
                new QuantityWeight(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM));
        System.out.println("Input: Quantity(2.0, POUND).convertTo(KILOGRAM) → Output: " +
                new QuantityWeight(2.0, WeightUnit.POUND).convertTo(WeightUnit.KILOGRAM));
        System.out.println("Input: Quantity(500.0, GRAM).convertTo(POUND) → Output: " +
                new QuantityWeight(500.0, WeightUnit.GRAM).convertTo(WeightUnit.POUND));
        System.out.println("Input: Quantity(0.0, KILOGRAM).convertTo(GRAM) → Output: " +
                new QuantityWeight(0.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM));

        System.out.println("\nAddition Operations (Implicit Target Unit):");
        System.out.println("Input: Quantity(1.0, KILOGRAM).add(Quantity(2.0, KILOGRAM)) → Output: " +
                new QuantityWeight(1.0, WeightUnit.KILOGRAM).add(new QuantityWeight(2.0, WeightUnit.KILOGRAM)));
        System.out.println("Input: Quantity(1.0, KILOGRAM).add(Quantity(1000.0, GRAM)) → Output: " +
                new QuantityWeight(1.0, WeightUnit.KILOGRAM).add(new QuantityWeight(1000.0, WeightUnit.GRAM)));
        System.out.println("Input: Quantity(500.0, GRAM).add(Quantity(0.5, KILOGRAM)) → Output: " +
                new QuantityWeight(500.0, WeightUnit.GRAM).add(new QuantityWeight(0.5, WeightUnit.KILOGRAM)));

        System.out.println("\nAddition Operations (Explicit Target Unit):");
        System.out.println("Input: Quantity(1.0, KILOGRAM).add(Quantity(1000.0, GRAM), GRAM) → Output: " +
                new QuantityWeight(1.0, WeightUnit.KILOGRAM).add(new QuantityWeight(1000.0, WeightUnit.GRAM), WeightUnit.GRAM));
        System.out.println("Input: Quantity(1.0, POUND).add(Quantity(453.592, GRAM), POUND) → Output: " +
                new QuantityWeight(1.0, WeightUnit.POUND).add(new QuantityWeight(453.592, WeightUnit.GRAM), WeightUnit.POUND));
        System.out.println("Input: Quantity(2.0, KILOGRAM).add(Quantity(4.0, POUND), KILOGRAM) → Output: " +
                new QuantityWeight(2.0, WeightUnit.KILOGRAM).add(new QuantityWeight(4.0, WeightUnit.POUND), WeightUnit.KILOGRAM));
    }
}
