package AgenceLocation;
import java.time.*;
import java.util.ArrayList;
public class AgenceLocation {
	static Boolean is_created=false;
	public class Voiture{
		   public class Reservations{
		    	int ID;
		    	Clients client;
		    	LocalDate date_reservation,date_afectation,date_retoure;
		    	double prix;
		    	String statut;
		    	 Reservations(int ID,Clients client,Voiture voiture,LocalDate date_reservation,LocalDate date_afectation ,LocalDate date_retoure,double prix,String statut){
		    		 this.ID=ID;
		    		 this.client=client;
		    		 this.date_reservation=date_reservation;
		    		 this.date_afectation=date_afectation;
		    		 this.date_retoure=date_retoure;
		    		 this.prix=prix;
		    		 this.statut=statut;
		    		 
		    	 }
		    	 void afecter() {
		    		 if (this.date_afectation==LocalDate.now()) {
		    			 this.client.Alocate(voiture, date_afectation, date_retoure);
		    		 }
		    		 /*exeption if not but I'm gonna let it simple for this prototipe (3jazt ndirha doka)*/
		    	 }
		    	
		    	
		    	
		    	
		    	
		    }
		int ID;
		String marque;
		String modele;
		double prix;
		Boolean disponible;
		ArrayList <Reservations> reseve;
		public Voiture(int ID,String modele,String marque,double prix,Boolean disponible ){
			this.ID=ID;
			this.marque=marque;
			this.modele=modele;
			this.prix=prix;
			this.disponible=disponible;
			
		}
		void seeVoiture() {
			System.out.println(ID);
			System.out.println(modele);
			System.out.println(marque);
			System.out.println(prix);
			System.out.println("disponible"+disponible);
			
		}
				
	
		
	}
	public class Clients{
		int ID,num_tell,num_permis;
		String f_name,l_name,adress;
		public class Alocation{
			Voiture voiture_alouer;
			LocalDate date_alocation,date_retoure;
			Alocation(Voiture voiture_alouer,LocalDate date_alocation,LocalDate date_retoure){
				this.voiture_alouer=voiture_alouer;
				this.date_alocation=date_alocation;
				this.date_retoure=date_retoure;}
			void seealocation() {
				this.voiture_alouer.seeVoiture();
				System.out.println("from "+this.date_alocation +" to "+this.date_retoure);
			}
			
		}
		ArrayList<Alocation> historique=new ArrayList<Alocation>();
		Clients(String f_name, String l_name,String adress,int ID,int num_tell,int num_permis ){
			this.ID=ID;
			this.adress=adress;
			this.f_name=f_name;
			this.l_name=l_name;
			this.num_permis=num_permis;
			this.num_tell=num_tell;
			this.historique=new ArrayList<Alocation>();
	
			
			
		}
		void Alocate(Voiture voiture_alouer,LocalDate date_alocation,LocalDate date_retoure){
			if (date_alocation.isBefore(date_retoure)) {
				this.historique.add(new Alocation(voiture_alouer,date_alocation,date_retoure));
				
				
			}
		}
		void seeClient() {
			System.out.println(f_name+" " +l_name);
			System.out.println("permis : "+num_permis);
			System.out.println("adress: "+adress);
			System.out.println("tellephon: "+ num_tell);
		}
		void seehistorique() {
			for (Alocation aloc :this.historique) {
				aloc.seealocation();
			}
				
			
		}
	}
 
    Voiture voiture;/*we will treat them one at the time */
    Clients client;
    
    AgenceLocation( Voiture voiture,Clients client){
    	if (!is_created) {
    	this.client=client;
    	this.voiture=voiture;
    	
    	is_created=true;
    	}
    	else {
    		throw new RuntimeException("AgenceLoation must be unique");
    		
    	}
    
    	
    }

    public AgenceLocation() {//this well be used by the db one time so the singularty is stel varified (:
    	
    }
	
    void addcar(int ID,String modele,String marque,double prix,Boolean disponible) {
    	this.voiture=new Voiture(ID,modele,marque,prix,disponible);
    	
    }
    
   
     
