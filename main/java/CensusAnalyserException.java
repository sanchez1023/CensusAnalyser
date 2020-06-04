public class CensusAnalyserException extends RuntimeException {



    enum ExceptionType {
        CENSUS_FILE_PROBLEM,
        INVALID_DATA,
        UNABLE_TO_PARSE,
        NO_CENSUS_DATA
    }

   public  ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
    public CensusAnalyserException(String name, String message) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }
}