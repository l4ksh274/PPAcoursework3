import java.util.Random;

/**
 * An enum class representing the genders of an animal.
 * The gender of an animal can be either MALE or FEMALE.
 * Gender of animal can be randomly assigned.
 */
public enum Gender {
    MALE, FEMALE;

    // Shared random instance to generate random values.
    private static Random rand = Randomizer.getRandom();

    /**
     * Generates a random gender.
     * Chances of returning a Male or Female is 50/50
     * 
     * @return A randomly selected gender.
     */
    public static Gender randomGender() {
        if (rand.nextBoolean()) {
            return Gender.MALE;
        }
        else {
            return Gender.FEMALE;
        }
    }
}
