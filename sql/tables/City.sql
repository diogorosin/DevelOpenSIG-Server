CREATE TABLE "City" (

	"identifier" INTEGER DEFAULT NEXTVAL('CitySequence') NOT NULL,
	
	"denomination" VARCHAR(40) NOT NULL,
	
	"state" INTEGER NOT NULL

);
