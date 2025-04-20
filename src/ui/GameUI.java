package ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import cards.Card;
import cards.TreasureCard;
import player.Player;

public class GameUI {
    public static Locale locale = Locale.of("fr", "FR");
    // public static Locale locale = Locale.of("en", "GB");
    public static final ResourceBundle MESSAGES = ResourceBundle.getBundle("resources.MessagesBundle", locale);
    
    // UI 1
    public static void printTurnOptions(Player player, ArrayList<String> options) {
        System.out.println(MESSAGES.getString("ui.separator"));
        printOptionsFragment(player, options);
    }

    // UI 1.5
    public static void printOptionsFragment(Player player, ArrayList<String> options) {
        player.printTokens();
        System.out.println(MESSAGES.getString("ui.gender").replace("{0}", player.getGender() != null ? player.getGender() : "Undefined"));
        System.out.println(MESSAGES.getString("ui.class").replace("{0}", player.getCharacterClass() != null ? player.getCharacterClass() : "Undefined"));
        System.out.println("");

        player.printArmour();
        System.out.println("");
        player.printHand();
        System.out.println("");
        System.out.println(MESSAGES.getString("ui.choose.action"));
        player.printMoveOptions(options);
    }

    // UI 2
    public static void printDrawnCard(Card card) {
        System.out.println(MESSAGES.getString("ui.drawn.card"));
        System.out.println(card.printCard());
    }

    // UI 3
    public static void printCombatOptions(Player player, Card monsterCard, ArrayList<String> options) {
        System.out.println(MESSAGES.getString("ui.combat.separator"));
        System.out.println(monsterCard.printCard());
        System.out.println("");

        printOptionsFragment(player, options);
    }

    // UI 4
    public static void printRunAwayResult(int dieRoll, Player player) {
        System.out.println(MESSAGES.getString("ui.runaway.dice"));
        System.out.println(MESSAGES.getString("ui.runaway.die.roll").replace("{0}", String.valueOf(dieRoll)));
        System.out.println(dieRoll >= 5 ? MESSAGES.getString("ui.runaway.success") : MESSAGES.getString("ui.runaway.failure"));
    }

    // UI 5
    public static void printCombatSucess(Player player, ArrayList<TreasureCard> treasureCards) {
        System.out.println(MESSAGES.getString("ui.combat.success"));
        player.printTokens();
        if (!treasureCards.isEmpty()) {
            System.out.println(MESSAGES.getString("ui.combat.treasure"));
            treasureCards.forEach(card -> System.out.println(card.printCard()));
        }
    }

    // UI 6
    public static void printCreateOrLoadGame(LocalDateTime time) {
        System.out.println(MESSAGES.getString("ui.separator"));
        System.out.println(MESSAGES.getString("ui.welcome"));

        if (time != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedTime = time.format(formatter);

            System.out.println(MESSAGES.getString("ui.create.option1"));
            System.out.println(MESSAGES.getString("ui.create.option2").replace("{0}", formattedTime));
            return;
        }

        System.out.println(MESSAGES.getString("ui.create.new"));
    }
}