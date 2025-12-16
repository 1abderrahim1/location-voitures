package app.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class AdminView {
    private BorderPane view;
    private TableView<Reservation> reservationTable;
    private ScrollPane scrollPane;

    public AdminView() {
        createView();
    }

    private void createView() {
        view = new BorderPane();
        view.setStyle("-fx-background-color: #f7fafc;");

        // HEADER
        VBox header = createHeader();
        view.setTop(header);

        // CONTENU PRINCIPAL AVEC SCROLL
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");

        VBox mainContent = createMainContent();
        scrollPane.setContent(mainContent);
        view.setCenter(scrollPane);
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

        // BARRE DE RECHERCHE ADMIN
        HBox searchBox = new HBox(10);
        searchBox.getStyleClass().add("search-bar");
        searchBox.setPadding(new Insets(5, 10, 5, 10));
        searchBox.setStyle("-fx-background-color: white; -fx-background-radius: 5;");
        searchBox.setAlignment(Pos.CENTER_LEFT);

        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher réservations, clients, voitures...");
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
        Button btnLogout = createIconButton("Déconnexion", "images/logout_icon.png");
        btnLogout.setOnAction(e -> app.Main.showAccueilView());
        rightButtons.getChildren().addAll(btnLogout);

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
            if (menuItems[i].equals("Admin")) {
                menuBtn.setStyle(menuBtn.getStyle()
                        + " -fx-background-color: #f7fafc; -fx-text-fill: #1a365d; -fx-border-color: #3182ce;");
            }
            navBar.getChildren().add(menuBtn);
        }

        header.getChildren().addAll(topBar, navBar);
        return header;
    }

    private VBox createMainContent() {
        VBox content = new VBox(25);
        content.setPadding(new Insets(30));
        content.setAlignment(Pos.TOP_CENTER);
        content.setStyle("-fx-background-color: #f7fafc;");

        // Titre et barre d'outils
        HBox titleBar = new HBox();
        titleBar.setAlignment(Pos.CENTER_LEFT);
        titleBar.setPadding(new Insets(0, 0, 20, 0));

        Label title = new Label("👨‍💼 TABLEAU DE BORD ADMINISTRATEUR");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: 900; -fx-text-fill: #1a365d;");
        HBox.setHgrow(title, Priority.ALWAYS);

        Button btnAddCar = new Button("➕ Ajouter une voiture");
        btnAddCar.setStyle(
                "-fx-background-color: #3182ce; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 10 20; " +
                        "-fx-background-radius: 6; " +
                        "-fx-cursor: hand;");

        Button btnExport = new Button("📊 Exporter les données");
        btnExport.setStyle(
                "-fx-background-color: #38a169; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 10 20; " +
                        "-fx-background-radius: 6; " +
                        "-fx-cursor: hand;");

        titleBar.getChildren().addAll(title, btnAddCar, btnExport);

        // Cartes de statistiques
        HBox statsCards = new HBox(20);
        statsCards.setAlignment(Pos.CENTER);
        statsCards.setPadding(new Insets(0, 0, 20, 0));

        statsCards.getChildren().addAll(
                createStatCard("💰 Revenus totaux", "245,800 DA", "#3182ce", "images/money_icon.png"),
                createStatCard("🚗 Voitures disponibles", "8/12", "#38a169", "images/voiture_icon.png"),
                createStatCard("📅 Réservations actives", "5", "#d69e2e", "images/reservation_icon.png"),
                createStatCard("👥 Clients inscrits", "42", "#805ad5", "images/users_icon.png"));

        // Graphique et tableau
        HBox chartsAndTable = new HBox(30);
        chartsAndTable.setAlignment(Pos.TOP_CENTER);
        chartsAndTable.setPadding(new Insets(0, 0, 30, 0));

        VBox leftColumn = new VBox(20);
        leftColumn.setPrefWidth(600);

        // Graphique
        VBox chartContainer = new VBox(15);
        chartContainer.setPadding(new Insets(20));
        chartContainer.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-color: #e2e8f0; " +
                        "-fx-border-radius: 10; " +
                        "-fx-border-width: 1; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 2);");

        Label chartTitle = new Label("📈 Réservations par mois");
        chartTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: 700; -fx-text-fill: #2d3748;");

        BarChart<String, Number> barChart = createBarChart();
        chartContainer.getChildren().addAll(chartTitle, barChart);

        // Tableau des réservations récentes
        VBox tableContainer = new VBox(15);
        tableContainer.setPadding(new Insets(20));
        tableContainer.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-color: #e2e8f0; " +
                        "-fx-border-radius: 10; " +
                        "-fx-border-width: 1; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 2);");

        Label tableTitle = new Label("📋 Réservations récentes");
        tableTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: 700; -fx-text-fill: #2d3748;");

        reservationTable = createReservationTable();
        tableContainer.getChildren().addAll(tableTitle, reservationTable);

        leftColumn.getChildren().addAll(chartContainer, tableContainer);

        // Colonne droite - Actions rapides
        VBox rightColumn = new VBox(20);
        rightColumn.setPrefWidth(350);

        VBox quickActions = new VBox(15);
        quickActions.setPadding(new Insets(20));
        quickActions.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-color: #e2e8f0; " +
                        "-fx-border-radius: 10; " +
                        "-fx-border-width: 1; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 2);");

        Label actionsTitle = new Label("⚡ Actions rapides");
        actionsTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: 700; -fx-text-fill: #2d3748;");

        VBox actionButtons = new VBox(10);
        actionButtons.setPadding(new Insets(10, 0, 0, 0));

        Button btnManageCars = createActionButton("Gérer le parc auto", "images/voiture_icon.png");
        Button btnManageReservations = createActionButton("Gérer les réservations", "images/reservation_icon.png");
        Button btnManageUsers = createActionButton("Gérer les utilisateurs", "images/users_icon.png");
        Button btnViewReports = createActionButton("Voir les rapports", "images/report_icon.png");
        Button btnSystemSettings = createActionButton("Paramètres système", "images/settings_icon.png");

        // Ajout des actions aux boutons
        btnManageCars.setOnAction(e -> System.out.println("Gestion du parc auto"));
        btnManageReservations.setOnAction(e -> System.out.println("Gestion des réservations"));
        btnManageUsers.setOnAction(e -> System.out.println("Gestion des utilisateurs"));
        btnViewReports.setOnAction(e -> System.out.println("Voir les rapports"));
        btnSystemSettings.setOnAction(e -> System.out.println("Paramètres système"));

        actionButtons.getChildren().addAll(btnManageCars, btnManageReservations, btnManageUsers, btnViewReports,
                btnSystemSettings);
        quickActions.getChildren().addAll(actionsTitle, actionButtons);

        // Section notifications
        VBox notifications = new VBox(15);
        notifications.setPadding(new Insets(20));
        notifications.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-color: #e2e8f0; " +
                        "-fx-border-radius: 10; " +
                        "-fx-border-width: 1; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 2);");

        Label notifTitle = new Label("🔔 Notifications récentes");
        notifTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: 700; -fx-text-fill: #2d3748;");

        VBox notificationList = new VBox(10);
        notificationList.setPadding(new Insets(10, 0, 0, 0));

        addNotification(notificationList, "⚠️", "Toyota Highlander nécessite révision", "Il y a 2 jours");
        addNotification(notificationList, "📅", "3 réservations à venir aujourd'hui", "Il y a 1 jour");
        addNotification(notificationList, "💰", "Paiement en attente - Réservation #4567", "Il y a 3 jours");
        addNotification(notificationList, "✅", "Nouvel utilisateur inscrit", "Il y a 5 jours");
        addNotification(notificationList, "🚗", "Nouvelle voiture ajoutée au catalogue", "Il y a 1 semaine");

        notifications.getChildren().addAll(notifTitle, notificationList);
        rightColumn.getChildren().addAll(quickActions, notifications);

        chartsAndTable.getChildren().addAll(leftColumn, rightColumn);
        content.getChildren().addAll(titleBar, statsCards, chartsAndTable);

        return content;
    }

    private VBox createStatCard(String title, String value, String color, String iconPath) {
        VBox card = new VBox(10);
        card.setPrefWidth(200);
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.CENTER);
        card.setStyle(
                "-fx-background-color: " + color + "; " +
                        "-fx-background-radius: 10; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 4);");

        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER);

        ImageView icon = loadImage(iconPath, 24, 24);
        icon.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 600; -fx-text-fill: rgba(255,255,255,0.9);");

        header.getChildren().addAll(icon, titleLabel);

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: 900; -fx-text-fill: white;");

        card.getChildren().addAll(header, valueLabel);
        return card;
    }

    private BarChart<String, Number> createBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        barChart.setTitle("Réservations mensuelles - 2024");
        barChart.setLegendVisible(false);
        barChart.setPrefHeight(250);

        // Style
        barChart.setStyle("-fx-background-color: transparent;");
        xAxis.setStyle("-fx-text-fill: #4a5568; -fx-font-size: 12px;");
        yAxis.setStyle("-fx-text-fill: #4a5568; -fx-font-size: 12px;");

        // Données adaptées pour l'Algérie (exemple)
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Janv", 12));
        series.getData().add(new XYChart.Data<>("Fév", 18));
        series.getData().add(new XYChart.Data<>("Mars", 22));
        series.getData().add(new XYChart.Data<>("Avr", 25));
        series.getData().add(new XYChart.Data<>("Mai", 28));
        series.getData().add(new XYChart.Data<>("Juin", 30));
        series.getData().add(new XYChart.Data<>("Juil", 35));
        series.getData().add(new XYChart.Data<>("Août", 32));
        series.getData().add(new XYChart.Data<>("Sept", 27));
        series.getData().add(new XYChart.Data<>("Oct", 24));
        series.getData().add(new XYChart.Data<>("Nov", 20));
        series.getData().add(new XYChart.Data<>("Déc", 15));

        barChart.getData().add(series);

        // Colorer les barres
        for (XYChart.Data<String, Number> data : series.getData()) {
            data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                if (newNode != null) {
                    newNode.setStyle("-fx-bar-fill: #3182ce;");
                }
            });
        }

        return barChart;
    }

    private TableView<Reservation> createReservationTable() {
        TableView<Reservation> table = new TableView<>();
        table.setPrefHeight(300);
        table.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");

        // Colonnes
        TableColumn<Reservation, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        colId.setPrefWidth(80);

        TableColumn<Reservation, String> colClient = new TableColumn<>("Client");
        colClient.setCellValueFactory(cellData -> cellData.getValue().clientProperty());
        colClient.setPrefWidth(150);

        TableColumn<Reservation, String> colVoiture = new TableColumn<>("Voiture");
        colVoiture.setCellValueFactory(cellData -> cellData.getValue().voitureProperty());
        colVoiture.setPrefWidth(120);

        TableColumn<Reservation, String> colDates = new TableColumn<>("Dates");
        colDates.setCellValueFactory(cellData -> cellData.getValue().datesProperty());
        colDates.setPrefWidth(120);

        TableColumn<Reservation, String> colStatut = new TableColumn<>("Statut");
        colStatut.setCellValueFactory(cellData -> cellData.getValue().statutProperty());
        colStatut.setPrefWidth(100);

        TableColumn<Reservation, String> colMontant = new TableColumn<>("Montant (DA)");
        colMontant.setCellValueFactory(cellData -> cellData.getValue().montantProperty());
        colMontant.setPrefWidth(120);

        table.getColumns().addAll(colId, colClient, colVoiture, colDates, colStatut, colMontant);

        // Données d'exemple adaptées pour l'Algérie
        table.getItems().addAll(
                new Reservation("#4567", "Ahmed B.", "Toyota RAV4", "15-22 Nov", "Confirmée", "31,500"),
                new Reservation("#4566", "Sarah M.", "Peugeot 208", "10-17 Nov", "En cours", "26,600"),
                new Reservation("#4565", "Karim L.", "BMW Série 5", "05-12 Nov", "Terminée", "91,000"),
                new Reservation("#4564", "Leila K.", "Hyundai Tucson", "01-08 Nov", "Annulée", "29,400"),
                new Reservation("#4563", "Omar S.", "Toyota Highlander", "28 Oct-4 Nov", "Terminée", "39,900"),
                new Reservation("#4562", "Fatima Z.", "Renault Clio", "20-27 Oct", "Terminée", "22,500"),
                new Reservation("#4561", "Yacine T.", "Mercedes Classe C", "15-20 Oct", "Confirmée", "65,000"),
                new Reservation("#4560", "Nadia R.", "Dacia Logan", "10-15 Oct", "Terminée", "18,000"));

        return table;
    }

    private Button createActionButton(String text, String iconPath) {
        Button btn = new Button(text);
        btn.setStyle(
                "-fx-background-color: #f7fafc; " +
                        "-fx-text-fill: #4a5568; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 12 15; " +
                        "-fx-border-color: #e2e8f0; " +
                        "-fx-border-width: 1; " +
                        "-fx-border-radius: 6; " +
                        "-fx-background-radius: 6; " +
                        "-fx-alignment: center-left; " +
                        "-fx-cursor: hand;");

        ImageView icon = loadImage(iconPath, 16, 16);
        btn.setGraphic(icon);

        btn.setOnMouseEntered(e -> {
            btn.setStyle(
                    "-fx-background-color: #edf2f7; " +
                            "-fx-text-fill: #1a365d; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: 600; " +
                            "-fx-padding: 12 15; " +
                            "-fx-border-color: #3182ce; " +
                            "-fx-border-width: 1; " +
                            "-fx-border-radius: 6; " +
                            "-fx-background-radius: 6; " +
                            "-fx-alignment: center-left;");
        });

        btn.setOnMouseExited(e -> {
            btn.setStyle(
                    "-fx-background-color: #f7fafc; " +
                            "-fx-text-fill: #4a5568; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: 600; " +
                            "-fx-padding: 12 15; " +
                            "-fx-border-color: #e2e8f0; " +
                            "-fx-border-width: 1; " +
                            "-fx-border-radius: 6; " +
                            "-fx-background-radius: 6; " +
                            "-fx-alignment: center-left;");
        });

        return btn;
    }

    private void addNotification(VBox container, String emoji, String text, String time) {
        HBox notification = new HBox(10);
        notification.setPadding(new Insets(10));
        notification.setStyle(
                "-fx-background-color: #f7fafc; " +
                        "-fx-background-radius: 6; " +
                        "-fx-border-color: #e2e8f0; " +
                        "-fx-border-radius: 6; " +
                        "-fx-border-width: 1;");

        Label emojiLabel = new Label(emoji);
        emojiLabel.setStyle("-fx-font-size: 16px;");

        VBox textBox = new VBox(2);
        Label textLabel = new Label(text);
        textLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #4a5568; -fx-font-weight: 500;");
        textLabel.setWrapText(true);

        Label timeLabel = new Label(time);
        timeLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #a0aec0;");

        textBox.getChildren().addAll(textLabel, timeLabel);
        HBox.setHgrow(textBox, Priority.ALWAYS);

        notification.getChildren().addAll(emojiLabel, textBox);
        container.getChildren().add(notification);
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
            if (!text.equals("Admin")) {
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
            if (!text.equals("Admin")) {
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
                    // Déjà sur cette page
                    break;
            }
        });

        return btn;
    }

    // Classe interne pour les réservations (pour le tableau)
    public static class Reservation {
        private final String id;
        private final String client;
        private final String voiture;
        private final String dates;
        private final String statut;
        private final String montant;

        public Reservation(String id, String client, String voiture, String dates, String statut, String montant) {
            this.id = id;
            this.client = client;
            this.voiture = voiture;
            this.dates = dates;
            this.statut = statut;
            this.montant = montant;
        }

        public StringProperty idProperty() {
            return new SimpleStringProperty(id);
        }

        public StringProperty clientProperty() {
            return new SimpleStringProperty(client);
        }

        public StringProperty voitureProperty() {
            return new SimpleStringProperty(voiture);
        }

        public StringProperty datesProperty() {
            return new SimpleStringProperty(dates);
        }

        public StringProperty statutProperty() {
            return new SimpleStringProperty(statut);
        }

        public StringProperty montantProperty() {
            return new SimpleStringProperty(montant);
        }
    }

    public BorderPane getView() {
        return view;
    }
}