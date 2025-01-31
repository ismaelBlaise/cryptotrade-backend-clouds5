INSERT INTO role (nom) VALUES ('Administrateur');


INSERT INTO utilisateur (fournisseur_id, email, mdp, photo, role_id) 
VALUES (1, 'senmanoafinaritra@gmail.com', 'srt', 'photo.jpg', 1);




INSERT INTO statut(statut) VALUES('attente'),('valider'),('refuser');


INSERT INTO type_transaction(type_transaction) VALUES('depot'),('retrait'),('achat'),('vente');