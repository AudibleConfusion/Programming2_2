package streamMachine;

import org.junit.Assert;
import org.junit.Test;

public class StreamMachineTest
{
    @Test
    public void gutTest1() throws PersistenceException
    {
        String sensorName1 = "sensor1";
        IStreamMachine sm1 = new StreamMachineFS(sensorName1);
        sm1.clean();

        String sensorName2 = "sensor2";
        IStreamMachine sm2 = new StreamMachineFS(sensorName2);

        long time1 = System.currentTimeMillis();
        float[] valueSet = new float[3];
        valueSet[0] = (float) 2.7;
        valueSet[1] = (float) 2.2;
        valueSet[2] = (float) 2.1;
        sm1.saveData(time1, valueSet);

        long time2 = System.currentTimeMillis() + 1000000;
        float[] valueSet2 = new float[3];
        valueSet2[0] = (float) 3.4;
        valueSet2[1] = (float) 3.1;

        sm2.saveData(time2, valueSet2);

        //load saved data
        int size = sm1.size();
        Assert.assertEquals(1, size);

        //load latest saved data set
        ISensorDataSet dataSet = sm1.getDataSet(size - 1);

        Assert.assertEquals(time1, dataSet.getTime());
        Assert.assertArrayEquals(valueSet, dataSet.getValues(), 0);
    }
}