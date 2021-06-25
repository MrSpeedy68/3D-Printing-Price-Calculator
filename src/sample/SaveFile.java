package sample;
import java.io.*;

public class SaveFile {

    public static void main(String[] args) {
        WriteToFile();




    }


    public static void CreateFile() {
        try {
            File myObj = new File("filename.txt");
            //File myObj = new File("C:\\Users\\MyName\\filename.txt"); //Add object to specific directory.
            if(myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            }
            else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occured: ");
            e.printStackTrace();
        }
    }

    public static void WriteToFile() {
        Filament p1 = new Filament("PeePee","PLA",1000,15.99);

        try {
            FileOutputStream f = new FileOutputStream(new File("myObjects.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            //Write object to file
            o.writeObject(p1);

            o.close();
            f.close();

            FileInputStream fi = new FileInputStream(new File("myObject.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            //Read Objects
            Filament pr1 = (Filament) oi.readObject();

            System.out.println(pr1.toString());

            oi.close();
            fi.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch (IOException e) {
            System.out.println("An error occured: ");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }





}
