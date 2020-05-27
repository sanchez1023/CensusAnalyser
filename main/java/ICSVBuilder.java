import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder<E> {
    public   Iterator<E> getCSVfileIterator(Reader reader, Class csvClass) throws CensusAnalyserException;
    public List<E> getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException;
    }

