package Project1;

public class Raid extends Operation
{
    private int numOfArrests;           //Integer value that records the number of arrests

    public static boolean canDeploy(int numCriminalsAdjusted){
        Services service =  Services.getService();
        return service.policeAvailable(numCriminalsAdjusted);   
    }

    public Raid(Community community)
    {
        super(community);

        //Update to Raid Constructor to arrest criminals in community
        numOfArrests = community.countCriminals();
        for (Criminal c : community.getCriminals()){
            c.arrest();
        }
    }

    //New constructor that includes multiplier argument
    public Raid(Community community, int multiplier)
    {
        super(community);
        int deploymentSize = community.getCriminals().size() * multiplier;

        Services service = Services.getService();
        
        service.deployPolice(deploymentSize);

        numOfArrests = community.getCriminals().size();
        for (Criminal c : community.getCriminals()){
            c.arrest();
        }
    }

    //Update to countArrests to return the number of arrests made
    public int countArrests(){
        return numOfArrests;
    }

    public String toString(){
            String str = "Operation " +super.getCallSign() + " to be deployed as a Raid in " +cm.getName()+ 
                           ".\nExpect "+countArrests() +" arrest(s).";
                           
                           return str;
                        }
   
}
