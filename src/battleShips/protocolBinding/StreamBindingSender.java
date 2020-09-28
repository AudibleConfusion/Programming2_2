package battleShips.protocolBinding;

import battleShips.IBattleShipsSender;
import battleShips.StatusException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.stream.Stream;

/**
 * Sendet Paremeter via OutputStream
 */
public class StreamBindingSender implements IBattleShipsSender
{
    private final DataOutputStream dos;

    public StreamBindingSender(OutputStream os)
    {
        this.dos = new DataOutputStream(os);
    }

    @Override
    public void sendDice(int random) throws StatusException, IOException
    {
        dos.writeInt(StreamBinding.DICE);
        dos.writeInt(random);
    }

    @Override
    public void sendFire(int row, int column) throws StatusException, IOException
    {
        dos.writeInt(StreamBinding.FIRE);
        dos.writeInt(row);
        dos.writeInt(column);
    }

    @Override
    public void sendSurrender() throws StatusException, IOException
    {
        dos.writeInt(StreamBinding.SURRENDER);
    }

    @Override
    public void sentResponse(boolean hit) throws StatusException, IOException
    {
        dos.writeInt(StreamBinding.RESPONSE);
        dos.writeBoolean(hit);
    }
}