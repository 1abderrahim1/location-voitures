package app.view;

import app.Main;
import app.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Label;

public class ReservationView {
    private BorderPane view;
    private ComboBox<String> voitureCombo;
    private DatePicker dateDebutPicker;
    private DatePicker dateFinPicker;
    private CheckBox cbGPS, cbAssurance, cbSiegeEnfant;

    // Champs d'information client
    private TextField txtNom;
    private TextField txtPermis;
    private TextField txtPhone;

    // Bouton de réservation (référence directe)
    private Button btnReserver;

    // Composants du récapitulatif
    private Label recapVoiture, recapPrix, recapDuree, recapDateDebut, recapDateFin;
    private Label recapSousTotal, recapOptions, recapGrandTotal;
    private ImageView carImage; // Référence à l'image de la voiture

    // Map pour associer les voitures à leurs images
    private Map<String, String> voitureImages;

    public ReservationView() {
        // Initialiser la map des images
        initVoitureImages();
        createView();
        prefillClientInfo(); // Pré-remplir si connecté
    }

    private void initVoitureImages() {
        voitureImages = new HashMap<>();
        // Ajouter les images correspondant aux voitures du ComboBox avec les noms EXACTS
        voitureImages.put("Toyota RAV4", "images/Toyota rav4.jpg");
        voitureImages.put("Hyundai Tucson", "images/hyundai tucson.jpg");
        voitureImages.put("Peugeot 208", "images/peugeot 208.jpg");
        voitureImages.put("BMW Série 5", "images/BMW serie 5.jpg");
        voitureImages.put("Toyota Highlander", "images/toyota highlander.jpg");

        // Image par défaut si les spécifiques n'existent pas
        voitureImages.put("DEFAULT", "images/voiture_icon.png");
    }

    // Méthode pour pré-remplir les informations client si connecté
    private void prefillClientInfo() {
        User currentUser = Main.getCurrentUser();
        if (currentUser != null) {
            // Pré-remplir les champs avec les infos du profil
            txtNom.setText(currentUser.getNom());
            txtPermis.setText(currentUser.getPermis());
            txtPhone.setText(currentUser.getTelephone());
            
            // Ajouter une note indiquant que les infos viennent du profil
            txtNom.setTooltip(new Tooltip("Pré-rempli depuis votre profil"));
            txtPermis.setTooltip(new Tooltip("Pré-rempli depuis votre profil"));
            txtPhone.setTooltip(new Tooltip("Pré-rempli depuis votre profil"));
            
            // Style pour indiquer que c'est pré-rempli
            String prefilledStyle = "-fx-background-color: #f0fff4; " +
                                   "-fx-border-color: #9ae6b4; " +
                                   "-fx-border-width: 1; " +
                                   "-fx-border-radius: 8; " +
                                   "-fx-background-radius: 8; " +
                                   "-fx-padding: 12; " +
                                   "-fx-font-size: 15px;";
            
            txtNom.setStyle(prefilledStyle);
            txtPermis.setStyle(prefilledStyle);
            txtPhone.setStyle(prefilledStyle);
            
            // Les champs sont pré-remplis, mettre à jour l'état du bouton
            updateBoutonReservation();
            
            System.out.println("Informations client pré-remplies depuis le profil");
        }
    }

    private void createView() {
        view = new BorderPane();
        view.setStyle("-fx-background-color: #f7fafc;");

        // HEADER
        HeaderView headerView = new HeaderView("Réservation");
        view.setTop(headerView.getHeader());

        // CONTENU PRINCIPAL avec ScrollPane pour tout voir
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");

        VBox mainContent = createMainContent();
        scrollPane.setContent(mainContent);
        view.setCenter(scrollPane);
    }

