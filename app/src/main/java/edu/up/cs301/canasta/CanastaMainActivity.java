/**
 * @author
 *      Ryan Hunter-Bliss
 *      Sarah Ebner
 *      Lute Lillo Portero
 *
 * Bugs: There are no known bugs or issues with the game
 *
 * Additional features:
 *      - Game supports both protrait and landscape
 */

package edu.up.cs301.canasta;


import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.gameConfiguration.GameConfig;
import edu.up.cs301.game.GameFramework.gameConfiguration.GamePlayerType;


import java.util.ArrayList;

public class CanastaMainActivity extends GameMainActivity {
    private static final int PORT_NUMBER = 2434;

    @Override
    public GameConfig createDefaultConfig() {
        ArrayList<GamePlayerType> playerTypes = new ArrayList<>();

        playerTypes.add(new GamePlayerType("Local human player") {
            public GamePlayer createPlayer(String name) {
                return new CanastaPlayer(0,name);
            }});

        playerTypes.add(new GamePlayerType("Dumb Computer player") {
            public GamePlayer createPlayer(String name) {
                return new CanastaComputerPlayer1(1, name);
            }});

        playerTypes.add(new GamePlayerType("Smart Computer player") {
            public GamePlayer createPlayer(String name) {
                return new CanastaComputerPlayer2(2, name);
            }});

        GameConfig defaultConfig = new GameConfig(playerTypes, 2, 2, "Canasta", PORT_NUMBER);
        defaultConfig.addPlayer("Human", 0);
        defaultConfig.addPlayer("Computer", 1);
        defaultConfig.addPlayer("Computer", 2);
        defaultConfig.setRemoteData("Remote Human Player", "", 0);

        return defaultConfig;
    }

    @Override
    public LocalGame createLocalGame() {
        return new CanastaLocalGame();
    }

}