package Core.models;

public class Producto {
    int id;
    String name,description;
    float price, price_sale;
    String code;
    int stock;
    public Producto(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "" +name+description+price+price_sale+code+stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice_sale() {
        return price_sale;
    }

    public void setPrice_sale(float price_sale) {
        this.price_sale = price_sale;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Producto(String name, String description, float price, float price_sale, String code, int stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.price_sale = price_sale;
        this.code = code;
        this.stock = stock;
    }
}
