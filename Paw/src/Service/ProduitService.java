/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Produit;
import Utility.DbHandler;
import Utility.Checksum;
import com.mysql.jdbc.StringUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author vinga
 */
public class ProduitService {
    
    final private DbHandler handler;
    protected Connection connection;
    private static ProduitService produitservice;
   
    
    public static ProduitService getProduitService() {
        if (ProduitService.produitservice == null) {
            produitservice = new ProduitService();
        }
         return produitservice;
     }
    
    
    private ProduitService() {
        handler = DbHandler.getDBHandler();
        connection =handler.getConnection();
    }
    
    public void addProduit(Produit p,List<File> files)
    {
        p.setImage1(imageSave(files.get(0)));
        p.setImage2( imageSave(files.get(1)));
        String req="INSERT INTO `produit` (`libelle`,`prix`,`quantite`,`description`,`type`,`image1`,`image2`) VALUES(?,?,?,?,?,?,?)" ; 
        try { 
            PreparedStatement ste = connection.prepareStatement(req) ;
            ste.setString(1,p.getLibelle()) ;
            ste.setFloat(2,p.getPrix());
            ste.setInt(3,p.getQuantite()) ; 
            ste.setString(4,p.getDescription()) ; 
            ste.setString(5,p.getType()) ; 
            ste.setString(6,p.getImage1()) ;
            ste.setString(7,p.getImage2()) ;
            ste.executeUpdate() ; 
        } catch (SQLException ex) {
            System.out.println("Problème insertion Produit");
        }
        
        
    }
    
    
    public void deleteProduit (int id )
    {
    String req=" update `produit`  set quantite = '0' where  id =?" ; 
        try { 
            PreparedStatement ste = connection.prepareStatement(req) ;
            ste.setInt(1,id) ;
            ste.executeUpdate() ; 
        }catch (SQLException ex1) {
            System.out.println("Problème delete produit");
        }
    
    }
    public void updateImage(File f,int index,int id)
    {
        
        Produit p =rechercher(id);
        String images="";
        
        if (index ==0)
        {
          String filePath = "E:\\xampp\\htdocs\\paw_web\\web\\images\\pawBoutique\\" +p.getImage1();
          deleteImage(new File(filePath));
          p.setImage1(imageSave(f));
             String req="UPDATE `produit` set image1=?  WHERE id =?" ; 
        try { 
            PreparedStatement ste = connection.prepareStatement(req) ;
            ste.setString(1,p.getImage1()) ;
            ste.setInt(2,id) ;
            ste.executeUpdate() ;
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
          
        }
        else if(index==1)
        {
            String filePath = "E:\\xampp\\htdocs\\paw_web\\web\\images\\pawBoutique\\" +p.getImage2();
            deleteImage(new File(filePath));
              p.setImage2(imageSave(f));
              
                 String req="UPDATE `produit` set image2=?  WHERE id =?" ; 
        try { 
            PreparedStatement ste = connection.prepareStatement(req) ;
            ste.setString(1,p.getImage2()) ;
            ste.setInt(2,id) ;
            ste.executeUpdate() ;
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
            
        }
     
    
    }

     public void updatelibelle (String valeur,int id)
    {
    String req="UPDATE `produit` SET libelle=? WHERE id =?" ; 
        try { 
            PreparedStatement ste = connection.prepareStatement(req) ;
            ste.setString(1,valeur) ;
            ste.setInt(2,id) ;
            ste.executeUpdate() ; 
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    
    }    
     public void updatedescription (String valeur,int id)
    {
    String req="UPDATE `produit` SET description=? WHERE id =?" ; 
        try { 
            PreparedStatement ste = connection.prepareStatement(req) ;
            ste.setString(1,valeur) ;
            ste.setInt(2,id) ;
            ste.executeUpdate() ; 
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    
    } 
        public void updatetype (String valeur,int id)
    {
    String req="UPDATE `produit` SET type=? WHERE id =?" ; 
        try { 
            PreparedStatement ste = connection.prepareStatement(req) ;
            ste.setString(1,valeur) ;
            ste.setInt(2,id) ;
            ste.executeUpdate() ; 
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    
    }      
     public void updateprix(Float valeur,int id)
    {
    String req="UPDATE `produit` SET prix=? WHERE id =?" ; 
        try { 
            PreparedStatement ste = connection.prepareStatement(req) ;
             
            ste.setFloat(1,valeur) ;
            ste.setInt(2,id) ;
            ste.executeUpdate() ; 
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    
    }
     public void updatequantite (int id,int valeur)
    {
    String req="UPDATE `produit` SET quantite=? WHERE id =?" ; 
        try { 
            PreparedStatement ste = connection.prepareStatement(req) ;
            ste.setInt(1,valeur) ;
            ste.setInt(2,id) ;
            ste.executeUpdate() ; 
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    
    }  
        public ArrayList<Produit> findAll() {
        String sql = "SELECT * FROM `produit` where quantite <> '0'";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             ResultSet results =  statement.executeQuery();
             ArrayList<Produit> produits = new ArrayList<>();
             Produit produit;
             while (results.next()) {
                 produit = new Produit(results.getInt("id"),results.getString("libelle"),results.getFloat("prix"),results.getInt("quantite"),results.getString("description"),results.getString("type"));
                 produit.setImage1(results.getString("image1"));
                 produit.setImage2(results.getString("image2"));
                 produits.add(produit);
             }
             return produits;
         } catch (SQLException ex) {
             System.out.println("erreur affichage produit");
         }
        return null;
    }
        public ArrayList<Produit> findAllAdmin() {
        String sql = "SELECT * FROM `produit`";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             ResultSet results =  statement.executeQuery();
             ArrayList<Produit> produits = new ArrayList<>();
             Produit produit;
             while (results.next()) {
                 produit = new Produit(results.getInt("id"),results.getString("libelle"),results.getFloat("prix"),results.getInt("quantite"),results.getString("description"),results.getString("type"));
                 produit.setImage1(results.getString("image1"));
                 produit.setImage2(results.getString("image2"));
                 produits.add(produit);
             }
             return produits;
         } catch (SQLException ex) {
             System.out.println("erreur affichage produit");
         }
        return null;
    }
         public ArrayList<Produit> findAllup() {
        String sql = "SELECT * FROM `produit` where quantite <> '0' order by prix";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             ResultSet results =  statement.executeQuery();
             ArrayList<Produit> produits = new ArrayList<>();
             Produit produit;
             while (results.next()) {
                 produit = new Produit(results.getInt("id"),results.getString("libelle"),results.getFloat("prix"),results.getInt("quantite"),results.getString("description"),results.getString("type"));
                    produit.setImage1(results.getString("image1"));
                 produit.setImage2(results.getString("image2"));
                 produits.add(produit);
             }
             return produits;
         } catch (SQLException ex) {
             System.out.println("erreur affichage produit");
         }
        return null;
    }
        
         
         
          public ArrayList<Produit> findAlldown() {
        String sql = "SELECT * FROM `produit` where quantite <> '0'  order by prix desc";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             ResultSet results =  statement.executeQuery();
             ArrayList<Produit> produits = new ArrayList<>();
             Produit produit;
             while (results.next()) {
                 produit = new Produit(results.getInt("id"),results.getString("libelle"),results.getFloat("prix"),results.getInt("quantite"),results.getString("description"),results.getString("type"));
                    produit.setImage1(results.getString("image1"));
                 produit.setImage2(results.getString("image2"));
                 produits.add(produit);
             }
             return produits;
         } catch (SQLException ex) {
             System.out.println("erreur affichage produit");
         }
        return null;
    }
         
         public ArrayList<Produit> findAllFiltrer(String filtre) {
        String sql = "SELECT * FROM `produit` where type=? and quantite <> '0'";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             statement.setString(1,filtre);
             ResultSet results =  statement.executeQuery();
             ArrayList<Produit> produits = new ArrayList<>();
             Produit produit;
             while (results.next()) {
                 produit = new Produit(results.getInt("id"),results.getString("libelle"),results.getFloat("prix"),results.getInt("quantite"),results.getString("description"),results.getString("type"));
                    produit.setImage1(results.getString("image1"));
                 produit.setImage2(results.getString("image2"));
                 produits.add(produit);
             }
             return produits;
         } catch (SQLException ex) {
             System.out.println("erreur affichage produit");
         }
        return null;
    }
         
           public ArrayList<Produit> findAllFiltrerup(String filtre) {
        String sql = "SELECT * FROM `produit` where type=? and quantite <> '0' order by prix";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             statement.setString(1,filtre);
             ResultSet results =  statement.executeQuery();
             ArrayList<Produit> produits = new ArrayList<>();
             Produit produit;
             while (results.next()) {
                 produit = new Produit(results.getInt("id"),results.getString("libelle"),results.getFloat("prix"),results.getInt("quantite"),results.getString("description"),results.getString("type"));
                    produit.setImage1(results.getString("image1"));
                 produit.setImage2(results.getString("image2"));
                 produits.add(produit);
             }
             return produits;
         } catch (SQLException ex) {
             System.out.println("erreur affichage produit");
         }
        return null;
    }       
         
            public ArrayList<Produit> findAllFiltrerdown(String filtre) {
        String sql = "SELECT * FROM `produit` where type=? and quantite <> '0' order by prix desc";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             statement.setString(1,filtre);
             ResultSet results =  statement.executeQuery();
             ArrayList<Produit> produits = new ArrayList<>();
             Produit produit;
             while (results.next()) {
                 produit = new Produit(results.getInt("id"),results.getString("libelle"),results.getFloat("prix"),results.getInt("quantite"),results.getString("description"),results.getString("type"));
                    produit.setImage1(results.getString("image1"));
                 produit.setImage2(results.getString("image2"));
                 produits.add(produit);
             }
             return produits;
         } catch (SQLException ex) {
             System.out.println("erreur affichage produit");
         }
        return null;
    }       
    public ArrayList<Produit>  findAllNonDisponisble()
    {
                String sql = "SELECT * FROM `produit` where quantite='0'";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             ResultSet results =  statement.executeQuery();
             ArrayList<Produit> produits = new ArrayList<>();
             Produit produit;
             while (results.next()) {
                 produit = new Produit(results.getInt("id"),results.getString("libelle"),results.getFloat("prix"),results.getInt("quantite"),results.getString("description"),results.getString("type"));
                    produit.setImage1(results.getString("image1"));
                 produit.setImage2(results.getString("image2"));
                 produits.add(produit);
             }
             return produits;
         } catch (SQLException ex) {
             System.out.println("erreur affichage produit");
         }
        return null;
    }
         
         
      public ArrayList<Produit>  findAllNonDisponisbleup()
    {
                String sql = "SELECT * FROM `produit` where quantite='0' order by prix ";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             ResultSet results =  statement.executeQuery();
             ArrayList<Produit> produits = new ArrayList<>();
             Produit produit;
             while (results.next()) {
                 produit = new Produit(results.getInt("id"),results.getString("libelle"),results.getFloat("prix"),results.getInt("quantite"),results.getString("description"),results.getString("type"));
                    produit.setImage1(results.getString("image1"));
                 produit.setImage2(results.getString("image2"));
                 produits.add(produit);
             }
             return produits;
         } catch (SQLException ex) {
             System.out.println("erreur affichage produit");
         }
        return null;
    }    
        
       public ArrayList<Produit>  findAllNonDisponisbledown()
    {
                String sql = "SELECT * FROM `produit` where quantite='0' order by prix desc";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             ResultSet results =  statement.executeQuery();
             ArrayList<Produit> produits = new ArrayList<>();
             Produit produit;
             while (results.next()) {
                 produit = new Produit(results.getInt("id"),results.getString("libelle"),results.getFloat("prix"),results.getInt("quantite"),results.getString("description"),results.getString("type"));
                   produit.setImage1(results.getString("image1"));
                 produit.setImage2(results.getString("image2"));
                 produits.add(produit);
             }
             return produits;
         } catch (SQLException ex) {
             System.out.println("erreur affichage produit");
         }
        return null;
    }
      
      
        public List<File> getFiles(String path)
        {
            List<File> res = new ArrayList<>();
            List<String>list = StringUtils.split(path,";",true);
            list.stream().map((p) -> new File(p)).forEachOrdered((file) -> {
                res.add(file);
        });
           
            return res;
        }
        
        public String imageSave(File file) {
           
        try {
            String imageName = Checksum.createChecksum(file.getAbsolutePath());
            String extension = file.getName().substring(file.getName().lastIndexOf("."), file.getName().length());
            String filePath = "E:\\xampp\\htdocs\\paw_web\\web\\images\\pawBoutique\\" + imageName + extension;
            File dest = new File(filePath);
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return imageName + extension;
        } catch (Exception ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public Produit rechercher(int id)
    {
        Produit produit =null;
        String sql = "SELECT * FROM `produit` where id=?";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
              statement.setInt(1,id) ;
             ResultSet results =  statement.executeQuery();
             
             while (results.next()) {
                 produit = new Produit(results.getInt("id"),results.getString("libelle"),results.getFloat("prix"),results.getInt("quantite"),results.getString("description"),results.getString("type"));
                    produit.setImage1(results.getString("image1"));
                 produit.setImage2(results.getString("image2"));
             }
             
         } catch (SQLException ex) {
             System.out.println("erreur affichage produit");
         }
        return produit;
    }
    
    public void deleteImage(File f)
    {
        try { 
            Files.delete(f.toPath());
            
        } catch (NoSuchFileException x) {
           System.err.format("no such file or directory");
       } catch (DirectoryNotEmptyException x) {
           System.err.format("not empty");
       } catch (IOException x) {
           System.err.println(x); 
    }
    }
    
    
       public int nombreProduit() {
        int y = 0;
        String sql = "SELECT count(*) as nbr FROM `produit`";
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
       
         public int nombreProduitOut() {
        int y = 0;
        String sql = "SELECT count(*) as nbr FROM `produit` where quantite=0";
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
