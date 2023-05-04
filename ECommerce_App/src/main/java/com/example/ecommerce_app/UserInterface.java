package com.example.ecommerce_app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class UserInterface {
    GridPane loginPage;

    ProductList productList=new ProductList();
    VBox productPage;

    VBox body;

    Customer loggedInCustomer;

    Button signInButton;
    Label wellComeLabel;
    HBox footerBar;
    HBox headerBar; // HBox include all his child, node or component in horigantel menner;

    ObservableList<Product>ItemsInCart= FXCollections.observableArrayList();
    Button placeOrderButton=new Button("Place Order");
    BorderPane createContent(){
        BorderPane root=new BorderPane();
        root.setPrefSize(800, 600);
       // root.getChildren().add(loginPage); //method to add node to pane;
        root.setTop(headerBar);
      //  root.setCenter(loginPage);
        body=new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);
        productPage=productList.getAllProducts();
        body.getChildren().add(productPage);
        root.setBottom(footerBar);
        return root;
    }

    public  UserInterface(){
        createLoginPage();
        createHeaderBar();
        createFooterBar();
    }
    private  void createLoginPage(){

        Text userNameText=new Text("User Name");
        Text passedWordText=new Text("PassWord");

        TextField userName=new TextField("pabitrause111@gmail.com");
        userName.setPromptText("Type your user name here");
        PasswordField password=new PasswordField();
        password.setText("pab432");
        password.setPromptText("Type your password here");

        Label messageLabel=new Label("Hi");
        Button logButton=new Button("Login");
                loginPage=new GridPane();
                //loginPage.setStyle(" -fx-background-color:grey;");
                loginPage.setAlignment(Pos.CENTER); // it is puting all in middle;
        loginPage.setHgap(10);
        loginPage.setVgap(10);
        loginPage.add(userNameText, 0,0 );
        loginPage.add(userName, 1, 0);
        loginPage.add(passedWordText, 0, 1);
        loginPage.add(password, 1, 1);
        loginPage.add(messageLabel, 0, 2);
        loginPage.add(logButton, 1, 2);

        logButton.setOnAction(new EventHandler<ActionEvent>() { // when you click login then show any text;
            @Override
            public void handle(ActionEvent actionEvent) {
                String name=userName.getText();
                String pass=password.getText();

                Login login=new Login();

                loggedInCustomer=login.customerLogin(name, pass);

                if(loggedInCustomer!=null){
                    messageLabel.setText("WellCome : "+ loggedInCustomer.getName());
                    wellComeLabel.setText("WellCome - "+ loggedInCustomer.getName());
                    headerBar.getChildren().add(wellComeLabel);
                    body.getChildren().clear();
                    body.getChildren().add(productPage);

                }else{
                    messageLabel.setText("LogIn Failed ! please provide correct credentials");
                }


            }
        });
        createHeaderBar();
    }


    private void createHeaderBar(){
        Button homeButton=new Button();
        Image image=new Image("C:\\Users\\91936\\IdeaProjects\\ECommerce_App\\src\\img.png");
        ImageView imageView=new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(80);
        homeButton.setGraphic(imageView);

        TextField searchBar=new TextField();
        searchBar.setPromptText("Search here");
        searchBar.setPrefWidth(180);

        Button searchButton=new Button("Search");
         signInButton=new Button("Sign In");
         wellComeLabel=new Label();

        Button cartButton=new Button("Cart");
        Button orderButton=new Button("orders");

        headerBar=new HBox(20); // you can use headerBar.setSpacing for gap;
        headerBar.setPadding(new Insets(10));
       // headerBar.setStyle(" -fx-background-color:grey;");
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(homeButton, searchBar, searchButton, signInButton, cartButton, orderButton);

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(loginPage);
                headerBar.getChildren().remove(signInButton );
            }
        });

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox prodPage= productList.getProductsInCart(ItemsInCart);
                prodPage.getChildren().add(placeOrderButton);
                prodPage.setAlignment(Pos.CENTER);
                prodPage.setSpacing(10);
                body.getChildren().add(prodPage);
                footerBar.setVisible(false);
            }
        });

        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                //need list of product and customer;
                if(ItemsInCart==null){
                    showDialog("please add some product in the cart to place order");
                    return;
                }

                if(loggedInCustomer==null){
                    showDialog("Please login first to place order");
                    return;
                }

                int count=Order.placeMultipleOrder(loggedInCustomer, ItemsInCart);

                if(count !=0){
                    showDialog("Order for "+count+" products placed successfully !!");
                }else{
                    showDialog("Order Failed !!");
                }
            }
        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(productPage);
                footerBar.setVisible(true);

                if(loggedInCustomer==null && headerBar.getChildren().indexOf(signInButton)==-1){
                    headerBar.getChildren().add(signInButton);
                }
            }
        });
    }


    private void createFooterBar(){

        Button buyNowButton=new Button("BuyNow");
        Button addToCartButton=new Button("Add to Cart");

        footerBar=new HBox(); // you can use headerBar.setSpacing for gap;
        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        // headerBar.setStyle(" -fx-background-color:grey;");
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton, addToCartButton);

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product=productList.getSelectedProduct();
                //
                if(product==null){
                    showDialog("please select a product first to place order");
                    return;
                }

                if(loggedInCustomer==null){
                    showDialog("Please login first to place order");
                    return;
                }

                boolean status=Order.placeOrder(loggedInCustomer, product);

                if(status==true){
                    showDialog("Order placed successfully !!");
                }else{
                    showDialog("Order Failed !!");
                }

            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product=productList.getSelectedProduct();
                //
                if(product==null){
                    showDialog("please select a product first to add it to Cart!");
                    return;
                }

                ItemsInCart.add(product);
                showDialog("Selected Item has been added to the Cart Successfully!");
            }
        });

    }

    public void showDialog(String message){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait();
    }

}
