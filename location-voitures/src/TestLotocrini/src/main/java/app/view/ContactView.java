package app.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class ContactView {
    private BorderPane view;

    public ContactView() {
        createView();
    }

    private void createView() {
        view = new BorderPane();
        view.getStyleClass().add("contact-root");
        view.setStyle("-fx-background-color: #f7fafc;");

        // HEADER
        VBox header = createHeader();
        view.setTop(header);

        // CONTENU PRINCIPAL
        HBox mainContent = createMainContent();
        view.setCenter(mainContent);

        // FOOTER
        VBox footer = createFooter();
        view.setBottom(footer);
    }

    // HEADER
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

        // Boutons à droite
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
            if (menuItems[i].equals("Contact")) {
                menuBtn.setStyle(menuBtn.getStyle()
                        + " -fx-background-color: #f7fafc; -fx-text-fill: #1a365d;");
            }
            navBar.getChildren().add(menuBtn);
        }

        header.getChildren().addAll(topBar, navBar);
        return header;
    }

    // CONTENU
    private HBox createMainContent() {
        HBox content = new HBox(40);
        content.setPadding(new Insets(40));
        content.setAlignment(Pos.CENTER);

        // Colonne gauche - Informations
        VBox infoColumn = new VBox(30);
        infoColumn.setPrefWidth(400);
        infoColumn.setPadding(new Insets(30));
        infoColumn.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: #e2e8f0; " +
                        "-fx-border-radius: 15; " +
                        "-fx-border-width: 1; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 10, 0, 0, 3);");

        Label infoTitle = new Label("📞 CONTACT");
        infoTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: 900; -fx-text-fill: #1a365d;");

        VBox contactItems = new VBox(25);

        VBox addressBox = new VBox(5);
        Label addressTitle = new Label("Adresse");
        addressTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: 700; -fx-text-fill: #2d3748;");
        Label address = new Label("75 Rue Didouche Mourad\nSidi M'Hamed, Alger");
        address.setStyle("-fx-font-size: 15px; -fx-text-fill: #718096;");
        addressBox.getChildren().addAll(addressTitle, address);

        VBox phoneBox = new VBox(5);
        Label phoneTitle = new Label("📞 Numéro");
        phoneTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: 700; -fx-text-fill: #2d3748;");
        Label phone = new Label("0552 88 45 67");
        phone.setStyle("-fx-font-size: 15px; -fx-text-fill: #718096;");
        phoneBox.getChildren().addAll(phoneTitle, phone);

        VBox emailBox = new VBox(5);
        Label emailTitle = new Label("Email");
        emailTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: 700; -fx-text-fill: #2d3748;");
        Label email = new Label("lotocrini@gmail.com");
        email.setStyle("-fx-font-size: 15px; -fx-text-fill: #718096;");
        emailBox.getChildren().addAll(emailTitle, email);

        contactItems.getChildren().addAll(addressBox, phoneBox, emailBox);
        infoColumn.getChildren().addAll(infoTitle, contactItems);

        // Colonne droite - Formulaire
        VBox formColumn = new VBox(25);
        formColumn.setPrefWidth(500);
        formColumn.setPadding(new Insets(30));
        formColumn.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: #e2e8f0; " +
                        "-fx-border-radius: 15; " +
                        "-fx-border-width: 1; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 10, 0, 0, 3);");

        Label formTitle = new Label("NOUS CONTACTER");
        formTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: 900; -fx-text-fill: #1a365d;");

        VBox form = new VBox(20);
        TextField txtNom = new TextField();
        txtNom.setPromptText("Votre nom");
        txtNom.setStyle(getFieldStyle());

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Votre email");
        txtEmail.setStyle(getFieldStyle());

        TextArea txtMessage = new TextArea();
        txtMessage.setPromptText("Votre message...");
        txtMessage.setPrefHeight(150);
        txtMessage.setStyle(getFieldStyle() + " -fx-wrap-text: true;");

        Button btnEnvoyer = new Button("ENVOYER LE MESSAGE");
        btnEnvoyer.setStyle(
                "-fx-background-color: #3182ce; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: 800; " +
                        "-fx-padding: 15; " +
                        "-fx-background-radius: 8; " +
                        "-fx-cursor: hand; " +
                        "-fx-max-width: infinity;");

        form.getChildren().addAll(txtNom, txtEmail, txtMessage, btnEnvoyer);
        formColumn.getChildren().addAll(formTitle, form);

        content.getChildren().addAll(infoColumn, formColumn);
        return content;
    }

    private VBox createFooter() {
        VBox footer = new VBox(10);
        footer.setPadding(new Insets(20, 40, 20, 40));
        footer.setAlignment(Pos.CENTER);
        footer.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-width: 1 0 0 0;");

        Label copyright = new Label("© 2025 Lotocrini - Tous droits réservés");
        copyright.setStyle("-fx-text-fill: #718096; -fx-font-size: 14px;");

        footer.getChildren().add(copyright);
        return footer;
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
            Image image = new Image(getClass().getResourceAsStream(path));
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
            if (!text.equals("Contact")) {
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
            if (!text.equals("Contact")) {
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