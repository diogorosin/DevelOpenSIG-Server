CREATE TABLE "Individual" (

	"subject" INTEGER NOT NULL,

	"name" VARCHAR(150) NOT NULL,

	"motherName" VARCHAR(150) NOT NULL,

    "fatherName" VARCHAR(150),

    "cpf" BIGINT,

    "rgNumber" BIGINT,

    "rgAgency" INTEGER,

    "rgState" INTEGER,

    "birthPlace" INTEGER NOT NULL,

    "birthDate" DATE NOT NULL,

    "gender" VARCHAR(1) NOT NULL

);
