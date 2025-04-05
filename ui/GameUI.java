package ui;
import java.util.ArrayList;
import cards.Card;
import cards.TreasureCard;
import player.Player;

public class GameUI {
    /**
     * UI Elements:
     * 1. 
     * 
     * TODO: Add final UI descriptions when report is done
     */

    // UI 1
    public static void printTurnOptions(Player player, ArrayList<String> options) {
        System.out.println("--------------------------------------------------------------------------------");
        printOptionsFragment(player, options);
    }

    // UI 1.5
    public static void printOptionsFragment(Player player, ArrayList<String> options){
        player.printTokens();
        System.out.println("Gender: " + player.getGender());
        System.out.println("Class: " + player.getCharacterClass());
        System.out.println("");

        player.printArmour();
        System.out.println("");
        player.printHand();
        System.out.println("");
        System.out.println("Choose an action:");
        player.printMoveOptions(options);
    }

    // UI 2
    public static void printDrawnCard(Card card) {
        System.out.println("--------------------------------- Drawn card: ---------------------------------");
        System.out.println(card.printCard());
    }

    // UI 3
    public static void printCombatOptions(Player player, Card monsterCard, ArrayList<String> options) {
        System.out.println("----------------------------------- Combat: -----------------------------------");
        System.out.println(monsterCard.printCard()); 
        System.out.println("");

        printOptionsFragment(player, options);
    }

    // UI 4
    public static void printRunAwayResult(int dieRoll, Player player) {
        System.out.println(dieRoll >= 5 ? "You ran away!" : "You failed to run away! You lose a token");
    }

    // SPEC: 2a intermediate operations, .forEach()
    // UI 5
    public static void printCombatSucess(Player player, ArrayList<TreasureCard> treasureCards) {
        System.out.println("You defeated the monster!");
        player.printTokens();
        if (!treasureCards.isEmpty()) {
            System.out.println("Treasure cards drawn:");
            treasureCards.forEach(card -> System.out.println(card.printCard()));
        }
    }

    // UI 6
    public static void printCreateOrLoadGame(String time) {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Welcome to Munchkin!");

        if (time != null) {
            System.out.println("Choose an option: ");
            System.out.println("1. [ Create new game ]");
            System.out.println("2. [ Load game : " + time + " ]");
            return;
        } 

        System.out.println(" Creating a new game ...");
    }
}
