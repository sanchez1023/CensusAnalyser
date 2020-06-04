import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusCSV> censusCSVList=null;
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));)
                {
           ICSVBuilder csvBuilder=     CSVBuilderFactory.createCSVBuilder();

            censusCSVList = csvBuilder.getCSVFileList(reader,IndiaCensusCSV.class);

            return censusCSVList.size();
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
            ICSVBuilder csvBuilder=     CSVBuilderFactory.createCSVBuilder();

            Iterator<IndiaStateCode> censusCSVIterator = csvBuilder.getCSVFileIterator(reader,IndiaStateCode.class);

            return this.getCount(censusCSVIterator);
        } catch (IOException e) {
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
        if(censusCSVList == null || censusCSVList.size() == 0){
            throw new CensusAnalyserException("No census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusCSV> censusComporator=Comparator.comparing(census->census.state);
        this.sort(censusComporator);
        String sortedStateCensusJson=new Gson().toJson(censusCSVList);
        return sortedStateCensusJson;

    }
    private  void sort(Comparator<IndiaCensusCSV> censusComparator){
        for(int i=0;i<censusCSVList.size()-i-1;i++){
            for(int j=0;j<censusCSVList.size()-i-1;j++){
                IndiaCensusCSV census1=censusCSVList.get(j);
                IndiaCensusCSV census2=censusCSVList.get(j+1);
                if(censusComparator.compare(census1,census2)>0){
                    censusCSVList.set(j,census2);
                    censusCSVList.set(j+1,census1);
                }
            }
        }
    }
}