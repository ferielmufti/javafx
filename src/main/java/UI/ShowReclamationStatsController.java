/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Entities.User;
import Entities.Reclamation;
import Service.ReclamationServiceClient;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author olfakaroui
 */
public class ShowReclamationStatsController implements Initializable {

    @FXML
    private AnchorPane hr;
    @FXML
    private AnchorPane schedule;
    @FXML
    private AnchorPane salary;
    @FXML
    private AnchorPane resources;
    private User company;
    HashMap<String,ArrayList<Integer>> map;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setCompany(User company) {
        this.company = company;
    }
    
    
    public void init()
    {
       map = new HashMap<>();
       ArrayList<Integer> rating = new ArrayList<>();
       for(int i =0; i<5;i++) {
           rating.add(i,0);
       }
       map.put("HR rating", rating);
       map.put("Work balance rating", rating);
       map.put("Work conditions rating", rating);
       map.put("Salary rating", rating);
       ArrayList<Reclamation> reclamations = ReclamationServiceClient.getInstance().selectReclamationByCompany(company.getId());
       for(Reclamation r : reclamations )
       {
           addMap("Work conditions rating", r.getRatingLogistic());
           addMap("Salaryrating", r.getRatingSalary());
           addMap("HR rating", r.getRatingRH());
           addMap("Work balance rating", r.getRatingSchedule());
           
       }
        createChat(map.get("HR rating"), hr, "HR rating");
        createChat(map.get("Work balance rating"), schedule, "Work balance rating");
        createChat(map.get("Salary rating"), salary, "Salary rating");
        createChat(map.get("Work conditions rating"), resources, "Work conditions rating");
    }
    public void createChat(ArrayList<Integer> stats, AnchorPane parent, String title)
    {
       ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("1 star", stats.get(0)),
                new PieChart.Data("2 stars", stats.get(1)),
                new PieChart.Data("3 stars", stats.get(2)),
                new PieChart.Data("4 stars", stats.get(3)),
                new PieChart.Data("5 stars", stats.get(4)));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle(title);

        parent.getChildren().add(chart);
    }
    public void addMap(String key, float value)
    {
        if(value == 1)
           {
               map.get(key).set(0, map.get(key).get(0) + 1);
           }
           else if(value == 2)
           {
               map.get(key).set(1, map.get(key).get(1) + 1);
           }
           else if(value == 3)
           {
               map.get(key).set(2, map.get(key).get(2) + 1);
           }
           else if(value == 4)
           {
               map.get(key).set(3, map.get(key).get(3) + 1);
           }
           else if(value == 5)
           {
               map.get(key).set(4, map.get(key).get(4) + 1);
           }
    }
    
}
