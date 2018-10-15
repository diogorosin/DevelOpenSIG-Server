CREATE TABLE "Agency" (

	"identifier" INTEGER DEFAULT NEXTVAL('AgencySequence') NOT NULL,

	"denomination" VARCHAR(100),

	"acronym" VARCHAR(10)

);
