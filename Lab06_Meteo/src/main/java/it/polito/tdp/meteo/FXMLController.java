/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.meteo;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.meteo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model m;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxMese"
    private ChoiceBox<String> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnUmidita"
    private Button btnUmidita; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcola"
    private Button btnCalcola; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaSequenza(ActionEvent event) {

    	this.txtResult.clear();
    	switch(boxMese.getValue()) {
    		case "Gennaio":
    		this.txtResult.setText(this.m.trovaSequenza(1));
    		break;
    		case "Febbraio":
    			this.txtResult.setText(this.m.trovaSequenza(2));
        		break;
    		case "Marzo":
    			this.txtResult.setText(this.m.trovaSequenza(3));
        		break;
    		case "Aprile":
    			this.txtResult.setText(this.m.trovaSequenza(4));
        		break;
    		case "Maggio":
    			this.txtResult.setText(this.m.trovaSequenza(5));
        		break;
    		case "Giugno":
    			this.txtResult.setText(this.m.trovaSequenza(6));
        		break;
    		case "Luglio":
    			this.txtResult.setText(this.m.trovaSequenza(7));
        		break;
    		case "Agosto":
    			this.txtResult.setText(this.m.trovaSequenza(8));
        		break;
    		case "Settembre":
    			this.txtResult.setText(this.m.trovaSequenza(9));
        		break;
    		case "Ottobre":
    			this.txtResult.setText(this.m.trovaSequenza(10));
        		break;
    		case "Novembre":
    			this.txtResult.setText(this.m.trovaSequenza(11));
        		break;
    		case "Dicembre":
    			this.txtResult.setText(this.m.trovaSequenza(12));
        		break;
        	default:
        		this.txtResult.setText("Inserisci un mese");
        		break;
    	}

    }

    @FXML
    void doCalcolaUmidita(ActionEvent event) {
    	this.txtResult.clear();
//    	if(this.boxMese.getValue().compareTo("gennaio")==0) {
//    		this.txtResult.setText(m.getUmiditaMedia(1).toString());
//    		
//    	}
    	
    	switch(boxMese.getValue()) {
		case "Gennaio":
		this.txtResult.setText(this.m.getUmiditaMedia(1).toString());
		break;
		case "Febbraio":
			this.txtResult.setText(this.m.getUmiditaMedia(2).toString());
    		break;
		case "Marzo":
			this.txtResult.setText(this.m.getUmiditaMedia(3).toString());
    		break;
		case "Aprile":
			this.txtResult.setText(this.m.getUmiditaMedia(4).toString());
    		break;
		case "Maggio":
			this.txtResult.setText(this.m.getUmiditaMedia(5).toString());
    		break;
		case "Giugno":
			this.txtResult.setText(this.m.getUmiditaMedia(6).toString());
    		break;
		case "Luglio":
			this.txtResult.setText(this.m.getUmiditaMedia(7).toString());
    		break;
		case "Agosto":
			this.txtResult.setText(this.m.getUmiditaMedia(8).toString());
    		break;
		case "Settembre":
			this.txtResult.setText(this.m.getUmiditaMedia(9).toString());
    		break;
		case "Ottobre":
			this.txtResult.setText(this.m.getUmiditaMedia(10).toString());
    		break;
		case "Novembre":
			this.txtResult.setText(this.m.getUmiditaMedia(11).toString());
    		break;
		case "Dicembre":
			this.txtResult.setText(this.m.getUmiditaMedia(12).toString());
    		break;
    	default:
    		this.txtResult.setText("Inserisci un mese");
    		break;
    		}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		// TODO Auto-generated method stub
		this.m=model;
		this.boxMese.getItems().addAll("Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre");
	}
}

