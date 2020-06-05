import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusDAO> censusList=null;
    List<IndiaStateCodeDAO> indianStateList = null;

    public  CensusAnalyser(){
        this.censusList=new ArrayList<IndiaCensusDAO>( );
        this.indianStateList = new ArrayList<IndiaStateCodeDAO>();

    }
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
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



    public int loadIndianStateData(String csvFilePath) {
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
}