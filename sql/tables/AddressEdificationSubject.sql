CREATE TABLE "AddressEdificationSubject" (

	"address" INTEGER NOT NULL,

    "edification" VARCHAR(10) NOT NULL,

	"subject" INTEGER NOT NULL,

	"from" TIMESTAMP NOT NULL,

	"to" TIMESTAMP,

	"verifiedAt" TIMESTAMP NOT NULL,

	"verifiedBy" INTEGER NOT NULL

);
