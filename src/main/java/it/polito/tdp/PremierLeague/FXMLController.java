/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Adiacenza;
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
    	String goalS=this.txtGoals.getText();
    	double goal=0.0;
    	try {
    		goal=Double.parseDouble(goalS);
    	}catch(NumberFormatException e)
    	{
    		e.printStackTrace();
    	}
    	if(goalS==null)
    	{
    		txtResult.appendText("inserire testo\n");
    	}else
    	{
    		this.model.creaGrafo(goal);
    		txtResult.appendText("GRAFO CREATO!\n");
    		txtResult.appendText("# vertici: "+this.model.getVertici()+"\n");
    		txtResult.appendText("# archi: "+this.model.getArchi()+"\n");
    	}
    }

    @FXML
    void doDreamTeam(ActionEvent event) {
    	txtResult.clear();
    	String KS=this.txtK.getText();
    	Integer K=0;
    	try {
    		K=Integer.parseInt(KS);
    	}catch(NumberFormatException e)
    	{
    		e.printStackTrace();
    	}
    	if(KS==null)
    	{
    		txtResult.appendText("inserire un valore\n");
    	}else
    	{
    		List<Player> best=new ArrayList<Player>(this.model.percorsoBest(K));
    		txtResult.appendText("IL PERCORSO CHE MASSIMIZZA IL GRADO DI TITOLARITA' DI : "+this.model.gradoTit+" E' FORMATO DA:\n");
    		for(Player p:best)
    		{
    			txtResult.appendText(p.toString()+"\n");
    		}
    	}
    	
    }

    @FXML
    void doTopPlayer(ActionEvent event) {

    	txtResult.clear();
    	Player p=this.model.topPlayer();
    	List<Adiacenza> battuti=new ArrayList<Adiacenza>(this.model.battuti(p));
    	txtResult.appendText("IL TOP PLAYER E': "+p.toString()+" E HA BATTUTO:\n");
    	for(Adiacenza a:battuti)
    	{
    		txtResult.appendText(a.getP2()+" - "+a.getPeso()+"\n");
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
