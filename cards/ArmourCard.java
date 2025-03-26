package cards;

public final class ArmourCard implements TreasureCard {
    private final String name;
    private final int isBig;
    private final String description;
    private final String requiredClass;

    public ArmourCard(String name, int isBig, String requiredClass, String description) {
        this.name = name;
        this.isBig = isBig;
        this.description = description;
        this.requiredClass = requiredClass;
    }

    // TODO: Same
    // @Override
    // public String name() { return name; }

    @Override
    public String description() {
        return description + "TODO";
    }

    public String requiredClass() { return requiredClass; }
    public int isBig() { return isBig; }
}