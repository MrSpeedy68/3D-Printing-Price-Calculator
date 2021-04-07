package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class FilamentList {

    private static ArrayList<Filament> filaments = new ArrayList<>();

    public void addFilament(Filament filament) {
        filaments.add(filament);
    }

    public Filament getFilament(int index) {
        if(index < filaments.size() && index >= 0) {
            return filaments.get(index);
        }
        else return null;
    }

    public void getListOfFilaments() {
        if (filaments.isEmpty()) {
            System.out.println("Array is empty");
        }
        else {
            for (Filament filament : filaments) {
                System.out.println(filament.toString());
            }
        }
    }

    public int getFilamentArraySize() {
        return filaments.size();
    }

    public boolean removeFilament(int index) {
        if(index < filaments.size() && index >= 0) {
            filaments.remove(index);
            return true;
        }
        else return Boolean.parseBoolean(null);
    }

    public ObservableList <Filament> getFilamentObservableList() {
        ObservableList <Filament> filamentOL = FXCollections.observableArrayList();
        for(int i = 0; i < filaments.size(); i++) {
            filamentOL.add(getFilament(i));
        }
        return filamentOL;
    }


}
