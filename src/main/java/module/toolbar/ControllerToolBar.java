package module.toolbar;

import Core.Database;
import Core.Session;
import Core.models.Producto;
import com.calendarfx.model.Calendar;
import com.calendarfx.view.DateControl;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import module.searchTable.SearchTable;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerToolBar implements Initializable{


    @FXML
    VBox vBox;
    @FXML
    TabPane tabPane;

    @FXML
    Tab tab_citas,tab_pacientes,tab_compra_venta,tab_inventario,tab_recetas,tab_usuarios,tab_ayuda;

    @FXML
    Pane pane;

    @FXML
    Button boton_nueva_cita;

    @FXML
    private void listaProductos(){
        System.out.println("Select");
        try {
            VBox table = FXMLLoader.load(getClass().getResource("/dataTable/dataTableWithSearch.fxml"));
            SearchTable searchTable = Session.getInstance().getSearchTable();

            TableColumn<Producto,String> name = new TableColumn<>();
            TableColumn<Producto,String> description = new TableColumn<>();
            TableColumn<Producto,String> price = new TableColumn<>();
            TableColumn<Producto,String> priceSale = new TableColumn<>();
            TableColumn<Producto,String> code = new TableColumn<>();
            TableColumn<Producto,String> stock = new TableColumn<>();
            searchTable.table.getColumns().clear();
            searchTable.table.getColumns().addAll(name,description,price,priceSale,code,stock);
            name.setText("Nombre");
            description.setText("Descripcion");
            price.setText("Precio");
            priceSale.setText("Precio venta");
            code.setText("Codigo/Referencia");
            stock.setText("Existencia");
            name.setCellValueFactory(cell ->new ReadOnlyStringWrapper( cell.getValue().getName()));
            description.setCellValueFactory(cell ->new ReadOnlyStringWrapper( cell.getValue().getDescription()));
            price.setCellValueFactory(cell ->new ReadOnlyStringWrapper( String.valueOf(cell.getValue().getPrice())));
            priceSale.setCellValueFactory(cell ->new ReadOnlyStringWrapper( String.valueOf(cell.getValue().getPrice_sale())));
            code.setCellValueFactory(cell ->new ReadOnlyStringWrapper( cell.getValue().getCode()));
            stock.setCellValueFactory(cell ->new ReadOnlyStringWrapper( String.valueOf(cell.getValue().getStock())));


            ObservableList<Producto> productList = FXCollections.observableArrayList();
            productList.addAll(Database.getInstance().getProductos());


            searchTable.table.setItems(productList);
            searchTable.setup();
            Session.getInstance().getApp().setPane(table);


        }
        catch(Exception e){
            e.printStackTrace();
        }


    }
    @FXML void showAgenda(){
        Session.getInstance().getApp().setPane(Session.getInstance().getMyCalendar().getCalendar());
    }

    @FXML
    private void nuevaCita(){
        DateControl dateControl = new DateControl() {
            @Override
            public Optional<Calendar> getCalendarAt(double x, double y) {
                return super.getCalendarAt(x, y);
            }
        };
       // LocalDate localdate = new LocalDate();

        //vBox.getChildren().add(dateControl );

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Session.getInstance().getApp().setController(this);
        setToolbarByRol();


    }
    private void setToolbarByRol(){
        switch(Session.getInstance().getUser().rol){
            case Admin:
                tabPane.getTabs().remove(tab_recetas);
                break;
            case Master:

                break;
            case Usuario:
                tabPane.getTabs().remove(tab_inventario);
                tabPane.getTabs().remove(tab_recetas);
                tabPane.getTabs().remove(tab_usuarios);
                break;

        }
    }
    public void setCalendar(Calendar calendar){
        //pane.resize(1000,1300);
    }
    public void setPane(Node node){
        vBox.getChildren().remove(1);
        vBox.getChildren().add(node);



    }
}
