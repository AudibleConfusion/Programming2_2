package sensorData;

import streamMachine.IStreamMachine;
import streamMachine.PersistenceException;

import java.io.DataInputStream;
import java.io.IOException;

class SensorDataSetReader extends Thread
{
    private final IStreamMachine storage;
    private final DataInputStream dis;

    SensorDataSetReader(DataInputStream dis, IStreamMachine storage)
    {
        this.dis = dis;
        this.storage = storage;
    }

    public void run()
    {
        //read form tcp
        try
        {
            String name = dis.readUTF();
            long time = dis.readLong();
            int leng = dis.readInt();
            float[] values = new float[leng];
            for (int i = 0; i < leng; i++)
                values[i] = dis.readFloat();


        //write into machine
        storage.saveData(time, values);
        } catch (IOException | PersistenceException e)
        {
            System.err.println("problems when reading / writing sensor data");
        }
    }
}