    public void initializeFromDatabase(int voitureId, int clientId) {
        DBManager.VoitureData voitureData = DBManager.getVoitureDataById(voitureId);
        DBManager.ClientData clientData = DBManager.getClientDataById(clientId);
        
        if (voitureData != null) {
            this.voiture = new Voiture(voitureData.id, voitureData.modele, voitureData.marque, 
                voitureData.prix, voitureData.disponible);
        }
        
        if (clientData != null) {
            this.client = new Clients(clientData.fName, clientData.lName, clientData.adress, 
                clientData.id, clientData.numTell, clientData.numPermis);
            
            
            loadHistoriqueForClient(this.client);
        }
    }
    
    
    public Voiture createVoitureFromData(DBManager.VoitureData voitureData) {
        if (voitureData == null) return null;
        return new Voiture(voitureData.id, voitureData.modele, voitureData.marque, 
            voitureData.prix, voitureData.disponible);
    }
    
    
    public Clients createClientFromData(DBManager.ClientData clientData) {
        if (clientData == null) return null;
        
        Clients client = new Clients(clientData.fName, clientData.lName, clientData.adress, 
            clientData.id, clientData.numTell, clientData.numPermis);
        
        
        loadHistoriqueForClient(client);
        
        return client;
    }
    
   
    public Voiture.Reservations createReservationFromData(DBManager.ReservationData reservationData, 
            Clients client, Voiture voiture) {
        if (reservationData == null || client == null || voiture == null) return null;
        
        return voiture.new Reservations(reservationData.id, client, voiture, 
            reservationData.dateReservation, reservationData.dateAffectation, 
            reservationData.dateRetour, reservationData.prix, reservationData.statut);
    }
    
  
    private void loadHistoriqueForClient(Clients client) {
        ArrayList<DBManager.HistoriqueData> historiqueData = DBManager.getHistoriqueDataByClientId(client.ID);
        
        for (DBManager.HistoriqueData histData : historiqueData) {
            
            Voiture voiture = getVoitureById(histData.voitureId);
            if (voiture == null) {
                DBManager.VoitureData voitureData = DBManager.getVoitureDataById(histData.voitureId);
                if (voitureData != null) {
                    voiture = createVoitureFromData(voitureData);
                }
            }
            
            if (voiture != null) {
                client.Alocate(voiture, histData.dateAllocation, histData.dateRetour);
            }
        }
    }
    
   
    private Voiture getVoitureById(int voitureId) {
        
        if (this.voiture != null && this.voiture.ID == voitureId) {
            return this.voiture;
        }
        
        DBManager.VoitureData voitureData = DBManager.getVoitureDataById(voitureId);
        return createVoitureFromData(voitureData);
    }
   
    public ArrayList<Voiture> getAllVoituresFromDB() {
        ArrayList<Voiture> voitures = new ArrayList<>();
        ArrayList<DBManager.VoitureData> voituresData = DBManager.getAllVoituresData();
        
        for (DBManager.VoitureData voitureData : voituresData) {
            voitures.add(createVoitureFromData(voitureData));
        }
        
        return voitures;
    }
    
   
    public ArrayList<Clients> getAllClientsFromDB() {
        ArrayList<Clients> clients = new ArrayList<>();
        ArrayList<DBManager.ClientData> clientsData = DBManager.getAllClientsData();
        
        for (DBManager.ClientData clientData : clientsData) {
            clients.add(createClientFromData(clientData));
        }
        
        return clients;
    }
        public ArrayList<Voiture.Reservations> getAllReservationsFromDB() {
        ArrayList<Voiture.Reservations> reservations = new ArrayList<>();
        ArrayList<DBManager.ReservationData> reservationsData = DBManager.getAllReservationsData();
        
        
        java.util.HashMap<Integer, Clients> clientsCache = new java.util.HashMap<>();
        java.util.HashMap<Integer, Voiture> voituresCache = new java.util.HashMap<>();
        
        for (DBManager.ReservationData resData : reservationsData) {
          
            Clients client = clientsCache.get(resData.clientId);
            if (client == null) {
                DBManager.ClientData clientData = DBManager.getClientDataById(resData.clientId);
                if (clientData != null) {
                    client = createClientFromData(clientData);
                    clientsCache.put(resData.clientId, client);
                }
            }
            
            
            Voiture voiture = voituresCache.get(resData.voitureId);
            if (voiture == null) {
                voiture = createVoitureFromData(DBManager.getVoitureDataById(resData.voitureId));
                if (voiture != null) {
                    voituresCache.put(resData.voitureId, voiture);
                }
            }
            
            if (client != null && voiture != null) {
                Voiture.Reservations reservation = createReservationFromData(resData, client, voiture);
                if (reservation != null) {
                    reservations.add(reservation);
                }
            }
        }
        
        return reservations;
    }
    
}
