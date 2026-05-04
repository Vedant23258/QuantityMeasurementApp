
public class UseCase10QuantityMeasurementApp {

    interface IMeasurable {
        double toBaseUnit(double value);
        double fromBaseUnit(double baseValue);
        String getUnitName();
    }

    enum LengthUnit implements IMeasurable {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CM(0.393701 / 12.0);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double toBaseUnit(double value) {
            return value * factor;
        }

        public double fromBaseUnit(double baseValue) {
            return baseValue / factor;
        }

        public String getUnitName() {
            return name();
        }
    }

    enum WeightUnit implements IMeasurable {
        KILOGRAM(1.0),
        GRAM(0.001),
        TONNE(1000.0),
        POUND(0.453592);

        private final double factor;

        WeightUnit(double factor) {
            this.factor = factor;
        }

        public double toBaseUnit(double value) {
            return value * factor;
        }

        public double fromBaseUnit(double baseValue) {
            return baseValue / factor;
        }

        public String getUnitName() {
            return name();
        }
    }

    static class Quantity<U extends IMeasurable> {
        private final double value;
        private final U unit;

        public Quantity(double value, U unit) {
            if (unit == null)
                throw new IllegalArgumentException("Unit cannot be null");

            if (Double.isNaN(value) || Double.isInfinite(value))
                throw new IllegalArgumentException("Invalid value");

            this.value = value;
            this.unit = unit;
        }

        private double toBase() {
            return unit.toBaseUnit(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (this.getClass() != obj.getClass()) return false;

            Quantity<?> other = (Quantity<?>) obj;

            if (this.unit.getClass() != other.unit.getClass())
                return false;

            return Double.compare(this.toBase(), other.toBase()) == 0;
        }

        public Quantity<U> convertTo(U targetUnit) {
            double base = this.toBase();
            double converted = targetUnit.fromBaseUnit(base);

            return new Quantity<>(round(converted), targetUnit);
        }

        public Quantity<U> add(Quantity<U> other, U targetUnit) {
            double sumBase = this.toBase() + other.toBase();
            double result = targetUnit.fromBaseUnit(sumBase);

            return new Quantity<>(round(result), targetUnit);
        }

        private double round(double val) {
            return Math.round(val * 100.0) / 100.0;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit.getUnitName() + ")";
        }
    }

    public static boolean demonstrateEquality(Quantity<?> q1, Quantity<?> q2) {
        return q1.equals(q2);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateConversion(
            Quantity<U> q, U target) {
        return q.convertTo(target);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(
            Quantity<U> q1, Quantity<U> q2, U target) {
        return q1.add(q2, target);
    }

    public static void main(String[] args) {

        Quantity<LengthUnit> f = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> i = new Quantity<>(12.0, LengthUnit.INCH);

        System.out.println("1 ft == 12 in: " + demonstrateEquality(f, i));
        System.out.println("1 ft in inches: " +
                demonstrateConversion(f, LengthUnit.INCH));
        System.out.println("Add: " +
                demonstrateAddition(f, i, LengthUnit.FEET));

        Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> g = new Quantity<>(1000.0, WeightUnit.GRAM);

        System.out.println("1 kg == 1000 g: " + demonstrateEquality(kg, g));
        System.out.println("1 kg in grams: " +
                demonstrateConversion(kg, WeightUnit.GRAM));
        System.out.println("Add: " +
                demonstrateAddition(kg, g, WeightUnit.KILOGRAM));

        System.out.println("Length vs Weight: " +
                demonstrateEquality(f, kg)); // false
    }
}