package app.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Label;

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
        HeaderView headerView = new HeaderView("Contact");
        view.setTop(headerView.getHeader());

        // CONTENU PRINCIPAL
        HBox mainContent = createMainContent();
        view.setCenter(mainContent);

        // FOOTER
        VBox footer = createFooter();
        view.setBottom(footer);
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

    public BorderPane getView() {
        return view;
    }
}