import java.util.Random;

public enum Gender {
    MALE, FEMALE;
    private static Random rand = Randomizer.getRandom();

    public static Gender randomGender() {
        if (rand.nextBoolean()) {
            return Gender.MALE;
        }
        else {
            return Gender.FEMALE;
        }
    }
}
