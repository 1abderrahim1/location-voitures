package app.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class VoitureDetailView {
    private BorderPane view;
    private CatalogueView.Voiture voiture;

    public VoitureDetailView(CatalogueView.Voiture voiture) {
        this.voiture = voiture;
        createView();
    }

    private void createView() {
        view = new BorderPane();
        view.setStyle("-fx-background-color: #f7fafc;");

        // HEADER
        VBox header = createHeader();
        view.setTop(header);

        // CONTENU PRINCIPAL avec ScrollPane
        ScrollPane scrollContent = createScrollContent();
        view.setCenter(scrollContent);
    }

    private ScrollPane createScrollContent() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox mainContent = createMainContent();
        scrollPane.setContent(mainContent);

        return scrollPane;
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

        // Bouton droite (login seulement)
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
            if (menuItems[i].equals("Accueil")) {
                menuBtn.setStyle(menuBtn.getStyle()
                        + " -fx-background-color: #f7fafc; -fx-text-fill: #1a365d;");
            }
            navBar.getChildren().add(menuBtn);
        }

        header.getChildren().addAll(topBar, navBar);
        return header;
    }

    private VBox createMainContent() {
        VBox content = new VBox(30);
        content.setPadding(new Insets(30, 40, 60, 40)); // Ajout de padding en bas pour l'espace

        // ========== FLÈCHE DE RETOUR ==========
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_LEFT);

        Button btnRetour = new Button("← Retour au catalogue");
        btnRetour.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: #3182ce; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 10 20; " +
                        "-fx-border-color: #3182ce; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 5; " +
                        "-fx-cursor: hand;");

        btnRetour.setOnAction(e -> app.Main.showCatalogueView());
        topBar.getChildren().add(btnRetour);

        HBox mainRow = new HBox(40);
        mainRow.setAlignment(Pos.TOP_CENTER);

        // Colonne gauche - Image grande
        VBox imageColumn = new VBox(20);
        imageColumn.setPrefWidth(500);

        // Utiliser l'image spécifique de la voiture
        ImageView mainImage = new ImageView(voiture.icone.getImage());
        mainImage.setFitHeight(300);
        mainImage.setFitWidth(500);
        mainImage.setPreserveRatio(true);
        mainImage.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-color: #e2e8f0; " +
                        "-fx-border-radius: 10; " +
                        "-fx-border-width: 1; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        imageColumn.getChildren().add(mainImage);

        // Colonne droite - Détails
        VBox detailsColumn = new VBox(25);
        detailsColumn.setPrefWidth(600);

        Label title = new Label(voiture.nom);
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: 900; -fx-text-fill: #1a365d;");

        HBox brandModel = new HBox(20);
        brandModel.setAlignment(Pos.CENTER_LEFT);

        // Utiliser les getters de la voiture
        Label marque = new Label("Marque: " + voiture.getMarque());
        Label modele = new Label("Modèle: " + voiture.getModele());

        for (Label lbl : new Label[] { marque, modele }) {
            lbl.setStyle("-fx-font-size: 16px; -fx-text-fill: #4a5568; -fx-font-weight: 500;");
        }

        brandModel.getChildren().addAll(marque, modele);

        Label annee = new Label("Année: 2025");
        annee.setStyle("-fx-font-size: 16px; -fx-text-fill: #4a5568; -fx-font-weight: 500;");

        HBox priceBox = new HBox(10);
        priceBox.setAlignment(Pos.CENTER_LEFT);

        // Afficher DA
        Label price = new Label(String.format("%,.0f DA", voiture.getPrix()));
        price.setStyle("-fx-font-size: 36px; -fx-font-weight: 900; -fx-text-fill: #2d3748;");

        Label perDay = new Label("/ jour");
        perDay.setStyle("-fx-font-size: 18px; -fx-text-fill: #718096; -fx-font-weight: 500;");

        priceBox.getChildren().addAll(price, perDay);

        HBox dispoBox = new HBox(10);
        dispoBox.setAlignment(Pos.CENTER_LEFT);

        javafx.scene.shape.Circle dispoCircle = new javafx.scene.shape.Circle(8);
        dispoCircle.setFill(voiture.isDisponible() ? Color.GREEN : Color.RED);

        Label dispoLabel = new Label(voiture.isDisponible() ? "Disponible" : "Non disponible");
        dispoLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 700;");
        dispoLabel.setTextFill(voiture.isDisponible() ? Color.GREEN : Color.RED);

        dispoBox.getChildren().addAll(dispoCircle, dispoLabel);

        VBox optionsBox = new VBox(15);
        Label optionsTitle = new Label("OPTIONS INCLUSES");
        optionsTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 700; -fx-text-fill: #1a365d;");

        String[] options = {
                "• Assurance tout risque",
                "• Assistance 24h/24",
                "• Kilométrage illimité",
                "• Sièges enfants (sur demande)",
                "• GPS intégré"
        };

        VBox optionsList = new VBox(8);
        for (String opt : options) {
            Label optLabel = new Label(opt);
            optLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #4a5568;");
            optionsList.getChildren().add(optLabel);
        }

        optionsBox.getChildren().addAll(optionsTitle, optionsList);

        // ========== SECTION CARACTÉRISTIQUES TECHNIQUES ==========
        VBox specsBox = new VBox(15);
        Label specsTitle = new Label("CARACTÉRISTIQUES TECHNIQUES");
        specsTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 700; -fx-text-fill: #1a365d;");

        GridPane specsGrid = new GridPane();
        specsGrid.setHgap(40);
        specsGrid.setVgap(15);
        specsGrid.setPadding(new Insets(10, 0, 0, 0));

        // Ajouter des caractéristiques
        String[][] specifications = {
                { "Moteur", "2.0L Turbo" },
                { "Puissance", "245 ch" },
                { "Transmission", "Automatique 8 vitesses" },
                { "Consommation", "7.2L/100km" },
                { "Nombre de places", "5" },
                { "Coffre", "480 L" },
                { "Climatisation", "Automatique bi-zone" },
                { "Écran tactile", "10.25 pouces" }
        };

        for (int i = 0; i < specifications.length; i++) {
            Label specLabel = new Label(specifications[i][0] + ":");
            specLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #4a5568; -fx-font-weight: 500;");

            Label valueLabel = new Label(specifications[i][1]);
            valueLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #2d3748; -fx-font-weight: 600;");

            specsGrid.add(specLabel, 0, i);
            specsGrid.add(valueLabel, 1, i);
        }

        specsBox.getChildren().addAll(specsTitle, specsGrid);

        // ========== BOUTON RÉSERVER ==========
        Button btnReserver = new Button("📅 RÉSERVER MAINTENANT");
        btnReserver.setStyle(
                "-fx-background-color: #38a169; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 20px; " +
                        "-fx-font-weight: 800; " +
                        "-fx-padding: 20 40; " +
                        "-fx-background-radius: 10; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 5);");

        // Redirige vers la vue de réservation
        btnReserver.setOnAction(e -> {
            // On passe la voiture sélectionnée à la réservation
            app.Main.showReservationView();
        });

        // Ajouter un espace avant le bouton
        Pane spacer = new Pane();
        spacer.setPrefHeight(20);

        detailsColumn.getChildren().addAll(
                title, brandModel, annee, priceBox, dispoBox,
                optionsBox, specsBox, spacer, btnReserver);

        mainRow.getChildren().addAll(imageColumn, detailsColumn);
        content.getChildren().addAll(topBar, mainRow);

        return content;
    }

    private ImageView loadImage(String path, double height, double width) {
        ImageView imageView = new ImageView();
        try {
            // Normaliser le chemin comme dans CatalogueView
            if (!path.startsWith("images/") && !path.startsWith("/images/")) {
                path = "images/" + path;
            }

            if (path.startsWith("/images/")) {
                path = path.substring(1);
            }

            java.io.InputStream stream = getClass().getResourceAsStream(path);

            if (stream == null) {
                stream = getClass().getResourceAsStream("/" + path);
            }

            if (stream != null) {
                Image image = new Image(stream);
                imageView.setImage(image);
                imageView.setFitHeight(height);
                imageView.setFitWidth(width);
                imageView.setPreserveRatio(true);
            } else {
                throw new Exception("Image non trouvée: " + path);
            }
        } catch (Exception e) {
            System.err.println("Erreur chargement image: " + path + " - " + e.getMessage());
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
            if (!text.equals("Accueil")) {
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
            if (!text.equals("Accueil")) {
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