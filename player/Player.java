package player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import constants.Constants;
import cards.Card;
import cards.CombatPowerCard;
import cards.ArmourCard;

public class Player {
    private String gender;
    private String characterClass;
    private int tokens;
    private final List<Card> hand;
    private final List<ArmourCard> armour;
    private final List<CombatPowerCard> combatPowerCards;

    public Player() {
        this.gender = null;
        this.characterClass = null;
        this.tokens = Constants.STARTING_TOKENS; 
        this.hand = new ArrayList<>();
        this.armour = new ArrayList<>();
        this.combatPowerCards = new ArrayList<>();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    public int getTokens() {
        return tokens;
    }

    public void addToken() {
        tokens++;
    }

    public boolean removeToken() {
        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false; // TODO: Event on false
    }

    public void printTokens() {
        System.out.println("Tokens: " + tokens);
    }

    public List<Card> getHand() {
        return Collections.unmodifiableList(hand);
    }

    public void addCardToHand(Card card) {
        if (card != null) {
            hand.add(card);
        }
    }

    public void setHand(List<Card> hand) {
        this.hand.clear();
        if (hand != null) {
            this.hand.addAll(hand);
        }
    }

    public void removeCardFromHand(Card card) {
        hand.remove(card);
    }

    public List<ArmourCard> getArmour() {
        return Collections.unmodifiableList(armour);
    }

    public void addArmour(ArmourCard armourCard) {
        if (armourCard != null) {
            armour.add(armourCard);
        }
    }

    public void removeArmour(ArmourCard armourCard) {
        armour.remove(armourCard);
    }

    // SPEC: 2a intermediate operations, .forEach()
    public void printArmour() {
        System.out.println("Armour:");
        if (armour == null || armour.isEmpty()) {
            System.out.println("No armour equipped.");
            return;
        }
        armour.forEach(card -> System.out.println(card.printCard()));
    }

    public int getArmourValue() {
        int totalArmourValue = 0;
        for (ArmourCard armourCard : armour) {
            totalArmourValue += armourCard.value();
        }
        return totalArmourValue;
    }

    public List<Card> getCombatPowerCards() {
        return Collections.unmodifiableList(combatPowerCards);
    }

    public void addCombatPowerCardToHand(CombatPowerCard card) {
        if (card != null) {
            combatPowerCards.add(card);
        }
    }

    public void clearCombatPowerCards() {
        combatPowerCards.clear();
    }

    public int getCombatPower() {
        int totalCombatPower = 0;
        for (CombatPowerCard card : combatPowerCards) {
            totalCombatPower += card.bonusPower();
        }
        return totalCombatPower;
    }

    // SPEC: 2a intermediate operations, .forEach()
    public void printHand() {
        System.out.println("Cards in Hand:");
        hand.forEach(card -> System.out.println(card.printCard()));
    }

    public void printMoveOptions(ArrayList<String> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + options.get(i));
        }
    }

    @Override
    public String toString() {
        return "Gender: '" + gender + "', CharacterClass: " + characterClass +
               ", Tokens: " + tokens + ", Hand: " + hand + ", Armour: " + armour;
    }
}
