package Project1;

import java.io.*;
import java.util.ArrayList;

public class DoS
{
    private final ArrayList<Community> communities;
    private final ArrayList<Operation> ops;

    private final Services activeService;
    private final int forceMultiplier, gangLimit, rehabRate;

    public DoS(int forceMultiplier, int gangLimit,int rehabRate, int numSoldiers,int numEquipment,int numPolice,
    int numSocial, int numSupplies)
    {
        activeService = Services.getService(numSoldiers, numEquipment,
            numPolice, numSocial, numSupplies);
        communities= new ArrayList<Community>();
        ops = new ArrayList<Operation>();
        this.forceMultiplier = forceMultiplier;
        this.gangLimit = gangLimit;
        this.rehabRate = rehabRate;
    }

    public Services getService(){
        return activeService;
    }

    public int getForceMultiplier(){
        return forceMultiplier;
    }

    public int getGangLimit(){
        return gangLimit;
    }

    public Community getCommunity(String cname){
        Community retval=null;
        for (Community c:communities)
            if (c.getName().equals(cname))
                retval =c;
        if (retval ==null)
        {
            retval =new Community(cname);
            communities.add(retval);
        }
        return retval;   
    }

    public void assessForOps(){
        double emergencyRatio = 0.3; 
        for (Community cm:communities)
        //cm.assessForOp();
        {
            int numCriminals=cm.countCriminals();
            int totalResidents = cm.countResidents()+ numCriminals;
            int deploymentSize = numCriminals * forceMultiplier;
            String na = cm.getName();
            if (numCriminals > 0){
                if (numCriminals < gangLimit)
                {
                    if (Raid.canDeploy(deploymentSize)){
                        ops.add(new Raid(cm, forceMultiplier));
                    }    
                    else if (UnderCover.canDeploy()){
                        ops.add(new UnderCover(cm));
                    }
                    else return;
                }
            }    
            
            //Deploys ZOSO if criminals are greater than or equal to gangLimit and less than totalResidents * emergencyRatio
            if (numCriminals >= gangLimit && numCriminals < totalResidents * emergencyRatio){
                if(ZOSO.canDeploy(deploymentSize)){
                    ops.add(new ZOSO(cm, forceMultiplier));
                }
                else if (UnderCover.canDeploy()){
                    ops.add(new UnderCover(cm));
                }
                else return;
            }

            //Deploys SOE if criminals are greater than or equal to gangLimit and greater than or equal to totalResidents * emergencyRatio
            if (numCriminals >= gangLimit && numCriminals >= totalResidents * emergencyRatio){
                if (SOE.canDeploy(deploymentSize)){
                    ops.add(new SOE(cm, forceMultiplier, rehabRate));
                }
                    else if (UnderCover.canDeploy()){
                        ops.add(new UnderCover(cm));
                    }
                    else return;
                }
        }
    }             

    
        

    
    
    public void publicPolicyReport(){
         //Data for release to politicians.
        System.out.println("=================SUMMARY FOR POLICY MAKERS=============================");
        int totOps =0;              //Counter for total operations
        int totArrests =0;          //Counter for total arrests made
        int totRehabs =0;           //Counter for total rehabilitations made

        //Checks each operation and adds the number of arrests made
        for(Operation op:ops)
            {
                if (op instanceof SOE soe){
                    totArrests += soe.countArrests();
                    totRehabs += soe.countRehabs();         //Adds the number of rehabilitaions made during SOE
                    totOps++;
                }               
                else if (op instanceof ZOSO zoso){
                    totArrests += zoso.countArrests();
                    totOps++;
                }
                else if (op instanceof Raid raid){
                    totArrests += raid.countArrests();
                    totOps++;
                }
            }
        String str = "We expect " + totOps + " operation(s), yielding " + totArrests + " arrest(s)" + ".";
        //Checks if there were any rehabilitations and adds it to the string to be printed to the public
        if (totRehabs > 0){
            str = str.replace(".", " ");
            str += "and " + totRehabs + " rehab(s).";
        }
        
        System.out.println(str);
        System.out.println("");
        }
        
        public void classifiedInformationBrief(){
         System.out.println("=================CLASSIFIED INTERNAL OPERATION BRIEF=============================");
        //Security Officers Internal Brief
        //Prints toSting method from each operation that gives detailed classified report
        for(int i=0; i<ops.size(); i++)
            {
                Operation op = ops.get(i);
                if (op instanceof Raid raid)
                    System.out.println(raid);   
                else if (op instanceof UnderCover undercover)
                    System.out.println(undercover);   
                else if (op instanceof SOE soe)
                    System.out.println(soe);
                else if (op instanceof ZOSO zoso)
                    System.out.println(zoso);
            }   
        }

}

