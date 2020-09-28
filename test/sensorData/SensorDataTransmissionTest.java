package sensorData;

import org.junit.Assert;
import org.junit.Test;
import streamMachine.*;
import transmission.DataConnector;
import transmission.IDataConnector;

import java.io.IOException;

public class SensorDataTransmissionTest
{
    private static final int PORTNUMBER = 9876;

    @Test
    public void gutTransmissionTest() throws IOException, PersistenceException, InterruptedException
    {
        String sensorName = "MyGoodOldSensor";
        long timeStamp = System.currentTimeMillis();
        float[] valueSet = new float[3];
        valueSet[0] = (float)1.3;
        valueSet[1] = (float)2.6;
        valueSet[2] = (float)3.7;


        IStreamMachine dataStorage = new StreamMachineFS(sensorName);
        dataStorage.clean();

        //Establish connection between server and client
        IDataConnector receiverConnection = new DataConnector(PORTNUMBER);

        IDataConnector senderConnection = new DataConnector("localhost", PORTNUMBER);

        //send data
        SensorDataReceiver sdr = new SensorDataReceiver(receiverConnection, dataStorage);

        SensorDataSender sds = new SensorDataSender(senderConnection);



        //send TCP data
        sds.sendData(sensorName, timeStamp, valueSet);

        //test if stored
        IStreamMachine dataStorageReceived = sdr.getStorage();
        Thread.sleep(4);
        ISensorDataSet sdata = dataStorageReceived.getDataSet(0);
        String sensorNameReceived;
        long timeStampReceived = sdata.getTime();
        float[] valueSetReceived = sdata.getValues();

        //test
        //Assert.assertEquals(sensorName, sensorNameReceived);
        Assert.assertEquals(timeStamp, timeStampReceived);
        Assert.assertArrayEquals(valueSet, valueSetReceived, 0);
    }
}