    private VBox createMainContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30, 40, 50, 40));
        content.setAlignment(Pos.TOP_CENTER);

        // Titre
        Label title = new Label("📅 RÉSERVATION DE VOITURE");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: 900; -fx-text-fill: #1a365d;");

        Label subtitle = new Label("Réservez votre voiture en quelques étapes");
        subtitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #718096;");
        
        // Afficher le statut de connexion
        User currentUser = Main.getCurrentUser();
        if (currentUser != null) {
            Label statusLabel = new Label("✅ Connecté en tant que " + currentUser.getNom());
            statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #38a169; -fx-font-weight: 600; -fx-padding: 5 0;");
            content.getChildren().add(statusLabel);
        } else {
            Label statusLabel = new Label("⚠️ Non connecté - Les informations ne seront pas sauvegardées");
            statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #d69e2e; -fx-font-weight: 600; -fx-padding: 5 0;");
            content.getChildren().add(statusLabel);
        }

        // Conteneur principal
        HBox mainContainer = new HBox(40);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setPadding(new Insets(20, 0, 0, 0));

        // Colonne gauche - Formulaire
        VBox formColumn = createFormColumn();
        formColumn.setPrefWidth(550);

        // Colonne droite - Récapitulatif
        VBox recapColumn = createRecapColumn();
        recapColumn.setPrefWidth(500);

        mainContainer.getChildren().addAll(formColumn, recapColumn);
        content.getChildren().addAll(title, subtitle, mainContainer);

        return content;
    }

    private VBox createFormColumn() {
        VBox formColumn = new VBox(25);
        formColumn.setPadding(new Insets(30));
        formColumn.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: #e2e8f0; " +
                        "-fx-border-radius: 15; " +
                        "-fx-border-width: 1; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 15, 0, 0, 5);");

        Label formTitle = new Label("DÉTAILS DE LA RÉSERVATION");
        formTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 800; -fx-text-fill: #2d3748;");

        // Sélection de voiture
        VBox voitureSection = new VBox(10);
        Label voitureLabel = new Label("Voiture sélectionnée");
        voitureLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");

        voitureCombo = new ComboBox<>();
        voitureCombo.getItems().addAll(
                "Toyota RAV4 - 4,500 DA/jour",
                "Hyundai Tucson - 4,200 DA/jour",
                "Peugeot 208 - 3,800 DA/jour",
                "BMW Série 5 - 13,000 DA/jour",
                "Toyota Highlander - 5,700 DA/jour");
        voitureCombo.setValue("Toyota RAV4 - 4,500 DA/jour");
        voitureCombo.setStyle(getFieldStyle() + " -fx-padding: 10;");
        voitureCombo.setOnAction(e -> {
            updateRecap();
            updateCarImage(); // Mettre à jour l'image quand la voiture change
        });

        voitureSection.getChildren().addAll(voitureLabel, voitureCombo);

        // Dates
        GridPane datesGrid = new GridPane();
        datesGrid.setHgap(20);
        datesGrid.setVgap(15);

        Label dateDebutLabel = new Label("Date de début");
        dateDebutLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");

        dateDebutPicker = new DatePicker(LocalDate.now().plusDays(1));
        dateDebutPicker.setStyle(getFieldStyle());
        dateDebutPicker.setOnAction(e -> updateRecap());

        Label dateFinLabel = new Label("Date de fin");
        dateFinLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");

        dateFinPicker = new DatePicker(LocalDate.now().plusDays(7));
        dateFinPicker.setStyle(getFieldStyle());
        dateFinPicker.setOnAction(e -> updateRecap());

        datesGrid.add(dateDebutLabel, 0, 0);
        datesGrid.add(dateDebutPicker, 0, 1);
        datesGrid.add(dateFinLabel, 1, 0);
        datesGrid.add(dateFinPicker, 1, 1);

        // Informations client (OBLIGATOIRE)
        VBox clientSection = new VBox(10);
        Label clientLabel = new Label("Informations client *");
        clientLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568; -fx-font-size: 16px;");
        
        // Bouton pour se connecter si non connecté
        User currentUser = Main.getCurrentUser();
        if (currentUser == null) {
            HBox loginPrompt = new HBox(10);
            loginPrompt.setAlignment(Pos.CENTER_LEFT);
            loginPrompt.setPadding(new Insets(0, 0, 10, 0));
            
            Label loginText = new Label("Pour pré-remplir automatiquement :");
            loginText.setStyle("-fx-font-size: 14px; -fx-text-fill: #718096;");
            
            Button btnConnect = new Button("Se connecter");
            btnConnect.setStyle(
                "-fx-background-color: #3182ce; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 12px; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 6 12; " +
                "-fx-background-radius: 4; " +
                "-fx-cursor: hand;");
            btnConnect.setOnAction(e -> Main.showLoginViewForReservation());
            
            loginPrompt.getChildren().addAll(loginText, btnConnect);
            clientSection.getChildren().add(loginPrompt);
        } else {
            // Message indiquant que les infos viennent du profil
            HBox profileInfo = new HBox(10);
            profileInfo.setAlignment(Pos.CENTER_LEFT);
            profileInfo.setPadding(new Insets(0, 0, 10, 0));
            
            Label profileText = new Label("✓ Informations pré-remplies depuis votre profil");
            profileText.setStyle("-fx-font-size: 14px; -fx-text-fill: #38a169; -fx-font-weight: 500;");
            
            Button btnEditProfile = new Button("Modifier profil");
            btnEditProfile.setStyle(
                "-fx-background-color: transparent; " +
                "-fx-text-fill: #3182ce; " +
                "-fx-font-size: 12px; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 4 8; " +
                "-fx-border-color: #3182ce; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 3; " +
                "-fx-cursor: hand;");
            btnEditProfile.setOnAction(e -> Main.showProfileView());
            
            profileInfo.getChildren().addAll(profileText, btnEditProfile);
            clientSection.getChildren().add(profileInfo);
        }

        txtNom = new TextField();
        txtNom.setPromptText("Nom complet *");
        txtNom.setStyle(getFieldStyle());

        txtPermis = new TextField();
        txtPermis.setPromptText("Numéro de permis *");
        txtPermis.setStyle(getFieldStyle());

        txtPhone = new TextField();
        txtPhone.setPromptText("Téléphone * (ex: 0551-23-45-67)");
        txtPhone.setStyle(getFieldStyle());

        // Si non connecté, ajouter un lien vers la création de compte
        if (currentUser == null) {
            Hyperlink linkCreateAccount = new Hyperlink("Créer un compte pour sauvegarder mes informations");
            linkCreateAccount.setStyle("-fx-text-fill: #3182ce; -fx-font-weight: 500; -fx-font-size: 13px;");
            linkCreateAccount.setOnAction(e -> Main.showRegisterView());
            clientSection.getChildren().add(linkCreateAccount);
        }

        clientSection.getChildren().addAll(txtNom, txtPermis, txtPhone);

        // Options supplémentaires
        VBox optionsSection = new VBox(15);
        Label optionsLabel = new Label("Options supplémentaires");
        optionsLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");

        VBox optionsList = new VBox(10);
        cbGPS = new CheckBox("GPS (+ 500 DA/jour)");
        cbAssurance = new CheckBox("Assurance tout risque (+ 1,000 DA/jour)");
        cbSiegeEnfant = new CheckBox("Siège enfant (+ 300 DA/jour)");

        // Ajouter les listeners pour mettre à jour le récap
        cbGPS.selectedProperty().addListener((obs, oldVal, newVal) -> updateRecap());
        cbAssurance.selectedProperty().addListener((obs, oldVal, newVal) -> updateRecap());
        cbSiegeEnfant.selectedProperty().addListener((obs, oldVal, newVal) -> updateRecap());

        for (CheckBox cb : new CheckBox[] { cbGPS, cbAssurance, cbSiegeEnfant }) {
            cb.setStyle("-fx-text-fill: #4a5568; -fx-font-size: 14px;");
        }

        optionsList.getChildren().addAll(cbGPS, cbAssurance, cbSiegeEnfant);
        optionsSection.getChildren().addAll(optionsLabel, optionsList);

        // Bouton de réservation
        btnReserver = new Button("💳 CONFIRMER LA RÉSERVATION");
        btnReserver.setStyle(
                "-fx-background-color: #cbd5e0; " +
                        "-fx-text-fill: #718096; " +
                        "-fx-font-size: 18px; " +
                        "-fx-font-weight: 800; " +
                        "-fx-padding: 20; " +
                        "-fx-background-radius: 10; " +
                        "-fx-cursor: default; " +
                        "-fx-max-width: infinity; " +
                        "-fx-effect: none;");
        btnReserver.setOnAction(e -> {
            if (validateReservation()) {
                showConfirmationPopup();
            }
        });

        // Désactiver le bouton initialement
        btnReserver.setDisable(true);

        // Ajouter les listeners pour la validation en temps réel
        txtNom.textProperty().addListener((obs, oldVal, newVal) -> updateBoutonReservation());
        txtPermis.textProperty().addListener((obs, oldVal, newVal) -> updateBoutonReservation());
        txtPhone.textProperty().addListener((obs, oldVal, newVal) -> updateBoutonReservation());

        formColumn.getChildren().addAll(formTitle, voitureSection, datesGrid, clientSection, optionsSection,
                btnReserver);

        return formColumn;
    }

    // Méthode pour mettre à jour l'image de la voiture
    private void updateCarImage() {
        try {
            String selectedVoiture = voitureCombo.getValue();
            if (selectedVoiture != null && !selectedVoiture.isEmpty()) {
                // Extraire le nom de la voiture (avant le "-")
                String voitureName = selectedVoiture.split(" - ")[0];

                // Récupérer le chemin de l'image correspondante
                String imagePath = voitureImages.get(voitureName);
                if (imagePath == null) {
                    imagePath = voitureImages.get("DEFAULT");
                }

                System.out.println("Tentative de chargement de l'image: " + imagePath + " pour " + voitureName);

                // Essayer de charger l'image
                Image image = null;
                try {
                    image = new Image(getClass().getResourceAsStream(imagePath));
                    if (image.isError()) {
                        throw new Exception("Erreur lors du chargement de l'image");
                    }
                } catch (Exception e) {
                    System.err.println("Échec du chargement de " + imagePath + ", essai avec autres variations...");

                    // Essayer différentes variations
                    String[] variations = {
                            imagePath.toLowerCase(),
                            imagePath.replace(" ", "_"),
                            imagePath.replace(" ", ""),
                            "images/" + voitureName.toLowerCase().replace(" ", "") + ".jpg",
                            "images/" + voitureName.toLowerCase().replace(" ", "_") + ".jpg"
                    };

                    for (String variation : variations) {
                        try {
                            System.out.println("Essai avec: " + variation);
                            image = new Image(getClass().getResourceAsStream(variation));
                            if (!image.isError()) {
                                System.out.println("Succès avec: " + variation);
                                break;
                            }
                        } catch (Exception ex) {
                            // Continuer avec la prochaine variation
                        }
                    }

                    // Si toujours pas d'image, utiliser l'image par défaut
                    if (image == null || image.isError()) {
                        System.out.println("Utilisation de l'image par défaut");
                        image = new Image(getClass().getResourceAsStream(voitureImages.get("DEFAULT")));
                    }
                }

                // Mettre à jour l'ImageView
                if (carImage != null && image != null) {
                    carImage.setImage(image);
                    carImage.setFitHeight(120);
                    carImage.setFitWidth(200);
                    carImage.setPreserveRatio(true);
                    carImage.setStyle("-fx-background-color: #f7fafc; -fx-background-radius: 10; -fx-padding: 10;");
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image: " + e.getMessage());
            e.printStackTrace();
            // Charger l'image par défaut en cas d'erreur
            try {
                Image defaultImage = new Image(getClass().getResourceAsStream(voitureImages.get("DEFAULT")));
                if (carImage != null) {
                    carImage.setImage(defaultImage);
                }
            } catch (Exception ex) {
                System.err.println("Impossible de charger l'image par défaut: " + ex.getMessage());
            }
        }
    }

    // Méthode pour mettre à jour l'état du bouton de réservation
    private void updateBoutonReservation() {
        boolean tousRemplis = !txtNom.getText().trim().isEmpty() &&
                !txtPermis.getText().trim().isEmpty() &&
                !txtPhone.getText().trim().isEmpty();

        btnReserver.setDisable(!tousRemplis);

        if (tousRemplis) {
            btnReserver.setStyle(
                    "-fx-background-color: #38a169; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 18px; " +
                            "-fx-font-weight: 800; " +
                            "-fx-padding: 20; " +
                            "-fx-background-radius: 10; " +
                            "-fx-cursor: hand; " +
                            "-fx-max-width: infinity; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);");
        } else {
            btnReserver.setStyle(
                    "-fx-background-color: #cbd5e0; " +
                            "-fx-text-fill: #718096; " +
                            "-fx-font-size: 18px; " +
                            "-fx-font-weight: 800; " +
                            "-fx-padding: 20; " +
                            "-fx-background-radius: 10; " +
                            "-fx-cursor: default; " +
                            "-fx-max-width: infinity; " +
                            "-fx-effect: none;");
        }
    }

    private VBox createRecapColumn() {
        VBox recapColumn = new VBox(25);
        recapColumn.setPadding(new Insets(30));
        recapColumn.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: #e2e8f0; " +
                        "-fx-border-radius: 15; " +
                        "-fx-border-width: 1; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 15, 0, 0, 5);");

        Label recapTitle = new Label("📋 RÉCAPITULATIF");
        recapTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 800; -fx-text-fill: #2d3748;");

        // Image voiture - Créer l'ImageView et charger l'image initiale
        carImage = new ImageView();
        carImage.setFitHeight(120);
        carImage.setFitWidth(200);
        carImage.setPreserveRatio(true);
        carImage.setStyle("-fx-background-color: #f7fafc; -fx-background-radius: 10; -fx-padding: 10;");

        // Charger l'image initiale
        updateCarImage();

        // Détails de la réservation
        VBox recapDetails = new VBox(15);
        recapDetails.setPadding(new Insets(20, 0, 0, 0));
        recapDetails.setStyle("-fx-background-color: #f7fafc; -fx-background-radius: 10; -fx-padding: 15;");

        // Initialiser les labels du récap
        recapVoiture = new Label("Voiture: Toyota RAV4");
        recapPrix = new Label("Prix journalier: 4,500 DA");
        recapDuree = new Label("Durée: 7 jours");
        recapDateDebut = new Label("Date de début: " + LocalDate.now().plusDays(1));
        recapDateFin = new Label("Date de fin: " + LocalDate.now().plusDays(7));
        recapSousTotal = new Label("Sous-total: 31,500 DA");
        recapOptions = new Label("Options supplémentaires: 0 DA");
        recapGrandTotal = new Label("TOTAL: 31,500 DA");

        // Style des labels
        for (Label lbl : new Label[] { recapVoiture, recapPrix, recapDuree, recapDateDebut, recapDateFin,
                recapSousTotal, recapOptions }) {
            lbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #4a5568; -fx-padding: 3 0;");
        }

        recapGrandTotal
                .setStyle("-fx-font-size: 20px; -fx-font-weight: 900; -fx-text-fill: #38a169; -fx-padding: 10 0 0 0;");

        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));

        recapDetails.getChildren().addAll(
                recapVoiture, recapPrix, recapDuree, recapDateDebut, recapDateFin,
                separator, recapSousTotal, recapOptions, recapGrandTotal);

        // Informations importantes
        VBox infoBox = new VBox(10);
        infoBox.setPadding(new Insets(20, 0, 0, 0));
        infoBox.setStyle(
                "-fx-background-color: #fffaf0; -fx-background-radius: 10; -fx-padding: 15; -fx-border-color: #f6ad55; -fx-border-width: 1; -fx-border-radius: 10;");

        Label infoTitle = new Label("INFORMATIONS IMPORTANTES");
        infoTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: 700; -fx-text-fill: #d69e2e;");

        Label info1 = new Label("• Paiement à la prise en charge");
        Label info2 = new Label("• Pièces à fournir: permis + CNI");
        Label info3 = new Label("• Caution: 50,000 DA (remboursable)");
        Label info4 = new Label("• Contact: 0552-88-45-67");

        // Avantages de la connexion
        User currentUser = Main.getCurrentUser();
        if (currentUser == null) {
            Label info5 = new Label("• Avantage connexion: Infos sauvegardées + historique");
            info5.setStyle("-fx-font-size: 13px; -fx-text-fill: #3182ce; -fx-font-weight: bold;");
            infoBox.getChildren().add(info5);
        } else {
            Label info5 = new Label("✓ Connecté: Vos infos sont sauvegardées");
            info5.setStyle("-fx-font-size: 13px; -fx-text-fill: #38a169; -fx-font-weight: bold;");
            infoBox.getChildren().add(info5);
        }

        for (Label info : new Label[] { info1, info2, info3, info4 }) {
            info.setStyle("-fx-font-size: 13px; -fx-text-fill: #744210; -fx-padding: 2 0;");
        }

        infoBox.getChildren().addAll(infoTitle, info1, info2, info3, info4);

        recapColumn.getChildren().addAll(recapTitle, carImage, recapDetails, infoBox);

        return recapColumn;
    }

    // Méthode pour valider toute la réservation
    private boolean validateReservation() {
        // Valider les dates
        LocalDate debut = dateDebutPicker.getValue();
        LocalDate fin = dateFinPicker.getValue();

        if (debut == null || fin == null) {
            showAlert("Erreur", "Veuillez sélectionner les dates de début et de fin.");
            return false;
        }

        if (debut.isAfter(fin)) {
            showAlert("Erreur", "La date de début doit être avant la date de fin.");
            dateDebutPicker.requestFocus();
            return false;
        }

        if (debut.isBefore(LocalDate.now())) {
            showAlert("Erreur", "La date de début ne peut pas être dans le passé.");
            dateDebutPicker.requestFocus();
            return false;
        }

        // Valider les champs client
        if (txtNom.getText().trim().isEmpty()) {
            showAlert("Champ manquant", "Veuillez saisir votre nom complet.");
            txtNom.requestFocus();
            return false;
        }

        if (txtPermis.getText().trim().isEmpty()) {
            showAlert("Champ manquant", "Veuillez saisir votre numéro de permis.");
            txtPermis.requestFocus();
            return false;
        }

        if (txtPhone.getText().trim().isEmpty()) {
            showAlert("Champ manquant", "Veuillez saisir votre numéro de téléphone.");
            txtPhone.requestFocus();
            return false;
        }

        // Valider le format du téléphone (optionnel mais recommandé)
        String phone = txtPhone.getText().trim();
        if (!phone.matches("\\d{10}|\\d{4}-\\d{2}-\\d{2}-\\d{2}")) {
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
                txtPhone.requestFocus();
                return false;
            }
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

    private void updateRecap() {
        try {
            // Récupérer les valeurs
            String selectedVoiture = voitureCombo.getValue();
            LocalDate debut = dateDebutPicker.getValue();
            LocalDate fin = dateFinPicker.getValue();

            if (debut == null || fin == null)
                return;

            // Calculer la durée
            long duree = ChronoUnit.DAYS.between(debut, fin);
            if (duree < 1)
                duree = 1;

            // Extraire le prix de la voiture
            String voitureName = selectedVoiture.split(" - ")[0];
            String prixStr = selectedVoiture.split(" - ")[1].replace(" DA/jour", "").replace(",", "");
            double prixJournalier = Double.parseDouble(prixStr);

            // Calcul du sous-total
            double sousTotal = prixJournalier * duree;

            // Calcul des options
            double optionsTotal = 0;
            if (cbGPS.isSelected())
                optionsTotal += 500 * duree;
            if (cbAssurance.isSelected())
                optionsTotal += 1000 * duree;
            if (cbSiegeEnfant.isSelected())
                optionsTotal += 300 * duree;

            // TOTAL (pas de taxes en Algérie)
            double total = sousTotal + optionsTotal;

            // Mettre à jour les labels
            recapVoiture.setText("Voiture: " + voitureName);
            recapPrix.setText("Prix journalier: " + formatPrix(prixJournalier) + " DA");
            recapDuree.setText("Durée: " + duree + " jour" + (duree > 1 ? "s" : ""));
            recapDateDebut.setText("Date de début: " + debut.toString());
            recapDateFin.setText("Date de fin: " + fin.toString());
            recapSousTotal.setText("Sous-total: " + formatPrix(sousTotal) + " DA");
            recapOptions.setText("Options: " + formatPrix(optionsTotal) + " DA");
            recapGrandTotal.setText("TOTAL: " + formatPrix(total) + " DA");

        } catch (Exception e) {
            System.out.println("Erreur dans updateRecap: " + e.getMessage());
        }
    }

    private String formatPrix(double prix) {
        return String.format("%,.0f", prix).replace(",", " ");
    }

    private void showConfirmationPopup() {
        User currentUser = Main.getCurrentUser();
        String userInfo = "";
        
        if (currentUser != null) {
            userInfo = "Compte: " + currentUser.getEmail() + "\n" +
                      "Les informations ont été sauvegardées dans votre profil.\n\n";
        } else {
            userInfo = "⚠️ Non connecté - Les informations ne seront pas sauvegardées.\n" +
                      "Créez un compte pour garder un historique de vos réservations.\n\n";
        }
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Réservation confirmée");
        alert.setHeaderText("✅ Réservation confirmée avec succès !");
        alert.setContentText(
                userInfo +
                "Nom client: " + txtNom.getText() + "\n" +
                "Permis: " + txtPermis.getText() + "\n" +
                "Téléphone: " + txtPhone.getText() + "\n" +
                "Voiture: " + voitureCombo.getValue().split(" - ")[0] + "\n\n" +
                "Votre réservation a été enregistrée.\n" +
                "Vous recevrez un SMS de confirmation.\n" +
                "Présentez-vous à l'agence avec votre permis et CNI.\n\n" +
                "Référence: RES-" + System.currentTimeMillis());

        // Ajouter un bouton OK personnalisé
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait();

        // Optionnel: Réinitialiser le formulaire
        // resetForm();
    }

    // Méthode optionnelle pour réinitialiser le formulaire
    private void resetForm() {
        User currentUser = Main.getCurrentUser();
        
        // Ne pas effacer si pré-rempli depuis le profil
        if (currentUser == null) {
            txtNom.clear();
            txtPermis.clear();
            txtPhone.clear();
        }
        
        dateDebutPicker.setValue(LocalDate.now().plusDays(1));
        dateFinPicker.setValue(LocalDate.now().plusDays(7));
        cbGPS.setSelected(false);
        cbAssurance.setSelected(false);
        cbSiegeEnfant.setSelected(false);
        updateRecap();
        updateCarImage(); // Mettre à jour l'image aussi
        updateBoutonReservation(); // Mettre à jour l'état du bouton
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

    public BorderPane getView() {
        return view;
    }
}