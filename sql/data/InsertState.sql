DELETE FROM "State";

INSERT INTO "State" ("identifier", "denomination", "acronym", "ibge", "country") VALUES 
(1, 'Rondônia', 'RO', 11, 31),
(2, 'Acre', 'AC', 12, 31),
(3, 'Amazonas', 'AM', 13, 31),
(4, 'Roraima', 'RR', 14, 31),
(5, 'Pará', 'PA', 15, 31),
(6, 'Amapá', 'AP', 16, 31),
(7, 'Tocantins', 'TO', 17, 31),
(8, 'Maranhão', 'MA', 21, 31),
(9, 'Piauí', 'PI', 22, 31),
(10, 'Ceará', 'CE', 23, 31),
(11, 'Rio Grande do Norte', 'RN', 24, 31),
(12, 'Paraíba', 'PB', 25, 31),
(13, 'Pernambuco', 'PE', 26, 31),
(14, 'Alagoas', 'AL', 27, 31),
(15, 'Sergipe', 'SE', 28, 31),
(16, 'Bahia', 'BA', 29, 31),
(17, 'Minas Gerais', 'MG', 31, 31),
(18, 'Espírito Santo', 'ES', 32, 31),
(19, 'Rio de Janeiro', 'RJ', 33, 31),
(20, 'São Paulo', 'SP', 35, 31),
(21, 'Paraná', 'PR', 41, 31),
(22, 'Santa Catarina', 'SC', 42, 31),
(23, 'Rio Grande do Sul', 'RS', 43, 31),
(24, 'Mato Grosso do Sul', 'MS', 50, 31),
(25, 'Mato Grosso', 'MT', 51, 31),
(26, 'Goiás', 'GO', 52, 31),
(27, 'Distrito Federal', 'DF', 53, 31);

ALTER SEQUENCE StateSequence RESTART WITH 28;