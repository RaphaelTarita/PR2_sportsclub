package a11730648;

public enum Level {
    BEGINNER("Anfänger"),
    NORMAL("Normal"),
    ADVANCED("Fortgeschritten"),
    PROFESSIONAL("Profi");

    private final String mappedName;

    Level(String name) {
        mappedName = name;
    }

    public String getMappedName() {
        return mappedName;
    }

    public Level next() {
        return values()[Math.min(values().length - 1, ordinal() + 1)];
    }

    @Override
    public String toString() {
        return mappedName;
    }
}
