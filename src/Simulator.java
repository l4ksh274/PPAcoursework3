import java.util.*;

/**
 * A simple predator-prey simulator, based on a rectangular field containing 
 * rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 7.1
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a Trex will be created in any given grid position.
    private static final double TREX_CREATION_PROBABILITY = 0;
    // The probability that an Ankylosaurus will be created in any given position.
    private static final double ANKYLOSAURUS_CREATION_PROBABILITY = 0.3;
    // The probability that an allosaurus will be created in any given grid position.
    private static final double ALLOSAURUS_CREATION_PROBABILITY = 0;
    // The probability that a dodo will be created in any given position.
    private static final double DODO_CREATION_PROBABILITY = 0.8;
    // The probability that a raptor will be created in any given position.
    private static final double RAPTOR_CREATION_PROBABILITY = 0.8;
    // The probability that a berry will be created in a given position.
    private static final double BERRY_CREATION_PROBABILITY = 0;
    // The probability that a conifer will be created in a given position.
    private static final double CONIFER_CREATION_PROBABILITY = 0.4;


    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private final SimulatorView view;
    // Keeps track of the time
    private int day, hour;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be >= zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        field = new Field(depth, width);
        view = new SimulatorView(depth, width);

        day = 1;
        hour = 0;

        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long 
     * period (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(700);
    }
    
    /**
     * Run the simulation for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        reportStats();
        for(int n = 1; n <= numSteps && field.isViable(); n++) {
            simulateOneStep();
            delay(100);         // adjust this to change execution speed (usually 50)
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each fox and rabbit.
     * Increment time according to the step where each step is an hour.
     */
    public void simulateOneStep()
    {
        step++;

        if (hour + 1 == 24){
            hour = 0;
            day++;
        }else{
            hour++;
        }

        // Use a separate Field to store the starting state of
        // the next step.
        Field nextFieldState = new Field(field.getDepth(), field.getWidth());

        List<Living> beings = field.getBeings();
        for (Living aBeing : beings) {
            aBeing.act(field, nextFieldState, day, hour);
        }
        
        // Replace the old state with the new one.
        field = nextFieldState;

        reportStats();
        view.showStatus(step, field, day, hour);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        populate();
        day = 1;
        hour = 0;
        view.showStatus(step, field, day, hour);
    }
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= TREX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Trex trex = new Trex(true, location, field);
                    field.placeLiving(trex, location);
                }else if(rand.nextDouble() <= ALLOSAURUS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Allosaurus allosaurus = new Allosaurus(true, location, field);
                    field.placeLiving(allosaurus, location);
                }else if(rand.nextDouble() <= ANKYLOSAURUS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Ankylosaurus ankylosaurus = new Ankylosaurus(true, location, field);
                    field.placeLiving(ankylosaurus, location);
                }else if(rand.nextDouble() <= DODO_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Dodo dodo = new Dodo(true, location, field);
                    field.placeLiving(dodo, location);
                }else if(rand.nextDouble() <= RAPTOR_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Raptor raptor = new Raptor(true, location, field);
                    field.placeLiving(raptor, location);
                }else if(rand.nextDouble() <= BERRY_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Berry berry = new Berry(true, location, field);
                    field.placeLiving(berry, location);
                }else if(rand.nextDouble() <= CONIFER_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Conifer conifer = new Conifer(true, location, field);
                    field.placeLiving(conifer, location);
                }

                // else leave the location empty.
            }
        }
    }

    /**
     * Report on the number of each type of animal in the field.
     */
    public void reportStats()
    {
        //System.out.print("Step: " + step + " ");
        field.fieldStats();
    }
    
    /**
     * Pause for a given time.
     * @param milliseconds The time to pause for, in milliseconds
     */
    private void delay(int milliseconds)
    {
        try {
            Thread.sleep(milliseconds);
        }
        catch(InterruptedException e) {
            // ignore
        }
    }

    // TODO So intellij can run the program remove before submitting.

    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        simulator.runLongSimulation();
    }

}
