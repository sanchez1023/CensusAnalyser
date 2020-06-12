import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusLoader {
    public   <E> Map  loadCensusData( Class<E> censusCSVClass,String ... csvFilePath) {
        Map<String, CensusDAO> stateCensusMap=new HashMap<>();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));

            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();

            Iterator<E> csvIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<E>  csvIterable= ()->csvIterator;
            if(censusCSVClass.getName().equals("censusanalyser.IndiaCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map((IndiaCensusCSV.class::cast))
                        .forEach(censusCSV -> stateCensusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            } else if(censusCSVClass.getName().equals("censusanalyser.USCensusCSV")){
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map((UsCensusCSV.class::cast))
                        .forEach(censusCSV -> stateCensusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            if (csvFilePath.length == 1) {
                return stateCensusMap;
            }
            this.loadIndianStateData(stateCensusMap,csvFilePath[1]);
            return  stateCensusMap;
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

}
