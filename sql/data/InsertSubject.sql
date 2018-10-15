/* POLICIA MILITAR DE MARAVILHA */
INSERT INTO "Subject"("active") VALUES (true);
INSERT INTO "Organization"("subject", "denomination", "fancyName") VALUES (1, 'Policia Militar de Maravilha', '3a/11oBPM-FRON');
INSERT INTO "Government"("organization") VALUES (1);
INSERT INTO "GovernmentCity"("government", "city") VALUES (1, 4351);
INSERT INTO "GovernmentCity"("government", "city") VALUES (1, 4386);
INSERT INTO "GovernmentCity"("government", "city") VALUES (1, 4397);
INSERT INTO "GovernmentCity"("government", "city") VALUES (1, 4430);
INSERT INTO "GovernmentCity"("government", "city") VALUES (1, 4467);
INSERT INTO "GovernmentCity"("government", "city") VALUES (1, 4563);
INSERT INTO "GovernmentCity"("government", "city") VALUES (1, 4569);
INSERT INTO "GovernmentCity"("government", "city") VALUES (1, 4572);
INSERT INTO "GovernmentCity"("government", "city") VALUES (1, 4473);
INSERT INTO "Address"("denomination", "number", "district", "city", "postalCode", "latitude", "longitude") VALUES ('Rua Hercilio Luz', '475', 'Progresso', 4467, 89874000, -26.757168, -53.178105);
INSERT INTO "AddressEdification"("address", "edification") VALUES (1, 'POLMI00000');
INSERT INTO "AddressEdificationSubject"("address", "edification", "subject", "from", "to", "verifiedAt", "verifiedBy") VALUES (1, 'POLMI00000', 1, NOW(), null, NOW(), 2);

/* DIOGO BUZIN ROSIN */
INSERT INTO "Subject"("active") VALUES (true);
INSERT INTO "Individual"("subject", "name", "motherName", "fatherName", "cpf", "rgNumber", "rgAgency", "rgState", "birthPlace", "birthDate", "gender") VALUES (2, 'Diogo Buzin Rosin', 'Odilete Buzin', 'Ilonir Rosin', 05369609926, 3100191, 1, 24, 4564, '1987-02-03', 'M');
INSERT INTO "User"("individual", "login", "password") VALUES (2, 'diogorosin@gmail.com', '8C6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918');
INSERT INTO "Address"("denomination", "number", "district", "city", "postalCode", "latitude", "longitude") VALUES ('Rua Sao Paulo', '109', 'Sao Jose', 4467, 89874000, -26.774440, -53.169787);
INSERT INTO "AddressEdification"("address", "edification") VALUES (2, 'RESID00000');
INSERT INTO "AddressEdificationSubject"("address", "edification", "subject", "from", "to", "verifiedAt", "verifiedBy") VALUES (2, 'RESID00000', 2, NOW(), null, NOW(), 2);

/* PREFEITURA DE MARAVILHA */
INSERT INTO "Subject"("active") VALUES (true);
INSERT INTO "Organization"("subject", "denomination", "fancyName") VALUES (3, 'Prefeitura Municipal de Maravilha', 'Pref. Mun. Maravilha');
INSERT INTO "Government"("organization") VALUES (3);
INSERT INTO "GovernmentCity"("government", "city") VALUES (3, 4467);
INSERT INTO "Address"("denomination", "number", "district", "city", "postalCode", "latitude", "longitude") VALUES ('Av Euclides da Cunha', '60', 'Centro', 4467, 89874000, -26.760550, -53.174119);
INSERT INTO "AddressEdification"("address", "edification") VALUES (3, 'PREFE00000');
INSERT INTO "AddressEdificationSubject"("address", "edification", "subject", "from", "to", "verifiedAt", "verifiedBy") VALUES (3, 'PREFE00000', 3, NOW(), null, NOW(), 2);

/* POLICIA x DIOGO */
INSERT INTO "SubjectSubject"("parent", "child", "since", "level") VALUES (1, 2, NOW(), 5);

/* PREFEITURA x DIOGO */
INSERT INTO "SubjectSubject"("parent", "child", "since", "level") VALUES (3, 2, NOW(), 5);
