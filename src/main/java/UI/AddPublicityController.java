package UI;

import Entities.Publicity;
import Entities.Reclamation;
import Entities.Target;
import Entities.User;
import Service.PublicityServiceClient;
import Service.ReclamationServiceClient;
import Service.TargetServiceClient;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.PopOver;

public class AddPublicityController implements Initializable {
    
    @FXML
    private Button addBtn;
    @FXML
    private TextField name;
    @FXML
    private TextArea description;
    @FXML
    private Button photo;
    @FXML
    private CheckComboBox<Target> targets;
    @FXML
    private ImageView selectedphoto;
    User user;
    Label error = new Label();
    PopOver popOver = new PopOver();
    private boolean isModif = false;
    private Publicity modif;

    public void setIsModif(boolean isModif) {
        this.isModif = isModif;
    }

    public void setModif(Publicity modif) {
        this.modif = modif;
        init();
        for(Target t : modif.getTargetAudience())
        {
            targets.getCheckModel().check(t);
        }
        name.setText(modif.getName());
        description.setText(modif.getDescription());
    }
    
   private void init()
   {
       ArrayList<Target> alltargets = TargetServiceClient.getInstance().selectTarget();
        targets.getItems().addAll(alltargets);
        targets.setConverter(new StringConverter<Target>() {
        @Override
        public String toString(Target target) {
            return target == null ? "" : target.getName();
        }

        @Override
        public Target fromString(String s) {
            return targets.getItems().stream().filter(ap -> 
            ap.getName().equals(s)).findFirst().orElse(null);
        }
    });
   }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
    }    

    @FXML
    private void addBtn(ActionEvent event) {

        if(!name.getText().trim().isEmpty() && !description.getText().trim().isEmpty() && !targets.getCheckModel().isEmpty())
        {
            if(!isModif)
                {
            Publicity publicity = new Publicity();
            publicity.setName(name.getText());
            publicity.setDescription(description.getText());
            publicity.setPostedBy(user);
            publicity.setTargetAudience(new HashSet<Target>(targets.getCheckModel().getCheckedItems()));
           
            PublicityServiceClient.getInstance().addPublicity(publicity);
            }
            else
            {
            
            modif.setName(name.getText());
            modif.setDescription(description.getText());
            modif.setTargetAudience(new HashSet<Target>(targets.getCheckModel().getCheckedItems()));
           
            PublicityServiceClient.getInstance().editPublicity(modif);
                
            }
        }
        else
        {
          error.setText("Veuillez remplir tous les champs");
          error.setStyle("-fx-font-size:10;-fx-text-fill:#b20000;");
          popOver.setContentNode(error);
          popOver.show(addBtn);  
        }
    }

    @FXML
    private void onPhotoPick(ActionEvent event) {
    }
}
