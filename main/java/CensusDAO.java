public class CensusDAO {
    public int population;
    public float populationDensity;
    public float totalArea;
    public String state;
    public String stateCode;
    public CensusDAO(IndiaCensusCSV indiaCensusObject) {
        state = indiaCensusObject.state;
        totalArea = indiaCensusObject.areaInSqKm;
        populationDensity = indiaCensusObject.densityPerSqKm;
        population = indiaCensusObject.population;
    }

    public CensusDAO(UsCensusCSV censuscsv) {
        state = censuscsv.state;
        totalArea = censuscsv.totalArea;
        populationDensity = censuscsv.populationDensity;
        population = censuscsv.population;
        stateCode=censuscsv.stateId;
    }

}
