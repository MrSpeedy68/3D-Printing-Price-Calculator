package sample;

import java.io.Serializable;

public class Printer implements Serializable {

    private String name;
    private double price;
    private double kWhUsage;
    private double monthsToRepayPrinter;

    public Printer(String name, double price, double kWhUsage, double monthsToRepayPrinter) {

    }


    //Getters

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getkWhUsage() {
        return kWhUsage;
    }

    public double getMonthsToRepayPrinter() {
        return monthsToRepayPrinter;
    }


    //Setter

    public void setName(String name) {
        if(name.length() <= 25) {
            this.name = name;
        }
    }

    public void setPrice(double price) {
        if(price > 0.0 && price <= 100000000.0) {
            this.price = price;
        }
    }

    public void setkWhUsage(double kWhUsage) {
        this.kWhUsage = kWhUsage;
    }

    public void setMonthsToRepayPrinter(double monthsToRepayPrinter) {
        this.monthsToRepayPrinter = monthsToRepayPrinter;
    }
}
