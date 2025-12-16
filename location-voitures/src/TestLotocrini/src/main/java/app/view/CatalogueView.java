package app.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class CatalogueView {
    private BorderPane view;
    private Slider prixSlider;
    private ComboBox<String> marqueCombo;
    private CheckBox cbSUV, cbCitadine, cbLuxe, cbUtilitaire;
    private CheckBox cbDispo;
    private Button btnAppliquer;
    private TextField searchField;
    private VBox categoriesContainer;

    // Liste de toutes les catégories et voitures pour le filtrage
    private List<Categorie> toutesCategories = new ArrayList<>();
    private List<Voiture> toutesVoitures = new ArrayList<>();

    // ========== COMPOSITION PATTERN ==========
    public abstract class ComponentCatalogue {
        protected String nom;
        protected ImageView icone;

        public ComponentCatalogue(String nom, String iconPath) {
            this.nom = nom;
            this.icone = loadImage(iconPath, 30, 30);
        }

        public abstract VBox afficher();
    }

    public class Categorie extends ComponentCatalogue {
        private List<ComponentCatalogue> enfants = new ArrayList<>();

        public Categorie(String nom, String iconPath) {
            super(nom, iconPath);
        }

        public void ajouterEnfant(ComponentCatalogue enfant) {
            enfants.add(enfant);
        }

        public List<ComponentCatalogue> getEnfants() {
            return enfants;
        }

        @Override
        public VBox afficher() {
            VBox categorieBox = new VBox(15);
            categorieBox.setPadding(new Insets(20));
            categorieBox.setStyle(
                    "-fx-background-color: white; " +
                            "-fx-background-radius: 10; " +
                            "-fx-border-color: #e2e8f0; " +
                            "-fx-border-radius: 10; " +
                            "-fx-border-width: 1; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 2);");

            HBox header = new HBox(10);
            header.setAlignment(Pos.CENTER_LEFT);

            Label nomLabel = new Label(nom);
            nomLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 700; -fx-text-fill: #1a365d;");

            header.getChildren().addAll(icone, nomLabel);

            FlowPane enfantsContainer = new FlowPane();
            enfantsContainer.setHgap(20);
            enfantsContainer.setVgap(20);
            enfantsContainer.setPadding(new Insets(15, 0, 0, 0));

            for (ComponentCatalogue enfant : enfants) {
                if (enfant instanceof Voiture) {
                    enfantsContainer.getChildren().add(enfant.afficher());
                } else if (enfant instanceof Categorie) {
                    // Pour les sous-catégories, on affiche aussi leurs voitures
                    Categorie sousCat = (Categorie) enfant;
                    for (ComponentCatalogue sousEnfant : sousCat.getEnfants()) {
                        if (sousEnfant instanceof Voiture) {
                            enfantsContainer.getChildren().add(sousEnfant.afficher());
                        }
                    }
                }
            }

            categorieBox.getChildren().addAll(header, enfantsContainer);
            return categorieBox;
        }
    }

    public class Voiture extends ComponentCatalogue {
        private String marque;
        private String modele;
        private double prix;
        private boolean disponible;
        private String type; // Nouveau champ pour le type de véhicule

        public Voiture(String nom, String marque, String modele, double prix, boolean disponible, String iconPath) {
            super(nom, iconPath);
            this.marque = marque;
            this.modele = modele;
            this.prix = prix;
            this.disponible = disponible;
            this.type = determinerType(nom); // Détermine le type automatiquement
        }

        private String determinerType(String nom) {
            // Logique simple pour déterminer le type basé sur le nom
            if (nom.toLowerCase().contains("rav4") || nom.toLowerCase().contains("highlander") ||
                    nom.toLowerCase().contains("tucson")) {
                return "SUV";
            } else if (nom.toLowerCase().contains("208")) {
                return "Citadine";
            } else if (nom.toLowerCase().contains("série 5")) {
                return "Luxe";
            }
            return "Autre";
        }

        @Override
        public VBox afficher() {
            VBox carte = new VBox(10);
            carte.setPrefWidth(250);
            carte.setPadding(new Insets(15));
            carte.setStyle(
                    "-fx-background-color: white; " +
                            "-fx-background-radius: 8; " +
                            "-fx-border-color: #e2e8f0; " +
                            "-fx-border-radius: 8; " +
                            "-fx-border-width: 1; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 3, 0, 0, 1); " +
                            "-fx-cursor: hand;");

            // Utiliser l'image spécifique de la voiture
            ImageView photo = new ImageView(this.icone.getImage());
            photo.setFitHeight(120);
            photo.setFitWidth(220);
            photo.setPreserveRatio(true);
            photo.setStyle("-fx-background-color: #f7fafc; -fx-background-radius: 5;");

            Label nomLabel = new Label(nom);
            nomLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 700; -fx-text-fill: #1a365d;");

            Label detailsLabel = new Label(marque + " - " + modele);
            detailsLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #718096;");

            // Format DA
            Label prixLabel = new Label(String.format("%,.0f DA / jour", prix));
            prixLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 800; -fx-text-fill: #2d3748;");

            HBox dispoBox = new HBox(8);
            dispoBox.setAlignment(Pos.CENTER_LEFT);
            javafx.scene.shape.Circle dispoCircle = new javafx.scene.shape.Circle(6);
            dispoCircle.setFill(disponible ? Color.GREEN : Color.RED);

            Label dispoLabel = new Label(disponible ? "Disponible" : "Non disponible");
            dispoLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 600;");
            dispoLabel.setTextFill(disponible ? Color.GREEN : Color.RED);

            dispoBox.getChildren().addAll(dispoCircle, dispoLabel);

            Button btnDetails = new Button("Voir détails");
            btnDetails.setStyle(
                    "-fx-background-color: transparent; " +
                            "-fx-text-fill: #3182ce; " +
                            "-fx-font-weight: 600; " +
                            "-fx-padding: 8 16; " +
                            "-fx-border-color: #3182ce; " +
                            "-fx-border-width: 2; " +
                            "-fx-border-radius: 5; " +
                            "-fx-cursor: hand;");
            btnDetails.setOnAction(e -> app.Main.showVoitureDetailView(this));

            carte.getChildren().addAll(photo, nomLabel, detailsLabel, prixLabel, dispoBox, btnDetails);
            return carte;
        }

        public double getPrix() {
            return prix;
        }

        public boolean isDisponible() {
            return disponible;
        }

        public String getMarque() {
            return marque;
        }

        public String getType() {
            return type;
        }

        public String getModele() {
            return modele;
        }

        public String getNom() {
            return nom;
        }
    }

    public CatalogueView() {
        createView();
        initDonneesCatalogue();
        setupFiltres();
    }

    private void createView() {
        view = new BorderPane();
        view.setStyle("-fx-background-color: #f7fafc;");

        // HEADER
        VBox header = createHeader();
        view.setTop(header);

        // CONTENU PRINCIPAL avec sidebar + catalogue
        HBox mainContent = new HBox();

        VBox sidebar = createSidebar();
        sidebar.setPrefWidth(280);

        VBox catalogueContent = createCatalogueContent();
        HBox.setHgrow(catalogueContent, Priority.ALWAYS);

        mainContent.getChildren().addAll(sidebar, catalogueContent);
        view.setCenter(mainContent);
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

        HBox logoBox = new HBox();
        logoBox.setAlignment(Pos.CENTER_LEFT);
        ImageView logoView = loadImage("images/lotocrini_logo_header.png", 50, 50);
        logoView.setScaleX(1.6);
        logoView.setScaleY(1.6);
        logoBox.getChildren().add(logoView);

        HBox searchBox = new HBox(10);
        searchBox.getStyleClass().add("search-bar");
        searchBox.setPadding(new Insets(5, 10, 5, 10));
        searchBox.setStyle("-fx-background-color: white; -fx-background-radius: 5;");
        searchBox.setAlignment(Pos.CENTER_LEFT);

        searchField = new TextField();
        searchField.setPromptText("Rechercher une voiture...");
        searchField.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-font-size: 14px; " +
                        "-fx-prompt-text-fill: #a0aec0;");
        HBox.setHgrow(searchField, Priority.ALWAYS);

        // Live search: update listing as user types or presses Enter
        searchField.textProperty().addListener((obs, oldV, newV) -> {
            afficherVoituresFiltrees();
        });
        searchField.setOnAction(e -> afficherVoituresFiltrees());

        ImageView searchIcon = loadImage("images/recherche_icon.png", 20, 20);
        HBox.setMargin(searchIcon, new Insets(0, 0, 0, 5));

        // Cliquer sur l'icône lance la recherche
        searchIcon.setOnMouseClicked(e -> afficherVoituresFiltrees());

        searchBox.getChildren().addAll(searchField, searchIcon);

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
            if (menuItems[i].equals("Catalogue")) {
                menuBtn.setStyle(menuBtn.getStyle() + " -fx-background-color: #f7fafc; -fx-text-fill: #1a365d;");
            }
            navBar.getChildren().add(menuBtn);
        }

        header.getChildren().addAll(topBar, navBar);
        return header;
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(30, 20, 30, 20));
        sidebar.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #e2e8f0; " +
                        "-fx-border-width: 0 1 0 0;");

        HBox filterTitle = new HBox(10);
        filterTitle.setAlignment(Pos.CENTER_LEFT);

        ImageView filterIcon = loadImage("images/filtre_icon.png", 24, 24);
        Label title = new Label("FILTRES");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: 700; -fx-text-fill: #1a365d;");

        filterTitle.getChildren().addAll(filterIcon, title);

        VBox prixFilter = new VBox(10);
        Label lblPrix = new Label("Prix maximum :");
        lblPrix.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");

        // Converti de 20-300€ à 3000-45000 DA (approx 150 DA pour 1€)
        prixSlider = new Slider(3000, 45000, 22500);
        prixSlider.setShowTickLabels(true);
        prixSlider.setShowTickMarks(true);
        prixSlider.setMajorTickUnit(10000);
        prixSlider.setMinorTickCount(4);

        Label prixValue = new Label("22.500 DA");
        prixValue.setStyle("-fx-font-weight: 600; -fx-text-fill: #1a365d;");

        prixSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            prixValue.setText(String.format("%,.0f DA", newVal.doubleValue()));
        });

        prixFilter.getChildren().addAll(lblPrix, prixSlider, prixValue);

        VBox marqueFilter = new VBox(10);
        Label lblMarque = new Label("Marque :");
        lblMarque.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");

        marqueCombo = new ComboBox<>();
        marqueCombo.getItems().addAll("Toutes", "Toyota", "BMW", "Mercedes", "Audi", "Peugeot", "Renault", "Hyundai");
        marqueCombo.setValue("Toutes");
        marqueCombo.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0;");

        marqueFilter.getChildren().addAll(lblMarque, marqueCombo);

        VBox typeFilter = new VBox(10);
        Label lblType = new Label("Type de véhicule :");
        lblType.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");

        VBox typeOptions = new VBox(8);
        cbSUV = new CheckBox("SUV");
        cbCitadine = new CheckBox("Citadine");
        cbLuxe = new CheckBox("Luxe");
        cbUtilitaire = new CheckBox("Utilitaire");

        for (CheckBox cb : new CheckBox[] { cbSUV, cbCitadine, cbLuxe, cbUtilitaire }) {
            cb.setStyle("-fx-text-fill: #4a5568;");
        }

        typeOptions.getChildren().addAll(cbSUV, cbCitadine, cbLuxe, cbUtilitaire);
        typeFilter.getChildren().addAll(lblType, typeOptions);

        VBox dispoFilter = new VBox(10);
        cbDispo = new CheckBox("Afficher uniquement les disponibles");
        cbDispo.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");
        dispoFilter.getChildren().add(cbDispo);

        btnAppliquer = new Button("Appliquer les filtres");
        btnAppliquer.setStyle(
                "-fx-background-color: #3182ce; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: 700; " +
                        "-fx-padding: 12; " +
                        "-fx-background-radius: 6; " +
                        "-fx-cursor: hand;");

        sidebar.getChildren().addAll(filterTitle, prixFilter, marqueFilter, typeFilter, dispoFilter, btnAppliquer);
        return sidebar;
    }

    private VBox createCatalogueContent() {
        VBox content = new VBox(25);
        content.setPadding(new Insets(30));

        Label titre = new Label("NOTRE CATALOGUE DE VOITURES");
        titre.setStyle("-fx-font-size: 28px; -fx-font-weight: 900; -fx-text-fill: #1a365d;");

        Label description = new Label(
                "Découvrez notre sélection de véhicules. Utilisez les filtres pour affiner votre recherche.");
        description.setStyle("-fx-font-size: 16px; -fx-text-fill: #718096;");

        categoriesContainer = new VBox(25);

        ScrollPane scrollPane = new ScrollPane(categoriesContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");

        content.getChildren().addAll(titre, description, scrollPane);
        return content;
    }

    private void initDonneesCatalogue() {
        toutesCategories.clear();
        toutesVoitures.clear();

        Categorie catSUV = new Categorie("SUV", "images/voiture_icon.png");

        Categorie sousToyota = new Categorie("Toyota", "images/Toyota rav4.jpg");
        Voiture rav4 = new Voiture("Toyota RAV4", "Toyota", "RAV4", 4500, true, "images/Toyota rav4.jpg");
        Voiture highlander = new Voiture("Toyota Highlander", "Toyota", "Highlander", 5700, false,
                "images/toyota highlander.jpg");

        sousToyota.ajouterEnfant(rav4);
        sousToyota.ajouterEnfant(highlander);

        Categorie sousHyundai = new Categorie("Hyundai", "images/hyundai tucson.jpg");
        Voiture tucson = new Voiture("Hyundai Tucson", "Hyundai", "Tucson", 4200, true, "images/hyundai tucson.jpg");
        sousHyundai.ajouterEnfant(tucson);

        catSUV.ajouterEnfant(sousToyota);
        catSUV.ajouterEnfant(sousHyundai);

        Categorie catCitadines = new Categorie("Citadines", "images/peugeot 208.jpg");
        Categorie sousPeugeot = new Categorie("Peugeot", "images/peugeot 208.jpg");
        Voiture peugeot208 = new Voiture("Peugeot 208", "Peugeot", "208", 3800, true, "images/peugeot 208.jpg");
        sousPeugeot.ajouterEnfant(peugeot208);
        catCitadines.ajouterEnfant(sousPeugeot);

        Categorie catLuxe = new Categorie("Luxe", "images/BMW serie 5.jpg");
        Categorie sousBMW = new Categorie("BMW", "images/BMW serie 5.jpg");
        Voiture bmwSerie5 = new Voiture("BMW Série 5", "BMW", "Série 5", 13000, true, "images/BMW serie 5.jpg");
        sousBMW.ajouterEnfant(bmwSerie5);
        catLuxe.ajouterEnfant(sousBMW);

        Categorie catUtilitaires = new Categorie("Utilitaires", "images/renault kangoo.jpg");
        Categorie sousRenault = new Categorie("Renault", "images/renault kangoo.jpg");
        Voiture kangoo = new Voiture("Renault Kangoo", "Renault", "Kangoo", 3500, true, "images/renault kangoo.jpg");
        sousRenault.ajouterEnfant(kangoo);
        catUtilitaires.ajouterEnfant(sousRenault);

        // Ajouter toutes les voitures à la liste
        toutesVoitures.add(rav4);
        toutesVoitures.add(highlander);
        toutesVoitures.add(tucson);
        toutesVoitures.add(peugeot208);
        toutesVoitures.add(bmwSerie5);
        toutesVoitures.add(kangoo);

        // Ajouter toutes les catégories à la liste
        toutesCategories.add(catSUV);
        toutesCategories.add(catCitadines);
        toutesCategories.add(catLuxe);
        toutesCategories.add(catUtilitaires);

        // Afficher toutes les voitures initialement
        afficherVoituresFiltrees();
    }

    private void setupFiltres() {
        // Configuration du bouton "Appliquer les filtres"
        btnAppliquer.setOnAction(e -> {
            afficherVoituresFiltrees();
        });
    }

    private void afficherVoituresFiltrees() {
        // Effacer le contenu actuel
        categoriesContainer.getChildren().clear();

        // Récupérer les valeurs des filtres
        double prixMax = prixSlider.getValue();
        String marqueSelectionnee = marqueCombo.getValue();
        boolean filtreDispo = cbDispo.isSelected();

        // Texte de recherche (vide si null)
        String recherche = "";
        if (searchField != null && searchField.getText() != null) {
            recherche = searchField.getText().trim().toLowerCase();
        }

        // Récupérer les types sélectionnés
        List<String> typesSelectionnes = new ArrayList<>();
        if (cbSUV.isSelected())
            typesSelectionnes.add("SUV");
        if (cbCitadine.isSelected())
            typesSelectionnes.add("Citadine");
        if (cbLuxe.isSelected())
            typesSelectionnes.add("Luxe");
        if (cbUtilitaire.isSelected())
            typesSelectionnes.add("Utilitaire");

        // Filtrer les voitures
        List<Voiture> voituresFiltrees = new ArrayList<>();

        for (Voiture voiture : toutesVoitures) {
            boolean passePrix = voiture.getPrix() <= prixMax;
            boolean passeMarque = marqueSelectionnee.equals("Toutes") ||
                    voiture.getMarque().equalsIgnoreCase(marqueSelectionnee);
            boolean passeDispo = !filtreDispo || voiture.isDisponible();
            boolean passeType = typesSelectionnes.isEmpty() ||
                    typesSelectionnes.contains(voiture.getType());
            boolean passeRecherche = recherche.isEmpty() ||
                    (voiture.getNom() != null && voiture.getNom().toLowerCase().contains(recherche)) ||
                    (voiture.getMarque() != null && voiture.getMarque().toLowerCase().contains(recherche)) ||
                    (voiture.getModele() != null && voiture.getModele().toLowerCase().contains(recherche));

            if (passePrix && passeMarque && passeDispo && passeType && passeRecherche) {
                voituresFiltrees.add(voiture);
            }
        }

        // Grouper les voitures filtrées par catégorie
        afficherParCategories(voituresFiltrees);
    }

    private void afficherParCategories(List<Voiture> voitures) {
        // Créer des catégories basées sur les voitures filtrées
        List<Categorie> categoriesFiltrees = new ArrayList<>();

        // Regrouper par type principal
        for (Voiture voiture : voitures) {
            String type = voiture.getType();

            // Trouver ou créer la catégorie principale
            Categorie categoriePrincipale = null;
            for (Categorie cat : categoriesFiltrees) {
                if (cat.nom.equals(type)) {
                    categoriePrincipale = cat;
                    break;
                }
            }

            if (categoriePrincipale == null) {
                categoriePrincipale = new Categorie(type, "images/voiture_icon.png");
                categoriesFiltrees.add(categoriePrincipale);

                // Créer une sous-catégorie pour la marque
                Categorie sousCategorieMarque = new Categorie(voiture.getMarque(), "images/voiture_icon.png");
                sousCategorieMarque.ajouterEnfant(voiture);
                categoriePrincipale.ajouterEnfant(sousCategorieMarque);
            } else {
                // Trouver ou créer la sous-catégorie de marque
                boolean marqueTrouvee = false;
                for (ComponentCatalogue enfant : categoriePrincipale.getEnfants()) {
                    if (enfant instanceof Categorie && enfant.nom.equals(voiture.getMarque())) {
                        ((Categorie) enfant).ajouterEnfant(voiture);
                        marqueTrouvee = true;
                        break;
                    }
                }

                if (!marqueTrouvee) {
                    Categorie sousCategorieMarque = new Categorie(voiture.getMarque(), "images/voiture_icon.png");
                    sousCategorieMarque.ajouterEnfant(voiture);
                    categoriePrincipale.ajouterEnfant(sousCategorieMarque);
                }
            }
        }

        // Afficher les catégories filtrées
        for (Categorie cat : categoriesFiltrees) {
            categoriesContainer.getChildren().add(cat.afficher());
        }

        // Si aucune voiture ne correspond aux filtres
        if (voitures.isEmpty()) {
            Label aucunResultat = new Label("Aucune voiture ne correspond à vos critères de recherche.");
            aucunResultat.setStyle("-fx-font-size: 18px; -fx-text-fill: #718096; -fx-padding: 40px;");
            categoriesContainer.getChildren().add(aucunResultat);
        }
    }

    private ImageView loadImage(String path, double height, double width) {
        ImageView imageView = new ImageView();
        try {
            // Si le chemin ne commence pas par "images/", l'ajouter
            if (!path.startsWith("images/") && !path.startsWith("/images/")) {
                path = "images/" + path;
            }

            // Si le chemin commence par "/images/", enlever le "/" initial
            if (path.startsWith("/images/")) {
                path = path.substring(1);
            }

            // Charger l'image depuis le classloader
            java.io.InputStream stream = getClass().getResourceAsStream(path);

            if (stream == null) {
                // Essayer avec un chemin absolu
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

        btn.setOnMouseEntered(e -> {
            btn.setStyle(
                    "-fx-background-color: rgba(255,255,255,0.1); " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: 600; " +
                            "-fx-padding: 8 15; " +
                            "-fx-background-radius: 4;");
        });

        btn.setOnMouseExited(e -> {
            btn.setStyle(
                    "-fx-background-color: transparent; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: 600; " +
                            "-fx-padding: 8 15;");
        });

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

        btn.setOnAction(e -> {
            switch (text) {
                case "Accueil":
                    app.Main.showAccueilView();
                    break;
                case "Catalogue":
                    // Déjà sur cette page
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