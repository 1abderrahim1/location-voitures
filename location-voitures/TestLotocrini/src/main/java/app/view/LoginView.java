package app.view;

import app.Main;
import app.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Label;

public class LoginView {
    private BorderPane view;

    public LoginView() {
        createView();
    }

    private void createView() {
        view = new BorderPane();
        view.setStyle("-fx-background-color: #f7fafc;");

        // HEADER
        HeaderView headerView = new HeaderView("Mon compte");
        view.setTop(headerView.getHeader());

        // FORMULAIRE DE CONNEXION
        VBox loginForm = createLoginForm();
        view.setCenter(loginForm);
    }

    private VBox createLoginForm() {
        VBox form = new VBox(30);
        form.setMaxWidth(500);
        form.setPadding(new Insets(50));
        form.setAlignment(Pos.CENTER);
        form.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: #e2e8f0; " +
                        "-fx-border-radius: 15; " +
                        "-fx-border-width: 1; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 15, 0, 0, 5);");

        ImageView logo = loadImage("images/lotocrini_logo.png", 150, 240);
        ImageView userIcon = loadImage("images/profil_icon.png", 10, 10);

        Label title = new Label("CONNEXION");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: 900; -fx-text-fill: #1a365d;");
        
        // Message spécial pour réservation si besoin
        String currentView = Main.getCurrentView();
        if (currentView.equals("LoginForReservation")) {
            Label reservationNote = new Label("Vous devez être connecté pour réserver une voiture.");
            reservationNote.setStyle("-fx-font-size: 14px; -fx-text-fill: #e53e3e; -fx-font-weight: 500; -fx-padding: 0 0 10 0;");
            form.getChildren().add(reservationNote);
        }

        VBox fields = new VBox(20);
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        txtEmail.setStyle(getFieldStyle());

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Mot de passe");
        txtPassword.setStyle(getFieldStyle());

        Button btnLogin = new Button("SE CONNECTER");
        btnLogin.setStyle(
                "-fx-background-color: #3182ce; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: 800; " +
                        "-fx-padding: 15; " +
                        "-fx-background-radius: 8; " +
                        "-fx-cursor: hand; " +
                        "-fx-max-width: infinity;");
        
        btnLogin.setOnAction(e -> {
            if (validateLogin(txtEmail, txtPassword)) {
                // Simuler une connexion (en vrai, vérifier avec base de données)
                // Pour l'exemple, on accepte n'importe quelle combinaison non vide
                User user = new User(
                    "U001",
                    "Utilisateur Test",
                    txtEmail.getText().trim(),
                    txtPassword.getText().trim(),
                    "123456789",
                    "0551-23-45-67",
                    "Alger Centre"
                );
                
                Main.setCurrentUser(user);
                
                // Rediriger selon la vue précédente
                if (currentView.equals("LoginForReservation")) {
                    Main.showReservationView();
                } else {
                    Main.showCatalogueView();
                }
            }
        });

        Hyperlink linkRegister = new Hyperlink("Créer un compte");
        linkRegister.setStyle("-fx-text-fill: #3182ce; -fx-font-weight: 600; -fx-font-size: 14px;");
        linkRegister.setOnAction(e -> Main.showRegisterView());

        fields.getChildren().addAll(txtEmail, txtPassword, btnLogin, linkRegister);
        
        if (currentView.equals("LoginForReservation")) {
            Button btnSkip = new Button("Continuer sans compte (remplir manuellement)");
            btnSkip.setStyle(
                "-fx-background-color: transparent; " +
                "-fx-text-fill: #718096; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 10; " +
                "-fx-border-color: #e2e8f0; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 6; " +
                "-fx-cursor: hand;");
            btnSkip.setOnAction(e -> Main.showReservationView());
            fields.getChildren().add(btnSkip);
        }
        
        form.getChildren().addAll(logo, userIcon, title, fields);

        return form;
    }
    
    private boolean validateLogin(TextField email, PasswordField password) {
        if (email.getText().trim().isEmpty()) {
            showAlert("Email manquant", "Veuillez saisir votre email.");
            email.requestFocus();
            return false;
        }
        
        if (password.getText().trim().isEmpty()) {
            showAlert("Mot de passe manquant", "Veuillez saisir votre mot de passe.");
            password.requestFocus();
            return false;
        }
        
        // En réalité, vérifier dans la base de données
        // Pour l'exemple, on accepte tout
        return true;
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String getFieldStyle() {
        return "-fx-background-color: #f7fafc; " +
                "-fx-background-radius: 8; " +
                "-fx-border-color: #e2e8f0; " +
                "-fx-border-radius: 8; " +
                "-fx-border-width: 1; " +
                "-fx-padding: 15; " +
                "-fx-font-size: 15px;";
    }

    private ImageView loadImage(String path, double height, double width) {
        ImageView imageView = new ImageView();
        try {
            javafx.scene.image.Image image = new javafx.scene.image.Image(getClass().getResourceAsStream(path));
            imageView.setImage(image);
            imageView.setFitHeight(height);
            imageView.setFitWidth(width);
            imageView.setPreserveRatio(true);
        } catch (Exception e) {
            imageView.setFitHeight(height);
            imageView.setFitWidth(width);
            imageView.setStyle("-fx-background-color: #e2e8f0; -fx-background-radius: 4;");
        }
        return imageView;
    }

    public BorderPane getView() {
        return view;
    }
}