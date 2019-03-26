package UI;

import Entities.Reclamation;
import Entities.User;
import Service.ReclamationServiceClient;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.Rating;

public class AddReclamationController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Button addBtn;
    @FXML
    private TextArea comment;
    @FXML
    private Rating ratingSchedule;
    @FXML
    private Rating ratingLogistic;
    @FXML
    private Rating ratingRH;
    @FXML
    private Rating ratingSalary;
    Label error = new Label();
    User user;
    private User company;
    
    PopOver popOver = new PopOver();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setCompany(User company) {
        this.company = company;
    }
    

    @FXML
    private void addBtn(ActionEvent event) {
         double sceduleRate = ratingSchedule.getRating();
        double logisticRate = ratingLogistic.getRating();
        double rhRate = ratingRH.getRating();
        double salaryRate = ratingSalary.getRating();
        if(!comment.getText().trim().isEmpty())
        {
            Reclamation reclamation = new Reclamation();
            reclamation.setComment(comment.getText());
            reclamation.setRatingLogistic((float)logisticRate);
            reclamation.setRatingRH((float)rhRate);
            reclamation.setRatingSalary((float)salaryRate);
            reclamation.setRatingSchedule((float)sceduleRate);
            reclamation.setEntreprise(company);
            reclamation.setReclamedBy(user);
            ReclamationServiceClient.getInstance().addReclamation(reclamation);
        }
        else
        {
            error.setText("Veuillez remplir tous les champs");
          error.setStyle("-fx-font-size:10;-fx-text-fill:#b20000;");
          popOver.setContentNode(error);
          popOver.show(addBtn);  
        }
    }

}
