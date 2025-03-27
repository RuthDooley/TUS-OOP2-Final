import java.util.ArrayList;
import cards.Card;

public class GameUI {
    /**
     * UI Elements:
     * 1. 
     * 
     * TODO: Add final UI descriptions when report is done
     */

    // UI 1
    public void printTurnOptions(Player player, ArrayList<String> options) {
        player.printTokens();
        player.printArmour();
        player.printHand();
        player.printMoveOptions(options);
    }

    // UI 2
    public void printDrawnCard(Card card) {
        System.out.println(card.printCard());
    }

    // UI 3
    public void printCombatOptions(Player player, Card monsterCard, ArrayList<String> options) {
        System.out.println(monsterCard.printCard()); 
        player.printHand();
        player.printMoveOptions(options);
    }

    // UI 4
    public void printRunAwayResult(int dieRoll, Player player) {
        System.out.println(dieRoll >= 5 ? "You ran away!" : "You failed to run away! You lose a token");
    }

    // SPEC: 2a intermediate operations, .forEach()
    // UI 5
    public void printCombatSucess(Player player, ArrayList<Card> treasureCards) {
        System.out.println("You defeated the monster!");
        player.printTokens();
        if (!treasureCards.isEmpty()) {
            System.out.println("Treasure cards drawn:");
            treasureCards.forEach(card -> System.out.println(card.printCard()));
        }
    }
}
