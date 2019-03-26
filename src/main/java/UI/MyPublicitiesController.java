/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Entities.Publicity;
import Entities.Publicity;
import Entities.Target;
import Service.PublicityServiceClient;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author olfakaroui
 */
public class MyPublicitiesController implements Initializable {

    @FXML
    private TableView<Publicity> tableView;
    @FXML
    private TableColumn<Publicity, String> Name;
    @FXML
    private TableColumn<Publicity, String> Description;
    @FXML
    private TableColumn<Publicity, String> Targets;
    @FXML
    private TableColumn<Publicity, Boolean> modif;
    @FXML
    private Button btnAjout;
    int id = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

     private void AfficherPublicity() {
        
         ObservableList<Publicity> lstsess = FXCollections.observableArrayList(PublicityServiceClient.getInstance().selectPublicitiesByOwner(id));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        Targets.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Publicity,String> , ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Publicity, String> param) {
                String s = "";
                for(Target t : param.getValue().getTargetAudience())
                {
                    s += " "+t.getName();
                }
                return new SimpleStringProperty(s);
            }
          
      });
        
        modif.setCellFactory(new Callback<TableColumn<Publicity, Boolean>, TableCell<Publicity, Boolean>>() {
            @Override
            public TableCell<Publicity, Boolean> call(TableColumn<Publicity, Boolean> param) {
                return new Buttons();
            }
        });

        tableView.setItems(lstsess);

    }

    private class Buttons extends TableCell<Publicity, Boolean> {

        final Button cellButton = new Button("Edit");
        final HBox hbox = new HBox();
        final Button supp = new Button("X");

        public Buttons() {
            String style = getClass().getResource("/styles/general.css").toExternalForm();
            cellButton.getStylesheets().add(style);
            cellButton.setId("btn");
            supp.getStylesheets().add(style);
            supp.setId("btn");
            hbox.getChildren().addAll(cellButton, supp);
            hbox.setSpacing(10);

            cellButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    try {
                        System.out.println("hello");
                        int selectdIndex = getTableRow().getIndex();
                        Publicity Publicity = new Publicity();
                        Publicity = Buttons.this.getTableView().getItems().get(Buttons.this.getIndex());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPublicity.fxml"));

                        AnchorPane node = loader.load();

                        AddPublicityController controller = loader.getController();

                       controller.setModif(Publicity);
                       controller.setIsModif(true);

                        Stage stage = new Stage();
                        Scene scene = new Scene(node);
                        stage.setScene(scene);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.setOnHiding(new EventHandler<WindowEvent>() {
                            public void handle(WindowEvent we) {
                                System.err.println("closing");
                                AfficherPublicity();
                            }

                        });
                        stage.show();

                    } catch (IOException ex) {
                       
                    }

                }
            });
            supp.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    int selectdIndex = getTableRow().getIndex();
                    //Publicity Publicity = (Publicity) tableView.getItems().get(selectdIndex);
                    Publicity Publicity = Buttons.this.getTableView().getItems().get(Buttons.this.getIndex());

                    
                    PublicityServiceClient.getInstance().deletePublicity(Publicity.getId());

                    AfficherPublicity();

                }
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(hbox);
            }
        }
    }

    @FXML
    private void Ajouter(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AddPublicity.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);

            stage.setOnHiding(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    System.err.println("closing");
                    AfficherPublicity();
                }

            });
            stage.show();

        } catch (IOException ex) {
            
    }}
    
}
