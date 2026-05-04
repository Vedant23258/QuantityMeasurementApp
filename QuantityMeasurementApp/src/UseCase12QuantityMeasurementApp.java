public class UseCase12QuantityMeasurementApp {

    public static void main(String[] args) {

        Quantity<VolumeUnit> v1 = new Quantity<>(5.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> v3 = new Quantity<>(1.0, VolumeUnit.GALLON);

        System.out.println("1L == 1000mL : " +
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .equals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));

        System.out.println("5L to mL : " + v1.convertTo(VolumeUnit.MILLILITRE));

        System.out.println("Addition : " + v1.add(v2));


        System.out.println("Subtraction : " + v1.subtract(v2)); // 4.5 L

        System.out.println("Subtraction (mL) : " +
                v1.subtract(v2, VolumeUnit.MILLILITRE)); // 4500 mL

        System.out.println("Division : " +
                v1.divide(new Quantity<>(10.0, VolumeUnit.LITRE))); // 0.5

        System.out.println("Division (mL vs L) : " +
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
                        .divide(new Quantity<>(1.0, VolumeUnit.LITRE))); // 1.0
    }
}
