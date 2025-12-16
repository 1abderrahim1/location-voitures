package AgenceLocation;

import java.util.ArrayList;


public class DBManagerExample {
    
    public static void main(String[] args) {
       
        try {
            System.out.println("=== DBManager Independent Usage ===\n");
            
           
            
            // Example 1: Search for voitures in database
            System.out.println("1. Getting all voitures from database:");
            ArrayList<DBManager.VoitureData> voituresData = DBManager.getAllVoituresData();
            System.out.println("Found " + voituresData.size() + " voitures");
            for (DBManager.VoitureData v : voituresData) {
                System.out.println("  - ID: " + v.id + ", " + v.marque + " " + v.modele + 
                    ", Prix: " + v.prix + ", Disponible: " + v.disponible);
            }
            
            // Example 2: Search for available voitures
            System.out.println("\n2. Getting available voitures:");
            ArrayList<DBManager.VoitureData> available = DBManager.getAvailableVoituresData();
            System.out.println("Found " + available.size() + " available voitures");
            
            // Example 3: Search for clients
            System.out.println("\n3. Getting all clients from database:");
            ArrayList<DBManager.ClientData> clientsData = DBManager.getAllClientsData();
            System.out.println("Found " + clientsData.size() + " clients");
            for (DBManager.ClientData c : clientsData) {
                System.out.println("  - ID: " + c.id + ", " + c.fName + " " + c.lName + ", Permis: " + c.numPermis + ", Trustworthy: " + c.trustworthy);
            }
            
            // Example 4: Search for a specific client
            System.out.println("\n4. Getting client by ID:");
            DBManager.ClientData clientData = DBManager.getClientDataById(1);
            if (clientData != null) {
                System.out.println("Found: " + clientData.fName + " " + clientData.lName);
            }
            
            // Example 5: Search for reservations
            System.out.println("\n5. Getting all reservations:");
            ArrayList<DBManager.ReservationData> reservationsData = DBManager.getAllReservationsData();
            System.out.println("Found " + reservationsData.size() + " reservations");
            
            // Example 6: Search for historique
            System.out.println("\n6. Getting historique for client ID 1:");
            ArrayList<DBManager.HistoriqueData> historiqueData = DBManager.getHistoriqueDataByClientId(1);
            System.out.println("Found " + historiqueData.size() + " historique entries");
            
            
            System.out.println("\n=== Initializing AgenceLocation from Database ===\n");
            
            // Create AgenceLocation instance (using default constructor)
            AgenceLocation agence = new AgenceLocation();
            
            // Initialize from database (load voiture ID 1 and client ID 1)
            System.out.println("Initializing AgenceLocation with voiture ID 1 and client ID 1:");
            agence.initializeFromDatabase(1, 1);
            
            if (agence.voiture != null) {
                System.out.println("\nLoaded Voiture:");
                agence.voiture.seeVoiture();
            }
            
            if (agence.client != null) {
                System.out.println("\nLoaded Client:");
                agence.client.seeClient();
                System.out.println("\nClient Historique:");
                agence.client.seehistorique();
            }
            
            // Example 7: Get all voitures as objects
            System.out.println("\n=== Getting All Voitures as Objects ===");
            ArrayList<AgenceLocation.Voiture> voitures = agence.getAllVoituresFromDB();
            System.out.println("Total voitures: " + voitures.size());
            
            // Example 8: Get all clients as objects (with historique)
            System.out.println("\n=== Getting All Clients as Objects ===");
            ArrayList<AgenceLocation.Clients> clients = agence.getAllClientsFromDB();
            System.out.println("Total clients: " + clients.size());
            if (!clients.isEmpty()) {
                System.out.println("\nFirst client with historique:");
                clients.get(0).seeClient();
                clients.get(0).seehistorique();
            }
            
            // Example 9: Get all reservations as objects
            System.out.println("\n=== Getting All Reservations as Objects ===");
            ArrayList<AgenceLocation.Voiture.Reservations> reservations = agence.getAllReservationsFromDB();
            System.out.println("Total reservations: " + reservations.size());
            
            // Close database connection
            DatabaseConnection.closeConnection();
            System.out.println("\n✓ Database operations completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
