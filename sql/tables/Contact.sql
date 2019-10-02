CREATE TABLE "Contact" (

	"identifier" INTEGER DEFAULT NEXTVAL('ContactSequence') NOT NULL,

	"phone" BIGINT,

	"cellPhone" BIGINT,

	"email" VARCHAR(254),

	"webSite" VARCHAR(254)
	
);