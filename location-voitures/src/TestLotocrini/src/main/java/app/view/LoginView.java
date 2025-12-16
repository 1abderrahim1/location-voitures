package app.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class LoginView {
    private BorderPane view;

    public LoginView() {
        createView();
    }

    private void createView() {
        view = new BorderPane();
        view.setStyle("-fx-background-color: #f7fafc;");

        // HEADER (
        VBox header = createHeader();
        view.setTop(header);

        // FORMULAIRE DE CONNEXION
        VBox loginForm = createLoginForm();
        view.setCenter(loginForm);
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.getStyleClass().add("header");
        header.setPadding(new Insets(0));

        // Barre supérieure
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(15, 40, 15, 40));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #1a365d;");

        // LOGO
        HBox logoBox = new HBox();
        logoBox.setAlignment(Pos.CENTER_LEFT);
        ImageView logoView = loadImage("images/lotocrini_logo_header.png", 50, 50);
        logoView.setScaleX(1.6);
        logoView.setScaleY(1.6);
        logoBox.getChildren().add(logoView);

        // BARRE DE RECHERCHE
        HBox searchBox = new HBox(10);
        searchBox.getStyleClass().add("search-bar");
        searchBox.setPadding(new Insets(5, 10, 5, 10));
        searchBox.setStyle("-fx-background-color: white; -fx-background-radius: 5;");
        searchBox.setAlignment(Pos.CENTER_LEFT);

        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher une voiture...");
        searchField.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-font-size: 14px; " +
                        "-fx-prompt-text-fill: #a0aec0;");
        HBox.setHgrow(searchField, Priority.ALWAYS);

        ImageView searchIcon = loadImage("images/recherche_icon.png", 20, 20);
        HBox.setMargin(searchIcon, new Insets(0, 0, 0, 5));

        searchBox.getChildren().addAll(searchField, searchIcon);

        // Boutons droite
        HBox rightButtons = new HBox(15);
        rightButtons.setAlignment(Pos.CENTER_RIGHT);

        Button btnLogin = createIconButton("Se connecter / Créer un compte", "images/login_icon.png");
        btnLogin.setOnAction(e -> app.Main.showLoginView());
        rightButtons.getChildren().addAll(btnLogin);

        HBox.setHgrow(searchBox, Priority.ALWAYS);
        topBar.getChildren().addAll(logoBox, searchBox, rightButtons);

        // NAVBAR
        HBox navBar = new HBox(0);
        navBar.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-width: 0 0 1 0;");

        String[] menuItems = { "Accueil", "Catalogue", "Réservation", "Mon compte", "Contact", "Admin" };
        String[] menuIcons = {
                "images/Accueil_icon.png",
                "images/voiture_icon.png",
                "images/reservation_icon.png",
                "images/contact_icon.png",
                "images/parametres_icon.png",
                "images/administrator_icon.png"
        };

        for (int i = 0; i < menuItems.length; i++) {
            Button menuBtn = createMenuButton(menuItems[i], menuIcons[i]);
            if (menuItems[i].equals("Mon compte")) {
                menuBtn.setStyle(menuBtn.getStyle() + " -fx-background-color: #f7fafc; -fx-text-fill: #1a365d;");
            }
            navBar.getChildren().add(menuBtn);
        }

        header.getChildren().addAll(topBar, navBar);

        return header;
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

        ImageView logo = loadImage("images/lotocrini_logo.png", 80, 240);
        ImageView userIcon = loadImage("images/profil_icon.png", 60, 60);

        Label title = new Label("CONNEXION");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: 900; -fx-text-fill: #1a365d;");

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
        btnLogin.setOnAction(e -> app.Main.showCatalogueView());

        Hyperlink linkRegister = new Hyperlink("Créer un compte");
        linkRegister.setStyle("-fx-text-fill: #3182ce; -fx-font-weight: 600; -fx-font-size: 14px;");
        linkRegister.setOnAction(e -> app.Main.showRegisterView());

        fields.getChildren().addAll(txtEmail, txtPassword, btnLogin, linkRegister);
        form.getChildren().addAll(logo, userIcon, title, fields);

        return form;
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

    private Button createIconButton(String text, String iconPath) {
        Button btn = new Button(text);
        btn.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 8 15;" +
                        "-fx-cursor: hand;");

        ImageView icon = loadImage(iconPath, 20, 20);
        btn.setGraphic(icon);

        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: rgba(255,255,255,0.1); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 8 15; " +
                        "-fx-background-radius: 4;"));

        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 8 15;"));

        return btn;
    }

    private Button createMenuButton(String text, String iconPath) {
        Button btn = new Button(text);
        btn.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: #4a5568; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 15 25; " +
                        "-fx-border-width: 0 0 3 0; " +
                        "-fx-border-color: transparent; " +
                        "-fx-cursor: hand;");

        ImageView icon = loadImage(iconPath, 16, 16);
        btn.setGraphic(icon);

        btn.setOnMouseEntered(e -> {
            if (!text.equals("Mon compte")) {
                btn.setStyle(
                        "-fx-background-color: #f7fafc; " +
                                "-fx-text-fill: #1a365d; " +
                                "-fx-font-size: 14px; " +
                                "-fx-font-weight: 600; " +
                                "-fx-padding: 15 25; " +
                                "-fx-border-width: 0 0 3 0; " +
                                "-fx-border-color: transparent;");
            }
        });

        btn.setOnMouseExited(e -> {
            if (!text.equals("Mon compte")) {
                btn.setStyle(
                        "-fx-background-color: transparent; " +
                                "-fx-text-fill: #4a5568; " +
                                "-fx-font-size: 14px; " +
                                "-fx-font-weight: 600; " +
                                "-fx-padding: 15 25; " +
                                "-fx-border-width: 0 0 3 0; " +
                                "-fx-border-color: transparent;");
            }
        });

        btn.setOnAction(e -> {
            switch (text) {
                case "Accueil":
                    app.Main.showAccueilView();
                    break;
                case "Catalogue":
                    app.Main.showCatalogueView();
                    break;
                case "Réservation":
                    app.Main.showReservationView();
                    break;
                case "Contact":
                    app.Main.showContactView();
                    break;
                case "Mon compte":
                    app.Main.showLoginView();
                    break;
                case "Admin":
                    app.Main.showAdminView();
                    break;
            }
        });

        return btn;
    }

    public BorderPane getView() {
        return view;
    }
}
