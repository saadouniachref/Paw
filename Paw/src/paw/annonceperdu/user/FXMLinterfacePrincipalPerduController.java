/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paw.annonceperdu.user;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import paw.mainUI.FXMLCnxController;
import paw.profile.FXMLprofileController;

/**
 * FXML Controller class
 *
 * @author Guideinfo
 */
public class FXMLinterfacePrincipalPerduController extends FXMLCnxController implements Initializable {

    @FXML
    private AnchorPane reclamations;
    @FXML
    private AnchorPane reclamations1;
    @FXML
    private AnchorPane reclamations11;
    @FXML
    private AnchorPane window;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void goToListe(ActionEvent event) {
        try{
            loadSplashScreen("/paw/annonceperdu/user/FXMLListeAnnoncesPerdus.fxml");
        } catch (Exception ex) {
            Logger.getLogger(FXMLprofileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goToAjout(ActionEvent event) {
        try{
            loadSplashScreen("/paw/annonceperdu/user/FXMLAjouterAnnoncePerdu.fxml");
        } catch (Exception ex) {
            Logger.getLogger(FXMLprofileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goToConseils(ActionEvent event) {
        try{
            loadSplashScreen("/paw/annonceperdu/user/FXMLinterfaceConseil.fxml");
        } catch (Exception ex) {
            Logger.getLogger(FXMLprofileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
