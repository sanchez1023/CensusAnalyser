import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder<E> {
    public   Iterator<E> getCSVfileIterator(Reader reader, Class csvClass) throws CensusAnalyserException;

    }

