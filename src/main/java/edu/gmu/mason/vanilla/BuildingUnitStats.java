package edu.gmu.mason.vanilla;

import java.util.HashSet;

public class BuildingUnitStats {
    public long buildingUnitId;
    public int infectedVisits;
    public int uninfectedVisits;
    public long totalAgent;
    public double ratio;
    private HashSet<Person> infectedVisitedAgent;
    private HashSet<Person> unInfectedVisitedAgent;
    public BuildingUnitStats(long buildingUnitId, long totalAgent){
        this.ratio=0;
        this.buildingUnitId=buildingUnitId;
        infectedVisits=0;
        uninfectedVisits=0;
        infectedVisitedAgent=new HashSet<>();
        unInfectedVisitedAgent=new HashSet<>();
        this.totalAgent=totalAgent;
    }
    public void addInfectedVisit(Person agent){
        infectedVisits++;
        if(!infectedVisitedAgent.contains(agent)){
            infectedVisitedAgent.add(agent);
        }
    }
    public void addUninfectedVisit(Person agent){
        uninfectedVisits++;
        if(!unInfectedVisitedAgent.contains(agent)){
            unInfectedVisitedAgent.add(agent);
        }
    }

    public int getInfectedVisitedAgentSize() {
        return infectedVisitedAgent.size();
    }

    public int getUnInfectedVisitedAgentSize() {
        return unInfectedVisitedAgent.size();
    }

    public int getTotalVisitedAgentSize(){
        return getInfectedVisitedAgentSize()+getUnInfectedVisitedAgentSize();
    }
    public void calculateRatio(){
        float ratio1, ratio2, ratio3;
        //this.ratio=(double) infectedVisits /(infectedVisits+uninfectedVisits);
        this.ratio = 0.5*infectedVisits /(infectedVisits+uninfectedVisits)+0.3*((double) getInfectedVisitedAgentSize() /getTotalVisitedAgentSize())+0.2*((double) getTotalVisitedAgentSize() /totalAgent);
    }
    public void calculateRatio(double ratio1, double ratio2, double ratio3){
        this.ratio = ratio1*infectedVisits /(infectedVisits+uninfectedVisits)+ratio2*((double) getInfectedVisitedAgentSize() /getTotalVisitedAgentSize())+ratio3*((double) getTotalVisitedAgentSize() /totalAgent);
    }
    public double getRatio(){
        return ratio;
    }
}
