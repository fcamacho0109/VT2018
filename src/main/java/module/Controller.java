package module;

import Core.Session;
import Core.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextField userLoginText;
    @FXML
    PasswordField userLoginPass;
    @FXML
    Button loginBtn;
    @FXML
    ComboBox<User.Rol> comboType;
    @FXML
    Label loginLabel;
    @FXML
    Label labelUsuario;
    @FXML
    Label labelContrasena;
    @FXML
    private void login_Btn(){

        String user = "";
        String pass = "";
        user = userLoginText.getText().toString();
        pass = userLoginPass.getText().toString();





        if (user.length()<=8 && pass.length()<=8) {
            userLoginText.setText("");
            userLoginPass.setText("");
            userLoginText.setPromptText("Minimo 8 caracteres");
            userLoginPass.setPromptText("Minimo 8 caracteres");

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Aun no se definen acciones para el boton!" +
                    "\nEditar metodo login_Btn() para agregar login \n" +
                    "con user role y password de la BD");


            alert.showAndWait();
        }
        else {

            User user1 = new User(user,"LastName harcoded","33 33 33 33",comboType.getValue());
            Session.getInstance().setUser(user1);
            System.out.println("bienvenido "+Session.getInstance().getUser().rol);
            Stage myStage = (Stage)(labelContrasena.getScene().getWindow() );
            //Parent root = FXMLLoader.load(getClass().getResource("/res_main/MainScreen.fxml"));

            myStage.setScene(Session.getInstance().getApp().getView());
            myStage.setTitle(Session.getInstance().getApp().getTitle());


        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userLoginText.setPromptText("Minimo 8 caracteres");
        userLoginPass.setPromptText("Minimo 8 caracteres");
        comboType.setItems(FXCollections.observableArrayList(
                User.Rol.Admin,
                User.Rol.Usuario,
                User.Rol.Master
        ));
    }
}
