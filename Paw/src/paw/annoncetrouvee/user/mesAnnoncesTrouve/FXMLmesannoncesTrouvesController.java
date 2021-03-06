/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paw.annoncetrouvee.user.mesAnnoncesTrouve;

import Entity.AnnoncePerdu;
import Entity.AnnonceTrouvee;
import Entity.Utilisateur;
import Service.AnnoncePerduServices;
import Service.AnnonceTrouveServices;
import Service.UtilisateurServices;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import static paw.Paw.session;

/**
 * FXML Controller class
 *
 * @author Guideinfo
 */
public class FXMLmesannoncesTrouvesController implements Initializable {

    @FXML
    private AnchorPane princi;
    @FXML
    private AnchorPane box1;
    @FXML
    private Label nom1;
    @FXML
    private Label tel1;
    @FXML
    private Label type1;
    @FXML
    private Label adr1;
    @FXML
    private JFXButton modifier1;
    @FXML
    private JFXButton supprimer1;
    @FXML
    private ImageView imageanimal1;
    @FXML
    private Label mail1;
    @FXML
    private Label datedepo;
    @FXML
    private AnchorPane box3;
    @FXML
    private Label nom3;
    @FXML
    private Label tel3;
    @FXML
    private Label type3;
    @FXML
    private Label adr3;
    @FXML
    private JFXButton modifier3;
    @FXML
    private JFXButton supprimer3;
    @FXML
    private ImageView imageanimal3;
    @FXML
    private Label mail3;
    @FXML
    private Label datedepo3;
    @FXML
    private AnchorPane box2;
    @FXML
    private Label nom2;
    @FXML
    private Label tel2;
    @FXML
    private Label type2;
    @FXML
    private Label adr2;
    @FXML
    private JFXButton modifier2;
    @FXML
    private JFXButton supprimer2;
    @FXML
    private ImageView imageanimal2;
    @FXML
    private Label mail2;
    @FXML
    private Label datedepo2;
    @FXML
    private AnchorPane box4;
    @FXML
    private Label nom4;
    @FXML
    private Label tel4;
    @FXML
    private Label type4;
    @FXML
    private Label adr4;
    @FXML
    private JFXButton modifier4;
    @FXML
    private JFXButton supprimer4;
    @FXML
    private ImageView imageanimal4;
    @FXML
    private Label mail4;
    @FXML
    private Label datedepo4;
    @FXML
    private Pagination paginator;
    @FXML
    private StackPane consulterAnnonce;
    @FXML
    private Label nom12;
    @FXML
    private Label nom11;
    @FXML
    private JFXTextField ageModification;
    @FXML
    private JFXTextField sexModification;
    @FXML
    private JFXTextField lieuModification;
    @FXML
    private JFXTextField msgModification;
    @FXML
    private JFXTextField raceModification;
    @FXML
    private JFXTextField couleurModification;
    @FXML
    private JFXTextField colierModification;
    @FXML
    private ChoiceBox<String> choixModification;
    @FXML
    private JFXButton valider;
    
     private ArrayList<AnnonceTrouvee> list ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        consulterAnnonce.setVisible(false);
        AnnonceTrouveServices service = new AnnonceTrouveServices();
        choixModification.getItems().setAll("Chien","Chat","Chèvre","Cheval","Rongeur");
        choixModification.setValue("Chien");
             
        list = service.getMesAnnoncesTrouve(session.getId());
        
