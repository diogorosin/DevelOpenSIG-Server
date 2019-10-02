CREATE TABLE "City" (

	"identifier" INTEGER DEFAULT NEXTVAL('CitySequence') NOT NULL,

	"denomination" VARCHAR(40) NOT NULL,

	"state" INTEGER NOT NULL,

	"ibge" INTEGER NOT NULL,
	
	"postalCodeBegin" INTEGER NOT NULL,
	
	"postalCodeEnd" INTEGER NOT NULL

);