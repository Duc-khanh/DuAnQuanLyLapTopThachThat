package org.example.laptopthachthat.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.laptopthachthat.ConectionJDBC;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeAdminController {
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, Boolean> stockColumn;
    @FXML
    private TableColumn<Product, ImageView> imageColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> describeColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;


    @FXML
    private Label showAdd;
    @FXML
    private void addProduct() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/laptopthachthat/AddProduct.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Thêm Sản Phẩm");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Lỗi khi tải form Thêm Sản Phẩm: " + e.getMessage());
            e.printStackTrace();
        }
    }





    @FXML
    public void initialize() {


        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        describeColumn.setCellValueFactory(new PropertyValueFactory<>("describe"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        centerColumnData(idColumn);
        centerColumnData(stockColumn);
        centerColumnData(nameColumn);
        centerColumnData(describeColumn);
        centerColumnData(quantityColumn);
        centerColumnData(priceColumn);


        loadProducts();

    }

    private void loadProducts() {
        String query = "SELECT productID, stock, image, productName, description, quality, price FROM Products";

        try (Connection connection = ConectionJDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int productID = resultSet.getInt("productID");
                boolean stock = resultSet.getBoolean("stock");
                String imageURL = resultSet.getString("image");
                String productName = resultSet.getString("productName");
                String description = resultSet.getString("description");
                int quality = resultSet.getInt("quality");
                double price = resultSet.getDouble("price");

                ImageView imageView = new ImageView(imageURL);
                imageView.setFitHeight(145);
                imageView.setFitWidth(150);

                Product product = new Product(productID, productName, price, quality, description, stock, imageView);
                productTable.getItems().add(product);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tải dữ liệu sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private <T> void centerColumnData(TableColumn<Product, T> column) {
        column.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }

    @FXML
    private void showTableView() {
        productTable.setVisible(true);
    }

    @FXML
    private void BackToSignin(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log out");
        alert.setHeaderText("Are you sure you want to log out?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/org/example/laptopthachthat/Login.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();

                    System.out.println("Signed out successfully.");
                } catch (IOException e) {
                    System.err.println("Error returning to the login page: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("Cancel logout.");
            }
        });
    }
}
