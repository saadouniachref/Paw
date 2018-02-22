package paw.trainingService;

import Entity.AnnonceTraining;
import Service.AnnonceTrainingServices;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import static paw.Paw.session;
import paw.profile.FXMLprofileController;

public class FXMLTrainingController implements Initializable {

    @FXML
    private TextField racePet;
    @FXML
    private TextField nomPet;
    @FXML
    private TextField agePet;
    @FXML
    private DatePicker dateTr;
    @FXML
    private RadioButton sexeM;
    @FXML
    private RadioButton sexeF;
    @FXML
    private JFXComboBox<String> typeTr;
    @FXML
    private JFXComboBox<String> typePet;
    @FXML
    private JFXTextField colorPet;
    
    ToggleGroup sexe = new ToggleGroup();
    @FXML
    private JFXButton validerB;
    @FXML
    private JFXButton annulerB;
    @FXML
    private JFXTextField descPet;
    @FXML
    private ImageView imgTr3;
    @FXML
    private JFXButton retourB;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeTr.getItems().add("Puppy Training");
        typeTr.getItems().add("Beginner Training");
        typeTr.getItems().add("Advanced Training");
        typePet.getItems().add("Chien");
        typePet.getItems().add("Chat");
        typePet.getItems().add("Autre");
        sexeM.setToggleGroup(sexe);
        sexeF.setToggleGroup(sexe);
        System.out.println(session.getId());

        
    }    

    @FXML
    private void ValiderTraining(ActionEvent event) {
        System.out.println(typeTr.getValue());
        String type = "Annonce Training";
        
        String s="";
        if (sexeM.isSelected())
        {
            s="Male";
        }
        else
        {
            s="Female";
        }
        AnnonceTrainingServices as = new AnnonceTrainingServices();
        System.out.println(session.getId());
            as.insererAnnonceTraining(
                    new AnnonceTraining(
                    java.sql.Date.valueOf(dateTr.getValue()), 
                    (String)typeTr.getValue(),
                    (String)typePet.getValue(),
                            nomPet.getText() , 
                            0,
                            Integer.parseInt(agePet.getText()), 
                            colorPet.getText(), 
                            s, 
                            racePet.getText(),
                            descPet.getText(),
                            type,
                            null,
                            session.getId()));
//            initialisation des champs
            dateTr.setValue(LocalDate.now());
            typeTr.setValue("");
            agePet.setText("");
            racePet.setText("");
            descPet.setText("");
            colorPet.setText("");
            typePet.setValue("");
            dateTr.setValue(null);
    }

    @FXML
    private void annulerTraining(ActionEvent event) {
         dateTr.setValue(null);
            typeTr.setValue("");
            agePet.setText("");
            racePet.setText("");
            descPet.setText("");
            colorPet.setText("");
            typePet.setValue("");
//            sexe.setUserData(null);
    }

    @FXML
    private void redirection(ActionEvent event) throws IOException {
        try{
//            loadSplashScreen("/paw/trainingService/FXMLTrainingPrincipal.fxml");
            System.out.println("redirection in progress !!");
            Parent trainingPrincipalParent = FXMLLoader.load(getClass().getResource("FXMLTrainingPrincipal.fxml"));
            Scene trainingPrincipalScene = new Scene(trainingPrincipalParent);  
            Stage app_stage =  (Stage)((Node)event.getSource()).getScene().getWindow();
            app_stage.setScene(trainingPrincipalScene);
            app_stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLprofileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

 
  

    

   
    
    
}
