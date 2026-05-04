public class UseCase13QuantityMeasurementApp {

    public static void main(String[] args) {

        Quantity<VolumeUnit> v1 = new Quantity<>(5.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);

        System.out.println("Add: " + v1.add(v2));
        System.out.println("Subtract: " + v1.subtract(v2));
        System.out.println("Divide: " +
                v1.divide(new Quantity<>(10.0, VolumeUnit.LITRE)));

        System.out.println("Convert: " +
                v1.convertTo(VolumeUnit.MILLILITRE));

        System.out.println("Equality: " +
                v1.equals(new Quantity<>(5000.0, VolumeUnit.MILLILITRE)));
    }
}