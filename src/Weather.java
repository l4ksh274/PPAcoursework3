import java.util.Random;

/**
 * Stores the different weather states the field can be in.
 *
 * @author Laksh Patel and Jiwei Cao
 * @version 1.0
 */

public enum Weather {
    SUNNY,
    RAINY,
    FOG;

    private static Random rand = Randomizer.getRandom();

    /**
     * Selects and returns a random weather state from the available weather values.
     *
     * @return A randomly selected Weather state.
     */
    public static Weather getRandomWeather() {
        return Weather.values()[rand.nextInt(Weather.values().length)];
    }

}
