package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;  // Import the File class
import java.io.IOException;

public class Controller {

    @FXML TextField filamentPrice;
    @FXML TextField modelWeight;
    @FXML Text pricePerGramText;
    @FXML Text materialCostTotal;
    @FXML ComboBox<Integer> filamentWeight;

    @FXML TextField printingTime;
    @FXML ComboBox<String> printerSelect;
    @FXML Text printerOperationTotalText;

    @FXML TextField manHours;
    @FXML Text manPowerTotalCost;

    @FXML Text electricityTotalCost;


    private FilamentList filamentList = new FilamentList();
    public AddFilamentController addFilamentController = new AddFilamentController();

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


    @FXML Slider materialPercentageMarkupSlider;
    @FXML Text materialPercentageMarkupText;
    @FXML CheckBox useFilamentDatabase;

    //Handle the percentage filament markup by getting the value and adding 1.
    @FXML
    public double HandleFilamentPercentageMarkUp() {
        double filamentPercentageMarkUp = 1.0;

        System.out.println(Math.ceil(materialPercentageMarkupSlider.getValue()));

        filamentPercentageMarkUp = 1 + ((Math.ceil(materialPercentageMarkupSlider.getValue()))/100);
        materialPercentageMarkupText.setText("" + Math.ceil(materialPercentageMarkupSlider.getValue()) + "%");

        return filamentPercentageMarkUp;
    }

    //Calculate the price per gram of filament
    @FXML
    public double CalculatePricePerGram(double weight, double price) {
        double pricePerGram = 0;

        if(useFilamentDatabase.isSelected() && weight != 0 && price != 0) {
            pricePerGram = (price/weight) * HandleFilamentPercentageMarkUp();
            pricePerGramText.setText("€" + pricePerGram); //Set the text of the price per gram
        }
        return pricePerGram;
    }

    //Calculate the price per gram of filament
    @FXML
    public double CalculatePricePerGram() {
        double pricePerGram = 0;

        if (filamentPrice != null && !filamentPrice.getText().isEmpty() && filamentWeight != null) {
            pricePerGram = (Double.parseDouble(filamentPrice.getText())/filamentWeight.getValue()) * HandleFilamentPercentageMarkUp(); //Get the value of the weight int; //Get the price per gram of the filament

            pricePerGramText.setText("€" + pricePerGram); //Set the text of the price per gram
        }
        return pricePerGram;
    }

    //Calculate the overall cost for this part
    @FXML
    public double CalculcateMaterialCost() {
        double totalMaterialCost = 0;

        if(modelWeight!= null && !modelWeight.getText().isEmpty() && useFilamentDatabase.isSelected()) {

            totalMaterialCost = HelperClass.round(CalculatePricePerGram(fWeight,fPrice) * Double.parseDouble(modelWeight.getText()),2);

            materialCostTotal.setText("€" + totalMaterialCost);

            CalculateTotalCost(totalMaterialCost);
        }
        else if(modelWeight!= null && !modelWeight.getText().isEmpty()) {
            totalMaterialCost = HelperClass.round(CalculatePricePerGram() * Double.parseDouble(modelWeight.getText()),2);

            materialCostTotal.setText("€" + totalMaterialCost);

            CalculateTotalCost(totalMaterialCost);
        }
        return totalMaterialCost;
    }

    private double fWeight = 0;
    private double fPrice = 0;

    @FXML
    public void HandleMaterialCalculationsComboBox() { // get current selected filament from combo box and set weight and price values

        if(useFilamentDatabase.isSelected()) {
            System.out.println(selectFilament.getValue());
            fWeight = selectFilament.getValue().getWeight();
            fPrice = selectFilament.getValue().getPrice();

            CalculatePricePerGram(fWeight,fPrice);
        }
    }

    //Calculate the cost to pay off the printer
    @FXML
    public double PrinterCost() {
        double printOperationCost = 0;

        double printerFee = 0; //The amount per hour to recoup costs of printer in a given time frame

        if(printerSelect.getValue().equals("Ender 3")) {
            printerFee = 0.12; //Price per hour to pay back printer in 3 months HARD CODED 0.12 for ender 0.185 for mars 2 pro
        }

        if(printingTime != null && !printingTime.getText().isEmpty()) {
            double printingHours = ConvertTimeToDecimal(Double.parseDouble(printingTime.getText()));

            printOperationCost = HelperClass.round(printerFee * printingHours, 2);

            printerOperationTotalText.setText("€" + printOperationCost);

            ElectricityCost();
        }

        return printOperationCost;
    }

    @FXML
    public double ManHourCosts() {
        double manHourCost = 0;

        if(manHours != null && !manHours.getText().isEmpty()) {
            double amountOfHours = ConvertTimeToDecimal(Double.parseDouble(manHours.getText()));

            manHourCost = amountOfHours * 12; //12 is the wage per hour needs to be editable currently hard coded

            manPowerTotalCost.setText("€" + manHourCost);

        }
        return manHourCost;
    }

    public double kWhPrice = 0.1753; //Price per kW
    public double printerKWhUsage = 0.16; //How much kWh printer uses per hour

    //Calculate the electricty costs
    @FXML
    public double ElectricityCost() {
        double totalElectricCost = 0;
        double electricCostPerHour = kWhPrice * printerKWhUsage;

        totalElectricCost = HelperClass.round(electricCostPerHour * ConvertTimeToDecimal(Double.parseDouble(printingTime.getText())),2);

        electricityTotalCost.setText("€" + totalElectricCost);

        return totalElectricCost;
    }


    //Convert decimal time to decimal. 1 hour 30 mins = 1.30 in app which converts to 1.5 hours
    public double ConvertTimeToDecimal(double time) {
        int hours = (int)time;
        double minutes = (((time - hours) * 100) / 60);

        time = hours + minutes;

        System.out.println(time);

        return time;
    }

    @FXML Text totalText;
    @FXML Button totalButton;

    public void TotalCostButton() {
        CalculateTotalCost(CalculcateMaterialCost(),PrinterCost(),ManHourCosts(),ElectricityCost());
    }

    public void CalculateTotalCost(double materialCost) {
        double totalCost = 0.0;
        totalCost = materialCost;
        totalText.setText("" + totalCost);
    }


    //Calculate total price for the print
    public void CalculateTotalCost(double materialCost, double printerCost, double manHourCost, double electricityCost) {
        double totalCost = 0.0;
        totalCost = HelperClass.round((materialCost + printerCost + manHourCost + electricityCost),2);
        totalText.setText("" + totalCost);
    }

    @FXML
    public void OpenNewFilamentWindow() {
        Parent root;
        try {
            //mainWindow=primaryStage;
            root = FXMLLoader.load(getClass().getResource("AddFilament.fxml"));
            Stage stage = new Stage();
            stage.setTitle("3D Printer Price Calculator");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    ComboBox <Filament> selectFilament;

    @FXML
    Button updateFilament;

    @FXML
    public void UpdateFilamentComboBox() {
//        System.out.println(filamentList.getFilament(0).toString());
//        selectFilament.setItems(filamentList.getFilamentObservableList());

    }







}
