CREATE TABLE "ModifiedAddressEdificationDweller" (

	"modifiedAddress" INTEGER NOT NULL,

    "edification" INTEGER NOT NULL,

	"dweller" INTEGER NOT NULL,

	"individual" INTEGER,

    "name" VARCHAR(150) NOT NULL,

	"motherName" VARCHAR(150),

    "fatherName" VARCHAR(150),

    "cpf" BIGINT,

    "rgNumber" BIGINT,

    "rgAgency" INTEGER,

    "rgState" INTEGER,

    "birthPlace" INTEGER,

    "birthDate" DATE,

    "gender" VARCHAR(1),

	"from" TIMESTAMP,

	"to" TIMESTAMP

);