import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;


public  class OpenCSVBuilder<E>  implements ICSVBuilder {
    public  Iterator<E> getCSVfileIterator(Reader reader, Class csvClass) throws CSVBuilderException {

        try{
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return  csvToBean.iterator();

        }
        catch(IllegalStateException e) {
            throw  new CSVBuilderException(CSVBuilderException.ExceptionType.INVALID_DATA,
                    e.getMessage());

        }
    }


    @Override
    public Iterator getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException {
        return null;
    }

    @Override
    public List getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException {
        return null;
    }
}
