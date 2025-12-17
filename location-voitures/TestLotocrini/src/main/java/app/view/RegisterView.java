package app.view;

import app.Main;
import app.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Label;

public class RegisterView {
    private BorderPane view;

    public RegisterView() {
        createView();
    }

    private void createView() {
        view = new BorderPane();
        view.setStyle("-fx-background-color: #f7fafc;");

        // HEADER
        HeaderView headerView = new HeaderView("Mon compte");
        view.setTop(headerView.getHeader());

        // FORMULAIRE D'INSCRIPTION
        VBox registerForm = createRegisterForm();
        view.setCenter(registerForm);
    }

    private VBox createRegisterForm() {
        VBox form = new VBox(25);
        form.setMaxWidth(600);
        form.setPadding(new Insets(40));
        form.setAlignment(Pos.CENTER);
        form.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: #e2e8f0; " +
                        "-fx-border-radius: 15; " +
                        "-fx-border-width: 1; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 15, 0, 0, 5);");

        ImageView formIcon = loadImage("images/profil_icon.png", 60, 60);

        Label title = new Label("INSCRIPTION");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: 900; -fx-text-fill: #1a365d;");
        
        Label subtitle = new Label("Tous les champs sont obligatoires");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #e53e3e; -fx-font-weight: 500;");

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20, 0, 30, 0));

        // Champs avec validation
        TextField txtNom = new TextField();
        txtNom.setPromptText("Nom complet *");
        txtNom.setStyle(getFieldStyle());
        txtNom.setPrefWidth(250);

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email *");
        txtEmail.setStyle(getFieldStyle());
        txtEmail.setPrefWidth(250);

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Mot de passe *");
        txtPassword.setStyle(getFieldStyle());
        txtPassword.setPrefWidth(250);

        TextField txtPermis = new TextField();
        txtPermis.setPromptText("Numéro de permis *");
        txtPermis.setStyle(getFieldStyle());
        txtPermis.setPrefWidth(250);

        TextField txtPhone = new TextField();
        txtPhone.setPromptText("Téléphone * (ex: 0551-23-45-67)");
        txtPhone.setStyle(getFieldStyle());
        txtPhone.setPrefWidth(250);

        TextField txtAdresse = new TextField();
        txtAdresse.setPromptText("Adresse *");
        txtAdresse.setStyle(getFieldStyle());
        txtAdresse.setPrefWidth(250);

        // Ajouter les champs au grid
        grid.add(txtNom, 0, 0, 2, 1);
        grid.add(txtEmail, 0, 1, 1, 1);
        grid.add(txtPassword, 1, 1, 1, 1);
        grid.add(txtPermis, 0, 2, 2, 1);
        grid.add(txtPhone, 0, 3, 1, 1);
        grid.add(txtAdresse, 1, 3, 1, 1);

        Button btnRegister = new Button("S'INSCRIRE");
        btnRegister.setStyle(
                "-fx-background-color: #38a169; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: 800; " +
                        "-fx-padding: 15; " +
                        "-fx-background-radius: 8; " +
                        "-fx-cursor: hand; " +
                        "-fx-max-width: infinity;");
        GridPane.setColumnSpan(btnRegister, 2);
        
        btnRegister.setOnAction(e -> {
            if (validateRegistration(txtNom, txtEmail, txtPassword, txtPermis, txtPhone, txtAdresse)) {
                // Créer l'utilisateur
                User newUser = new User(
                    "U" + System.currentTimeMillis(),
                    txtNom.getText().trim(),
                    txtEmail.getText().trim(),
                    txtPassword.getText().trim(),
                    txtPermis.getText().trim(),
                    txtPhone.getText().trim(),
                    txtAdresse.getText().trim()
                );
                
                // Connecter l'utilisateur
                Main.setCurrentUser(newUser);
                
                // Afficher message de succès
                showSuccessMessage("Inscription réussie ! Bienvenue " + newUser.getNom());
                
                // Rediriger vers le catalogue
                Main.showCatalogueView();
            }
        });

        grid.add(btnRegister, 0, 4);

        Hyperlink linkLogin = new Hyperlink("Déjà un compte ? Se connecter");
        linkLogin.setStyle("-fx-text-fill: #3182ce; -fx-font-weight: 600; -fx-font-size: 14px;");
        linkLogin.setOnAction(e -> Main.showLoginView());
        GridPane.setColumnSpan(linkLogin, 2);
        grid.add(linkLogin, 0, 5);

        form.getChildren().addAll(formIcon, title, subtitle, grid);
        return form;
    }

    private boolean validateRegistration(TextField nom, TextField email, PasswordField password,
                                        TextField permis, TextField phone, TextField adresse) {
        // Vérifier tous les champs
        if (nom.getText().trim().isEmpty()) {
            showAlert("Champ manquant", "Le nom complet est obligatoire.");
            nom.requestFocus();
            return false;
        }
        
        if (email.getText().trim().isEmpty()) {
            showAlert("Champ manquant", "L'email est obligatoire.");
            email.requestFocus();
            return false;
        }
        
        // Vérifier format email
        if (!email.getText().trim().contains("@")) {
            showAlert("Email invalide", "Veuillez saisir une adresse email valide.");
            email.requestFocus();
            return false;
        }
        
        if (password.getText().trim().isEmpty()) {
            showAlert("Champ manquant", "Le mot de passe est obligatoire.");
            password.requestFocus();
            return false;
        }
        
        if (password.getText().trim().length() < 6) {
            showAlert("Mot de passe trop court", "Le mot de passe doit contenir au moins 6 caractères.");
            password.requestFocus();
            return false;
        }
        
        if (permis.getText().trim().isEmpty()) {
            showAlert("Champ manquant", "Le numéro de permis est obligatoire.");
            permis.requestFocus();
            return false;
        }
        
        if (phone.getText().trim().isEmpty()) {
            showAlert("Champ manquant", "Le téléphone est obligatoire.");
            phone.requestFocus();
            return false;
        }
        
        // Vérifier format téléphone (algérien)
        String phoneText = phone.getText().trim();
        if (!phoneText.matches("\\d{10}|\\d{4}-\\d{2}-\\d{2}-\\d{2}")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Format de téléphone");
            alert.setHeaderText("Format de téléphone non standard");
            alert.setContentText("Le format recommandé est 0551-23-45-67.\n" +
                    "Voulez-vous continuer avec ce numéro ?");
            
            ButtonType ouiButton = new ButtonType("Oui", ButtonBar.ButtonData.YES);
            ButtonType nonButton = new ButtonType("Non", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(ouiButton, nonButton);
            
            java.util.Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == nonButton) {
                phone.requestFocus();
                return false;
            }
        }
        
        if (adresse.getText().trim().isEmpty()) {
            showAlert("Champ manquant", "L'adresse est obligatoire.");
            adresse.requestFocus();
            return false;
        }
        
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
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
                "-fx-padding: 12; " +
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