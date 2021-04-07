package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
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

    //Calculate the price per gram of filament
    @FXML
    public double CalculatePricePerGram() {
        double pricePerGram = 0;
        double price = 0;

        int weight = filamentWeight.getValue(); //Get the value of the weight int

        if (filamentPrice != null && !filamentPrice.getText().isEmpty()) {
            price = Double.parseDouble(filamentPrice.getText()); //Get Price from text to double

            pricePerGram = price/weight; //Get the price per gram of the filament

            pricePerGramText.setText("€" + pricePerGram); //Set the text of the price per gram
        }

        return pricePerGram;
    }

    //Calculate the overall cost for this part
    @FXML
    public double CalculcateMaterialCost() {
        double weight = 0;
        double totalMaterialCost = 0;

        if(modelWeight!= null && !modelWeight.getText().isEmpty()) {
            weight = Double.parseDouble(modelWeight.getText());

            totalMaterialCost = CalculatePricePerGram() * weight;

            materialCostTotal.setText("€" + totalMaterialCost);

            CalculateTotalCost(totalMaterialCost);
        }
        return totalMaterialCost;
    }

    private double weight = 0;
    private double price = 0;
    private boolean isMaterial = false;

    @FXML
    public void HandleMaterialCalculations() {
        System.out.println(selectFilament.getItems());




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

            printOperationCost = printerFee * printingHours;

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
    public double printerKWhUsage = 0.15; //How much kWh printer uses per hour

    //Calculate the electricty costs
    @FXML
    public double ElectricityCost() {
        double totalElectricCost = 0;
        double electricCostPerHour = kWhPrice * printerKWhUsage;

        totalElectricCost = electricCostPerHour * ConvertTimeToDecimal(Double.parseDouble(printingTime.getText()));

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
        totalCost = materialCost + printerCost + manHourCost + electricityCost;
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
    ComboBox <String> selectFilament;

    @FXML
    Button updateFilament;

    @FXML
    public void UpdateFilamentComboBox() {
        System.out.println(filamentList.getFilament(0).toString());
        selectFilament.setItems(filamentList.getFilamentObservableList());
    }





}
