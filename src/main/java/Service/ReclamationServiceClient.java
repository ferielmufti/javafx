package Service;

import Entities.Publicity;
import Entities.Reclamation;
import Interfaces.IReclamationServiceRemote;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import java.util.logging.Level;
import java.util.logging.Logger;

public class ReclamationServiceClient {
    private IReclamationServiceRemote proxy;
    private static ReclamationServiceClient instance = null;
    
    private ReclamationServiceClient() {
       
        try {
            String jndiName = "pi-ear/pi-ejb/ReclamationService!Interfaces.IReclamationServiceRemote";
            Context context = new InitialContext();
            proxy  = (IReclamationServiceRemote) context.lookup(jndiName);
        } catch (NamingException ex) {
            Logger.getLogger(ReclamationServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public static ReclamationServiceClient getInstance() {
        if(instance == null)
        {
            instance = new ReclamationServiceClient();
        }
        return instance;
    }
    
    public void addReclamation(Reclamation re)
    {
    proxy.addReclamation(re);
    }
     public ArrayList<Reclamation> selectReclamationByCompany(int id)
    {
    return new ArrayList<Reclamation>();
    }
   
}