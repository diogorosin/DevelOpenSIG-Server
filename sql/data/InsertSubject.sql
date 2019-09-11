/* POLICIA MILITAR DE MARAVILHA */
INSERT INTO "Subject"("active") VALUES (true);
INSERT INTO "Organization"("subject", "denomination", "fancyName") VALUES (1, 'Polícia Militar de Maravilha', '3a/11oBPM-FRON');
INSERT INTO "Government"("organization") VALUES (1);
INSERT INTO "GovernmentCity"("government", "city") VALUES (1, 4569);
INSERT INTO "GovernmentCity"("government", "city") VALUES (1, 4499);
INSERT INTO "GovernmentCity"("government", "city") VALUES (1, 4532);
INSERT INTO "GovernmentCity"("government", "city") VALUES (1, 4666);
INSERT INTO "GovernmentCity"("government", "city") VALUES (1, 4678);
INSERT INTO "GovernmentCity"("government", "city") VALUES (1, 4453);
INSERT INTO "GovernmentCity"("government", "city") VALUES (1, 4672);
INSERT INTO "GovernmentCity"("government", "city") VALUES (1, 4575);
INSERT INTO "GovernmentCity"("government", "city") VALUES (1, 4675);
INSERT INTO "GovernmentCity"("government", "city") VALUES (1, 4488);
--INSERT INTO "Address"("denomination", "number", "district", "city", "postalCode", "latitude", "longitude", "verifiedAt", "verifiedBy") VALUES ('Rua Hercílio Luz', '475', 'Progresso', 4569, 89874000, -26.757168, -53.178105, NOW(), 2);
--INSERT INTO "AddressEdification"("address", "edification", "type", "from", "to") VALUES (1, 1, 5, NOW(), null);
--INSERT INTO "AddressEdificationDweller"("address", "edification", "dweller", "subject", "from", "to") VALUES (1, 1, 1, 1, NOW(), null);

/* DIOGO BUZIN ROSIN */
INSERT INTO "Subject"("active") VALUES (true);
INSERT INTO "Individual"("subject", "name", "motherName", "fatherName", "cpf", "rgNumber", "rgAgency", "rgState", "birthPlace", "birthDate", "gender") VALUES (2, 'Diogo Buzin Rosin', 'Odilete Buzin', 'Ilonir Rosin', 05369609926, 3100191, 1, 24, 4564, '1987-02-03', 'M');
INSERT INTO "User"("individual", "login", "password") VALUES (2, 'diogorosin@gmail.com', '8C6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918');
INSERT INTO "Address"("denomination", "number", "district", "city", "postalCode", "latitude", "longitude", "verifiedAt", "verifiedBy") VALUES ('Rua Sao Paulo', '109', 'São José', 4569, 89874000, -26.774440, -53.169787, NOW(), 2);
INSERT INTO "AddressEdification"("address", "edification", "type", "from", "to") VALUES (1, 1, 1, NOW(), null);
INSERT INTO "AddressEdificationDweller"("address", "edification", "dweller", "individual", "from", "to") VALUES (1, 1, 1, 2, NOW(), null);

/* PREFEITURA DE MARAVILHA */
INSERT INTO "Subject"("active") VALUES (true);
INSERT INTO "Organization"("subject", "denomination", "fancyName") VALUES (3, 'Prefeitura Municipal de Maravilha', 'Pref. Mun. Maravilha');
INSERT INTO "Government"("organization") VALUES (3);
INSERT INTO "GovernmentCity"("government", "city") VALUES (3, 4569);
--INSERT INTO "Address"("denomination", "number", "district", "city", "postalCode", "latitude", "longitude", "verifiedAt", "verifiedBy") VALUES ('Avenida Euclides da Cunha', '60', 'Centro', 4569, 89874000, -26.760550, -53.174119, NOW(), 2);
--INSERT INTO "AddressEdification"("address", "edification", "type", "from", "to") VALUES (3, 1, 5, NOW(), null);
--INSERT INTO "AddressEdificationDweller"("address", "edification", "dweller", "subject", "from", "to") VALUES (3, 1, 1, 3, NOW(), null);

/* POLICIA x DIOGO */
INSERT INTO "SubjectSubject"("parent", "child", "since", "level") VALUES (1, 2, NOW(), 5);

/* PREFEITURA x DIOGO */
INSERT INTO "SubjectSubject"("parent", "child", "since", "level") VALUES (3, 2, NOW(), 5);
