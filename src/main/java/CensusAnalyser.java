import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CensusAnalyser {

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));

            Iterator<IndiaCensusCSV> censusCSVIterator = this.getCSVfileIterator(reader,IndiaCensusCSV.class);
            int namOfEnteries = 0;
            while (censusCSVIterator.hasNext()) {
                namOfEnteries++;
                IndiaCensusCSV censusData = censusCSVIterator.next();
            }
            return namOfEnteries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_DATA);
        }

    }

    public int loadIndianStateData(String csvFilePath) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));

            Iterator<IndiaStateCode> censusCSVIterator = this.getCSVfileIterator(reader,IndiaStateCode.class);
            int namOfEnteries = 0;
            List<IndiaStateCode> csvData = new ArrayList<>();
            while (censusCSVIterator.hasNext()) {
                namOfEnteries++;
                IndiaStateCode censusData = censusCSVIterator.next();
                csvData.add(censusData);
            }
            System.out.println(csvData);
            return namOfEnteries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);

        }
        catch (RuntimeException e){
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_DATA);
        }
    }

    private <E> Iterator<E> getCSVfileIterator(Reader reader,Class csvClass)throws    CensusAnalyserException{

        try{
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return  csvToBean.iterator();

        }
        catch(IllegalStateException e) {
            throw  new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_DATA);
            
        }
    }


}