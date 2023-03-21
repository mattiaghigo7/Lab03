package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;
import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;

public class FXMLController {

	private Dictionary model;
	
	@FXML
    private Button btnSpell;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cmbLanguage;

    @FXML
    private Label txtError;

    @FXML
    private TextArea txtInsert;

    @FXML
    private TextArea txtResult;

    @FXML
    private Label txtSpeed;

    @FXML
    void doClearText(ActionEvent event) {
    	this.txtInsert.clear();
    	this.txtResult.clear();
    	this.txtError.setText("");
    	this.txtSpeed.setText("");
    	this.model.clearDictionary();
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	long startTime = System.nanoTime();
    	String s = this.cmbLanguage.getValue();
    	this.model.loadDictionary(s);
    	if (this.txtInsert.getText().equals("")) {
    		this.txtError.setText("Inserire un testo");
    	}
    	else {
	    	List<String> l = new LinkedList<String>();
	    	String frase = this.txtInsert.getText();
	    	String minuscola = frase.toLowerCase();
	    	String finale = minuscola.replaceAll("[.,\\/#!?$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", "");
	    	String array[];
			array = finale.split(" ");
			for (int i=0;i<array.length;i++) {
				l.add(array[i]);
			}
			List<RichWord> l2 = this.model.spellCheckTextDicotomica(l);
			int cont = 0;
			for (int i=0;i<l2.size();i++) {
				if(l2.get(i).isCorretta()==false) {
					this.txtResult.appendText(l2.get(i).getParola()+"\n");
					cont++;
				}
			}
			this.txtError.setText("The text contains "+cont+" errors");
			long endTime = System.nanoTime();
			this.txtSpeed.setText("Spell check completed in "+((endTime-startTime)/1000000000)+" seconds");
    	}
    }

    @FXML
    void selectLanguage(ActionEvent event) {
    	this.btnSpell.setDisable(false);
    }
    
    @FXML
    void initialize() {
        assert cmbLanguage != null : "fx:id=\"cmbLanguage\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtError != null : "fx:id=\"txtError\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtInsert != null : "fx:id=\"txtInsert\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtSpeed != null : "fx:id=\"txtSpeed\" was not injected: check your FXML file 'Scene.fxml'.";
        this.cmbLanguage.getItems().add("English");        
        this.cmbLanguage.getItems().add("Italian");
    }

    public void setModel(Dictionary model) {
    	this.model=model;
    }
}
