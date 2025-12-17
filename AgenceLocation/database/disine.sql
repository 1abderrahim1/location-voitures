-- ============================================
-- Table: voitures
-- ============================================
CREATE TABLE IF NOT EXISTS voitures (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    marque TEXT NOT NULL,
    modele TEXT NOT NULL,
    prix REAL NOT NULL,
    disponible BOOLEAN DEFAULT 1,
    path_to_image TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_voitures_disponible ON voitures(disponible);
CREATE INDEX IF NOT EXISTS idx_voitures_marque_modele ON voitures(marque, modele);

-- ============================================
-- Table: clients
-- ============================================
CREATE TABLE IF NOT EXISTS clients (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    f_name TEXT NOT NULL,
    l_name TEXT NOT NULL,
    adress TEXT,
    num_tell TEXT,
    num_permis INTEGER UNIQUE NOT NULL,
    loyale BOOLEAN DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_clients_name ON clients(f_name, l_name);
CREATE INDEX IF NOT EXISTS idx_clients_num_permis ON clients(num_permis);

-- ============================================
-- Table: reservations
-- ============================================
CREATE TABLE IF NOT EXISTS reservations (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    client_id INTEGER NOT NULL,
    voiture_id INTEGER NOT NULL,
    date_reservation DATE NOT NULL,
    date_affectation DATE,
    date_retour DATE NOT NULL,
    prix REAL NOT NULL,
    statut TEXT DEFAULT 'en_attente',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
    FOREIGN KEY (voiture_id) REFERENCES voitures(id) ON DELETE CASCADE,
    CHECK (date_retour >= date_reservation)
);

CREATE INDEX IF NOT EXISTS idx_reservations_client ON reservations(client_id);
CREATE INDEX IF NOT EXISTS idx_reservations_voiture ON reservations(voiture_id);
CREATE INDEX IF NOT EXISTS idx_reservations_date ON reservations(date_reservation);
CREATE INDEX IF NOT EXISTS idx_reservations_statut ON reservations(statut);

-- ============================================
-- Table: historique
-- ============================================
CREATE TABLE IF NOT EXISTS historique (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    client_id INTEGER NOT NULL,
    voiture_id INTEGER NOT NULL,
    date_allocation DATE NOT NULL,
    date_retour DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
    FOREIGN KEY (voiture_id) REFERENCES voitures(id) ON DELETE CASCADE,
    CHECK (date_retour >= date_allocation)
);

CREATE INDEX IF NOT EXISTS idx_historique_client ON historique(client_id);
CREATE INDEX IF NOT EXISTS idx_historique_voiture ON historique(voiture_id);
CREATE INDEX IF NOT EXISTS idx_historique_date_allocation ON historique(date_allocation);
