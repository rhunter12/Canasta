/**
 * @author
 *      Ryan Hunter-Bliss
 *      Sarah Ebner
 *      Lute Lillo Portero
 */

package edu.up.cs301.canasta;

//import androidx.appcompat.app.AppCompatActivity;

import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.gameConfiguration.GameConfig;

public class CanastaMainActivity extends GameMainActivity {

    @Override
    public GameConfig createDefaultConfig() {
        return null;
    }

    @Override
    public LocalGame createLocalGame() {
        return null;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        CanastaGameState state = new CanastaGameState();
//
//        Button runTest = findViewById(R.id.test_button);
//        runTest.setOnClickListener(state);
//
//        TextView outputView = findViewById(R.id.output_view);
//        state.setTextView(outputView);
//    }
}