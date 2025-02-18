import java.util.Random;

public enum Weather {
    SUNNY,
    RAINY,
    FOG;

    private static Random rand = Randomizer.getRandom();

    public static Weather getRandomWeather() {
        return Weather.values()[rand.nextInt(Weather.values().length)];
    }

}
