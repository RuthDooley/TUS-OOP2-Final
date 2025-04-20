package app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Iterator; 

import cards.*;
import game.*;
import constants.*;
import player.*;
import savegame.*;
import ui.*;

public class App {
    static Player player;
    static int turnCount = 1;

    public static void main(String[] args) {

        Path path = Paths.get("card-desc.txt");

        player = new Player();

        // ** Create or load game
        int choice = 0;
        GameState loadedState = SaveManager.loadGame();
        if (loadedState != null) {
            GameUI.printCreateOrLoadGame(loadedState.getSaveTime());
            choice = PlayManager.getUserInput(2) - 1;
        }

        // Cards from card-desc.txt, in a map of values monster, armour and combat
        Map<String, List<String[]>> inputCards = new HashMap<>();

        // SPEC: 2 Streams
        try (Stream<String> lines = Files.lines(path)) {
            System.out.println(lines);

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
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ** DOOR CARDS

        // * Monster cards
        // SPEC: 2b Collectors.toList()
        List<MonsterCard> monsterCards = inputCards.get("monster").stream()
            .map(data -> {
                int level = Integer.parseInt(data[0].trim());
                String typeImmune = data[1].trim();
                int treasureDrop = Integer.parseInt(data[2].trim());
                String name = data[3].trim();
                String description = "Lvl " + level + " monster, " + typeImmune + " + 1 combat power, defeat yields " + treasureDrop + " treasures";
                Runnable action = () -> {
                    System.out.println("Play monster card " + name + ". Victory yields " + treasureDrop + " treasures");
                };
                return new MonsterCard(name, level, typeImmune, treasureDrop, description, action);
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
        String[] curses = {"lose lvl", "lose armour", "lose class"};
        for (int i = 0; i < Constants.CURSE_CARD_COUNT; i++) {
            String randomCurse = curses[(int) (Math.random() * curses.length)];

            Runnable action = switch (randomCurse) {
                case "lose lvl" -> () -> player.removeToken();
                case "lose armour" -> {
                    if (!player.getArmour().isEmpty()) {
                        yield () -> {
                            List<ArmourCard> armours = player.getArmour();
                            int random = new Random().nextInt(armours.size());
                            player.removeArmour(armours.get(random));
                        };
                    } else {
                        yield () -> System.out.println("No armour equipped");
                    }
                }
                case "lose class" -> () -> player.setCharacterClass(null);
                default -> () -> System.out.println("No valid curse applied");
            };

            CurseCard card = new CurseCard(randomCurse, "Instantly " + randomCurse, action);
            curseCards.add(card);
        }
                        
        // ** TREASURE CARDS

        // * Level up cards
        List<LevelUpCard> levelUpCards = new ArrayList<>();
        for (int i = 0; i < Constants.LEVEL_UP_CARD_COUNT; i++) {
            LevelUpCard card = new LevelUpCard("+1 Level");
            levelUpCards.add(card);
        }

        // * Armour cards
        List<ArmourCard> armourCards = inputCards.get("armour").stream()
            .map(data -> {
                int value = Integer.parseInt(data[0].trim());
                int isBig = Integer.parseInt(data[1].trim());
                String requiredClass = data[2].trim();
                String name = data[3].trim();
                String description = "Add +" + value + " lvl in combat for " + requiredClass + ", " + (isBig == 1 ? "big" : "small");

                return new ArmourCard(name, value, isBig, requiredClass, description);
            })
            .collect(Collectors.toList());

        // * Combat power cards
        List<CombatPowerCard> combatPowerCards = inputCards.get("combat").stream()
            .map(data -> {
                int monsterLvl = Integer.parseInt(data[0].trim());
                String name = data[1].trim();
                int bonusPower = new Random().nextInt(3) + 1; // 1 - 3 inclusive
                String description = "Add +" + bonusPower + " lvl in combat for monster lvl >=" + monsterLvl;

                return new CombatPowerCard(name, monsterLvl, bonusPower, description);
            })
            .collect(Collectors.toList());

        switch (choice) {
            case 0 -> {
                System.out.println("Creating new game...");

                DeckManager.doorCardsDeck.addAll(List.of(monsterCards, changeClassCards, changeGenderCards, curseCards).stream()
                    .flatMap(List::stream)
                    .toList());
        
                DeckManager.treasureCardsDeck.addAll(List.of(levelUpCards, armourCards, combatPowerCards).stream()
                    .flatMap(List::stream)
                    .toList());
        
                DeckManager.shuffleDeck.accept(DeckManager.doorCardsDeck);
                DeckManager.shuffleDeck.accept(DeckManager.treasureCardsDeck);

                // Draw 2 door cards, and 3 treasure cards
                for (int i = 0; i < Constants.DOOR_CARD_START_COUNT; i++) 
                    player.addCardToHand(DeckManager.drawDoorCard.get()); 

                for (int i = 0; i < Constants.TREASURE_CARD_START_COUNT; i++) 
                    player.addCardToHand(DeckManager.drawTreasureCard.get());

                // Removing white iterating: Curse cards just removed from hand to begin, and added to discard pile
                List<Card> modifiableHand = new ArrayList<>(player.getHand()); 
                Iterator<Card> iterator = modifiableHand.iterator();
                while (iterator.hasNext()) {
                    Card card = iterator.next();
                    if (card instanceof CurseCard) {
                        iterator.remove(); 
                        DeckManager.doorDiscardPile.add((DoorCard) card); 
                    }
                }
                player.setHand(modifiableHand);

            }
            case 1 -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formattedTime = loadedState.getSaveTime().format(formatter);
                System.out.println("Loading game " + formattedTime + "...");

                // Get all cards and combine into one array list
                List<? extends Card> allCardsDeck = List.of(
                    monsterCards, 
                    changeClassCards, 
                    changeGenderCards, 
                    curseCards, 
                    levelUpCards,  
                    armourCards, 
                    combatPowerCards
                ).stream()
                .flatMap(List::stream)
                .toList();
                
                player = new Player(
                        loadedState.getPlayerGender(), 
                        loadedState.getPlayerClass(), 
                        loadedState.getPlayerTokens(), 
                        CardManager.extractCardsByName(allCardsDeck, loadedState.getPlayerHand()), 
                        CardManager.extractCardsByName(allCardsDeck, loadedState.getPlayerArmour()),
                        CardManager.extractCardsByName(allCardsDeck, loadedState.getPlayerCombatPowerCards()
                    ));
                
                System.out.println("Game loaded successfully!");
            }
        }

        // ** GAME LOOP
        while (true) {
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("Turn " + turnCount);

            // Get the options for turn and print
            String selectedOption = CardManager.calculateTurnOptionsStart(player);
            switch (selectedOption) {
                case "Draw a Door Card" -> {
                    turnCount++;
                    DoorCard drawnCard = (DoorCard) DeckManager.drawDoorCard.get();
                    GameUI.printDrawnCard(drawnCard);
            
                    switch (drawnCard) {
                        case MonsterCard monster -> PlayManager.triggerCombat(monster, player);
                        case CurseCard curse -> curse.play(player);
                        default -> player.addCardToHand(drawnCard);
                    }
                }
                case "Save & Exit" -> {
                    // SPEC 5: Date/Time API
                    LocalDateTime now = LocalDateTime.now();

                    System.out.println("Saving game...");
                    SaveManager.saveGame(new GameState(
                        player.getGender(), 
                        player.getCharacterClass(), 
                        player.getTokens(), 
                        CardManager.listOfCardNameStringsFromCardArrayList(player.getHand()),
                        CardManager.listOfCardNameStringsFromCardArrayList(player.getArmour()), 
                        CardManager.listOfCardNameStringsFromCardArrayList(player.getCombatPowerCards()), 
                        CardManager.listOfCardNameStringsFromCardArrayList(DeckManager.treasureCardsDeck), 
                        CardManager.listOfCardNameStringsFromCardArrayList(DeckManager.doorCardsDeck), 
                        CardManager.listOfCardNameStringsFromCardArrayList(DeckManager.treasureDiscardPile), 
                        CardManager.listOfCardNameStringsFromCardArrayList(DeckManager.doorDiscardPile), 
                        turnCount,
                        now
                    ));
                    System.exit(0);
                }
                case "Exit" -> {
                    System.out.println("Exiting game...");
                    System.exit(0);
                }
                default -> {
                    Card selectedCard = PlayManager.parseCardFromOptionString("Play ", selectedOption, player);

                    // Discard card appropriately
                    switch (selectedCard) {
                        case DoorCard doorCard -> DeckManager.doorDiscardPile.add(doorCard);
                        case TreasureCard treasureCard -> DeckManager.treasureDiscardPile.add(treasureCard);
                        default -> System.out.println("Error handelling, unknown card type");
                    }

                    if (selectedCard instanceof MonsterCard monster){
                        PlayManager.triggerCombat(monster, player);
                        break;
                    } else if (selectedCard instanceof ChangeClassCard changeClassCard) {
                        // If the change class card is not of the same class
                        if (changeClassCard.name() != player.getCharacterClass()) {
                            // Need to get rid of all of the armour
                            for (ArmourCard armourCard : player.getArmour()) {
                                if (armourCard.requiredClass() == player.getCharacterClass()) {
                                    player.removeArmour(armourCard);
                                    DeckManager.treasureCardsDeck.add(armourCard);
                                }
                            }
                        } 
                        selectedCard.play(player);
                    } else {
                        selectedCard.play(player);
                    }
                }
            }

            // Step 4: Exit loop if tokens is >= 10 or <= 0
            if (player.getTokens() >= 10) {
                System.out.println("You won!");
                System.exit(0);
                break;
            } else if (player.getTokens() <= 0) {
                System.out.println("You lost!");
                System.exit(0);
                break;
            }
        }
    }
}