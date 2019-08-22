CREATE TABLE "ModifiedAddress" (

	"identifier" INTEGER DEFAULT NEXTVAL('ModifiedAddressSequence') NOT NULL,

    "address" INTEGER,

	"denomination" VARCHAR(100),

	"number" VARCHAR(5),

	"reference" VARCHAR(50),

	"district" VARCHAR(100),

	"postalCode" INTEGER,

	"city" INTEGER NOT NULL,

    "latitude" FLOAT NOT NULL,

    "longitude" FLOAT NOT NULL,

    "modifiedBy" INTEGER NOT NULL,

    "modifiedAt" TIMESTAMP NOT NULL,

    "importedAt" TIMESTAMP

);