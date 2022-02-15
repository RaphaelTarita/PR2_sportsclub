package a11730648;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Trainer extends Member {
    private final Map<Sports, Level> accreditations;

    public Trainer(String name, Map<Sports, Level> accreditations) {
        super(name, accreditations);
        this.accreditations = new HashMap<>(accreditations);
    }

    public Map<Sports, Level> getAccreditations() {
        return new HashMap<>(accreditations);
    }

    public Set<Sports> getBillableSports() {
        Set<Sports> superSports = new HashSet<>(super.getBillableSports());
        superSports.removeAll(accreditations.keySet());
        return superSports;
    }

    @Override
    public String toString() {
        return super.toString() + ", accreditations: " + accreditations.toString();
    }
}
