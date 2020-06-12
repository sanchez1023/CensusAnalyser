



import java.util.Map;

    class USCensusAdapter extends CensusAdapter {
        @Override
        public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
            return super.loadCensusData(UsCensusCSV.class, csvFilePath[0]);
        }
    }

