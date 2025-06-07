package Project1;

public class ZOSO extends Operation{
    private int numArrests;

    //Checks if a ZOSO Operation can be deployed based on resources available
    public static boolean canDeploy(int deploymentSize){
        Services service = Services.getService();
        return service.soldiersAvailable(deploymentSize) && service.policeAvailable(1);
    }

    //Constructor to initialise a ZOSO Operation
    public ZOSO(Community community, int multiplier){
        super(community);
        Services service = Services.getService();
        int numSoldiers = community.getCriminals().size() * multiplier;

        service.deploySoldiers(numSoldiers);
        service.deployPolice(1);

        numArrests = community.getCriminals().size();
        for (Criminal c : community.getCriminals()){
            c.arrest();
        }
    }

    //Counter to keep track of the number of arrests made during the Operation
    public int countArrests(){
        return numArrests;
    }

    //String method used for Classified Report
    public String toString(){
        return "Operation " + super.getCallSign() + " to be deployed as a ZOSO in " + cm.getName() + ".\nExpect " + countArrests() + " arrest(s)" + ".";
    }
}
