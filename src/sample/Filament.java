package sample;

public class Filament {

    private String Name;
    private String MaterialType;
    private double Weight;
    private double Price;

    public Filament(String name, String materialType, int weight, Double price) {
        if(materialType.length() <= 25) {
            this.MaterialType = materialType;
        }
        else this.MaterialType = materialType.substring(0,25);

        if(weight > 0.0 && weight <= 10000.0) {
            this.Weight = weight;
        }
        else this.Weight = 0.0;

        if(name.length() <= 30) {
            this.Name = name;
        }
        else this.Name = name.substring(0,30);

        if(price > 0.0 && price <= 10000.0) {
            this.Price = price;
        }
        else this.Price = 0.0;
    }

    //Getters-----------------------
    public String getName() {
        return Name;
    }

    public String getMaterialType() {
        return MaterialType;
    }

    public double getWeight() {
        return Weight;
    }

    public double getPrice() {
        return Price;
    }


    //Setters-------------------------


    public void setMaterialType(String materialType) {
        if(materialType.length() <= 25) {
            this.MaterialType = materialType;
        }
    }

    public void setWeight(int weight) {
        if(weight > 0.0 && weight <= 10000.0) {
            this.Weight = weight;
        }
    }

    public void setName(String name) {
        if(name.length() <= 30) {
            this.Name = name;
        }
    }

    public void setPrice(Double price) {
        if(price > 0.0 && price <= 10000.0) {
            this.Price = price;
        }
    }

    public String toString() {
        return Name + ", Material Type: " + MaterialType + ", Roll Weight " + Weight + ", Roll Price " + Price;
    }


}
