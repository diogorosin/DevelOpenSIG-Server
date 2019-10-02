/* PM */
INSERT INTO "Address"("denomination", "number", "district", "city", "postalCode") 
VALUES ('Rua Visconde de Ouro Preto', '549', 'Centro', 4401, 88020040);

INSERT INTO "Contact"("phone", "cellPhone", "email", "webSite") 
VALUES (null, null, null, 'http://pm.sc.gov.br');

INSERT INTO "Organization"("active", "address", "contact", "denomination", "fancyName") 
VALUES (true, 1, 1, 'Polícia Militar do Estado de Santa Catarina', 'PMSC');

/* DIOGO ROSIN */
INSERT INTO "Address"("denomination", "number", "district", "city", "postalCode") 
VALUES ('Rua São Paulo', '109', 'São José', 4470, 89874000);

INSERT INTO "Contact"("phone", "cellPhone", "email", "webSite") 
VALUES (4936646575, 49991159377, 'diogorosin@gmail.com', null);

INSERT INTO "User"("active", "address", "contact", "name", "login", "password", "organization", "level", "secret")
VALUES (true, 2, 2, 'Diogo Buzin Rosin', 'diogorosin@gmail.com', '5E112B71A8BEAED6B0A1F475DF800624F6178A69A1015261FE389497923351E6', 1, 70, null);