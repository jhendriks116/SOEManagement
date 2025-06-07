package Project1;

public class SOE extends ZOSO{
    private int numRehabs;

    //Checks if a SOE Operation can be deployed based on resources available
    public static boolean canDeploy(int deploymentSize){
        Services service = Services.getService();
        return service.soldiersAvailable(deploymentSize) && service.socialAvailable(deploymentSize) && service.policeAvailable(2);
    }

    //Constructor to initialise a SOE Operation
    public SOE(Community community, int multiplier, int rehabRate){
        super(community, multiplier);
        int numCriminals = community.getCriminals().size();
        numRehabs = numCriminals * rehabRate / 100;
        
        //Rehabilitates criminals and reinstates them into the community as a resident
        for (int i = 0; i < numRehabs && !community.getCriminals().isEmpty(); i++){
            Criminal criminal = community.getCriminals().remove(0);
            criminal.rehabilitate();
            Resident resident = new Resident(criminal.getName(), community);
        }
        
        Services service = Services.getService();

        service.deploySocial(numCriminals);
    }
    
    //Counter to keep track of the number of Rehabilitations done during the Operation
    public int countRehabs(){
        return numRehabs;
    }

    //String method used for Classified Report
    public String toString(){
        String result = super.toString();
        result = result.replace(".", " ");
        result = result.replace("ZOSO", "SOE");
        if (countRehabs() > 0){
            result += "and " + countRehabs() + " rehabilitation(s).";
        }
        return result;
    }
}
