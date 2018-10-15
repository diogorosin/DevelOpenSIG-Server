CREATE TABLE "Address" (

	"identifier" INTEGER DEFAULT NEXTVAL('AddressSequence') NOT NULL,

	"denomination" VARCHAR(100),

	"number" VARCHAR(5),

	"reference" VARCHAR(50),

	"district" VARCHAR(100),

	"postalCode" INTEGER,

	"city" INTEGER NOT NULL,

    "latitude" FLOAT NOT NULL,

    "longitude" FLOAT NOT NULL

);
