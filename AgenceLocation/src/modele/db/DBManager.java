package AgenceLocation;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;


public class DBManager {
   
    private static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            System.err.println("Warning: Could not parse date: " + dateStr);
            return null;
        }
    }
    
   
     
    public static ArrayList<VoitureData> getAllVoituresData() {
        ArrayList<VoitureData> voituresData = new ArrayList<>();
        String sql = "SELECT id, marque, modele, prix, disponible FROM voitures";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String marque = rs.getString("marque");
                String modele = rs.getString("modele");
                double prix = rs.getDouble("prix");
                boolean disponible = rs.getBoolean("disponible");
                
                voituresData.add(new VoitureData(id, marque, modele, prix, disponible));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching voitures: " + e.getMessage());
            e.printStackTrace();
        }
        
        return voituresData;
    }
    
   
    public static VoitureData getVoitureDataById(int voitureId) {
        String sql = "SELECT id, marque, modele, prix, disponible, FROM voitures WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, voitureId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id");
                String marque = rs.getString("marque");
                String modele = rs.getString("modele");
                double prix = rs.getDouble("prix");
                boolean disponible = rs.getBoolean("disponible");
                
                return new VoitureData(id, marque, modele, prix, disponible);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching voiture: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static boolean insertVoiture(String marque, String modele, double prix,
            boolean disponible, String pathToImage) {
String sql = """
INSERT INTO voitures (marque, modele, prix, disponible, path_to_image)
VALUES (?, ?, ?, ?, ?)
""";

try (Connection conn = DatabaseConnection.getConnection();
PreparedStatement pstmt = conn.prepareStatement(sql)) {

pstmt.setString(1, marque);
pstmt.setString(2, modele);
pstmt.setDouble(3, prix);
pstmt.setBoolean(4, disponible);
pstmt.setString(5, pathToImage);

return pstmt.executeUpdate() > 0;

} catch (SQLException e) {
System.err.println("Error inserting voiture: " + e.getMessage());
return false;
}
}

    public static boolean updateVoiture(int id, String marque, String modele,
            double prix, boolean disponible, String pathToImage) {
String sql = """
UPDATE voitures
SET marque = ?, modele = ?, prix = ?, disponible = ?, path_to_image = ?,
updated_at = CURRENT_TIMESTAMP
WHERE id = ?
""";

try (Connection conn = DatabaseConnection.getConnection();
PreparedStatement pstmt = conn.prepareStatement(sql)) {

pstmt.setString(1, marque);
pstmt.setString(2, modele);
pstmt.setDouble(3, prix);
pstmt.setBoolean(4, disponible);
pstmt.setString(5, pathToImage);
pstmt.setInt(6, id);

return pstmt.executeUpdate() > 0;

} catch (SQLException e) {
System.err.println("Error updating voiture: " + e.getMessage());
return false;
}
}

    public static ArrayList<ClientData> getAllClientsData() {
        ArrayList<ClientData> clientsData = new ArrayList<>();
        String sql = "SELECT id, f_name, l_name, adress, num_tell, num_permis FROM clients";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String fName = rs.getString("f_name");
                String lName = rs.getString("l_name");
                String adress = rs.getString("adress");
                String numTellStr = rs.getString("num_tell");
                int numPermis = rs.getInt("num_permis");
                
                // Convert phone number string to int
                int numTell = 0;
                if (numTellStr != null && !numTellStr.isEmpty()) {
                    try {
                        String digitsOnly = numTellStr.replaceAll("[^0-9]", "");
                        if (!digitsOnly.isEmpty()) {
                            numTell = Integer.parseInt(digitsOnly);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Warning: Could not parse phone number: " + numTellStr);
                    }
                }
                
                clientsData.add(new ClientData(id, fName, lName, adress, numTell, numPermis));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching clients: " + e.getMessage());
            e.printStackTrace();
        }
        
        return clientsData;
    }
    
   
    public static ClientData getClientDataById(int clientId) {
        String sql = "SELECT id, f_name, l_name, adress, num_tell, num_permis FROM clients WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, clientId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id");
                String fName = rs.getString("f_name");
                String lName = rs.getString("l_name");
                String adress = rs.getString("adress");
                String numTellStr = rs.getString("num_tell");
                int numPermis = rs.getInt("num_permis");
                
                int numTell = 0;
                if (numTellStr != null && !numTellStr.isEmpty()) {
                    try {
                        String digitsOnly = numTellStr.replaceAll("[^0-9]", "");
                        if (!digitsOnly.isEmpty()) {
                            numTell = Integer.parseInt(digitsOnly);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Warning: Could not parse phone number: " + numTellStr);
                    }
                }
                
                return new ClientData(id, fName, lName, adress, numTell, numPermis);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching client: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    public static ClientData getClientDataByNumPermis(int numPermis) {
        String sql = "SELECT id, f_name, l_name, adress, num_tell, num_permis FROM clients WHERE num_permis = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, numPermis);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id");
                String fName = rs.getString("f_name");
                String lName = rs.getString("l_name");
                String adress = rs.getString("adress");
                String numTellStr = rs.getString("num_tell");
                int numPermisDb = rs.getInt("num_permis");
                
                int numTell = 0;
                if (numTellStr != null && !numTellStr.isEmpty()) {
                    try {
                        String digitsOnly = numTellStr.replaceAll("[^0-9]", "");
                        if (!digitsOnly.isEmpty()) {
                            numTell = Integer.parseInt(digitsOnly);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Warning: Could not parse phone number: " + numTellStr);
                    }
                }
                
                return new ClientData(id, fName, lName, adress, numTell, numPermisDb);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching client by num_permis: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static boolean insertClient(String fName, String lName, String adress,
            String numTell, int numPermis, boolean loyale) {
String sql = """
INSERT INTO clients (f_name, l_name, adress, num_tell, num_permis, loyale)
VALUES (?, ?, ?, ?, ?, ?)
""";

try (Connection conn = DatabaseConnection.getConnection();
PreparedStatement pstmt = conn.prepareStatement(sql)) {

pstmt.setString(1, fName);
pstmt.setString(2, lName);
pstmt.setString(3, adress);
pstmt.setString(4, numTell);
pstmt.setInt(5, numPermis);
pstmt.setBoolean(6, loyale);

return pstmt.executeUpdate() > 0;

} catch (SQLException e) {
System.err.println("Error inserting client: " + e.getMessage());
return false;
}
}
    public static boolean updateClient(int id, String fName, String lName,
            String adress, String numTell, boolean loyale) {
String sql = """
UPDATE clients
SET f_name = ?, l_name = ?, adress = ?, num_tell = ?, loyale = ?,
updated_at = CURRENT_TIMESTAMP
WHERE id = ?
""";

try (Connection conn = DatabaseConnection.getConnection();
PreparedStatement pstmt = conn.prepareStatement(sql)) {

pstmt.setString(1, fName);
pstmt.setString(2, lName);
pstmt.setString(3, adress);
pstmt.setString(4, numTell);
pstmt.setBoolean(5, loyale);
pstmt.setInt(6, id);

return pstmt.executeUpdate() > 0;

} catch (SQLException e) {
System.err.println("Error updating client: " + e.getMessage());
return false;
}
}

    
    
    
    public static ArrayList<ReservationData> getAllReservationsData() {
        ArrayList<ReservationData> reservationsData = new ArrayList<>();
        String sql = "SELECT id, client_id, voiture_id, date_reservation, " +
                     "date_affectation, date_retour, prix, statut FROM reservations";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                int clientId = rs.getInt("client_id");
                int voitureId = rs.getInt("voiture_id");
                LocalDate dateReservation = parseDate(rs.getString("date_reservation"));
                LocalDate dateAffectation = parseDate(rs.getString("date_affectation"));
                LocalDate dateRetour = parseDate(rs.getString("date_retour"));
                double prix = rs.getDouble("prix");
                String statut = rs.getString("statut");
                
                reservationsData.add(new ReservationData(id, clientId, voitureId, dateReservation, 
                    dateAffectation, dateRetour, prix, statut));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching reservations: " + e.getMessage());
            e.printStackTrace();
        }
        
        return reservationsData;
    }
    
   
    public static ArrayList<ReservationData> getReservationsDataByClientId(int clientId) {
        ArrayList<ReservationData> reservationsData = new ArrayList<>();
        String sql = "SELECT id, voiture_id, date_reservation, " +
                     "date_affectation, date_retour, prix, statut " +
                     "FROM reservations WHERE client_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, clientId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                int voitureId = rs.getInt("voiture_id");
                LocalDate dateReservation = parseDate(rs.getString("date_reservation"));
                LocalDate dateAffectation = parseDate(rs.getString("date_affectation"));
                LocalDate dateRetour = parseDate(rs.getString("date_retour"));
                double prix = rs.getDouble("prix");
                String statut = rs.getString("statut");
                
                reservationsData.add(new ReservationData(id, clientId, voitureId, dateReservation, 
                    dateAffectation, dateRetour, prix, statut));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching reservations: " + e.getMessage());
            e.printStackTrace();
        }
        
        return reservationsData;
    }
    
    
    public static ArrayList<ReservationData> getReservationsDataByVoitureId(int voitureId) {
        ArrayList<ReservationData> reservationsData = new ArrayList<>();
        String sql = "SELECT id, client_id, date_reservation, " +
                     "date_affectation, date_retour, prix, statut " +
                     "FROM reservations WHERE voiture_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, voitureId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                int clientId = rs.getInt("client_id");
                LocalDate dateReservation = parseDate(rs.getString("date_reservation"));
                LocalDate dateAffectation = parseDate(rs.getString("date_affectation"));
                LocalDate dateRetour = parseDate(rs.getString("date_retour"));
                double prix = rs.getDouble("prix");
                String statut = rs.getString("statut");
                
                reservationsData.add(new ReservationData(id, clientId, voitureId, dateReservation, 
                    dateAffectation, dateRetour, prix, statut));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching reservations: " + e.getMessage());
            e.printStackTrace();
        }
        
        return reservationsData;
    }
    public static boolean insertReservation(int clientId, int voitureId,
            LocalDate dateReservation,
            LocalDate dateAffectation,
            LocalDate dateRetour,
            double prix, String statut) {
String sql = """
INSERT INTO reservations
(client_id, voiture_id, date_reservation, date_affectation,
date_retour, prix, statut)
VALUES (?, ?, ?, ?, ?, ?, ?)
""";

try (Connection conn = DatabaseConnection.getConnection();
PreparedStatement pstmt = conn.prepareStatement(sql)) {

pstmt.setInt(1, clientId);
pstmt.setInt(2, voitureId);
pstmt.setString(3, dateReservation.toString());
pstmt.setString(4, dateAffectation != null ? dateAffectation.toString() : null);
pstmt.setString(5, dateRetour.toString());
pstmt.setDouble(6, prix);
pstmt.setString(7, statut);

return pstmt.executeUpdate() > 0;

} catch (SQLException e) {
System.err.println("Error inserting reservation: " + e.getMessage());
return false;
}
}
    public static boolean updateReservation(int id, LocalDate dateAffectation,
            LocalDate dateRetour, double prix,
            String statut) {
String sql = """
UPDATE reservations
SET date_affectation = ?, date_retour = ?, prix = ?, statut = ?,
updated_at = CURRENT_TIMESTAMP
WHERE id = ?
""";

try (Connection conn = DatabaseConnection.getConnection();
PreparedStatement pstmt = conn.prepareStatement(sql)) {

pstmt.setString(1, dateAffectation != null ? dateAffectation.toString() : null);
pstmt.setString(2, dateRetour.toString());
pstmt.setDouble(3, prix);
pstmt.setString(4, statut);
pstmt.setInt(5, id);

return pstmt.executeUpdate() > 0;

} catch (SQLException e) {
System.err.println("Error updating reservation: " + e.getMessage());
return false;
}
}

    
    public static ArrayList<HistoriqueData> getHistoriqueDataByClientId(int clientId) {
        ArrayList<HistoriqueData> historiqueData = new ArrayList<>();
        String sql = "SELECT voiture_id, date_allocation, date_retour " +
                     "FROM historique WHERE client_id = ? ORDER BY date_allocation";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, clientId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                int voitureId = rs.getInt("voiture_id");
                LocalDate dateAllocation = parseDate(rs.getString("date_allocation"));
                LocalDate dateRetour = parseDate(rs.getString("date_retour"));
                
                historiqueData.add(new HistoriqueData(clientId, voitureId, dateAllocation, dateRetour));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching historique: " + e.getMessage());
            e.printStackTrace();
        }
        
        return historiqueData;
    }
    
   
    public static ArrayList<HistoriqueData> getAllHistoriqueData() {
        ArrayList<HistoriqueData> historiqueData = new ArrayList<>();
        String sql = "SELECT client_id, voiture_id, date_allocation, date_retour " +
                     "FROM historique ORDER BY client_id, date_allocation";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int clientId = rs.getInt("client_id");
                int voitureId = rs.getInt("voiture_id");
                LocalDate dateAllocation = parseDate(rs.getString("date_allocation"));
                LocalDate dateRetour = parseDate(rs.getString("date_retour"));
                
                historiqueData.add(new HistoriqueData(clientId, voitureId, dateAllocation, dateRetour));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching historique: " + e.getMessage());
            e.printStackTrace();
        }
        
        return historiqueData;
    }
    public static boolean insertHistorique(int clientId, int voitureId,
            LocalDate dateAllocation, LocalDate dateRetour) {
String sql = """
INSERT INTO historique (client_id, voiture_id, date_allocation, date_retour)
VALUES (?, ?, ?, ?)
""";

try (Connection conn = DatabaseConnection.getConnection();
PreparedStatement pstmt = conn.prepareStatement(sql)) {

pstmt.setInt(1, clientId);
pstmt.setInt(2, voitureId);
pstmt.setString(3, dateAllocation.toString());
pstmt.setString(4, dateRetour.toString());

return pstmt.executeUpdate() > 0;

} catch (SQLException e) {
System.err.println("Error inserting historique: " + e.getMessage());
return false;
}
}
    public static boolean updateHistorique(int id, LocalDate dateRetour) {
        String sql = """
            UPDATE historique
            SET date_retour = ?, updated_at = CURRENT_TIMESTAMP
            WHERE id = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dateRetour.toString());
            pstmt.setInt(2, id);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating historique: " + e.getMessage());
            return false;
        }
    }

    
    public static ArrayList<VoitureData> getAvailableVoituresData() {
        ArrayList<VoitureData> voituresData = new ArrayList<>();
        String sql = "SELECT id, marque, modele, prix, disponible FROM voitures WHERE disponible = 1";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String marque = rs.getString("marque");
                String modele = rs.getString("modele");
                double prix = rs.getDouble("prix");
                boolean disponible = rs.getBoolean("disponible");
                
                voituresData.add(new VoitureData(id, marque, modele, prix, disponible));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching available voitures: " + e.getMessage());
            e.printStackTrace();
        }
        
        return voituresData;
    }
    
    
    public static class VoitureData {
        public final int id;
        public final String marque;
        public final String modele;
        public final double prix;
        public final boolean disponible;
        
        public VoitureData(int id, String marque, String modele, double prix, boolean disponible) {
            this.id = id;
            this.marque = marque;
            this.modele = modele;
            this.prix = prix;
            this.disponible = disponible;
        }
    }
    
    
    public static class ClientData {
        public final int id;
        public final String fName;
        public final String lName;
        public final String adress;
        public final int numTell;
        public final int numPermis;
        
        public ClientData(int id, String fName, String lName, String adress, int numTell, int numPermis) {
            this.id = id;
            this.fName = fName;
            this.lName = lName;
            this.adress = adress;
            this.numTell = numTell;
            this.numPermis = numPermis;
        }
    }
    
    
    public static class ReservationData {
        public final int id;
        public final int clientId;
        public final int voitureId;
        public final LocalDate dateReservation;
        public final LocalDate dateAffectation;
        public final LocalDate dateRetour;
        public final double prix;
        public final String statut;
        
        public ReservationData(int id, int clientId, int voitureId, LocalDate dateReservation, 
                              LocalDate dateAffectation, LocalDate dateRetour, double prix, String statut) {
            this.id = id;
            this.clientId = clientId;
            this.voitureId = voitureId;
            this.dateReservation = dateReservation;
            this.dateAffectation = dateAffectation;
            this.dateRetour = dateRetour;
            this.prix = prix;
            this.statut = statut;
        }
    }
    
    
    public static class HistoriqueData {
        public final int clientId;
        public final int voitureId;
        public final LocalDate dateAllocation;
        public final LocalDate dateRetour;
        
        public HistoriqueData(int clientId, int voitureId, LocalDate dateAllocation, LocalDate dateRetour) {
            this.clientId = clientId;
            this.voitureId = voitureId;
            this.dateAllocation = dateAllocation;
            this.dateRetour = dateRetour;
        }
    }
}
