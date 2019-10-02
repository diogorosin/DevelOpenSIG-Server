CREATE TABLE "Organization" (

	"identifier" INTEGER DEFAULT NEXTVAL('OrganizationSequence') NOT NULL,

    "active" BOOLEAN NOT NULL,

    "image" VARCHAR(200),

    "address" INTEGER NOT NULL,
    
    "contact" INTEGER NOT NULL,

    "denomination" VARCHAR(100) NOT NULL,

    "fancyName" VARCHAR(32)

);