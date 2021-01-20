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

    @FXML TextField printingTime;
    @FXML ComboBox<String> printerSelect;
    @FXML Text printerOperationTotalText;

    @FXML
    public void initialize() {
        //Initialize the ComboBox for filament weights with set integers of weights
        filamentWeight.getItems().removeAll(filamentWeight.getItems());
        filamentWeight.getItems().addAll(250,500,1000,3000); //Input range of common filament weights
        filamentWeight.getSelectionModel().select(2); //Defaults 1000 grams

        //Initialize the ComboBox for printer Selection with the required printers will be updated
        //Down the line to add more printers for now only 1 hard coded printer
        printerSelect.getItems().removeAll(printerSelect.getItems());
        printerSelect.getItems().addAll("Ender 3");
        printerSelect.getSelectionModel().select(0); //Default to main printer
    }

    //Calculate the price per gram of filament
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

    //Calculate the overall cost for this part
    @FXML
    public double CalculcateMaterialCost() {
        int weight = 0;
        double totalMaterialCost = 0;

        if(modelWeight!= null && !modelWeight.getText().isEmpty()) {
            weight = Integer.parseInt(modelWeight.getText());

            totalMaterialCost = CalculatePricePerGram() * weight;

            materialCostTotal.setText("€" + Double.toString(totalMaterialCost));
        }
        return totalMaterialCost;
    }

    //Calculate the cost to pay off the printer
    @FXML
    public double PrinterCost() {
        double printOperationCost = 0;

        double printerFee = 0; //The amount per hour to recoup costs of printer in a given time frame

        if(printerSelect.getValue().equals("Ender 3")) {
            printerFee = 0.12; //Price per hour to pay back printer in 3 months
        }

        if(printingTime != null && !printingTime.getText().isEmpty()) {
            double printingHours = ConvertTimeToDecimal(Double.parseDouble(printingTime.getText()));

            printOperationCost = printerFee * printingHours;

            printerOperationTotalText.setText("€" + Double.toString(printOperationCost));
        }

        return printOperationCost;
    }

    public double ConvertTimeToDecimal(double time) {
        int hours = (int)time;
        double minutes = (((time - hours) * 100) / 60);

        time = hours + minutes;

        System.out.println(time);

        return time;
    }

}
