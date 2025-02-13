import java.util.List;
import java.util.Random;

public enum Symptoms implements ISymptom {
    FEVER {
        /**
         * @param animal The animal you want to apply this to.
         * @param nextFieldState The next field state
         */
        @Override
        public void applyAffect(Animal animal, Field nextFieldState) {
            animal.modifyMoveProbability(0.75f);
            animal.increaseMortalityRate(0.05f);
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
            animal.increaseMortalityRate(0.02f);
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

            animal.increaseMortalityRate(0.1f);
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
            animal.increaseMortalityRate(0.01f);
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
            animal.increaseMortalityRate(0.1f);
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
            animal.changeBreedingProbability(0.6f);
        }
    };

    protected static Random rand = Randomizer.getRandom();

    protected void spreadDisease(Animal animal, Field nextFieldState) {
        List<Location> locationList = nextFieldState.getFreeAdjacentLocations(animal.getLocation());
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
