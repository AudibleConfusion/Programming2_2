package battleShips.protocolBinding;

import battleShips.IBattleShipsReceiver;
import battleShips.StatusException;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamBindingReceiver extends Thread //implements IBattleShipsReceiver
{
    private final DataInputStream dis;
    private final IBattleShipsReceiver receiver;

    StreamBindingReceiver(InputStream is, IBattleShipsReceiver receiver)
    {
        this.dis = new DataInputStream(is);
        this.receiver = receiver;
    }

    public void readDice() throws IOException, StatusException
    {
        int random = dis.readInt();

        this.receiver.receiveDice(random);
    }

    public void readFire() throws IOException, StatusException
    {
        int x = dis.readInt();
        int y = dis.readInt();

        this.receiver.receiveFire(x, y);
    }

    public void readSurrender() throws IOException, StatusException
    {
        this.receiver.receiveSurrender();
    }

    public void readResponse() throws IOException, StatusException
    {
        boolean hit = dis.readBoolean();
        this.receiver.receiveResponse(hit);
    }

    public void run()
    {
        loop: while(true)
        {
            try
            {
                int cmd = dis.readInt();

                switch(cmd)
                {
                    case StreamBinding.DICE:        readDice(); break;
                    case StreamBinding.FIRE:        readFire(); break;
                    case StreamBinding.SURRENDER:   readSurrender(); break;
                    case StreamBinding.RESPONSE:    readResponse(); break;
                    default: break loop;
                }
            }
            catch(IOException e)
            {
                System.err.println("Status Exception: " + e.getLocalizedMessage());
                break loop;
            }
            catch(StatusException e)
            {
                System.err.println("Status Exception: " + e.getLocalizedMessage());
                break loop;
            }
        }
    }
}