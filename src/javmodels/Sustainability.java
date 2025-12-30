// Sustainability.java
package javmodels;

public class Sustainability {
    private int id;
    private double energyConsumption;
    private double dailyConsumption;
    private double efficiencyRating;
    private double wasteGenerated;
    private double wasteRecycled;
    private double recyclingRate;
    
    public Sustainability(int id, double energy, double daily, double efficiency, 
                         double waste, double recycled, double rate) {
        this.id = id;
        this.energyConsumption = energy;
        this.dailyConsumption = daily;
        this.efficiencyRating = efficiency;
        this.wasteGenerated = waste;
        this.wasteRecycled = recycled;
        this.recyclingRate = rate;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public double getEnergyConsumption() { return energyConsumption; }
    public void setEnergyConsumption(double energy) { this.energyConsumption = energy; }
    
    public double getDailyConsumption() { return dailyConsumption; }
    public void setDailyConsumption(double daily) { this.dailyConsumption = daily; }
    
    public double getEfficiencyRating() { return efficiencyRating; }
    public void setEfficiencyRating(double efficiency) { this.efficiencyRating = efficiency; }
    
    public double getWasteGenerated() { return wasteGenerated; }
    public void setWasteGenerated(double waste) { this.wasteGenerated = waste; }
    
    public double getWasteRecycled() { return wasteRecycled; }
    public void setWasteRecycled(double recycled) { this.wasteRecycled = recycled; }
    
    public double getRecyclingRate() { return recyclingRate; }
    public void setRecyclingRate(double rate) { this.recyclingRate = rate; }
}