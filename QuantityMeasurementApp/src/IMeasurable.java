public interface IMeasurable {

    double getConversionFactor();

    String getUnitName();

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double baseValue);
}