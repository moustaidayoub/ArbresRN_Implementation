/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import arbre_implementation.ArbreRN;
import arbre_implementation.Noeud;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Ayoub MOUSTAID
 */
public class ArbreViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    ArbreRN arn;
    @FXML
    private Button addBtn;

    @FXML
    private Button modifyBtn;
    
    @FXML
    private Button deleteBtn;

    @FXML
    private Pane zone;

    @FXML
    private TextField value;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        arn=new ArbreRN();
    }    
    @FXML
     public void add() {
            arn.ajout(Integer.parseInt(value.getText()));
            paintArbre(arn);  
        }
         @FXML
    public void search() {
        paintArbre(arn);
        int x = (int) (zone.getWidth() / 2);
        int y = 50;
        int searched = Integer.parseInt(value.getText());
        searchNode(arn.getRacine(),searched,x,y);
    }
     private void searchNode(Noeud node,int searched,int x,int y) {
        if (node.getInfo() == null) {
             JOptionPane.showMessageDialog(null, "Non trouvé !");
        } else {
            int info = Integer.parseInt(node.getInfo().toString());
            if (info == searched) {
                zone.getChildren().add(new Circle(x, y, 25, Color.GREEN));
               createText(info,Color.YELLOW,x,y);
            } else if (info > searched) {
                searchNode(node.getGauche(), searched, x - 45 * node.getGauche().getHauteur(), y + 50);
            } else if (info < searched) {
                searchNode(node.getDroit(), searched, x + 45 * node.getDroit().getHauteur(), y + 50);
            }
        }

    }
     private void createText(int info,Color c,int x,int y)
     {
      Text text = new Text("" + info);
                text.setFill(c);
               text.setTranslateX(x);
            text.setTranslateY(y);
               zone.getChildren().add(text);
     }
         @FXML
    private void delete() {
            arn.supprimer(Integer.parseInt(value.getText()));
            paintArbre(arn);
    }
     
         public void paintArbre(ArbreRN arn) {
        zone.getChildren().clear();
        int x = (int) (zone.getWidth() / 2);
        int y=50;
        paintLine(arn.getRacine(), x, y, -1, -3);
        paintCircle(arn.getRacine(), x, y);
    }
             public void paintLine(Noeud node, int x, int y, int x2, int y2) {
        if (node.getInfo() != null) {
            if (y2 > 0) 
                zone.getChildren().add(new Line(x, y, x2, y2));
            paintLine(node.getGauche(), x - 45 * node.getGauche().getHauteur(), y + 50, x, y);
            paintLine(node.getDroit(), x + 45 * node.getDroit().getHauteur(), y + 50, x, y);
        }
    }
             
                 public void paintCircle(Noeud node, int x, int y) {
        if (node.getInfo() != null) {
            zone.getChildren().add(new Circle(x, y, 25, node.getCouleur()));
            createText(Integer.parseInt(node.getInfo().toString()),Color.YELLOW,x,y);
            paintCircle(node.getGauche(), x - 45 * node.getGauche().getHauteur(), y + 50);
            paintCircle(node.getDroit(), x + 45 * node.getDroit().getHauteur(), y + 50);
        }
    }
      
 
    
}
