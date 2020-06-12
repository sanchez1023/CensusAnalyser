import com.opencsv.bean.CsvBindByName;


public class UsCensusCSV {
    @CsvBindByName(column = "State Id", required = true)
    public String stateId;

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population")
    public int population;

    @CsvBindByName(column = "Housing units")
    public float housingUnits;

    @CsvBindByName(column = "Total area")
    public float totalArea;

    @CsvBindByName(column = "Water area")
    public float waterArea;

    @CsvBindByName(column = "Land area")
    public float landArea;

    @CsvBindByName(column = "Population Density")
    public float populationDensity;

    @CsvBindByName(column = "HousingDensity")
    public float housingDensity;

    public UsCensusCSV(String state, String stateCode, int population, float populationDensity, float totalArea) {

    }
}
