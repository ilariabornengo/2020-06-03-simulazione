/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Battuti;
import it.polito.tdp.PremierLeague.model.Model;

import it.polito.tdp.PremierLeague.model.Player;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnTopPlayer"
    private Button btnTopPlayer; // Value injected by FXMLLoader

    @FXML // fx:id="btnDreamTeam"
    private Button btnDreamTeam; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="txtGoals"
    private TextField txtGoals; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String xs=this.txtGoals.getText();
    	double x=0.0;
    	try {
    		x=Double.parseDouble(xs);
    	}catch(NumberFormatException e)
    	{
    		e.printStackTrace();
    	}
    	this.model.creaGrafo(x);
    	txtResult.appendText("GRAFO CREATO!\n");
    	txtResult.appendText("# vertici: "+this.model.getVertci()+"\n");
    	txtResult.appendText("# archi: "+this.model.getArchi()+"\n");
    }

    @FXML
    void doDreamTeam(ActionEvent event) {
    	txtResult.clear();
    	String ks=this.txtK.getText();
    	Integer k=0;
    	try {
    		k=Integer.parseInt(ks);
    	}catch(NumberFormatException e)
    	{
    		e.printStackTrace();
    	}
    	List<Player> ricorsione=new ArrayList<Player>(k);
    	for(Player p:ricorsione)
    	{
    		txtResult.appendText(p.toString()+"\n");
    	}
    	
    }

    @FXML
    void doTopPlayer(ActionEvent event) {

    	txtResult.clear();
    	Player best=this.model.getMigliore();
    	List<Battuti> listaBest=new ArrayList<Battuti>(this.model.battuti(best));
    	txtResult.appendText("IL TOP PLAYER E': "+best.toString()+"\n");
    	txtResult.appendText("I GIOCATORI BATTUTI IN ORDINE DI PESO DECRESCENTE SONO:\n");
    	for(Battuti b:listaBest)
    	{
    		txtResult.appendText(b.toString()+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnTopPlayer != null : "fx:id=\"btnTopPlayer\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDreamTeam != null : "fx:id=\"btnDreamTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGoals != null : "fx:id=\"txtGoals\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
