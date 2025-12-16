package app.view;

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
    }

    private void initVoitureImages() {
        voitureImages = new HashMap<>();
        // Ajouter les images correspondant aux voitures du ComboBox avec les noms
        // EXACTS
        voitureImages.put("Toyota RAV4", "images/Toyota rav4.jpg");
        voitureImages.put("Hyundai Tucson", "images/hyundai tucson.jpg");
        voitureImages.put("Peugeot 208", "images/peugeot 208.jpg");
        voitureImages.put("BMW Série 5", "images/BMW serie 5.jpg");
        voitureImages.put("Toyota Highlander", "images/toyota highlander.jpg");

        // Image par défaut si les spécifiques n'existent pas
        voitureImages.put("DEFAULT", "images/voiture_icon.png");
    }

    private void createView() {
        view = new BorderPane();
        view.setStyle("-fx-background-color: #f7fafc;");

        // HEADER
        VBox header = createHeader();
        view.setTop(header);

        // CONTENU PRINCIPAL avec ScrollPane pour tout voir
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");

        VBox mainContent = createMainContent();
        scrollPane.setContent(mainContent);
        view.setCenter(scrollPane);
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.getStyleClass().add("header");
        header.setPadding(new Insets(0));

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
            if (menuItems[i].equals("Réservation")) {
                menuBtn.setStyle(menuBtn.getStyle() + " -fx-background-color: #f7fafc; -fx-text-fill: #1a365d;");
            }
            navBar.getChildren().add(menuBtn);
        }

        header.getChildren().addAll(topBar, navBar);
        return header;
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

        txtNom = new TextField();
        txtNom.setPromptText("Nom complet *");
        txtNom.setStyle(getFieldStyle());

        txtPermis = new TextField();
        txtPermis.setPromptText("Numéro de permis *");
        txtPermis.setStyle(getFieldStyle());

        txtPhone = new TextField();
        txtPhone.setPromptText("Téléphone * (ex: 0551-23-45-67)");
        txtPhone.setStyle(getFieldStyle());

        clientSection.getChildren().addAll(clientLabel, txtNom, txtPermis, txtPhone);

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

        // Ajouter une info sur les champs obligatoires
        Label info5 = new Label("• Tous les champs marqués * sont obligatoires");
        info5.setStyle("-fx-font-size: 13px; -fx-text-fill: #e53e3e; -fx-font-weight: bold;");

        for (Label info : new Label[] { info1, info2, info3, info4 }) {
            info.setStyle("-fx-font-size: 13px; -fx-text-fill: #744210; -fx-padding: 2 0;");
        }

        infoBox.getChildren().addAll(infoTitle, info1, info2, info3, info4, info5);

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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Réservation confirmée");
        alert.setHeaderText("✅ Réservation confirmée avec succès !");
        alert.setContentText(
                "Nom client: " + txtNom.getText() + "\n" +
                        "Permis: " + txtPermis.getText() + "\n" +
                        "Téléphone: " + txtPhone.getText() + "\n\n" +
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
        txtNom.clear();
        txtPermis.clear();
        txtPhone.clear();
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

    private ImageView loadImage(String path, double height, double width) {
        ImageView imageView = new ImageView();
        try {
            Image image = new Image(getClass().getResourceAsStream(path));
            imageView.setImage(image);
            imageView.setFitHeight(height);
            imageView.setFitWidth(width);
            imageView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Erreur de chargement d'image: " + path + " - " + e.getMessage());
            // Si l'image n'existe pas, créer un placeholder
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
            if (!text.equals("Réservation")) {
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
            if (!text.equals("Réservation")) {
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
                    // Déjà sur cette page
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