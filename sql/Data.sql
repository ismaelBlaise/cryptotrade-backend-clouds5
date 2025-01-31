INSERT INTO role (nom) VALUES
('Admin'),
('Utilisateur');

INSERT INTO cryptomonnaie (nom, symbole, date_creation) VALUES
('Bitcoin', 'BTC', '2009-01-03'),
('Ethereum', 'ETH', '2015-07-30'),
('Binance Coin', 'BNB', '2017-07-25'),
('Cardano', 'ADA', '2017-09-29'),
('Solana', 'SOL', '2020-03-20');

INSERT INTO historique_prix (prix, cryptomonnaie_id) VALUES
(45000.25, 1),
(3200.75, 2),
(410.50, 3),
(1.20, 4),
(90.15, 5);


INSERT INTO statut(statut) VALUES
('attente'),
('valider'),
('refuser');


INSERT INTO type_transaction(type_transaction) VALUES
('depot'),
('retrait'),
('achat'),
('vente');


INSERT INTO utilisateur (fournisseur_id, email, mdp, photo, role_id) VALUES
(1, 'admin@example.com', 'password123', NULL, 1),
(2, 'user1@example.com', 'password123', NULL, 2),
(3, 'trader1@example.com', 'password123', NULL, 2);

INSERT INTO portefeuille (montant, utilisateur_id) VALUES
(10000.00, 1),
(5000.00, 2),
(2000.00, 3);

INSERT INTO portefeuille_crypto (quantite, cryptomonnaie_id, utilisateur_id) VALUES
(0.5, 1, 1),
(1.2, 2, 2),
(3.0, 3, 3);

INSERT INTO type_commission (type_commission) VALUES
('Frais de transaction'),
('Frais de retrait');

INSERT INTO commission (pourcentage, type_commission_id) VALUES
(1.50, 1),
(0.75, 2);

INSERT INTO notification (titre, message, utilisateur_id) VALUES
('Achat validé', 'Votre achat de BTC a été validé.', 1),
('Retrait refusé', 'Votre demande de retrait a été refusée.', 2);

