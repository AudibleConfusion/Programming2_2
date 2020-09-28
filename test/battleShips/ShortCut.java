package battleShips;

import java.io.IOException;

public class ShortCut implements IBattleShipsSender
{
    private IBattleShipsReceiver receiver;

    public void setReceiver(IBattleShipsReceiver receiver)
    {
        this.receiver = receiver;
    }

    @Override
    public void sendDice(int random) throws StatusException, IOException
    {
        receiver.receiveDice(random);
    }

    @Override
    public void sendFire(int row, int column) throws StatusException, IOException
    {
        receiver.receiveFire(row, column);
    }

    @Override
    public void sendSurrender() throws StatusException, IOException
    {
        receiver.receiveSurrender();
    }

    @Override
    public void sentResponse(boolean hit) throws StatusException, IOException
    {
        receiver.receiveResponse(hit);
    }
}
