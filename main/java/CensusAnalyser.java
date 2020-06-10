import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusDAO> censusList=null;
    List<IndiaStateCodeDAO> indianStateList = null;
    Map<String, CensusDAO> stateCensusMap;


    public  CensusAnalyser(){
        this.censusList=new ArrayList<IndiaCensusDAO>( );
        this.indianStateList = new ArrayList<IndiaStateCodeDAO>();
        this.stateCensusMap = new HashMap<String, CensusDAO>();


    }
    public <E> int loadIndiaCensusData(String csvFilePath, Class<E> csvClass) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));)
                {
           ICSVBuilder csvBuilder=     CSVBuilderFactory.createCSVBuilder();

                    Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
                    while(csvFileIterator.hasNext()){
                     this.censusList.add(new IndiaCensusDAO(csvFileIterator.next()));
                    }
            return censusList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType
                            .CENSUS_FILE_PROBLEM);
        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType
                            .INVALID_DATA);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }

    }



    public <E> int loadIndianStateData(String csvFilePath,Class<E> csvClass) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();

            Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while (csvFileIterator.hasNext()) {
                this.censusList.add(new IndiaCensusDAO(csvFileIterator.next()));
            }
            return censusList.size();
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

    private <E> int getCount(Iterator<E> iterator){
        Iterable<E> csvIterable=()->iterator;
       int  numOfEnteries=(int) StreamSupport.stream(csvIterable.spliterator(),false)
               .count();
       return  numOfEnteries;
    }


    public String getStateWiseSortedCensusData() {
        if(censusList == null || censusList.size() == 0){
            throw new CensusAnalyserException("No census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusComporator=Comparator.comparing(census->census.state);
        this.sort(censusComporator,censusList);
        String sortedStateCensusJson=new Gson().toJson(censusList);
        return sortedStateCensusJson;

    }
    public String getStateCodeWiseSortedCensusStateData() throws CensusAnalyserException {
        if (indianStateList == null || indianStateList.size() == 0) {
            throw new CensusAnalyserException("No census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaStateCodeDAO> censusComparator = Comparator.comparing(census -> census.StateCode);
        this.sort(censusComparator, indianStateList);
        String sortedStateCensusJson = new Gson().toJson(indianStateList);
        return sortedStateCensusJson;
    }
    private <E> void sort(Comparator<E> censusComparator, List list){
        for(int i=0;i<list.size()-i-1;i++){
            for(int j=0;j<list.size()-i-1;j++){
                E census1 = (E) list.get(j);
                E census2 = (E) list.get(j + 1);
                if(censusComparator.compare(census1,census2)>0){
                    list.set(j,census2);
                    list.set(j+1,census1);
                }
            }
        }
    }

    public void addStateCodeToCensusDAO(String CSV_FILE_PATH) throws CensusAnalyserException {
        if (stateCensusMap.size() == 0) {
            throw new CensusAnalyserException("NO census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA );
        }
        try {
            Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
            ICSVBuilder csvBuilder = new CSVBuilderFactory().createCSVBuilder();
            Iterator<IndiaStateCode> censusIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCode.class);
            while (censusIterator.hasNext()) {
                IndiaStateCode object = censusIterator.next();
                if (stateCensusMap.get(object.State) != null) {
                    CensusDAO censusObject = stateCensusMap.get(object.State);
                    censusObject.stateCode= object.stateCode;
                    stateCensusMap.put(object.State, censusObject);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.type.name(), e.getMessage());
        }
    }

    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("NO census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA );
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        this.sort(censusComparator, censusList);
        Collections.reverse(censusList);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }
    public String getPopulationDensityWiseSortedCensusData() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("NO census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA );
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        this.sort(censusComparator, censusList);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }
    public Comparator<CensusDAO> getComparater(String type) {
        switch (type) {
            case "totalArea":
                return Comparator.comparing(census -> census.totalArea);
            case "StateCode":
                return Comparator.comparing(census -> census.stateCode);
            case "populationDensity":
                return Comparator.comparing(census -> census.populationDensity);
            case "population":
                return Comparator.comparing(census -> census.population);
            case "state":
                return Comparator.comparing(census -> census.state);
            default:
                return Comparator.comparing(census -> census.totalArea);
        }
    }
    public String getSortedData(String type, boolean isReverse) throws CensusAnalyserException {
        if (stateCensusMap == null || stateCensusMap.size() == 0) {
            throw new CensusAnalyserException("NO census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA );
        }
        Comparator<CensusDAO> censusComparator = getComparater(type);
        List<CensusDAO> censusDAOS = stateCensusMap.values().stream().collect(Collectors.toList());
        this.sort(censusComparator, censusDAOS);
        if (isReverse)
            Collections.reverse(censusDAOS);
        String sortedStateCensusJson = new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;
    }
    public String getAreaWiseStateCensusData() {
     return    this.getSortedData("totalArea", true);

    }
}