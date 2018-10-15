CREATE TABLE "State" (

	"identifier" INTEGER DEFAULT NEXTVAL('StateSequence') NOT NULL,
     
	"denomination" VARCHAR(20) NOT NULL,
	
	"acronym" VARCHAR(2) NOT NULL,
     
	"country" INTEGER NOT NULL

);
