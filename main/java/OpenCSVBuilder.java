import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;


public  class OpenCSVBuilder<E>  implements ICSVBuilder {
    public  Iterator<E> getCSVfileIterator(Reader reader, Class csvClass) throws CSVBuilderException {
        return  this.getCsvBean(reader,csvClass).iterator();


    }


    @Override
    public Iterator getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException {
        return null;
    }

    @Override
    public List getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException {
    return  this.getCsvBean(reader,csvClass).parse();

    }

    private CsvToBean<E> getCsvBean(Reader reader, Class csvClass) throws CSVBuilderException {
        try{
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            return csvToBeanBuilder.build();


        }
        catch(IllegalStateException e) {
            throw  new CSVBuilderException(CSVBuilderException.ExceptionType.INVALID_DATA,
                    e.getMessage());

        }
    }
}
