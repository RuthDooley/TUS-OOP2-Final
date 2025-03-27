import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cards.*;

public class App {
    // SPEC: 1 Consumer lambda (Moved outside the method)
    private static final Consumer<List<?>> shuffleDeck = Collections::shuffle;
    public static void main(String[] args) {
        Path path = Paths.get("card-desc.txt");

        // Cards from card-desc.txt, in a map of values monster, armour and combat
        Map<String, List<String[]>> inputCards = new HashMap<>();

        // SPEC: 2 Streams
        try (Stream<String> lines = Files.lines(path)) {

            // SPEC: 2a intermediate operations, .map(), .filter()
            // SPEC: 2b Collectors.groupingBy, Collectors.mapping, .collect()
            inputCards = lines
                .map(line -> line.split("\\s*,\\s*")) // Split by line, excl leading and trailing space
                .filter(data -> data.length > 1)  // More than 1 element in the array
                .collect(Collectors.groupingBy(data -> { // Get category
                    if (data[0].contains("armour")) return "armour";
                    else if (data[0].contains("combat")) return "combat";
                    else if (data[0].contains("monster")) return "monster";
                    else return "null"; 
                }, Collectors.mapping(data -> Arrays.copyOfRange(data, 1, data.length), Collectors.toList())));

            // TODO: Remove sample print 
            inputCards.forEach((category, values) -> {
                // if (category == "monster") {
                //     System.out.println("Monster Cards:");
                //     values.forEach(value -> System.out.println(Arrays.toString(value)));
                // }
                // System.out.println(category + " Cards:");
                // values.forEach(value -> System.out.println(Arrays.toString(value))); 
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        // All info including remaining card types, "cardified"

        // ** DOOR CARDS

        // * Monster cards
        // SPEC: 2b Collectors.toList()
        List<MonsterCard> monsterCards = new ArrayList<>();
        monsterCards = inputCards.get("monster").stream()
                    .map(data -> {
                        int level = Integer.parseInt(data[0].trim());
                        String typeImmmune = data[1].trim();
                        int treasureDrop = Integer.parseInt(data[2].trim()); 
                        String description = data.length > 4 ? data[3].trim() : ""; 

                        return new MonsterCard(level, typeImmmune, treasureDrop, description);
                    })
                    .collect(Collectors.toList());

        // * Change class cards
        List<ChangeClassCard> changeClassCards = new ArrayList<>();
        for (int i = 0; i < Constants.CHANGE_CLASS_CARD_COUNT; i++) {
            String[] classes = {"warrior", "wizard", "cleric"};
            String randomClass = classes[(int) (Math.random() * classes.length)];
            ChangeClassCard card = new ChangeClassCard(randomClass);
            changeClassCards.add(card);
        }

        // * Change gender cards
        List<ChangeGenderCard> changeGenderCards = new ArrayList<>();
        for (int i = 0; i < Constants.CHANGE_GENDER_CARD_COUNT; i++) {
            String[] genders = {"male", "female"};
            String randomGender = genders[(int) (Math.random() * genders.length)];
            ChangeGenderCard card = new ChangeGenderCard(randomGender);
            changeGenderCards.add(card);
        }

        // * Curse cards
        List<CurseCard> curseCards = new ArrayList<>();
        for (int i = 0; i < Constants.CURSE_CARD_COUNT; i++) {
            String[] curses = {"life", "armour", "class"}; // Losing
            String randomCurse = curses[(int) (Math.random() * curses.length)];
            CurseCard card = new CurseCard(randomCurse, "TODO");
            curseCards.add(card);
        }

        // ** TREASURE CARDS

        // * Level up cards
        List<LevelUpCard> levelUpCards = new ArrayList<>();
        for (int i = 0; i < Constants.LEVEL_UP_CARD_COUNT; i++) {
            LevelUpCard card = new LevelUpCard();
            levelUpCards.add(card);
        }

        // * Armour cards
        List<ArmourCard> armourCards = new ArrayList<>();
        armourCards = inputCards.get("armour").stream()
                    .map(data -> {
                        int isBig = Integer.parseInt(data[0].trim());
                        String requiredClass = data[1].trim();
                        String description = data.length > 3 ? data[2].trim() : ""; 

                        return new ArmourCard("TODO", isBig, requiredClass, description);
                    })
                    .collect(Collectors.toList());

        // * Combat power cards
        List<CombatPowerCard> combatPowerCards = new ArrayList<>();
        combatPowerCards = inputCards.get("combat").stream()
                    .map(data -> {
                        int monsterLvl = Integer.parseInt(data[0].trim());
                        String description = data.length > 3 ? data[1].trim() : ""; 
                        int bonusPower = new Random().nextInt(3) + 1; // 1 - 3 inclusive

                        return new CombatPowerCard("TODO", monsterLvl, bonusPower, description);
                    })
                    .collect(Collectors.toList());

        List<DoorCard> doorCardsDeck = new ArrayList<>();
        doorCardsDeck.addAll(monsterCards);
        doorCardsDeck.addAll(changeClassCards);
        doorCardsDeck.addAll(changeGenderCards);
        doorCardsDeck.addAll(curseCards);

        List<TreasureCard> treasureCardsDeck = new ArrayList<>();
        treasureCardsDeck.addAll(levelUpCards);
        treasureCardsDeck.addAll(armourCards);
        treasureCardsDeck.addAll(combatPowerCards);

        shuffleDeck.accept(doorCardsDeck);
        shuffleDeck.accept(treasureCardsDeck);

        List<DoorCard> doorDiscardPile = new ArrayList<>();
        List<TreasureCard> treasureDiscardPile = new ArrayList<>();

        // Draw 2 door cards, and 3 treasure cards
        Player player = new Player();
        for (int i = 0; i < Constants.DOOR_CARD_START_COUNT; i++) 
            player.addCardToHand(drawCard.apply(doorCardsDeck));

        for (int i = 0; i < Constants.TREASURE_CARD_START_COUNT; i++) 
            player.addCardToHand(drawCard.apply(treasureCardsDeck));


        ArrayList<String> options = new ArrayList<>();
        options.add("OPt 1");
        options.add("OPt 2");
        options.add("OPt 3");
        options.add("OPt 4");
        GameUI.printTurnOptions(player, options);

        // Game loop
        int turnCount = 0;
        while (true) {
            // Step 1: Get the options for turn and print
            ArrayList<String> turnOptions = calculateTurnOptions(); // TODO: 
            GameUI.printTurnOptions(player, turnOptions);

            // Step 2: Reciev user input
            String userInput = getPlayerChoice(); // TODO: Lambda?

            // Step 3: If decison is not to play monster from hand or draw door card, excute action of card chosen
            if (!userInput.equals("draw door card") && !userInput.equals("play monster from hand")) {
                executeCardAction(userInput); // TODO
            } else {
                turnCount++;
                Card drawnCard = drawDoorCard(); // TODO: Lambda?

                if (drawnCard instanceof MonsterCard monsterCard) {
                    triggerCombat(monsterCard);
                } else if (drawnCard instanceof CurseCard curseCard) {
                    applyCurse(curseCard); // TODO: Lambda?
                } else {
                    player.addCardToHand(drawnCard);
                    drawTreasureCard(); // TODO: Lambda?
                }
            }

            // Step 4: Check for win/loss conditions
            if (player.getTokens() >= 10) {
                // TODO: WIN EVENT
                break;
            } else if (player.getTokens() <= 0) {
                // TODO: LOSE EVENT
                break;
            }
        }
    }

    private static final Function<List<? extends Card>, Card> drawCard = deck -> {
        // TODO: Maybe handle the empty deck somewhere else
        if (!deck.isEmpty()) {
            return deck.remove(0); // Draw the top card
        }
        System.out.println("Deck is empty!");
        return null;
    };

    private static void triggerCombat(MonsterCard monsterCard) {
        while (true) {
            GameUI.printCombatOptions();
            String userChoice = getPlayerChoice();

            if (userChoice.equals("play combat boost")) {
                applyCombatPower();
            } else if (userChoice.equals("fight monster")) {
                if (player.getLevel() + player.getArmourValue() + player.getCombatPower() > monsterCard.getLevel()) {
                    player.gainToken();
                    break;
                } else {
                    triggerRunAway(monsterCard);
                    break;
                }
            } else if (userChoice.equals("run away")) {
                triggerRunAway(monsterCard);
                break;
            }
        }
    }

    private static void triggerRunAway(Player player, MonsterCard monsterCard) {
        player.resetCombatPower();
        int diceRoll = rollDice(); // Lambda?

        if (diceRoll < 5) {
            player.loseToken(); // TODO: need to make combat power attributes in player
        }
    }
}

// Monster type, level, run away condition, treasure, description?
// Armour type, big, class, description?
// Combat card type, monster level, description?