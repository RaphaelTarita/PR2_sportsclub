package a11730648;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Member implements Comparable<Member> {
    private final String name;
    private final Map<Sports, Level> sports = new LinkedHashMap<>();

    private static boolean hasNullKey(Map<?, ?> m) {
        assert m != null;
        try {
            return m.containsKey(null);
        } catch (NullPointerException npe) {
            return false;
        }
    }

    private static boolean hasNullValue(Map<?, ?> m) {
        assert m != null;
        try {
            return m.containsValue(null);
        } catch (NullPointerException npe) {
            return false;
        }
    }

    public Member(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        this.name = name;
    }

    public Member(String name, Map<Sports, Level> sportsLevelMap) {
        this(name);
        if (
            sportsLevelMap == null
                || sportsLevelMap.isEmpty()
                || hasNullKey(sportsLevelMap)
                || hasNullValue(sportsLevelMap)
        ) {
            throw new IllegalArgumentException("SportsLevelMap cannot be null or empty, or contain null keys or values");
        }

        sports.putAll(sportsLevelMap);
    }

    public String getName() {
        return name;
    }

    public Map<Sports, Level> getSports() {
        return new HashMap<>(sports);
    }

    public Set<Sports> getBillableSports() {
        return sports.keySet();
    }

    public Level learn(Sports newSports, Level newLevel) {
        if (newSports == null || newLevel == null) {
            throw new IllegalArgumentException("Cannot execute 'learn()' with 'null' sports or level");
        }
        return sports.compute(newSports, (s, l) -> {
            if (l == null) {
                return Level.BEGINNER;
            }
            if ((newLevel.ordinal() - l.ordinal()) >= 1) {
                return l.next();
            }
            return l;
        });
    }

    @Override
    public int compareTo(Member other) {
        if (other == null) {
            return 1;
        }
        return name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof Member) {
            return ((Member) other).name.equals(name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "name: " + name + ", sports: " + sports;
    }
}
