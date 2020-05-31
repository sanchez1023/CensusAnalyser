public class CSVBuilderException extends Exception {

    public

    enum ExceptionType {
            FILE_NOT_FOUND,
            NULL_POINTER_EXCEPTION,
            UNABLE_TO_PARSE,
        INVALID_DATA
        }

        ExceptionType type;

    CSVBuilderException(ExceptionType type, String message) {
            super(message);
            this.type = type;
        }
    }

