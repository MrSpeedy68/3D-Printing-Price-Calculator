package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AddFilamentController {

    private FilamentList filamentList = new FilamentList();

    @FXML
    TextField filamentName;
    @FXML
    TextField materialType;
    @FXML
    TextField rollWeight;
    @FXML
    TextField pricePerRoll;
    @FXML
    Button addNewFilament;
    @FXML
    Button addFilamentBackButton;
    @FXML
    Text newFilamentText;

    public void GoBackToMainMenu() {

    }


    @FXML
    public void AddNewFilament() {
        if(filamentName != null && materialType != null && rollWeight != null && pricePerRoll != null) { //Check if the textfileds arent null before adding a new filament
            filamentList.addFilament(new Filament(filamentName.getText(),materialType.getText(),Integer.parseInt(rollWeight.getText()),Double.parseDouble(pricePerRoll.getText()))); //Adds a new filament to the array.

            for(int i = 0; i < filamentList.getFilamentArraySize(); i++) {
                System.out.println(filamentList.getFilament(i).toString());
            }
        }
        else newFilamentText.setText("The Fields Cannot Be Empty!!"); //if it is empty then display an error message
    }

    @FXML
    public void Test() {
        filamentList.getListOfFilaments();
    }






}
