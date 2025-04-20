package savegame;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

public class SaveManager {
    private static final Path SAVE_FILE = Path.of("game_save.dat");

    public static void saveGame(GameState gameState) {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(SAVE_FILE))) {
            oos.writeObject(gameState);
            System.out.println("Game saved successfully!");
        } catch (Exception e) {
            System.err.println("Failed to save game: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static GameState loadGame() {
        if (!Files.exists(SAVE_FILE)) {
            System.out.println("No save file found.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(SAVE_FILE))) {
            GameState gameState = (GameState) ois.readObject();
            System.out.println("Game loaded successfully! Last saved at: " +
                gameState.getSaveTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            return gameState;
        } catch (Exception e) {
            System.err.println("Failed to load game: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}