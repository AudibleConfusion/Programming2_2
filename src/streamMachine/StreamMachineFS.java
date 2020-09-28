package streamMachine;

import java.io.*;

public class StreamMachineFS implements IStreamMachine
{
    private final String sensorName;
    private int numberDataSets = 0;

    public StreamMachineFS(String sensorName)
    {
        this.sensorName = sensorName;
        try
        {
            this.readMetaData();
        } catch (IOException e)
        {
            //ignore
        }
    }

    String getMetaDataFileName()
    {
        return sensorName + "_meta.txt";
    }
    private String getFileName()
    {
        return sensorName + ".txt";
    }

    private void saveMetaData() throws IOException
    {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(getMetaDataFileName()));

        dos.writeUTF(sensorName);
        dos.writeInt(numberDataSets);
    }

    private void readMetaData() throws IOException
    {
        DataInputStream dis = new DataInputStream(new FileInputStream(getMetaDataFileName()));

        dis.readUTF();
        numberDataSets = dis.readInt();
    }

    @Override
    public void saveData(long time, float[] values) throws PersistenceException
    {
        OutputStream fos = null;

        try
        {
            fos = new FileOutputStream(getFileName(), true);
        } catch (FileNotFoundException e)
        {
            throw new PersistenceException();
        }


        DataOutputStream dos = new DataOutputStream(fos);

        try
        {
            // time stamp
            dos.writeLong(time);

            // values
            dos.writeInt(values.length);
            for (int i = 0; i < values.length; i++)
                dos.writeFloat(values[i]);
            dos.close();
            numberDataSets++;
            saveMetaData();

        } catch (IOException e)
        {
            throw new PersistenceException();
        }
    }

    @Override
    public int size()
    {
        return numberDataSets;
    }

    private ISensorDataSet[] sensorData = null;

    private void readFromFile() throws IOException
    {
        readMetaData();
        sensorData = new SensorDataSet[numberDataSets];

        //open file
        InputStream is = new FileInputStream(getFileName());
        DataInputStream dis = new DataInputStream(is);

        //read data set
        for (int i = 0; i < numberDataSets; i++)
        {

        // time stamp
        Long timeStamp = dis.readLong();

        // values
        int sensorDataLength = dis.readInt();
        float[] values = new float[sensorDataLength];
        for (int j = 0; j < sensorDataLength; j++)
            values[j] = dis.readFloat();

        ISensorDataSet set = new SensorDataSet(timeStamp, values);

        //keep it in memory
        sensorData[i] = new SensorDataSet(timeStamp, values);
        }
    }

    @Override
    public ISensorDataSet getDataSet(int index) throws PersistenceException
    {
        try
        {
            readFromFile();
        } catch (IOException e)
        {
            throw new PersistenceException();
        }

        if(index < 0 && index >= numberDataSets)
        {
            throw new PersistenceException();
        }

        return sensorData[index];
    }

    @Override
    public void clean()
    {
        numberDataSets = 0;
        sensorData = null;
        File file = new File(getMetaDataFileName());
        file.delete();
        File metaFile = new File(getFileName());
        metaFile.delete();
    }
}
