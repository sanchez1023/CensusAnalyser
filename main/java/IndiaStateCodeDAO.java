public  class IndiaStateCodeDAO {
    public int SrNo;
    public String State;
    public String Name;
    public int  TIN;
    public String StateCode;

    IndiaStateCodeDAO( IndiaStateCode stateCode){
        this.SrNo=stateCode.srNo;
        this.State=stateCode.State;
        this.Name=stateCode.stateName;
        this.TIN=stateCode.tin;
        this.StateCode=stateCode.stateCode;
    }

}
