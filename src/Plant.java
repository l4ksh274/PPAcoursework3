import java.util.List;
import java.util.ArrayList;

public abstract class Plant extends Living {

    // Tracks the root plant
    private Plant originalPlant;

    // Stores all plants connected to the original plant
    private ArrayList<Plant> offspring;

    protected Field field;

    public Plant(Location location, Field field, Plant originalPlant) {
        super(location, field);
        if (originalPlant == null) {
            this.originalPlant = this;
        }
        else {
            this.originalPlant = originalPlant;
        }
    }

    /*
     * Only original plant can breed
     */
    public void act(Field currentField, Field nextFieldState, int day, int hour) {
        
        incrementAge();
        if (isOriginalPlantAlive()){ 
            List<Location> freeLocations = getFreeLocations(nextFieldState);

            if (!freeLocations.isEmpty()) {  
                spread(nextFieldState, freeLocations);
            }
        }
        else {
            offspring.clear();
        }
    }

    public List<Location> getFreeLocations(Field nextFieldState) {
        Location originalPlantLocation = originalPlant.getLocation();
        List<Location> freeLocations = nextFieldState.getFreeAdjacentLocations(originalPlantLocation);
        return freeLocations;
    }

    protected void spread(Field nextFieldState, List<Location> freeLocations) {
        for (Location loc : freeLocations) {
            Plant young = createOffspring(loc, this.originalPlant);
            offspring.add(young);
            nextFieldState.placeLiving(young, loc);
        }
    }

    public boolean isOriginalPlantAlive() {
        return originalPlant != null && originalPlant.isAlive();
    }

    protected abstract Plant createOffspring(Location loc, Plant originalPlant);
}