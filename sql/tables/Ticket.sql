CREATE TABLE "Ticket" (

	"identifier" VARCHAR(64) NOT NULL,

	"active" BOOLEAN NOT NULL,

	"date" TIMESTAMP NOT NULL,

	"user" INTEGER NOT NULL,

	"email" VARCHAR(254) NOT NULL

);