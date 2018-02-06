/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Achat;
import Entity.LigneAchat;
import Utility.DbHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author vinga
 */
public class AchatService {
    final private DbHandler handler;
    protected Connection connection;
    private static AchatService achatservice;
    
    
    public static AchatService getAchatService() {
        if (AchatService.achatservice == null) {
            achatservice = new AchatService();
        }
         return achatservice;
     }
    
    
    public AchatService() {
        handler = DbHandler.getDBHandler();
        connection =handler.getConnection();
    }
    
    public void addLigneAchat(Achat a)
    {
        String req="INSERT INTO `achat` (`id_client`,`prix`) VALUES(?,?)" ; 
        try { 
            PreparedStatement ste = connection.prepareStatement(req) ;
            ste.setInt(1,a.getId_client()) ;  
            ste.setFloat(2, a.getPrix());
            ste.executeUpdate() ; 
        } catch (SQLException ex) {
            System.out.println("Problème insertion Achat");
        }
        
    }
    
    public void deleteLigneAchat (int id )
    {
    String req="DELETE  `achat` achat where  id =?" ; 
        try { 
            PreparedStatement ste = connection.prepareStatement(req) ;
            ste.setInt(1,id) ;
            ste.executeUpdate() ; 
            
        } catch (SQLException ex) {
            System.out.println("Problème delete LigneAchat");
        }
    
    }
     
        public ArrayList<Achat> findAll() {
        String sql = "SELECT * FROM `ligneachat`";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             ResultSet results =  statement.executeQuery();
             ArrayList<Achat> achats = new ArrayList<>();
             ArrayList<LigneAchat> ligneachats ;
             Achat a;
             while (results.next()) {
                 ligneachats = LigneAchatService.getLigneService().findAll(results.getInt("id_achat"));
                 a = new Achat(results.getInt("id_achat"),results.getInt("id_client"),ligneachats,results.getDate("date_achat"),results.getFloat("prix"));
                 achats.add(a);
             }
             return achats;
         } catch (SQLException ex) {
             System.out.println("erreur affichage LigneAchat");
         }
        return null;
    }
        
}
