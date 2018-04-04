package module.searchTable;

import Core.Session;
import Core.models.Producto;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchTable implements Initializable {

    @FXML
    VBox vbox;
    @FXML
    public TableView table;
    @FXML
    TextField inputText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Session.getInstance().setSearchTable(this);


    }
    public void setup(){
        FilteredList<Producto> filteredData = new FilteredList<>(table.getItems(), p -> true);
        inputText.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredData.setPredicate(product ->{
                if(newValue == null || newValue.isEmpty()) return true;

                String lowers = newValue.toLowerCase();
                if(product.toString().toLowerCase().contains(lowers)){
                    return true;
                }
                else{
                    return false;
                }

            });
        }));
        SortedList<Producto> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData.sorted());
        table.setRowFactory( tv -> {
            TableRow<Producto> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Producto producto = row.getItem();
                    System.out.println(producto);
                }
            });
            return row ;
        });

    }
}
