CREATE TABLE "SubjectSubject" (

	"parent" INTEGER NOT NULL,

	"child" INTEGER NOT NULL,

	"since" TIMESTAMP NOT NULL,

	"level" INTEGER NOT NULL DEFAULT 0

);
