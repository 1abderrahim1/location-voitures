package app.view;

import app.Main;
import app.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.util.function.Consumer;
import javafx.scene.control.Label;  // <-- AJOUTE CETTE LIGNE
import java.util.function.Consumer;

public class HeaderView {
    private VBox header;
    private TextField searchField;
    private String activePage;
    private Consumer<String> onSearchAction; // Callback pour la recherche
    private HBox rightButtons; // Pour pouvoir le mettre à jour

    public HeaderView(String activePage) {
        this.activePage = activePage;
        createHeader();
    }

    public HeaderView(String activePage, Consumer<String> onSearchAction) {
        this.activePage = activePage;
        this.onSearchAction = onSearchAction;
        createHeader();
    }

    private void createHeader() {
        header = new VBox();
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

        searchField = new TextField();
        searchField.setPromptText("Rechercher une voiture...");
        searchField.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-font-size: 14px; " +
                        "-fx-prompt-text-fill: #a0aec0;");
        HBox.setHgrow(searchField, Priority.ALWAYS);

        // Icône recherche
        ImageView searchIcon = loadImage("images/recherche_icon.png", 20, 20);
        HBox.setMargin(searchIcon, new Insets(0, 0, 0, 5));

        // Action de recherche
        searchIcon.setOnMouseClicked(e -> performSearch());
        searchField.setOnAction(e -> performSearch());

        searchBox.getChildren().addAll(searchField, searchIcon);

        // Boutons droite (dynamiques selon connexion)
        rightButtons = new HBox(15);
        rightButtons.setAlignment(Pos.CENTER_RIGHT);
        
        updateRightButtons(); // Initialiser les boutons

        HBox.setHgrow(searchBox, Priority.ALWAYS);

        // Ajouter au topBar
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

            if (menuItems[i].equals(activePage)) {
                menuBtn.setStyle(menuBtn.getStyle()
                        + " -fx-background-color: #f7fafc; -fx-text-fill: #1a365d; -fx-border-color: transparent;");
            }

            navBar.getChildren().add(menuBtn);
        }

        header.getChildren().addAll(topBar, navBar);
    }

    // Méthode pour mettre à jour les boutons de droite selon l'état de connexion
    private void updateRightButtons() {
        rightButtons.getChildren().clear();
        
        User currentUser = Main.getCurrentUser();
        
        if (currentUser == null) {
            // Non connecté : bouton login
            Button btnLogin = createIconButton("Se connecter", "images/login_icon.png");
            btnLogin.setOnAction(e -> Main.showLoginView());
            rightButtons.getChildren().add(btnLogin);
        } else {
            // Connecté : profil + déconnexion
            HBox userInfo = new HBox(10);
            userInfo.setAlignment(Pos.CENTER);
            
            ImageView userIcon = loadImage("images/profil_icon.png", 24, 24);
            
            VBox userText = new VBox(2);
            Label userName = new Label(currentUser.getNom());
            userName.setStyle("-fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: 600;");
            
            Label userStatus = new Label("Connecté");
            userStatus.setStyle("-fx-text-fill: #a0aec0; -fx-font-size: 10px;");
            
            userText.getChildren().addAll(userName, userStatus);
            
            userInfo.getChildren().addAll(userIcon, userText);
            userInfo.setOnMouseClicked(e -> Main.showProfileView());
            userInfo.setStyle("-fx-cursor: hand; -fx-padding: 5; -fx-background-radius: 4;");
            
            userInfo.setOnMouseEntered(e -> {
                userInfo.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-cursor: hand; -fx-padding: 5; -fx-background-radius: 4;");
            });
            
            userInfo.setOnMouseExited(e -> {
                userInfo.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-padding: 5; -fx-background-radius: 4;");
            });
            
            Button btnDeconnexion = new Button("Déconnexion");
            btnDeconnexion.setStyle(
                "-fx-background-color: transparent; " +
                "-fx-text-fill: #feb2b2; " +
                "-fx-font-size: 12px; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 5 10;" +
                "-fx-cursor: hand;");
            btnDeconnexion.setOnAction(e -> Main.logout());
            
            rightButtons.getChildren().addAll(userInfo, btnDeconnexion);
        }
    }

    private void performSearch() {
        String searchText = searchField.getText().trim();

        if (onSearchAction != null) {
            // Si un callback est défini (pour CatalogueView), l'utiliser
            onSearchAction.accept(searchText);
        } else {
            // Sinon, rediriger vers le catalogue
            Main.showCatalogueViewWithSearch(searchText);
        }
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
            if (!text.equals(activePage)) {
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
            if (!text.equals(activePage)) {
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
                    Main.showAccueilView();
                    break;
                case "Catalogue":
                    Main.showCatalogueView();
                    break;
                case "Réservation":
                    Main.showReservationView();
                    break;
                case "Contact":
                    Main.showContactView();
                    break;
                case "Mon compte":
                    // Rediriger vers profil si connecté, sinon login
                    if (Main.getCurrentUser() != null) {
                        Main.showProfileView();
                    } else {
                        Main.showLoginView();
                    }
                    break;
                case "Admin":
                    Main.showAdminView();
                    break;
            }
        });

        return btn;
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

    public VBox getHeader() {
        return header;
    }

    public String getSearchText() {
        return searchField.getText();
    }

    public void setSearchText(String text) {
        searchField.setText(text);
    }

    // Setter pour le callback de recherche
    public void setOnSearchAction(Consumer<String> onSearchAction) {
        this.onSearchAction = onSearchAction;
    }
    
    // Méthode pour rafraîchir le header (après connexion/déconnexion)
    public void refresh() {
        updateRightButtons();
    }
}