INSERT INTO role (nom) VALUES ('Administrateur');


INSERT INTO utilisateur (fournisseur_id, email, mdp, photo, role_id) 
VALUES (1, 'senmanoafinaritra@gmail.com', 'srt', 'photo.jpg', 1);




INSERT INTO statut(statut) VALUES('attente'),('valider'),('refuser');


INSERT INTO type_transaction(type_transaction) VALUES('depot'),('retrait'),('achat'),('vente');


INSERT INTO cryptomonnaie (nom, symbole, date_creation) VALUES
('Bitcoin', 'BTC', '2009-01-03'),
('Ethereum', 'ETH', '2015-07-30'),
('Binance Coin', 'BNB', '2017-07-25'),
('Solana', 'SOL', '2020-03-20'),
('Cardano', 'ADA', '2017-09-29'),
('Ripple', 'XRP', '2012-04-02'),
('Polkadot', 'DOT', '2020-05-26'),
('Dogecoin', 'DOGE', '2013-12-06'),
('Litecoin', 'LTC', '2011-10-07'),
('Avalanche', 'AVAX', '2020-09-21');
