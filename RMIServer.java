package laerm;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Observable;
import java.util.Observer;

import de.htw.vt.SensorModel;
import de.htw.vt.SensorModelImpl;
import jaxrs.jaxrs.src.org.htw.fiw.FrontendServerImpl;


public class RMIServer extends SensorModelImpl 
{
    public RMIServer() throws RemoteException {
		super();
		
	}

	String address;
    Registry registry;

    public void addObserver(final Remote observer) throws RemoteException
    {
        // Hier werden der Observer (update) und der RMIClient stub integriert
        super.addObserver(new Observer()
        {
            @Override
            public void update(Observable o,
                Object arg)
            {
                try
                {
                    ((RmiClient) observer).update((String) arg);
                }
                catch (RemoteException e)
                {

                }
            }
        });
    }

    /**
     * @return 
     * @throws RemoteException
     */
    public void RmiServer() throws RemoteException
    {
        try
        {
        	SensorModel remoteService = (SensorModel) Naming
                    .lookup("rmi://localhost:1099/sensors/");
        	address = (InetAddress.getLocalHost()).toString();
        	FrontendServerImpl frontendService = (FrontendServerImpl) Naming
        			.lookup("rmi://localhost:1099/FrontendServer/");
        }
        catch (Exception e)
        {
            System.out.println("can't get inet address.");
            e.printStackTrace();
        }
        int port = 1099;
        System.out.println("this address=" + address + ",port=" + port);
        
    }

    /**
     * 
     * @param args
     */
    static public void main(String args[])
    {
        try
        {
            RmiServer server = new RmiServer();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
 }  

