/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Conseil;
import Utility.DbHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author gmehd
 */
public class ConseilServices {
    protected Connection connection;
    private DbHandler handler;
    
    public ConseilServices (){
        handler = DbHandler.getDBHandler();
        connection =handler.getConnection();
    }
    public void insererConseil (Conseil p)
    {
        String req="INSERT INTO Conseil (titre,animal,type,description,date) VALUES(?,?,?,?,now())" ; 
        try { 
            PreparedStatement ste = connection.prepareStatement(req) ;
            ste.setString(1,p.getTitre()) ;
            ste.setString(2,p.getAnimal()) ;
            ste.setString(3,p.getType()) ; 
            ste.setString(4,p.getDescription()) ; 
            
            ste.executeUpdate() ; 
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    
    }
    
    public ArrayList<Conseil> getList() {
        String req="SELECT * FROM Conseil" ;
        ArrayList<Conseil> list = new ArrayList();
        try 
        { 
            PreparedStatement ste = connection.prepareStatement(req) ;
            ResultSet rs = ste.executeQuery(); 

            while (rs.next())
            {

                int id = rs.getInt("id");
                String titre = rs.getString("titre");
                String animal = rs.getString("animal");
                String type = rs.getString("type");
                String description = rs.getString("description");
                Date date = rs.getDate("date");

                list.add(new Conseil(id, titre, animal, type, description, date));
            }

        } catch (SQLException ex) {
            System.out.println("Problème importation liste Conseil");
        }
        return list;
        
    }
    
    public ObservableList<Conseil> getAll(){
        String req="SELECT * FROM Conseil" ;
        ObservableList<Conseil> list = FXCollections.observableArrayList();
        try 
        { 
            PreparedStatement ste = connection.prepareStatement(req) ;
            ResultSet rs = ste.executeQuery(); 

            while (rs.next())
            {

                int id = rs.getInt("id");
                String titre = rs.getString("titre");
                String animal = rs.getString("animal");
                String type = rs.getString("type");
                String description = rs.getString("description");
                Date date = rs.getDate("date");

                list.add(new Conseil(id, titre, animal, type, description, date));
            }

        } catch (SQLException ex) {
            System.out.println("Problème importation liste Conseil");
        }
        return list;
    }
     
    public void updateConseil (Conseil p)
    {
    String req="UPDATE Conseil SET titre=?,animal=?,type=?,description=?,date=now() WHERE id =?" ; 
        try { 
            PreparedStatement ste = connection.prepareStatement(req) ;
             
            ste.setString(1,p.getTitre()) ;
            ste.setString(2,p.getAnimal()) ;
            ste.setString(3,p.getType()) ; 
            ste.setString(4,p.getDescription()) ; 
            
            ste.setInt(5,p.getId()) ;
            ste.executeUpdate() ; 
            
        } catch (SQLException ex) {
            System.out.println("Problème update Conseil");
        }
    
    }
    
     public void DeleteConseil (int id )
    {
    String req="DELETE  from Conseil where  id =?" ; 
        try { 
            PreparedStatement ste = connection.prepareStatement(req) ;
             
            
            ste.setInt(1,id) ;
            ste.executeUpdate() ; 
            
        } catch (SQLException ex) {
            System.out.println("Problème delete Conseil");
        }
    
      }
     
       public int nombre() {
        int y = 0;
        String sql = "SELECT count(*) as nbr FROM `conseil`";
        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                y = results.getInt("nbr");
            }
        } catch (SQLException ex) {
            System.out.println("erreur affichage nombre");
        }
        return y;
    }
    
}
