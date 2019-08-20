CREATE TABLE "ModifiedAddressEdificationDweller" (

	"modifiedAddress" INTEGER NOT NULL,

    "edification" INTEGER NOT NULL,

	"dweller" INTEGER NOT NULL,

	"subject" INTEGER,

    "nameOrDenomination" VARCHAR(150) NOT NULL,

    "type" VARCHAR(1) NOT NULL,

    "fancyName" VARCHAR(32), 

	"motherName" VARCHAR(150),

    "fatherName" VARCHAR(150),

    "cpf" BIGINT,

    "rgNumber" BIGINT,

    "rgAgency" INTEGER,

    "rgState" INTEGER,

    "birthPlace" INTEGER,

    "birthDate" DATE,

    "gender" VARCHAR(1),

	"from" TIMESTAMP NOT NULL,

	"to" TIMESTAMP

);
