import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE="./src/main/resources/IndiaStateCensusData.txt";
    private static final String WRONG_CSV_FILE_DELIMITER="./src/test/resources/delimeter.csv";
    private static final String CSV_FILE_INVALID_HEADER="./src/test/resources/InvalidHeader.csv";
    private static final String INDIA_STATECODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_ISC_CSV_FILE_DELIMITER="./src/test/resources/IscDelimiter.csv";
    private static final String WRONG_ISC_CSV_FILE_INVALID_HEADER="./src/test/resources/IscHeader.csv";
    private static final String WRONG_INDIAN_STATECODE_CSV_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String WRONG_INDIAN_STATECODE_CSV_FILE_TYPE="./src/main/resources/IndiaStateCode.txt";
    private static final String US_CENSUS_CSV_PATH = "./src/main/resources/USCensusData.csv";


    @Test
    public void givenCsvFilePathShouldReturnProperRecods() throws CensusAnalyserException {
        CensusAnalyser csvStateCensus = new CensusAnalyser();
        int list = csvStateCensus.loadIndiaCensusData(IndiaCensusCSV.class,INDIA_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(29,list);
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(IndiaCensusCSV.class,WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndiaCensusData_WithWrongFileType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData (IndiaCensusCSV.class,WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndiaCensusData_WithWrongFileData_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(IndiaCensusCSV.class,WRONG_CSV_FILE_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_DATA,e.type);
        }
    }
    @Test
    public void givenIndiaCensusData_WithWrongFileHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(IndiaCensusCSV.class,CSV_FILE_INVALID_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_DATA,e.type);
        }
    }
//    @Test
//    public void givenIndianStateCodeCsvFilePath_ShouldReturnProperRecords() {
//        try {
//            CensusAnalyser csvStateCensus = new CensusAnalyser();
//            int list = csvStateCensus.loadIndianStateData(INDIA_STATECODE_CSV_FILE_PATH,IndiaStateCode.class);
//            Assert.assertEquals(37, list);
//        }
//        catch (CensusAnalyserException e) {
//            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
//        }
//    }

    @Test
    public void givenIndianStateData_WithWrongFilePath_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(IndiaCensusCSV.class,WRONG_ISC_CSV_FILE_INVALID_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFileType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(IndiaCensusCSV.class,WRONG_ISC_CSV_FILE_INVALID_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFileData_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(IndiaCensusCSV.class,WRONG_ISC_CSV_FILE_INVALID_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_DATA,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFileHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(IndiaCensusCSV.class,WRONG_ISC_CSV_FILE_INVALID_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_DATA,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();

            String  SortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
             IndiaCensusCSV[] censusCSV= new Gson().fromJson(SortedCensusData,IndiaCensusCSV[].class);
            System.out.println(censusCSV[0].state);
            Assert.assertEquals("Andhra Pradesh",censusCSV[0].state);
        }catch (CensusAnalyserException err){}

    }
    @Test
    public void givenIndianStateData_WhenSortedOnStateCode_ShouldReturnSortedState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(IndiaCensusCSV.class,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATECODE_CSV_FILE_PATH);
            String stateCodeWiseSortedCensusData = censusAnalyser.getStateCodeWiseSortedCensusStateData();
            IndiaStateCode[] indiancensusList = new Gson().fromJson(stateCodeWiseSortedCensusData, IndiaStateCode[].class);
            System.out.println(indiancensusList[0].stateCode);
            Assert.assertEquals("AN", indiancensusList[0].stateCode);
        } catch (CensusAnalyserException err) {

        }
    }
    @Test
    public void loadStateCode_to_indiaCensusDao() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadIndiaCensusData( IndiaCensusCSV.class,INDIA_CENSUS_CSV_FILE_PATH
);
            censusAnalyser.addStateCodeToCensusDAO(INDIA_CENSUS_CSV_FILE_PATH);

        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenCensusData_whenSortedBasedOn_population_should_return_sorted_data() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadIndiaCensusData(IndiaCensusCSV.class,INDIA_CENSUS_CSV_FILE_PATH);
            String stateCodeWiseSortedCensusData = censusAnalyser.getPopulationWiseSortedCensusData();
            IndiaCensusCSV[] indiancensusList = new Gson().fromJson(stateCodeWiseSortedCensusData, IndiaCensusCSV[].class);
            System.out.println("value of population"+indiancensusList[0]);
            Assert.assertEquals(199812341, indiancensusList[0].population);

        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void givenCensusData_whenSortedBasedOn_population_density_should_return_sorted_data() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadIndiaCensusData(IndiaCensusCSV.class,INDIA_CENSUS_CSV_FILE_PATH );
            String stateCodeWiseSortedCensusData = censusAnalyser.getPopulationDensityWiseSortedCensusData();
            IndiaCensusCSV[] indiancensusList = new Gson().fromJson(stateCodeWiseSortedCensusData, IndiaCensusCSV[].class);
            System.out.println(indiancensusList[0]);
            Assert.assertEquals(22.0, indiancensusList[0].densityPerSqKm,0);

        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void givenCensusData_whenSortedBasedOn_largest_state_by_area_should_return_sorted_data() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadIndiaCensusData( IndiaCensusCSV.class,INDIA_CENSUS_CSV_FILE_PATH);
            String stateCodeWiseSortedCensusData = censusAnalyser.getAreaWiseStateCensusData();
            CensusDAO[] indiancensusList = new Gson().fromJson(stateCodeWiseSortedCensusData, CensusDAO[].class);
            System.out.println(indiancensusList[0]);
            Assert.assertEquals(342239.0, indiancensusList[0].totalArea, 0);

        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecords(){
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
int censusDataCnt= censusAnalyser.loadUsCensusData(US_CENSUS_CSV_PATH);
            System.out.println("valu eof count"+censusDataCnt);
            Assert.assertEquals(51,censusDataCnt);
        }



         catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }


}
