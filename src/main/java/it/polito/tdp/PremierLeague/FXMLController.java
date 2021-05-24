/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Opponent;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.TopPlayer;
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

    	String numG=this.txtGoals.getText();
    	Double numGI=0.0;
    	try {
    		numGI=Double.parseDouble(numG);
    	}catch(NumberFormatException e)
    	{
    		e.printStackTrace();
    	}
    	this.model.creaGrafo(numGI);
    	this.txtResult.appendText("GRAFO CREATO");
    	
    }

    @FXML
    void doDreamTeam(ActionEvent event) {
    	this.txtResult.clear();
    	String numG=this.txtGoals.getText();
    	Double numGI=0.0;
    	String nGiocatori=this.txtK.getText();
    	int nGiocatoriI=0;
    	try {
    	nGiocatoriI=Integer.parseInt(nGiocatori);	
    	numGI=Double.parseDouble(numG);
    	}catch(NumberFormatException e)
    	{
    		e.printStackTrace();
    	}
    	this.model.creaGrafo(numGI);
    	List<Player> dreamTeam=this.model.getDreamteam(nGiocatoriI);
    	this.txtResult.appendText("IL DREAMTEAM HA UN GRADO DI TITOLARITA' DI:"+this.model.getGradoTitolarita(dreamTeam)+"\n");
    	this.txtResult.appendText("IL DREAMTEAM E' COMPOSTO DA: \n");
    	for(Player p:dreamTeam)
    	{
    		this.txtResult.appendText(p.toString()+"\n");
    	}
    	
    	
    }

    @FXML
    void doTopPlayer(ActionEvent event) {

    	txtResult.clear();
    	TopPlayer top=this.model.getBestPlayer();
    	txtResult.appendText("IL TOP SCORER PER MINUTI GIOCATI E': "+top.getTopPlayer().toString()+"\n");
    	txtResult.appendText("GIOCATORI BATTUTI IN ORDINE DI PERMANENZA IN CAMPO DECRESCENTE\n");
    	for(Opponent o:top.getOpponents())
    	{
    		txtResult.appendText(o.toString()+"\n");
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
