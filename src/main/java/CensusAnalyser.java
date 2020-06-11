import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<IndiaCensusCSV> censusCSVList =null;

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));

            Iterator<IndiaCensusCSV> censusCSVIterator = new OpenCSVBuilder().getCSVfileIterator(reader,IndiaCensusCSV.class);
             censusCSVList = new OpenCSVBuilder().getCSVfileList(reader,IndiaCensusCSV.class);

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
        }

    }



    public int loadIndianStateData(String csvFilePath) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));

            Iterator<IndiaStateCode> censusCSVIterator = new OpenCSVBuilder().getCSVfileIterator(reader,IndiaStateCode.class);

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
        }
    }

    private <E> int getCount(Iterator<E> iterator){
        Iterable<E> csvIterable=()->iterator;
       int  numOfEnteries=(int) StreamSupport.stream(csvIterable.spliterator(),false)
               .count();
       return  numOfEnteries;
    }



}