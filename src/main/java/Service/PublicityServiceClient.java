package Service;

import Entities.Publicity;
import Entities.Target;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import Interfaces.IPublicityServiceRemote;
import Services.PublicityService;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PublicityServiceClient {
    private IPublicityServiceRemote proxy;
    private static PublicityServiceClient instance = null;
    private PublicityServiceClient() {
        
        try {
            String jndiName = "pi-ear/pi-ejb/PublicityService!Interfaces.IPublicityServiceRemote";
            Context context = new InitialContext();
            proxy  = (IPublicityServiceRemote) context.lookup(jndiName);
        } catch (NamingException ex) {
            Logger.getLogger(PublicityServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

    public static PublicityServiceClient getInstance() {
        if(instance == null)
        {
            instance = new PublicityServiceClient();
        }
        return instance;
    }
    
    public void addPublicity(Publicity pb)
    {
    proxy.addPublicity(pb);
    }
    public void editPublicity(Publicity pb)
    {
    proxy.updatePublicity(pb);
    }
    public ArrayList<Publicity> selectPublicitiesByOwner(int id)
    {
    return new ArrayList<Publicity>(proxy.findPublicityByOwner(id));
    }
    public void deletePublicity(int id)
    {
    proxy.removePublicity(id);
    }
}