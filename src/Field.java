import java.util.*;

/**
 * Represent a rectangular grid of field positions.
 * Each position is able to store a single entity/object.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 7.0
 *
 * @author Laksh Patel and Jiwei Cao
 * @version 1.0
 */
public class Field
{
    // A random number generator for providing random locations.
    private static final Random rand = Randomizer.getRandom();
    
    // The dimensions of the field.
    private final int depth, width;
    // Entities mapped by location.
    private final Map<Location, Entity> field = new HashMap<>();
    // The animals.
    private final List<Entity> entities = new ArrayList<>();
    // The current weather.
    private Weather currentWeather;
    // A multiplier which will affect how often animals move.
    private final double fogVisibility = 0.7;

    /**
     * Represent a field of the given dimensions.
     * @param depth The depth of the field.
     * @param width The width of the field.
     */
    public Field(int depth, int width)
    {
        this.depth = depth;
        this.width = width;
        currentWeather = Weather.SUNNY;
    }

    /**
     * Place an entity at a specific location in the field.
     * If another entity exists at the specified location, it is removed first.
     *
     * @param anEntity The entity to be placed in the field.
     * @param location The location where the entity will be placed. Must not be null.
     */
    public void placeEntity(Entity anEntity, Location location)
    {
        assert location != null;
        Entity entity = field.get(location);
        if(entity != null) {
            entities.remove(entity);
        }
        field.put(location, anEntity);
        entities.add(anEntity);
    }

    /**
     * Return the entity at the given location, if any.
     * @param location Where in the field.
     * @return The entity at the given location, or null if there is none.
     */
    public Entity getEntityAt(Location location)
    {
        return field.get(location);
    }

    /**
     * Get a shuffled list of the free adjacent locations.
     * @param location Get locations adjacent to this.
     * @return A list of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocations(Location location)
    {
        List<Location> free = new LinkedList<>();
        List<Location> adjacent = getAdjacentLocations(location);
        for(Location next : adjacent) {
            Entity aEntity = field.get(next);
            if(aEntity == null) {
                free.add(next);
            }
            else if(!aEntity.isAlive()) {
                free.add(next);
            }
        }
        return free;
    }

    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> getAdjacentLocations(Location location)
    {
        // The list of locations to be returned.
        List<Location> locations = new ArrayList<>();
        if(location != null) {
            int row = location.row();
            int col = location.col();
            for(int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if(nextRow >= 0 && nextRow < depth) {
                    for(int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }
            
            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }

    /**
     * Print out the number of entities in the field.
     */
    public void fieldStats()
    {
        HashMap<Class<? extends Entity>, Integer> counts = new HashMap<>();
        for(Entity aEntity : field.values()) {
            if (!counts.containsKey(aEntity.getClass())) {
                counts.put(aEntity.getClass(), 1);
            }else{
                counts.put(aEntity.getClass(), counts.get(aEntity.getClass()) + 1);
            }
        }
        for (Map.Entry<Class<? extends Entity>, Integer> entry : counts.entrySet()) {
            System.out.print(entry.getKey().getName() + " : " + entry.getValue()  + " ");
        }
        System.out.println();
    }

    /**
     * Empty the field.
     */
    public void clear()
    {
        field.clear();
    }

    /**
     * Checks if the field is viable for simulation by ensuring the presence of specific
     * predator-prey and prey-plant pairs. At least one of the defined pairs must exist
     * for the field to be considered viable.
     *
     * Predator -> Prey pairs:
     * - Trex -> Ankylosaurus
     * - Allosaurus -> Ankylosaurus
     * - Raptor -> Dodo
     *
     * Prey -> Plant pairs:
     * - Ankylosaurus -> Berry
     * - Dodo -> Conifer
     *
     * @return true if at least one of the defined predator-prey or prey-plant pairs
     *         exists in the field, false otherwise.
     */
    public boolean isViable() {
        // Keeps track of the animals classes that are still on the field. A value of 1 indicates it exists.
        HashSet<Class<? extends Entity>> classExists = new HashSet<>();



        for (Entity entities : entities){

            classExists.add(entities.getClass());

            // Checks if a pair exists by querying the hashmap and seeing in a non-null value is returned for both animals.
            boolean trexAnkylosaurusPair = classExists.contains(Trex.class) && classExists.contains(Ankylosaurus.class);
            boolean allosaurusAnkylosaurusPair = classExists.contains(Allosaurus.class) && classExists.contains(Ankylosaurus.class);
            boolean raptorDodoPair = classExists.contains(Raptor.class) && classExists.contains(Dodo.class);
            boolean ankylosaurusBerryPair = classExists.contains(Ankylosaurus.class) && classExists.contains(Berry.class);
            boolean dodoConiferPair = classExists.contains(Dodo.class) && classExists.contains(Conifer.class);

            // If any of the 3 existing pairs exists, then true
            if (trexAnkylosaurusPair || allosaurusAnkylosaurusPair || raptorDodoPair || ankylosaurusBerryPair || dodoConiferPair) {
                return true;
            }
        }

        // Default case where no pairs are found.

        return false;

    }
    
    /**
     * Get the list of entities.
     */
    public List<Entity> getEntities()
    {
        return entities;
    }

    /**
     * Return the depth of the field.
     * @return The depth of the field.
     */
    public int getDepth()
    {
        return depth;
    }
    
    /**
     * Return the width of the field.
     * @return The width of the field.
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Return the weather of the field.
     * @return The weather of the field.
     */

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    /**
     * Sets the weather of the field.
     * @param currentWeather  The new weather of the field.
     */


    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }

    /**
     * Return the visibility of the field during a fog.
     * @return The visibility of the field during fog.
     */

    public double getVisibility() {
        return fogVisibility;
    }

}
