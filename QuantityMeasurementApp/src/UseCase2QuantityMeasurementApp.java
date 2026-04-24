
public class UseCase2QuantityMeasurementApp {

    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (this.getClass() != obj.getClass()) return false;

            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (this.getClass() != obj.getClass()) return false;

            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    public static void demonstrateFeetEquality() {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        Feet f3 = new Feet(2.0);

        System.out.println("Feet Same Value (1.0, 1.0): " + f1.equals(f2)); // true
        System.out.println("Feet Different Value (1.0, 2.0): " + f1.equals(f3)); // false
    }

    public static void demonstrateInchesEquality() {
        Inches i1 = new Inches(1.0);
        Inches i2 = new Inches(1.0);
        Inches i3 = new Inches(2.0);

        System.out.println("Inches Same Value (1.0, 1.0): " + i1.equals(i2)); // true
        System.out.println("Inches Different Value (1.0, 2.0): " + i1.equals(i3)); // false
    }

    public static void main(String[] args) {

        demonstrateFeetEquality();
        demonstrateInchesEquality();

        Feet f = new Feet(1.0);
        System.out.println("Feet Null Comparison: " + f.equals(null)); // false
        System.out.println("Feet Different Type: " + f.equals("1.0")); // false

        Inches i = new Inches(1.0);
        System.out.println("Inches Same Reference: " + i.equals(i)); // true
    }
}