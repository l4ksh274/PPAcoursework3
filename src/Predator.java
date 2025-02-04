/**
 * Write a description of class Predator here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Predator extends Animal
{   

    public Predator(Location location, Field field) {
        super(location, field);
    }


    public Predator(Location location, Field field, int sleepHour, int wakeHour, int timeOffset){
        super(location, field, sleepHour, wakeHour, timeOffset);
    }

    @Override
    public String toString() {
        return "Trex{" +
                "age=" + age +
                ", alive=" + isAlive() +
                ", location=" + getLocation() +
                ", foodLevel=" + foodLevel +
                '}';
    }
}