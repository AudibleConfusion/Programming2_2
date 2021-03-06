package transmission;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DataConnector extends Thread implements IDataConnector
{
    private ServerSocket serverSocket;
    private Socket socket;

    /**
     * Create client side - open connection to address / port
     * @param address
     * @param port
     */
    public DataConnector(String address, int port) throws IOException
    {
        this.socket = new Socket(address, port);
    }

    /**
     * Create Server side - open connection to port an wait for client
     * @param port
     */
    public DataConnector(int port) throws IOException
    {
        serverSocket = new ServerSocket(port);

        this.start();

        try {
            Thread.sleep(1);
        } catch (InterruptedException e)
        {
            //nothing
        }
    }

    public void run()
    {
        try
        {
            this.socket = serverSocket.accept();
        } catch (IOException e)
        {
            // F
        }
    }

    @Override
    public DataInputStream getDataInputStream() throws IOException
    {
        return new DataInputStream(this.socket.getInputStream());
    }

    @Override
    public DataOutputStream getDataOutputStream() throws IOException
    {
        return new DataOutputStream(this.socket.getOutputStream());
    }
}
