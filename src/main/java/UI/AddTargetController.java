/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Entities.Target;
import Service.TargetServiceClient;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author olfakaroui
 */
public class AddTargetController implements Initializable {

    @FXML
    private JFXTextField name;
    @FXML
    private JFXButton add;
    Label error = new Label();
    PopOver popOver = new PopOver();
    private boolean isModif = false;
    private Target modif;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setIsModif(boolean isModif) {
        this.isModif = isModif;
    }

    public void setModif(Target modif) {
        this.modif = modif;
        name.setText(modif.getName());
    }


    
    @FXML
    private void addBtn(ActionEvent event) {
        
        if(!name.getText().trim().isEmpty())
        {
           
            if(!isModif)
            {
                Target target = new Target();
                target.setName(name.getText());
                TargetServiceClient.getInstance().addTarget(target);
            }
            if(isModif)
            {
                modif.setName(name.getText());
                TargetServiceClient.getInstance().editTarget(modif);
            }
        }
        else
        {
          error.setText("Veuillez remplir tous les champs");
          error.setStyle("-fx-font-size:10;-fx-text-fill:#b20000;");
          popOver.setContentNode(error);
          popOver.show(add);  
        }
    }
    
}
