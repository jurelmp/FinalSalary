package ph.petrologisticscorp.finalsalary.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javax.xml.soap.Node;

/**
 * FXML Controller class
 *
 * @author patoc.jp
 */
public class MainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void test(ActionEvent actionEvent) {
        System.out.println("Clicked");
    }
}
