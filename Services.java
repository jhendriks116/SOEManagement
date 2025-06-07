package Project1;

public class Services
{
    private  int numSoldiers;
    private  int numEquipment;
    private  int numPolice;
    private int numSocialWorkers;           //Integer value to store number of Social Workers available
    private int numSupplies;                //Integer value to stor number of Supplies for Social Worker available
 
    private static Services theService;

    private Services(int numSoldiers,int numEquipment,int numPolice,
    int numSocial, int numSupplies){
        this.numSoldiers = numSoldiers;
        this.numEquipment = numEquipment;
        this.numPolice = numPolice;
        this.numSocialWorkers = numSocial;
        this.numSupplies = numSupplies;
    }

    public static Services getService(int numSoldiers,int numEquipment,int numPolice,
    int numSocial, int numSupplies){
        if (theService==null){
            theService = new Services(numSoldiers, numEquipment,
                numPolice, numSocial, numSupplies);
        }   
        return theService;
    }

            
    public static Services getService(){                          
        return theService;
    }

    //Checks if Social Workers can be deployed based on resources
    public boolean socialAvailable(int deploymentSize){
        return theService.numSocialWorkers >= deploymentSize && theService.numSupplies >= deploymentSize;
    }    

    //Deploys Social Workers, Supplies and one police if resources allow
    public void deploySocial(int deploymentSize){
        if (socialAvailable(deploymentSize)){
            theService.numSocialWorkers -= deploymentSize;
            theService.numSupplies -= deploymentSize;
            theService.numPolice -= 1;
        }
    }

    public boolean policeAvailable(int req)
    {
        return theService.numPolice >= req && theService.numPolice > 0;
    }

    public void deployPolice(int numNeeded){
        if (policeAvailable(numNeeded)){    
            theService.numPolice -= numNeeded;
        }
    }

    public boolean soldiersAvailable(int req)
    {
        return numSoldiers >= req &&
            theService.numEquipment >= req && 
            theService.numSoldiers > 0;
    }

    public void deploySoldiers(int numNeeded){
        if (soldiersAvailable(numNeeded)){
            theService.numSoldiers -= numNeeded;
            theService.numEquipment -= numNeeded;
        }
    }



}
