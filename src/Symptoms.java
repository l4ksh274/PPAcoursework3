import java.util.List;
import java.util.Random;

/**
 * Stores the different symptoms a disease can have as well as implementations for how they affect the field and animals.
 *
 * @author Laksh Patel and Jiwei Cao
 * @version 1.0
 */

public enum Symptoms implements ISymptom {
    FEVER {
        /**
         * @param animal The animal you want to apply this to.
         * @param nextFieldState The next field state
         */
        @Override
        public void applyAffect(Animal animal, Field nextFieldState) {
            animal.modifyMoveProbability(0.75f);
            animal.increaseMortalityRate(0.0015f);
        }
    },
    COUGH {
        /**
         * @param animal The animal you want to apply this to.
         * @param nextFieldState The next field state
         */
        @Override
        public void applyAffect(Animal animal, Field nextFieldState) {
            spreadDisease(animal, nextFieldState);
            animal.increaseMortalityRate(0.001f);
        }
    }, // Chance to spread a disease.
    VOMITING {
        /**
         *
         * @param animal The animal you want to apply this to.
         * @param nextFieldState The next field state
         */
        @Override
        public void applyAffect(Animal animal, Field nextFieldState) {
            animal.modifyMoveProbability(0.85f);

            spreadDisease(animal, nextFieldState);

            animal.increaseMortalityRate(0.0015f);
        }
    }, // Reduces activity and spreads disease.
    ACHES {
        /**
         * Slightly increases animal mortality and significantly reduces the animal's move probability
         * @param animal The animal you want to apply this to.
         * @param nextFieldState The next field state
         */
        @Override
        public void applyAffect(Animal animal, Field nextFieldState) {
            animal.modifyMoveProbability(0.5f);
            animal.increaseMortalityRate(0.0005f);
        }
    }, // Drastically reduces activity.
    DEATH {
        /**
         * Greatly increases the probability of animal death
         * @param animal The animal you want to apply this to.
         * @param nextFieldState The next field state
         */
        @Override
        public void applyAffect(Animal animal, Field nextFieldState) {
            animal.increaseMortalityRate(0.003f);
        }
    },
    ANTIBREEDING {
        /**
         * Reduces the likelihood of animals breeding to 60% of the existing chance. (Starts off at 100%)
         * @param animal The animal you want to apply this to.
         * @param nextFieldState The next field state
         */
        @Override
        public void applyAffect(Animal animal, Field nextFieldState) {
            spreadDisease(animal, nextFieldState);
            animal.changeBreedingProbability(0.8f);
        }
    };

    protected static Random rand = Randomizer.getRandom();

    /**
     * Spreads diseases from the specified animal to nearby animals within the field.
     * This method checks the adjacent locations of the animal, identifies neighboring
     * animals, and infects them with diseases that have the cough symptom.
     *
     * @param animal The animal that may spread its diseases to neighboring animals. Must not be null.
     * @param nextFieldState The field object that represents the current and adjacent states of entities.
     *                       Provides access to neighboring locations and entities.
     */
    protected void spreadDisease(Animal animal, Field nextFieldState) {
        List<Location> locationList = nextFieldState.getAdjacentLocations(animal.getLocation());
        // Searches all neighbouring cells for alive animals and spreads diseases that have the cough symptom.
        for (Location location : locationList) {
            Entity neighbouringEntity = nextFieldState.getEntityAt(location);
            if (neighbouringEntity instanceof Animal neighbouringAnimal && neighbouringAnimal.isAlive()) {
                for (Disease disease : animal.getDiseases()){
                    if (disease.getSymptoms().contains(this)){
                        neighbouringAnimal.infect(disease);
                    }
                }

            }
        }
    }

}
