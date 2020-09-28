package battleShips;

import java.io.IOException;
import java.util.Random;

public class BattleShipsEngine implements IBattleShipsReceiver,  IBattleShipsUsage
{
    public static final int UNDEFINED_DICE = -1;
    private final IBattleShipsSender sender;

    private BattleshipsStatus status;
    private int sentDice = -1;

    public BattleShipsEngine(IBattleShipsSender sender)
    {
        this.status = BattleshipsStatus.START;
        this.sender = sender;
    }

    /*--------------------------------------------------------------------------------------------------------------*/
    /*----------------------------------------  remote engine support  ---------------------------------------------*/
    /*--------------------------------------------------------------------------------------------------------------*/

    @Override
    public void receiveDice(int random) throws StatusException
    {
        if(status != BattleshipsStatus.START
            && status != BattleshipsStatus.DICE_SENT)
            throw new StatusException();

        //höhere Zahl -> AKTIVE, kleinere Zahl -> PASSIVE
        if(status == BattleshipsStatus.DICE_SENT)
        {
            if(random == sentDice)
                status = BattleshipsStatus.START;
            else if (random > sentDice)
                status = BattleshipsStatus.PASSIVE;
            else
                status = BattleshipsStatus.ACTIVE;
        }
    }

    @Override
    public void receiveFire(int x, int y) throws StatusException, IOException
    {
        if(status != BattleshipsStatus.PASSIVE)
            throw new StatusException();

        status = BattleshipsStatus.ACTIVE_RESPONSE;
    }

    @Override
    public void receiveSurrender() throws IOException, StatusException
    {
        if(status != BattleshipsStatus.PASSIVE)
            throw new StatusException();

        status = BattleshipsStatus.WON;
    }

    @Override
    public void receiveResponse(boolean hit) throws StatusException, IOException
    {
        if(this.status != BattleshipsStatus.PASSIVE_RESPONSE)
            throw new StatusException();

        status = BattleshipsStatus.PASSIVE;
    }

    /*
    @Override
    public void sendFire(int row, int column) throws StatusException
    {
        if(this.status != BattleshipsStatus.ACTIVE)
            throw new StatusException();

        //sende Werte über TCP

        status = BattleshipsStatus.PASSIVE_RESPONSE;
    }

    @Override
    public void sendSurrender() throws StatusException
    {
        if(this.status != BattleshipsStatus.ACTIVE)
            throw new StatusException();

        //Sende Aufgabe

        status = BattleshipsStatus.LOST;
    }

    @Override
    public void sentResponse(boolean hit) throws StatusException
    {
        if(this.status != BattleshipsStatus.ACTIVE_RESPONSE)
            throw new StatusException();

        //Sende Wert über TCP

        status = BattleshipsStatus.ACTIVE;
    }
    */

    /*--------------------------------------------------------------------------------------------------------------*/
    /*--------------------------------------------------  UI  ------------------------------------------------------*/
    /*--------------------------------------------------------------------------------------------------------------*/

    @Override
    public void doDice() throws StatusException, IOException
    {
        if(this.status != BattleshipsStatus.START)
            throw new StatusException();

        Random r = new Random();
        this.sentDice = r.nextInt();

        //sende den Wert über TCP
        sender.sendDice(sentDice);

        status = BattleshipsStatus.DICE_SENT;
    }

    @Override
    public boolean isActive()
    {
        return status == BattleshipsStatus.ACTIVE;
    }

    @Override
    public void fire(int x, int y) throws GameException, StatusException
    {

    }
}