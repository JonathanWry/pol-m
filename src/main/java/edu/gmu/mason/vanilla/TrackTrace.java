package edu.gmu.mason.vanilla;

import edu.gmu.mason.vanilla.environment.BuildingUnit;
import edu.gmu.mason.vanilla.log.Skip;

import java.util.ArrayList;
import java.util.Random;

public class TrackTrace {
    @Skip
    private Person agent;
    private int days_remembered=7;
    private int day_counter;
    private int LastDate;
    private ArrayList<DailyTrack> track;
    private DailyTrack dailyTrack;
    private Random rand;
    public TrackTrace(Person agent){
        this.agent=agent;
        this.track=new ArrayList<>();
        this.LastDate=agent.getSimulationTime().getDayOfYear();
        this.day_counter=0;
        this.track=new ArrayList<>();
        this.dailyTrack=new DailyTrack(LastDate, new ArrayList<>());
        this.rand=new Random();
    }
    public void update(){
        //if current date increases
        if(agent.getSimulationTime().getDayOfYear()>LastDate){
            LastDate=agent.getSimulationTime().getDayOfYear();
            day_counter+=1;
            //add dailyTrack
            track.add(dailyTrack);
            dailyTrack=new DailyTrack(LastDate++, new ArrayList<>());
            //update remembered daytrack
            if(day_counter>days_remembered){
                track.remove(0);
                day_counter-=1;
            }
        }else{
            ArrayList<DailyTrack> previous_track=getTrack();
            BuildingUnit currentUnit = agent.getCurrentUnit();
            ArrayList<Long> dayTrack=dailyTrack.getDayTrack();
            //prevent recording same building unit the person is currently visiting
            if(dayTrack.size()>0&&dayTrack.get(dayTrack.size()-1)==currentUnit.getId()){
                return;
            }
            //updating dailyTrack
            //toDo: Add uncertainty
            float p=rand.nextFloat();
            //probability of forgetting track
            if(p<0.95){
                p=rand.nextFloat();
                if(p>0.95&&!getTrack().isEmpty()) {
//                    //probability of misremember track
//                    System.out.println("RANDOM NUMBER!!");
//                    System.out.println("Original:"+currentUnit.getId());
                    ArrayList<Long> random_day_track=previous_track.get(rand.nextInt(Math.max(previous_track.size(),0))).getDayTrack();
                    long currentUnitId = random_day_track.get(Math.max(rand.nextInt(random_day_track.size()),0));
//                    System.out.println("Changed:"+currentUnitId);
                    dayTrack.add(currentUnitId);
                }else{
                   dayTrack.add(currentUnit.getId());
                }
            }else{
//                System.out.println(currentUnit.getId()+":Missed!");
            }
        }
    }
    public ArrayList<DailyTrack> getTrack(){
        return track;
    }
}
