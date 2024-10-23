package org.example.laptopthachthat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField UsernameTextFile;
    @FXML
    private PasswordField PasswordTextFile;
    @FXML
    private Button SigninButton;
    @FXML
    private Label error;

    private ConectionJDBC conectionJDBC = new ConectionJDBC();
    @FXML
    public void TextLogin() throws SQLException {
        String Username = UsernameTextFile.getText();
        String Password = PasswordTextFile.getText();

        boolean role = Signin(Username, Password);
        if (role) {
            error.setText("Login Successful");
        } else {
            error.setText("Login Failed");
        }
    }

    public boolean Signin(String userName, String password) throws SQLException {
        String query = "SELECT * FROM User WHERE username = ? AND password = ?";

        try (Connection conn = conectionJDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void switchToDisplayLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(LoginApplication.class.getResource("Register.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Sign up");
        stage.setScene(scene);
        stage.show();
    }

    }

