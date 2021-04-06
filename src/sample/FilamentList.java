package sample;

import java.util.ArrayList;

public class FilamentList {

    private ArrayList<Filament> filaments = new ArrayList<>();

    public void addFilament(Filament filament) {
        filaments.add(filament);
    }

    public Filament getFilament(int index) {
        if(index < filaments.size() && index >= 0) {
            return filaments.get(index);
        }
        else return null;
    }

}
