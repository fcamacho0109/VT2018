package module.productos;

import Core.models.Producto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProductView {

    private Producto product;

    @FXML
    private TextField inputName;

    @FXML
    private TextArea inputDescription;

    @FXML
    private TextField inputPrice;

    @FXML
    private TextField inputPriceSale;

    @FXML
    private TextField inputCode;

    @FXML
    private TextField inputStock;

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) inputCode.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(ActionEvent event) {

        Boolean isNew = false;
        if (product == null){
            product = new Producto();
            isNew = true;
        }
        product.setName(inputName.getText());
        product.setDescription(inputDescription.getText());
        product.setPrice(Float.valueOf(inputPrice.getText()));
        product.setPrice_sale(Float.valueOf(inputPriceSale.getText()));
        product.setCode(inputCode.getText());
        product.setStock(Integer.valueOf(inputStock.getText()));

        if(isNew){

        }else{

        }

    }

    public void setup(Producto producto){
        this.product = producto;
        inputName.setText(producto.getName());
        inputDescription.setText(producto.getDescription());
        inputPrice.setText(String.valueOf(producto.getPrice()));
        inputPriceSale.setText(String.valueOf(producto.getPrice_sale()));
        inputStock.setText(String.valueOf(producto.getStock()));
    }

}
