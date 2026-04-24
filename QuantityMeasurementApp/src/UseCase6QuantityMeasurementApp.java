package com.quantitymeasurement;

public class UseCase6QuantityMeasurementApp {

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
            return value * unit.getConversionFactor(); // normalize to inches
        }

        public QuantityLength add(QuantityLength other) {
            if (other == null) {
                throw new IllegalArgumentException("Cannot add null QuantityLength");
            }
            double sumInBase = this.toBaseUnit() + other.toBaseUnit();
            double sumInTargetUnit = sumInBase / this.unit.getConversionFactor();
            return new QuantityLength(sumInTargetUnit, this.unit);
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    public static void main(String[] args) {
        System.out.println("Example Output of running the App:");

        System.out.println("Input: add(Quantity(1.0, FEET), Quantity(2.0, FEET)) → Output: " +
                new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(2.0, LengthUnit.FEET)));

        System.out.println("Input: add(Quantity(1.0, FEET), Quantity(12.0, INCHES)) → Output: " +
                new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12.0, LengthUnit.INCHES)));

        System.out.println("Input: add(Quantity(12.0, INCHES), Quantity(1.0, FEET)) → Output: " +
                new QuantityLength(12.0, LengthUnit.INCHES).add(new QuantityLength(1.0, LengthUnit.FEET)));

        System.out.println("Input: add(Quantity(1.0, YARDS), Quantity(3.0, FEET)) → Output: " +
                new QuantityLength(1.0, LengthUnit.YARDS).add(new QuantityLength(3.0, LengthUnit.FEET)));

        System.out.println("Input: add(Quantity(36.0, INCHES), Quantity(1.0, YARDS)) → Output: " +
                new QuantityLength(36.0, LengthUnit.INCHES).add(new QuantityLength(1.0, LengthUnit.YARDS)));

        System.out.println("Input: add(Quantity(2.54, CENTIMETERS), Quantity(1.0, INCHES)) → Output: " +
                new QuantityLength(2.54, LengthUnit.CENTIMETERS).add(new QuantityLength(1.0, LengthUnit.INCHES)));

        System.out.println("Input: add(Quantity(5.0, FEET), Quantity(0.0, INCHES)) → Output: " +
                new QuantityLength(5.0, LengthUnit.FEET).add(new QuantityLength(0.0, LengthUnit.INCHES)));

        System.out.println("Input: add(Quantity(5.0, FEET), Quantity(-2.0, FEET)) → Output: " +
                new QuantityLength(5.0, LengthUnit.FEET).add(new QuantityLength(-2.0, LengthUnit.FEET)));
    }
}
