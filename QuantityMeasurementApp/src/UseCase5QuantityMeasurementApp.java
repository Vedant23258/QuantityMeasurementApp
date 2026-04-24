
public class UseCase5QuantityMeasurementApp {

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

    public static double convert(double value, LengthUnit source, LengthUnit target) {
        if (source == null || target == null || !Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid input for conversion");
        }
        double valueInBase = value * source.getConversionFactor();
        return valueInBase / target.getConversionFactor();
    }

    public static void main(String[] args) {
        System.out.println("Example Output of running the App:");

        System.out.println("Input: convert(1.0, FEET, INCHES) → Output: " +
                convert(1.0, LengthUnit.FEET, LengthUnit.INCHES));

        System.out.println("Input: convert(3.0, YARDS, FEET) → Output: " +
                convert(3.0, LengthUnit.YARDS, LengthUnit.FEET));

        System.out.println("Input: convert(36.0, INCHES, YARDS) → Output: " +
                convert(36.0, LengthUnit.INCHES, LengthUnit.YARDS));

        System.out.println("Input: convert(1.0, CENTIMETERS, INCHES) → Output: " +
                convert(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCHES));

        System.out.println("Input: convert(0.0, FEET, INCHES) → Output: " +
                convert(0.0, LengthUnit.FEET, LengthUnit.INCHES));
    }
}
