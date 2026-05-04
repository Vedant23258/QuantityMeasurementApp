public class UseCase11QuantityMeasurementApp {

    interface IMeasurable {
        double getConversionFactor();
        double convertToBaseUnit(double value);
        double convertFromBaseUnit(double baseValue);
        String getUnitName();
    }

    enum VolumeUnit implements IMeasurable {

        LITRE(1.0),
        MILLILITRE(0.001),
        GALLON(3.78541);

        private final double factor;

        VolumeUnit(double factor) {
            this.factor = factor;
        }

        public double getConversionFactor() {
            return factor;
        }

        public double convertToBaseUnit(double value) {
            return value * factor; // to litres
        }

        public double convertFromBaseUnit(double baseValue) {
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
            if (unit == null || Double.isNaN(value) || Double.isInfinite(value)) {
                throw new IllegalArgumentException("Invalid input");
            }
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
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity<?> other = (Quantity<?>) obj;

            if (this.unit.getClass() != other.unit.getClass()) return false;

            double base1 = this.unit.convertToBaseUnit(this.value);
            double base2 = other.unit.convertToBaseUnit(other.value);

            return Math.abs(base1 - base2) < 0.0001;
        }

        public Quantity<U> convertTo(U targetUnit) {
            double base = unit.convertToBaseUnit(value);
            double converted = targetUnit.convertFromBaseUnit(base);
            return new Quantity<>(round(converted), targetUnit);
        }

        public Quantity<U> add(Quantity<U> other) {
            return add(other, this.unit);
        }

        public Quantity<U> add(Quantity<U> other, U targetUnit) {
            double base1 = this.unit.convertToBaseUnit(this.value);
            double base2 = other.unit.convertToBaseUnit(other.value);

            double sumBase = base1 + base2;
            double result = targetUnit.convertFromBaseUnit(sumBase);

            return new Quantity<>(round(result), targetUnit);
        }

        private double round(double value) {
            return Math.round(value * 100.0) / 100.0;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit.getUnitName() + ")";
        }
    }

    public static void main(String[] args) {

        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> v3 = new Quantity<>(1.0, VolumeUnit.GALLON);

        System.out.println("1L == 1000mL: " + v1.equals(v2)); // true
        System.out.println("1 Gallon == 3.78541L: " + v3.equals(new Quantity<>(3.78541, VolumeUnit.LITRE)));

        System.out.println("1L -> mL: " + v1.convertTo(VolumeUnit.MILLILITRE));
        System.out.println("1 Gallon -> L: " + v3.convertTo(VolumeUnit.LITRE));


        System.out.println("1L + 1000mL: " + v1.add(v2));
        System.out.println("1L + 1 Gallon (in mL): " + v1.add(v3, VolumeUnit.MILLILITRE));
    }
}