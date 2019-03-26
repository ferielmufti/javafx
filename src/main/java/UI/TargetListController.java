/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Entities.Target;
import Service.TargetServiceClient;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
public class TargetListController implements Initializable {

    @FXML
    private TableView<Target> tableView;

    @FXML
    private TableColumn<Target, Boolean> modif;
    @FXML
    private Button btnAjout;
    @FXML
    private TableColumn<Target, String> Name;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
     private void AfficherTarget() {
        
         ObservableList<Target> lstsess = FXCollections.observableArrayList(TargetServiceClient.getInstance().selectTarget());
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        modif.setCellFactory(new Callback<TableColumn<Target, Boolean>, TableCell<Target, Boolean>>() {
            @Override
            public TableCell<Target, Boolean> call(TableColumn<Target, Boolean> param) {
                return new Buttons();
            }
        });

        tableView.setItems(lstsess);

    }

    private class Buttons extends TableCell<Target, Boolean> {

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
                        Target Target = new Target();
                        Target = Buttons.this.getTableView().getItems().get(Buttons.this.getIndex());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddTarget.fxml"));

                        AnchorPane node = loader.load();

                        AddTargetController controller = loader.getController();

                        controller.setModif(Target);
                        controller.setIsModif(true);

                        Stage stage = new Stage();
                        Scene scene = new Scene(node);
                        stage.setScene(scene);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.setOnHiding(new EventHandler<WindowEvent>() {
                            public void handle(WindowEvent we) {
                                System.err.println("closing");
                                AfficherTarget();
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
                    Target Target = Buttons.this.getTableView().getItems().get(Buttons.this.getIndex());
                    TargetServiceClient.getInstance().deleteTarget(Target.getId());

                    AfficherTarget();

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
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/AddTarget.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);

            stage.setOnHiding(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    System.err.println("closing");
                    AfficherTarget();
                }

            });
            stage.show();

        } catch (IOException ex) {
            
    }}
}
