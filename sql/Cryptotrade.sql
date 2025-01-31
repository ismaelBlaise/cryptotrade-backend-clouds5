\c postgres;

DROP DATABASE cryptotrade;

CREATE DATABASE cryptotrade;


\c cryptotrade;


CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(250) NOT NULL UNIQUE
);

CREATE TABLE cryptomonnaie (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(250) NOT NULL UNIQUE,
    symbole VARCHAR(50) NOT NULL UNIQUE,
    date_creation DATE NOT NULL
);

CREATE TABLE historique_prix (
    id SERIAL PRIMARY KEY,
    prix DECIMAL(15,6) NOT NULL,
    date_enregistrement TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cryptomonnaie_id INT NOT NULL,
    FOREIGN KEY (cryptomonnaie_id) REFERENCES cryptomonnaie (id)
);

CREATE TABLE type_transaction (
    id SERIAL PRIMARY KEY,
    type_transaction VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE statut (
    id SERIAL PRIMARY KEY,
    statut VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE utilisateur (
    id SERIAL PRIMARY KEY,
    fournisseur_id INT UNIQUE,
    email VARCHAR(250) NOT NULL UNIQUE,
    mdp VARCHAR(250) NOT NULL,
    photo VARCHAR(250),
    role_id INT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT NULL,
    FOREIGN KEY (role_id) REFERENCES role (id)
);

CREATE TABLE portefeuille (
    id SERIAL PRIMARY KEY,
    montant DECIMAL(15,3) NOT NULL DEFAULT 0,
    date_enregistrement TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    utilisateur_id INT NOT NULL UNIQUE,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateur (id)
);

CREATE TABLE transaction_fond (
    id SERIAL PRIMARY KEY,
    montant DECIMAL(15,3) NOT NULL,
    date_creation TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    statut_id INT NOT NULL,
    portefeuille_id INT NOT NULL,
    type_transaction_id INT NOT NULL,
    transaction_crypto_id INT DEFAULT 0,
    FOREIGN KEY (statut_id) REFERENCES statut (id),
    FOREIGN KEY (portefeuille_id) REFERENCES portefeuille (id),
    FOREIGN KEY (type_transaction_id) REFERENCES type_transaction (id)
);

CREATE TABLE portefeuille_crypto (
    id SERIAL PRIMARY KEY,
    quantite DECIMAL(15,6) NOT NULL DEFAULT 0,
    date_creation TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    cryptomonnaie_id INT NOT NULL,
    utilisateur_id INT NOT NULL,
    FOREIGN KEY (cryptomonnaie_id) REFERENCES cryptomonnaie (id),
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateur (id)
);


CREATE TABLE transaction_crypto (
    id SERIAL PRIMARY KEY,
    quantite DECIMAL(15,6) NOT NULL,
    montant DECIMAL(15,3),
    date_creation TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    statut_id INT NOT NULL,
    type_transaction_id INT NOT NULL,
    portefeuille_crypto_id INT NOT NULL,
    validation_token VARCHAR(255),
    FOREIGN KEY (statut_id) REFERENCES statut (id),
    FOREIGN KEY (type_transaction_id) REFERENCES type_transaction (id),
    FOREIGN KEY (portefeuille_crypto_id) REFERENCES portefeuille_crypto (id)
);


CREATE TABLE type_commission (
    id SERIAL PRIMARY KEY,
    type_commission VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE commission (
    id SERIAL PRIMARY KEY,
    pourcentage DECIMAL(5,2),
    type_commission_id INT NOT NULL, 
    FOREIGN KEY (type_commission_id) REFERENCES type_commission (id)
);



CREATE TABLE notification (
    id SERIAL PRIMARY KEY,
    titre VARCHAR(250) NOT NULL,
    message TEXT NOT NULL,
    date_creation TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    utilisateur_id INT NOT NULL,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateur (id)
);

CREATE TABLE crypto_favoris (
    id SERIAL PRIMARY KEY,
    utilisateur_id INT NOT NULL,
    cryptomonnaie_id INT NOT NULL,
    date_ajout TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateur (id),
    FOREIGN KEY (cryptomonnaie_id) REFERENCES cryptomonnaie (id),
    CONSTRAINT unique_favoris UNIQUE (utilisateur_id, cryptomonnaie_id)
);