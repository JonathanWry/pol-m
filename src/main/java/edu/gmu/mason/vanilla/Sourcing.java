package edu.gmu.mason.vanilla;

import java.util.*;

public class Sourcing {
    private HashMap<Long, BuildingUnitStats> unorderedData;
    private PriorityQueue<BuildingUnitStats> orderedData;
    private long totalInfected;
    private long totalAgent;
    private long totalUnInfected;
    public Sourcing(){
        totalInfected=0;
        unorderedData=new HashMap<>();
        orderedData=new PriorityQueue<>(new CustomComparator());
    }
    public void setTotalAgent(long totalAgent){
        this.totalAgent=totalAgent;
    }
    public void setTotalInfected(long totalInfected){
        this.totalInfected=totalInfected;
    }
    public void addTotalInfected(){
        totalInfected++;
    }

    public long getTotalAgent() {
        return totalAgent;
    }

    public long getTotalInfected(){
        return totalInfected;
    }
    public long getTotalUninfected(){
        this.totalUnInfected=getTotalAgent()-getTotalInfected();
        return totalUnInfected;
    }
    public void appendAgentInfo(Person agent){
        boolean infected=agent.getInfected();
        if(infected){
            addTotalInfected();
        }
        ArrayList<DailyTrack> track = agent.getTrack();
        for(int i=0;i<track.size();i++){
            DailyTrack dailyTrack=track.get(i);
            ArrayList<Long> dayTrack=dailyTrack.getDayTrack();
            for(int j=0;j<dayTrack.size();j++){
                long curBuildingUnitId=dayTrack.get(j);
                if(unorderedData.keySet().contains(curBuildingUnitId)){
                    BuildingUnitStats curBuildingUnitStats=unorderedData.get(curBuildingUnitId);
                    if(infected){
                        curBuildingUnitStats.addInfectedVisit(agent);
                    }else{
                        curBuildingUnitStats.addUninfectedVisit(agent);
                    }
                    unorderedData.put(curBuildingUnitId,curBuildingUnitStats);
                }else{
                    BuildingUnitStats curBuildingUnitStats=new BuildingUnitStats(curBuildingUnitId,totalAgent);
                    if(infected){
                        curBuildingUnitStats.addInfectedVisit(agent);
                    }else{
                        curBuildingUnitStats.addUninfectedVisit(agent);
                    }
                    unorderedData.put(curBuildingUnitId,curBuildingUnitStats);
                }
            }
        }
    }
    public void sort(){

        for(long id:unorderedData.keySet()){
            unorderedData.get(id).calculateRatio();
            orderedData.add(unorderedData.get(id));
        }
    }
    public void sort(double ratio1, double ratio2, double ratio3){

        for(long id:unorderedData.keySet()){
            unorderedData.get(id).calculateRatio(ratio1,ratio2,ratio3);
            orderedData.add(unorderedData.get(id));
        }
    }
    public double[] findPos(long diseaseUnitId){
        BuildingUnitStats curStat;
        for(int i=0;i<orderedData.size();i++){
            curStat=orderedData.poll();
            if(curStat.buildingUnitId==diseaseUnitId){
                orderedData.clear();
                return new double[]{(double)i, curStat.getRatio()};
            }
        }
        return new double[]{-1,-1};
    }
    public void show(long diseaseUnitId){
        for(int i=0;i<orderedData.size();i++){
            BuildingUnitStats curStat=orderedData.poll();
            if(curStat.buildingUnitId==diseaseUnitId){
                System.out.println(curStat.buildingUnitId+": RATIO->"+curStat.getRatio());
                System.out.println(curStat.infectedVisits);
                System.out.println(curStat.uninfectedVisits);
//                System.out.println((float)curStat.infectedVisits/(curStat.uninfectedVisits+curStat.infectedVisits));
            }
            if(curStat.getRatio()==1.0){
                System.out.println(curStat.buildingUnitId+": ratio->"+curStat.getRatio());
                System.out.println(curStat.infectedVisits);
                System.out.println(curStat.uninfectedVisits);
            }
            if(curStat.getRatio()<0.7){
                continue;
            }
            System.out.println(curStat.buildingUnitId+": ratio->"+curStat.getRatio());
//            System.out.println(curStat.infectedVisits);
//            System.out.println(curStat.uninfectedVisits);
        }
    }
}
class CustomComparator implements Comparator<BuildingUnitStats> {
    @Override
    public int compare(BuildingUnitStats obj1, BuildingUnitStats obj2) {
        // Compare objects based on their priority
        double diff=obj2.getRatio()-obj1.getRatio();
        if(diff>0){
            return 1;
        }else if(diff==0){
            return 0;
        }else{
            return -1;
        }

    }
}
