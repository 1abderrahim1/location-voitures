package app.view;

import app.Main;
import app.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ProfileView {
    private BorderPane view;
    private User currentUser;

    public ProfileView() {
        this.currentUser = Main.getCurrentUser();
        createView();
    }

    private void createView() {
        view = new BorderPane();
        view.setStyle("-fx-background-color: #f7fafc;");

        // HEADER avec "Mon Compte" comme page active
        HeaderView headerView = new HeaderView("Mon compte");
        view.setTop(headerView.getHeader());

        // CONTENU PRINCIPAL AVEC SCROLLPANE
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");

        VBox mainContent = createMainContent();
        scrollPane.setContent(mainContent);

        view.setCenter(scrollPane);
    }

    private VBox createMainContent() {
        VBox content = new VBox(30);
        content.setPadding(new Insets(40));
        content.setAlignment(Pos.TOP_CENTER);
        content.setStyle("-fx-background-color: #f7fafc;");

        Label title = new Label("👤 MON PROFIL");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: 900; -fx-text-fill: #1a365d;");

        if (currentUser == null) {
            // Message si pas connecté (normalement pas possible d'accéder ici)
            Label errorLabel = new Label("Veuillez vous connecter pour accéder à votre profil.");
            errorLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #e53e3e; -fx-padding: 20;");

            Button loginBtn = new Button("Se connecter");
            loginBtn.setStyle(
                    "-fx-background-color: #3182ce; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-weight: 600; " +
                            "-fx-padding: 12 30; " +
                            "-fx-background-radius: 6; " +
                            "-fx-cursor: hand;");
            loginBtn.setOnAction(e -> Main.showLoginView());

            content.getChildren().addAll(errorLabel, loginBtn);
            return content;
        }

        // Carte profil
        VBox profileCard = new VBox(25);
        profileCard.setPadding(new Insets(30));
        profileCard.setMaxWidth(600);
        profileCard.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: #e2e8f0; " +
                        "-fx-border-radius: 15; " +
                        "-fx-border-width: 1; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 15, 0, 0, 5);");

        // Avatar et nom
        HBox headerBox = new HBox(20);
        headerBox.setAlignment(Pos.CENTER_LEFT);

        ImageView avatar = loadImage("images/profil_icon.png", 80, 80);

        VBox nameBox = new VBox(5);
        Label nameLabel = new Label(currentUser.getNom());
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: 700; -fx-text-fill: #1a365d;");

        Label emailLabel = new Label(currentUser.getEmail());
        emailLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #718096;");

        nameBox.getChildren().addAll(nameLabel, emailLabel);
        headerBox.getChildren().addAll(avatar, nameBox);

        // Informations personnelles
        VBox infoSection = new VBox(15);
        Label infoTitle = new Label("INFORMATIONS PERSONNELLES");
        infoTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: 700; -fx-text-fill: #2d3748;");

        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(30);
        infoGrid.setVgap(15);
        infoGrid.setPadding(new Insets(10, 0, 0, 0));

        // Numéro de permis
        Label permisLabel = new Label("Numéro de permis:");
        permisLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");

        Label permisValue = new Label(currentUser.getPermis());
        permisValue.setStyle("-fx-font-size: 16px; -fx-text-fill: #2d3748;");

        infoGrid.add(permisLabel, 0, 0);
        infoGrid.add(permisValue, 1, 0);

        // Téléphone
        Label phoneLabel = new Label("Téléphone:");
        phoneLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");

        Label phoneValue = new Label(currentUser.getTelephone());
        phoneValue.setStyle("-fx-font-size: 16px; -fx-text-fill: #2d3748;");

        infoGrid.add(phoneLabel, 0, 1);
        infoGrid.add(phoneValue, 1, 1);

        // Adresse
        Label adresseLabel = new Label("Adresse:");
        adresseLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");

        Label adresseValue = new Label(currentUser.getAdresse());
        adresseValue.setStyle("-fx-font-size: 16px; -fx-text-fill: #2d3748;");

        infoGrid.add(adresseLabel, 0, 2);
        infoGrid.add(adresseValue, 1, 2);

        infoSection.getChildren().addAll(infoTitle, infoGrid);

        // Boutons d'actions
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));

        Button btnModifier = new Button("Modifier le profil");
        btnModifier.setStyle(
                "-fx-background-color: #3182ce; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 12 25; " +
                        "-fx-background-radius: 6; " +
                        "-fx-cursor: hand;");
        btnModifier.setOnAction(e -> {
            // Ici on pourrait ouvrir un formulaire de modification
            System.out.println("Modification du profil");
        });

        Button btnDeconnexion = new Button("Déconnexion");
        btnDeconnexion.setStyle(
                "-fx-background-color: #e53e3e; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 12 25; " +
                        "-fx-background-radius: 6; " +
                        "-fx-cursor: hand;");
        btnDeconnexion.setOnAction(e -> {
            Main.logout();
        });

        buttonBox.getChildren().addAll(btnModifier, btnDeconnexion);

        // Section réservations récentes
        VBox reservationsSection = new VBox(15);
        reservationsSection.setPadding(new Insets(20, 0, 0, 0));

        Label resTitle = new Label("📅 RÉSERVATIONS RÉCENTES");
        resTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 700; -fx-text-fill: #2d3748;");

        VBox reservationsList = new VBox(10);

        // Exemple de réservation
        VBox reservationItem = createReservationItem("Toyota RAV4", "15-22 Nov 2024", "31,500 DA", "Confirmée");
        VBox reservationItem2 = createReservationItem("Peugeot 208", "01-08 Nov 2024", "26,600 DA", "Terminée");

        Button btnVoirToutes = new Button("Voir toutes mes réservations");
        btnVoirToutes.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: #3182ce; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 10; " +
                        "-fx-border-color: #3182ce; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 5; " +
                        "-fx-cursor: hand;");

        reservationsList.getChildren().addAll(reservationItem, reservationItem2, btnVoirToutes);
        reservationsSection.getChildren().addAll(resTitle, reservationsList);

        // Ajouter un espace en bas pour le scroll
        Pane bottomSpacer = new Pane();
        bottomSpacer.setPrefHeight(50);

        profileCard.getChildren().addAll(
                headerBox,
                infoSection,
                buttonBox,
                reservationsSection,
                bottomSpacer);

        content.getChildren().addAll(title, profileCard);

        return content;
    }

    private VBox createReservationItem(String voiture, String dates, String prix, String statut) {
        VBox item = new VBox(10);
        item.setPadding(new Insets(15));
        item.setStyle(
                "-fx-background-color: #f7fafc; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-color: #e2e8f0; " +
                        "-fx-border-radius: 8; " +
                        "-fx-border-width: 1;");

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label voitureLabel = new Label(voiture);
        voitureLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 600; -fx-text-fill: #2d3748;");

        HBox.setHgrow(voitureLabel, Priority.ALWAYS);

        Label statutLabel = new Label(statut);
        if (statut.equals("Confirmée")) {
            statutLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: 600; -fx-text-fill: #38a169; " +
                    "-fx-background-color: #c6f6d5; -fx-padding: 4 8; -fx-background-radius: 4;");
        } else {
            statutLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: 600; -fx-text-fill: #718096; " +
                    "-fx-background-color: #e2e8f0; -fx-padding: 4 8; -fx-background-radius: 4;");
        }

        header.getChildren().addAll(voitureLabel, statutLabel);

        HBox details = new HBox(20);
        details.setAlignment(Pos.CENTER_LEFT);

        Label datesLabel = new Label("📅 " + dates);
        datesLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #4a5568;");

        Label prixLabel = new Label("💰 " + prix);
        prixLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #4a5568;");

        details.getChildren().addAll(datesLabel, prixLabel);

        item.getChildren().addAll(header, details);
        return item;
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