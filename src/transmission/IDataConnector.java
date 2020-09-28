package transmission;

import java.io.*;

public interface IDataConnector
{
    DataInputStream getDataInputStream() throws IOException;
    DataOutputStream getDataOutputStream() throws IOException;
}