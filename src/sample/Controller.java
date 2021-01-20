package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller {

    @FXML TextField filamentPrice;
    @FXML TextField modelWeight;
    @FXML Text pricePerGramText;
    @FXML Text materialCostTotal;
    @FXML ComboBox<Integer> filamentWeight;

    @FXML
    public void initialize() {
        //Initialize the ComboBox with set integers of weights
        filamentWeight.getItems().removeAll(filamentWeight.getItems());
        filamentWeight.getItems().addAll(250,500,1000,3000); //Input range of common filament weights
        filamentWeight.getSelectionModel().select(2); //Defaults 1000 grams
    }

    @FXML
    public double CalculatePricePerGram() {
        double pricePerGram = 0;
        double price = 0;

        int weight = filamentWeight.getValue(); //Get the value of the weight int

        if (filamentPrice != null && !filamentPrice.getText().isEmpty()) {
            price = Double.parseDouble(filamentPrice.getText()); //Get Price from text to double

            pricePerGram = price/weight; //Get the price per gram of the filament

            pricePerGramText.setText("€" + Double.toString(pricePerGram) ); //Set the text of the price per gram
        }

        return pricePerGram;
    }

    @FXML
    public double CalculcateMaterialCost() {
        
    }

}
