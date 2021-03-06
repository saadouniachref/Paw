/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paw.messagerie.inbox;

import Entity.Messagerie;
import Entity.Utilisateur;
import Service.MessagerieService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static paw.Paw.session;
import paw.messagerie.envoi.Envoi;

/**
 * FXML Controller class
 *
 * @author Guideinfo
 */
public class FXMLView_inboxController implements Initializable {

   @FXML
    private AnchorPane anchorPane;
//    @FXML
//    private JFXButton nouveau;
    private Utilisateur destinataire;
    @FXML
    private TableView<Messagerie> table;
    @FXML
    private TableColumn<Messagerie, Timestamp> date;
    @FXML
    private TableColumn<Messagerie, String> messages;
    @FXML
    private JFXButton supprimer;
    @FXML
    private JFXButton repondre;
    @FXML
    private JFXButton redi;
    private MessagerieService serviceMessagerie = new MessagerieService();
    @FXML
    private JFXListView<HBox> listView;
    @FXML
    private JFXListView<HBox> conversation;

    @FXML
    private JFXTextField contenuMsg;
    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXButton envoyer;
    List<Messagerie> messagesList;
    private int user_id ;
    Map<Utilisateur, Messagerie> messagesMap;

    public void threadTest() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user_id =session.getId();
        
        //initDrawer();
        repondre.setOnAction((e) -> {
            contenuMsg.setVisible(true);
            envoyer.setVisible(true);
        });
        loadMessages();

