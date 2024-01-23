package edu.gmu.mason.vanilla;

import edu.gmu.mason.vanilla.environment.BuildingUnit;
import edu.gmu.mason.vanilla.log.Skip;

import java.util.Random;

/**
 * General description_________________________________________________________
 * A class to represent disease properties of a person such as infected or not, infection time
 * and so on. Each agent has one {@code Disease} object.
 *
 * @author Jonathan Wang
 *
 */
public class Disease {
    @Skip
    private Person agent;
    //private boolean infected;
    public int period;
    private int initTime;
    private int LastDate;
    private BuildingUnit diseaseSource;
    private Random rand;

    public Disease(Person agent, BuildingUnit diesaseSource, int startDayAfterInitialization){
        this.agent=agent;
        //this.infected=false;
        this.initTime=agent.getSimulationTime().getDayOfYear()+startDayAfterInitialization;
        this.diseaseSource=diesaseSource;
        this.LastDate=agent.getSimulationTime().getDayOfYear();
        this.rand=new Random();
    }

    public void update(){
        if(agent.getSimulationTime().getDayOfYear()-initTime>0){
            BuildingUnit currentUnit=this.agent.getCurrentUnit();
            if(this.agent.getInfected()){
                if(agent.getSimulationTime().getDayOfYear()>LastDate){
                    period+=1;
                    LastDate++;
                }
                //todo: infecting other people; Recovery;
            }else{
                if(currentUnit.getId()==diseaseSource.getId()){
                    float p=rand.nextFloat();
                    if(p<0.9){
                        //infected=true;
                        agent.setInfected(true);
                        period=0;
//                        System.out.println("Infected!!!!");
//                        System.out.println("Infect Id:" +currentUnit.getId());
                    }
                }
            }
        }
    }
}
