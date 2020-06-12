import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

class IndianCensusAdpater  extends  CensusAdapter {

    private  <E> int loadIndianStateData(Map<String, CensusDAO> stateCensusMap , String csvFilePath) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();

            Iterator<IndiaStateCode> stateCodeIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCode.class);
            Iterable<IndiaStateCode>  stateCodeIterable= ()->stateCodeIterator;
            StreamSupport.stream(stateCodeIterable.spliterator(),false).
                    filter(csvState->stateCensusMap.get(csvState)!=null).forEach(csvState->stateCensusMap.get(csvState.State).stateCode=csvState.stateCode);

            return stateCensusMap.size();
        }
        catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.
                            CENSUS_FILE_PROBLEM);

        }
        catch (RuntimeException e){
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.
                            INVALID_DATA);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }



    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDAO> censusList = super.loadCensusData(IndiaCensusCSV.class, csvFilePath[0]);
        this.loadIndianStateData(censusList, csvFilePath[1]);
        return censusList;
    }
}
