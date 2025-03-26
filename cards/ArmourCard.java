package cards;

public final class ArmourCard implements TreasureCard {
    private final String name;
    private final String description;
    private final String requiredClass;
    private final String requiredGender;

    public ArmourCard(String name, String description, String requiredClass, String requiredGender) {
        this.name = name;
        this.description = description;
        this.requiredClass = requiredClass;
        this.requiredGender = requiredGender;
    }

    @Override
    public String name() { return name; }

    @Override
    public String description() {
        return description + " (Only for " + requiredGender + " " + requiredClass + "s)";
    }

    public String requiredClass() { return requiredClass; }
    public String requiredGender() { return requiredGender; }
}