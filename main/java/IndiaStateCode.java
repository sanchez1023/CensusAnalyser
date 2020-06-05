import com.opencsv.bean.CsvBindByName;

public class IndiaStateCode {

    @CsvBindByName(column = "SrNo" , required = true)
    int srNo;
    @CsvBindByName(column = "State")
    public String State;

    @CsvBindByName(column = "State Name" , required = true)
    String stateName;
    @CsvBindByName(column = "TIN" , required = true)
    int tin;
    @CsvBindByName(column = "StateCode" , required = true)
    String stateCode;

    @Override
    public String toString() {
        return "IndiaStateCode{" +
                "srNo=" + srNo +
                ", stateName='" + stateName + '\'' +
                ", tin='" + tin + '\'' +
                ", stateCode='" + stateCode + '\'' +
                '}';
    }
}