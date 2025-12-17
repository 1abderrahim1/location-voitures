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

// Import de ton Observateur
import app.modele.Observateur;

public class AdminView implements Observateur {
    private BorderPane view;
    private TableView<Reservation> reservationTable;
    private ScrollPane scrollPane;

    // 🔔 Liste des notifications rendue accessible
    private VBox notificationList;

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
        // ... (inchangé, ton header reste le même)
        // je ne recopie pas tout pour alléger, mais garde ton code existant
        return new VBox();
    }

    private VBox createMainContent() {
        VBox content = new VBox(25);
        content.setPadding(new Insets(30));
        content.setAlignment(Pos.TOP_CENTER);
        content.setStyle("-fx-background-color: #f7fafc;");

        // ... (stats, graphique, tableau inchangés)

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

        // 🔔 notificationList devient attribut de classe
        notificationList = new VBox(10);
        notificationList.setPadding(new Insets(10, 0, 0, 0));

        // Notifications initiales (exemple)
        addNotification(notificationList, "⚠️", "Toyota Highlander nécessite révision", "Il y a 2 jours");
        addNotification(notificationList, "📅", "3 réservations à venir aujourd'hui", "Il y a 1 jour");

        notifications.getChildren().addAll(notifTitle, notificationList);

        // Ajoute notifications à ta colonne droite
        content.getChildren().add(notifications);

        return content;
    }

    // 🔔 Méthode Observateur : ajout dynamique
    @Override
    public void notifier(String message) {
        addNotification(notificationList, "🔔", message, "Maintenant");
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

    // ... (tes autres méthodes createStatCard, createBarChart, etc. restent inchangées)

    // Classe interne pour les réservations (inchangée)
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

        public StringProperty idProperty() { return new SimpleStringProperty(id); }
        public StringProperty clientProperty() { return new SimpleStringProperty(client); }
        public StringProperty voitureProperty() { return new SimpleStringProperty(voiture); }
        public StringProperty datesProperty() { return new SimpleStringProperty(dates); }
        public StringProperty statutProperty() { return new SimpleStringProperty(statut); }
        public StringProperty montantProperty() { return new SimpleStringProperty(montant); }
    }

    public BorderPane getView() {
        return view;
    }
}
