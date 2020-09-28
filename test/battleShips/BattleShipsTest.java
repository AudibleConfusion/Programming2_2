package battleShips;

import battleShips.protocolBinding.StreamBindingSender;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class BattleShipsTest
{

    @Test
    public void usageTest() throws GameException, StatusException, IOException
    {
        ShortCut sender1 = new ShortCut();
        BattleShipsEngine game1 = new BattleShipsEngine(sender1);


        ShortCut sender2 = new ShortCut();
        BattleShipsEngine game2 = new BattleShipsEngine(sender2);

        //connect both games
        sender1.setReceiver(game2);
        sender2.setReceiver(game1);

        // test methods
        game1.doDice();
        game2.doDice();

        IBattleShipsUsage activeGame = game1.isActive() ? game1 : game2;

        activeGame.fire(0,0);
        Assert.assertFalse(game1.isActive());
    }
}