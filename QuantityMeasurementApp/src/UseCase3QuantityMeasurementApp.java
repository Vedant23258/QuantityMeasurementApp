
public class UseCase3QuantityMeasurementApp {

    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    static class Length {
        private final double value;
        private final LengthUnit unit;

        public Length(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;
            if (obj == null) return false;
            if (this.getClass() != obj.getClass()) return false;

            Length other = (Length) obj;

            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }
    }

    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        return l1.equals(l2);
    }

    public static void demonstrateFeetEquality() {
        Length f1 = new Length(1.0, LengthUnit.FEET);
        Length f2 = new Length(1.0, LengthUnit.FEET);

        System.out.println("Feet Equal (1.0, 1.0): " +
                demonstrateLengthEquality(f1, f2));
    }

    public static void demonstrateInchesEquality() {
        Length i1 = new Length(1.0, LengthUnit.INCH);
        Length i2 = new Length(1.0, LengthUnit.INCH);

        System.out.println("Inches Equal (1.0, 1.0): " +
                demonstrateLengthEquality(i1, i2));
    }

    public static void demonstrateFeetInchesComparison() {
        Length f = new Length(1.0, LengthUnit.FEET);
        Length i = new Length(12.0, LengthUnit.INCH);

        System.out.println("1 Feet == 12 Inches: " +
                demonstrateLengthEquality(f, i));
    }

    public static void main(String[] args) {

        demonstrateFeetEquality();
        demonstrateInchesEquality();
        demonstrateFeetInchesComparison();

        Length l = new Length(1.0, LengthUnit.FEET);
        System.out.println("Null check: " + l.equals(null));
        System.out.println("Same reference: " + l.equals(l));
    }
}