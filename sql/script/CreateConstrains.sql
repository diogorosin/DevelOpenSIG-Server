/*PRIMARY KEYS*/
ALTER TABLE "Type" ADD CONSTRAINT "TypePK" PRIMARY KEY("identifier");

ALTER TABLE "Address" ADD CONSTRAINT "AddressPK" PRIMARY KEY("identifier");

ALTER TABLE "AddressEdification" ADD CONSTRAINT "AddressEdificationPK" PRIMARY KEY("address", "edification");

ALTER TABLE "AddressEdificationDweller" ADD CONSTRAINT "AddressEdificationDwellerPK" PRIMARY KEY("address", "edification", "dweller");

ALTER TABLE "Agency" ADD CONSTRAINT "AgencyPK" PRIMARY KEY("identifier");

ALTER TABLE "City" ADD CONSTRAINT "CityPK" PRIMARY KEY("identifier");

ALTER TABLE "Country" ADD CONSTRAINT "CountryPK" PRIMARY KEY("identifier");

ALTER TABLE "Government" ADD CONSTRAINT "GovernmentPK" PRIMARY KEY("organization");

ALTER TABLE "GovernmentCity" ADD CONSTRAINT "GovernmentCityPK" PRIMARY KEY("government", "city");

ALTER TABLE "Individual" ADD CONSTRAINT "IndividualPK" PRIMARY KEY("subject");

ALTER TABLE "Organization" ADD CONSTRAINT "OrganizationPK" PRIMARY KEY("subject");

ALTER TABLE "State" ADD CONSTRAINT "StatePK" PRIMARY KEY("identifier");

ALTER TABLE "Subject" ADD CONSTRAINT "SubjectPK" PRIMARY KEY("identifier");

ALTER TABLE "SubjectSubject" ADD CONSTRAINT "SubjectSubjectPK" PRIMARY KEY("parent", "child");

ALTER TABLE "Token" ADD CONSTRAINT "TokenPK" PRIMARY KEY("identifier");

ALTER TABLE "User" ADD CONSTRAINT "UserPK" PRIMARY KEY("individual");


/*FOREIGN KEYS*/
ALTER TABLE "Address" ADD CONSTRAINT "AddressCityFK" FOREIGN KEY("city") REFERENCES "City"("identifier") ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE "AddressEdification" ADD CONSTRAINT "AddressEdificationAddressFK" FOREIGN KEY("address") REFERENCES "Address"("identifier") ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE "AddressEdification" ADD CONSTRAINT "AddressEdificationTypeFK" FOREIGN KEY("type") REFERENCES "Type"("identifier") ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE "AddressEdificationDweller" ADD CONSTRAINT "AddressEdificationDwellerAddressEdificationFK" FOREIGN KEY("address", "edification") REFERENCES "AddressEdification"("address", "edification") ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE "AddressEdificationDweller" ADD CONSTRAINT "AddressEdificationDwellerSubjectFK" FOREIGN KEY("subject") REFERENCES "Subject"("identifier") ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE "AddressEdificationDweller" ADD CONSTRAINT "AddressEdificationDwellerVerifiedByFK" FOREIGN KEY("verifiedBy") REFERENCES "User"("individual") ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE "City" ADD CONSTRAINT "CityStateFK" FOREIGN KEY("state") REFERENCES "State"("identifier") ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE "Government" ADD CONSTRAINT "GovernmentOrganizationFK" FOREIGN KEY("organization") REFERENCES "Organization"("subject") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "GovernmentCity" ADD CONSTRAINT "GovernmentCityGovernmentFK" FOREIGN KEY("government") REFERENCES "Government"("organization") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "GovernmentCity" ADD CONSTRAINT "GovernmentCityCityFK" FOREIGN KEY("city") REFERENCES "City"("identifier") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "Individual" ADD CONSTRAINT "IndividualSubjectFK" FOREIGN KEY("subject") REFERENCES "Subject"("identifier") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "Individual" ADD CONSTRAINT "IndividualRgAgencyFK" FOREIGN KEY("rgAgency") REFERENCES "Agency"("identifier") ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE "Individual" ADD CONSTRAINT "IndividualRgStateFK" FOREIGN KEY("rgState") REFERENCES "State"("identifier") ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE "Individual" ADD CONSTRAINT "IndividualBirthPlaceFK" FOREIGN KEY("birthPlace") REFERENCES "City"("identifier") ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE "Organization" ADD CONSTRAINT "OrganizationSubjectFK" FOREIGN KEY("subject") REFERENCES "Subject"("identifier") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "State" ADD CONSTRAINT "StateCountryFK" FOREIGN KEY("country") REFERENCES "Country"("identifier") ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE "SubjectSubject" ADD CONSTRAINT "SubjectSubjectParentFK" FOREIGN KEY("parent") REFERENCES "Subject"("identifier") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "SubjectSubject" ADD CONSTRAINT "SubjectSubjectChildFK" FOREIGN KEY("child") REFERENCES "Subject"("identifier") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "Token" ADD CONSTRAINT "TokenGovernmentFK" FOREIGN KEY("government") REFERENCES "Government"("organization") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "Token" ADD CONSTRAINT "TokenUserFK" FOREIGN KEY("user") REFERENCES "User"("individual") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "Token" ADD CONSTRAINT "TokenSubjectSubjectFK" FOREIGN KEY("government", "user") REFERENCES "SubjectSubject"("parent", "child") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "User" ADD CONSTRAINT "UserIndividualFK" FOREIGN KEY("individual") REFERENCES "Individual"("subject") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "User" ADD CONSTRAINT "UserLastLoggedInFK" FOREIGN KEY("lastLoggedIn") REFERENCES "Government"("organization") ON DELETE SET NULL ON UPDATE CASCADE;