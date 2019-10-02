CREATE TABLE "Address" (

	"identifier" INTEGER DEFAULT NEXTVAL('AddressSequence') NOT NULL,

	"denomination" VARCHAR(200),

	"number" VARCHAR(6),

	"complement" VARCHAR(20),

	"district" VARCHAR(100),

	"postalCode" INTEGER,

	"city" INTEGER,

	"latitude" FLOAT,

    "longitude" FLOAT	

);