        if (list.isEmpty()) {
            box1.setVisible(false);
            box2.setVisible(false);
            box3.setVisible(false);
            box4.setVisible(false);
        } else {
            setNbPages();
            initAnnonceTrouvePage(0);
        }
        
        
        
    }    

    private void setNbPages() {
        if (list.size() % 4 != 0) {
            paginator.setPageCount((list.size() / 4) + 1);
        } else {
            paginator.setPageCount(list.size() / 4);
        }

        paginator.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            initAnnonceTrouvePage(newIndex.intValue());
        });
    }

    public List<AnnonceTrouvee> getAnnonceTrouvePage(int index) {

        int start = 4 * index;
        int fin = start + 4;
        if (list.size() > start) {
            if (list.size() > fin) {
                return list.subList(start, fin);
            } else {
                return list.subList(start, list.size());
            }
        }
        return list.subList(0, 3);
    }

    private void initAnnonceTrouvePage(int index) {
        UtilisateurServices utilisateurservice = new UtilisateurServices();
        paginator.setCurrentPageIndex(index);
        List<AnnonceTrouvee> QuatreAnnonceTrouve = getAnnonceTrouvePage(index);
        //System.out.println(QuatreAnnoncePerdus.size());
        if ( QuatreAnnonceTrouve.size() >= 1) {
            box1.setVisible(true);
            System.out.println( QuatreAnnonceTrouve.get(0).getId_utilisateur());
        Utilisateur u = utilisateurservice.rechercher( QuatreAnnonceTrouve.get(0).getId_utilisateur());
       nom1.setText(u.getNom()+" "+u.getPrenom());
       mail1.setText(u.getEmail());
       tel1.setText(String.valueOf(u.getNumero()));
       adr1.setText( QuatreAnnonceTrouve.get(0).getLieu_trouve());
       datedepo.setText(String.valueOf( QuatreAnnonceTrouve.get(0).getDate_trouvee()).substring(0, 10));
            System.out.println( QuatreAnnonceTrouve.get(0).getImages());
       type1.setText( QuatreAnnonceTrouve.get(0).getType());
       Image im = new Image ("http://localhost/paw_web/web/images/pawLostFound/" + QuatreAnnonceTrouve.get(0).getImages().getName());
       imageanimal1.setFitHeight(100);
       imageanimal1.setFitWidth(100);
       imageanimal1.setImage(im);


       modifier1.setOnAction((ActionEvent e) -> {
                modifierannonce( QuatreAnnonceTrouve.get(0),u);
 
                            valider.setOnAction((ActionEvent b) ->{
                             modifier( QuatreAnnonceTrouve.get(0).getId());
                            });
       });
       
       supprimer1.setOnAction((ActionEvent e)->{
       supprimerannonce( QuatreAnnonceTrouve.get(0).getId()) ; 
       });

        } else {
            box1.setVisible(false);
        }

        ///////////////////////////////////////////////////////
         if ( QuatreAnnonceTrouve.size() >= 2) {
       box2.setVisible(true);
           
        Utilisateur u = utilisateurservice.rechercher( QuatreAnnonceTrouve.get(1).getId_utilisateur());
       nom2.setText(u.getNom()+" "+u.getPrenom());
       mail2.setText(u.getEmail());
       tel2.setText(String.valueOf(u.getNumero()));
       adr2.setText( QuatreAnnonceTrouve.get(1).getLieu_trouve());
       datedepo2.setText(String.valueOf( QuatreAnnonceTrouve.get(1).getDate_trouvee()).substring(0, 10));
       type2.setText( QuatreAnnonceTrouve.get(1).getType());
       Image im = new Image ("http://localhost/paw_web/web/images/pawLostFound/" + QuatreAnnonceTrouve.get(1).getImages().getName());
       imageanimal2.setFitHeight(100);
       imageanimal2.setFitWidth(100);
       imageanimal2.setImage(im);


       modifier2.setOnAction((ActionEvent e) -> {
                    modifierannonce( QuatreAnnonceTrouve.get(1),u);                   
                    valider.setOnAction((ActionEvent b) ->{
                             modifier( QuatreAnnonceTrouve.get(1).getId());
                            });
                }); 
       supprimer2.setOnAction((ActionEvent e)->{
       supprimerannonce( QuatreAnnonceTrouve.get(1).getId()) ; 
       });
        } else {
            box2.setVisible(false);
        }
        ///////////////////////////////////////////////////////////

        if ( QuatreAnnonceTrouve.size() >= 3) {
       box3.setVisible(true);
 
        Utilisateur u = utilisateurservice.rechercher( QuatreAnnonceTrouve.get(2).getId_utilisateur());
       nom3.setText(u.getNom()+" "+u.getPrenom());
       mail3.setText(u.getEmail());
       tel3.setText(String.valueOf(u.getNumero()));
       adr3.setText( QuatreAnnonceTrouve.get(2).getLieu_trouve());
       datedepo3.setText(String.valueOf( QuatreAnnonceTrouve.get(2).getDate_trouvee()).substring(0, 10));
       type3.setText( QuatreAnnonceTrouve.get(2).getType());
       Image im = new Image ("http://localhost/paw_web/web/images/pawLostFound/" + QuatreAnnonceTrouve.get(2).getImages().getName());
       imageanimal3.setFitHeight(100);
       imageanimal3.setFitWidth(100);
       imageanimal3.setImage(im);
       
       
       modifier3.setOnAction((ActionEvent e) -> {
                    modifierannonce( QuatreAnnonceTrouve.get(2),u);
                    valider.setOnAction((ActionEvent b) ->{
                             modifier( QuatreAnnonceTrouve.get(2).getId());
                            });
                });
        supprimer3.setOnAction((ActionEvent e)->{
       supprimerannonce( QuatreAnnonceTrouve.get(2).getId()) ; 
       });
        } else {
            box3.setVisible(false);
        }
        ///////////////////////////////////////////////////////////
        
        if ( QuatreAnnonceTrouve.size() >= 4) {
            box4.setVisible(true);
            //System.out.println(QuatreAnnoncePerdus.get(0).getId_utilisateur());
        Utilisateur u = utilisateurservice.rechercher( QuatreAnnonceTrouve.get(3).getId_utilisateur());
       nom4.setText(u.getNom()+" "+u.getPrenom());
       mail4.setText(u.getEmail());
       tel4.setText(String.valueOf(u.getNumero()));
       adr4.setText( QuatreAnnonceTrouve.get(3).getLieu_trouve());
       datedepo4.setText(String.valueOf( QuatreAnnonceTrouve.get(3).getDate_trouvee()).substring(0, 10));
       type4.setText( QuatreAnnonceTrouve.get(3).getType());
       Image im = new Image ("http://localhost/paw_web/web/images/pawLostFound/" + QuatreAnnonceTrouve.get(3).getImages().getName());
       imageanimal4.setFitHeight(100);
       imageanimal4.setFitWidth(100);
       imageanimal4.setImage(im);

        modifier4.setOnAction((ActionEvent e) -> {
                   modifierannonce( QuatreAnnonceTrouve.get(3),u);
                  valider.setOnAction((ActionEvent b) ->{
                             modifier( QuatreAnnonceTrouve.get(3).getId());
                            });
                });

         supprimer4.setOnAction((ActionEvent e)->{
       supprimerannonce( QuatreAnnonceTrouve.get(3).getId()) ; 
       });} else {
            box4.setVisible(false);
        }
         


    }
    
    
    
    
    
    
    
    
    
    
    
    
   
    
    @FXML
    private void retour(ActionEvent event) {
         consulterAnnonce.setVisible(false);
           
    }

    private void modifierannonce(AnnonceTrouvee a, Utilisateur u) {
     
             sexModification.setText(a.getSex());
             choixModification.setValue(a.getType());
             lieuModification.setText(a.getLieu_trouve());
             msgModification.setText(a.getMessage_complementaire());
             raceModification.setText(a.getRace());
             couleurModification.setText(a.getCouleur());
             colierModification.setText(a.getColier());
             ageModification.setText(String.valueOf(a.getAge()));
            consulterAnnonce.setVisible(true);     
            
    
    }

    private void modifier(int id) {
       if ((!"".equals(couleurModification.getText()))&& (!"".equals(ageModification.getText()))&& (!"".equals(sexModification.getText()))
                 && (!"".equals(raceModification.getText()))&& (!"".equals(msgModification.getText()))
                    && (!"".equals(choixModification.getValue()))&& (!"".equals(colierModification.getText()))
                    
                    
                    && (!"".equals(lieuModification.getText())))
        {
            AnnonceTrouveServices as = new AnnonceTrouveServices();
            
           as.updateAnnonceTrouvee(
                   new AnnonceTrouvee(colierModification.getText(), null, lieuModification.getText(), id, Integer.parseInt(ageModification.getText()), couleurModification.getText(), sexModification.getText(), raceModification.getText(), msgModification.getText(), choixModification.getValue(), null, 0));
           list=as.getMesAnnoncesTrouve(session.getId()) ; 
            
            if (list.isEmpty()) {
            box1.setVisible(false);
            box2.setVisible(false);
            box3.setVisible(false);
            box4.setVisible(false);
        } else {
            setNbPages();
            initAnnonceTrouvePage(0);
        }
            
        

                   
        }
    }

    private void supprimerannonce(int id) {
                AnnonceTrouveServices as = new AnnonceTrouveServices();
                as.DeleteAnnonceTrouvee(id);
                     consulterAnnonce.setVisible(false);
        AnnonceTrouveServices service = new AnnonceTrouveServices();
      
             
        list = service.getMesAnnoncesTrouve(session.getId());
        
        if (list.isEmpty()) {
            box1.setVisible(false);
            box2.setVisible(false);
            box3.setVisible(false);
            box4.setVisible(false);
        } else {
            setNbPages();
            initAnnonceTrouvePage(0);
        }
        
        
        
    }

   
}
