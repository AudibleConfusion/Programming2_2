package sensorData;

import streamMachine.IStreamMachine;
import streamMachine.PersistenceException;
import transmission.IDataConnector;

import java.io.DataInputStream;
import java.io.IOException;

public class SensorDataReceiver
{
    private final IDataConnector connection;
    private final IStreamMachine storage;

    public SensorDataReceiver(IDataConnector connection, IStreamMachine storage) throws IOException
    {
        this.connection = connection;
        this.storage = storage;

        SensorDataSetReader sdsr = new SensorDataSetReader(
                new DataInputStream(connection.getDataInputStream()),
                storage
        );

        sdsr.start();
    }

    IStreamMachine getStorage()
    {
        return storage;
    }
}
