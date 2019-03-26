package Service;

import Entities.Publicity;
import Entities.Target;
import Interfaces.ITargetServiceRemote;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import java.util.logging.Level;
import java.util.logging.Logger;

public class TargetServiceClient {
    private ITargetServiceRemote proxy;
    private static TargetServiceClient instance = null;
    
    private TargetServiceClient() {
       
        try {
            String jndiName = "pi-ear/pi-ejb/TargetService!Interfaces.ITargetServiceRemote";
            Context context = new InitialContext();
            proxy  = (ITargetServiceRemote) context.lookup(jndiName);
        } catch (NamingException ex) {
            Logger.getLogger(TargetServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public static TargetServiceClient getInstance() {
        if(instance == null)
        {
            instance = new TargetServiceClient();
        }
        return instance;
    }
    
    public void addTarget(Target re)
    {
    proxy.addTarget(re);
    }
    public void editTarget(Target re)
    {
    proxy.updateTarget(re);
    }
    public ArrayList<Target> selectTarget()
    {
    return new ArrayList<Target>(proxy.findAllTargets());
    }
    public void deleteTarget(int id)
    {
    proxy.removeTarget(id);
    }
}