        new Timer().schedule(
                new TimerTask() {

            @Override
            public void run() {
               System.out.println("running");
                loadMessages();
                if (destinataire == null) {
                    return;
                }
               conversation.getItems().clear();
                // System.out.println(messagesList.size());
                List<Messagerie> newmessagesList = serviceMessagerie.getConversation(user_id, destinataire.getId());
                //  // System.out.println(newmessagesList.size());
                for (int cmpt = 0; cmpt < newmessagesList.size(); cmpt++) {
                    Messagerie msg = newmessagesList.get(cmpt);
                    // // System.out.println(msg.isDeletedSender());
                    if ((msg.isDeletedReciever()) && (msg.getReciever_id() == user_id)) {
                        continue;
                    }
                    if ((msg.isDeletedSender()) && (msg.getSender_id() == user_id)) {
                        continue;
                    }
                    //System.out.println(msg);
                    if (messagesList.contains(msg)) {
                        continue;
                    }
                    HBox hb = new HBox();
                    StackPane p = new StackPane();
                   ImageView i = new ImageView(new Image("/Ressource/images/messageRecieved.png"));
                  i.setFitWidth(257);
                   i.setFitHeight(102);

                    Text text = new Text(msg.getContenuMsg());
                        HBox hb3 = new HBox();
                //    HBox hb2 = new HBox();
                    if (msg.getSender_id() == 1) {
                        hb.setAlignment(Pos.TOP_LEFT);
                        i.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                       i.setEffect(new ColorAdjust(0, 0, 0.42, -1));
                     // hb2.setAlignment(Pos.BOTTOM_RIGHT);
                    } else {
                        hb.setAlignment(Pos.TOP_LEFT);
                        i.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
                     //   hb2.setAlignment(Pos.BOTTOM_RIGHT);
                    }

                  //  p.getChildren().add(i);
                    LocalDateTime datetime = msg.getDateHeureEnvoi().toLocalDateTime();
                    Text dateEnvoi = new Text("Le " + datetime.toLocalDate() + " à " + datetime.toLocalTime());

                   // p.getChildren().add(text);
                   hb.getChildren().add(text);
                //    hb2.getChildren().add(dateEnvoi);

                   hb3.getChildren().add(hb);
            //       hb3.getChildren().add(hb2);

                    p.getChildren().add(hb3);

                    //hb.getChildren().add(p);
                    System.out.println("new message");
                    conversation.getItems().add(0, hb);
                    messagesList.add(msg);
                }
            }
        }, 0, 60000);
    }

    
    public void loadMessages() {
        listView.getItems().clear();
        envoyer.setVisible(false);
        contenuMsg.setVisible(false);
        repondre.setVisible(false);
        supprimer.setVisible(false);
        messagesMap = serviceMessagerie.getDiscussions(user_id);
        for (Map.Entry<Utilisateur, Messagerie> e : messagesMap.entrySet()) {
             System.out.println(e.getKey().getId() + " : " + e.getValue().isDeletedSender());
            if ((e.getValue().isDeletedReciever()) && (e.getValue().getReciever_id() == user_id)) {
                continue;
            }
            if ((e.getValue().isDeletedSender()) && (e.getValue().getSender_id() == user_id)) {
                continue;
            }
            HBox item = new HBox();
            VBox userInfo = new VBox();
            userInfo.setAlignment(Pos.CENTER);
            ImageView image = new ImageView("/Ressource/images/profile.png");
            Separator sp = new Separator(Orientation.VERTICAL);
            image.setFitWidth(50);
            image.setFitHeight(50);
            Label userName = new Label(e.getKey().getNom() + " " + e.getKey().getPrenom());
            userInfo.getChildren().add(image);
            userInfo.getChildren().add(userName);

            VBox messageInfo = new VBox();
            messageInfo.setAlignment(Pos.CENTER);
            messageInfo.setSpacing(20);
            Label dateHeure = new Label(e.getValue().getDateHeureEnvoi().toString());
            Text contenu = new Text(e.getValue().getContenuMsg());
            messageInfo.getChildren().add(dateHeure);
            messageInfo.getChildren().add(contenu);

            item.getChildren().add(userInfo);
            item.getChildren().add(sp);
            item.getChildren().add(messageInfo);

            listView.getItems().add(item);
        }

        listView.setOnMouseClicked((t) -> {
            if (conversation.getItems() != null) {
                conversation.getItems().clear();
            }
            repondre.setVisible(true);
            supprimer.setVisible(true);
            envoyer.setVisible(false);
            contenuMsg.setText("");
            contenuMsg.setVisible(false);
            Utilisateur u = (Utilisateur) messagesMap.keySet().toArray()[listView.getSelectionModel().getSelectedIndex()];
            destinataire = u;
            messagesList = serviceMessagerie.getConversation(user_id, u.getId());
            // // System.out.println(messagesList.size());
            messagesList.forEach((msg) -> {
                // // System.out.println(msg.isDeletedSender());
                if ((msg.isDeletedReciever()) && (msg.getReciever_id() == user_id)) {
                    return;
                }
                if ((msg.isDeletedSender()) && (msg.getSender_id() == user_id)) {
                    return;
                }
                HBox hb = new HBox();
                StackPane p = new StackPane();
               ImageView i = new ImageView(new Image("/Ressource/images/messageRecieved.png"));
               i.setFitWidth(257);
               i.setFitHeight(102);

                Text text = new Text(msg.getContenuMsg());
                HBox hb2 = new HBox();
                if (msg.getSender_id() == 1) {
                    hb.setAlignment(Pos.TOP_LEFT);
                    i.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                    i.setEffect(new ColorAdjust(0, 0, 0.42, -1));
                    hb2.setAlignment(Pos.BOTTOM_LEFT);
                } else {
                    hb.setAlignment(Pos.TOP_RIGHT);
                 i.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
                    hb2.setAlignment(Pos.BOTTOM_RIGHT);
                }

                p.getChildren().add(i);
                LocalDateTime datetime = msg.getDateHeureEnvoi().toLocalDateTime();
                Text dateEnvoi = new Text("Le " + datetime.toLocalDate() + " à " + datetime.toLocalTime());

                p.getChildren().add(text);

                hb2.getChildren().add(dateEnvoi);
                p.getChildren().add(hb2);

                hb.getChildren().add(p);
                conversation.getItems().add(hb);
            });
        });

    }

    public void refreshDiscussion() {

    }
    private ObservableList<Messagerie> data;
   // private MyConnection Cn;

    @FXML
    public void redirection(ActionEvent event) throws IOException {
      
        Envoi e = new Envoi();
        try {
            e.start(new Stage());
        } catch (Exception ex) {
                        System.out.println(ex);
        }
    }

    @FXML
    private void LoadMessage(ActionEvent event) {
        loadMessages();
    }

    @FXML
    void sendMessage(ActionEvent event) {
        Messagerie m = new Messagerie();
        m.setSender_id(user_id);
        m.setReciever_id(destinataire.getId());
        m.setDateHeureEnvoi(Timestamp.valueOf(LocalDateTime.now()));
        m.setContenuMsg(contenuMsg.getText());
        serviceMessagerie.ajouterMessage(m);

        contenuMsg.setText("");
    }

    @FXML
    public void deleteConversation(ActionEvent event) {
        //  // System.out.println("deleting");
        serviceMessagerie.supprimerConversation(user_id, destinataire.getId(), user_id);
        listView.getItems().remove(listView.getSelectionModel().getSelectedIndex());
        listView.getSelectionModel().selectFirst();
        messagesMap.remove(destinataire);
        conversation.getItems().clear();

    }
    
}
