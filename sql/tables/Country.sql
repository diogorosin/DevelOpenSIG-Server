CREATE TABLE "Country" (

	"identifier" INTEGER DEFAULT NEXTVAL('CountrySequence') NOT NULL,

	"denomination" VARCHAR(50) NOT NULL,

	"acronym" VARCHAR(3) NOT NULL

);
