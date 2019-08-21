package br.com.developen.sig.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;

import br.com.developen.sig.bean.ModifiedAddressBean001;
import br.com.developen.sig.bean.ModifiedAddressEdificationBean001;
import br.com.developen.sig.bean.ModifiedAddressEdificationDwellerBean001;
import br.com.developen.sig.bean.UploadDatasetBean001;
import br.com.developen.sig.orm.AddressDAO;
import br.com.developen.sig.orm.AgencyDAO;
import br.com.developen.sig.orm.CityDAO;
import br.com.developen.sig.orm.Government;
import br.com.developen.sig.orm.ModifiedAddress;
import br.com.developen.sig.orm.ModifiedAddressDAO;
import br.com.developen.sig.orm.ModifiedAddressEdification;
import br.com.developen.sig.orm.ModifiedAddressEdificationDweller;
import br.com.developen.sig.orm.ModifiedAddressEdificationDwellerPK;
import br.com.developen.sig.orm.ModifiedAddressEdificationPK;
import br.com.developen.sig.orm.StateDAO;
import br.com.developen.sig.orm.SubjectDAO;
import br.com.developen.sig.orm.TypeDAO;
import br.com.developen.sig.orm.User;
import br.com.developen.sig.orm.UserDAO;

public class UploadDatasetReader001 {


	private Session session;

	private Government government;

	private User user;

	private UploadDatasetReaderResultBean001 result;


	public UploadDatasetReader001(Session session) {

		this.session = session;

	}


	public UploadDatasetReader001 readModifiedAddresses(UploadDatasetBean001 uploadDatasetBean001) {

		AddressDAO addressDAO = new AddressDAO(session);

		UserDAO userDAO = new UserDAO(session);

		CityDAO cityDAO = new CityDAO(session);

		TypeDAO typeDAO = new TypeDAO(session);

		SubjectDAO subjectDAO = new SubjectDAO(session);

		AgencyDAO agencyDAO = new AgencyDAO(session);

		StateDAO stateDAO = new StateDAO(session);

		ModifiedAddressDAO modifiedAddressDAO = new ModifiedAddressDAO(session);

		for (ModifiedAddressBean001 modifiedAddressBean : uploadDatasetBean001.getModifiedAddresses()) {

			ModifiedAddress modifiedAddress = new ModifiedAddress();

			modifiedAddress.setAddress(
					modifiedAddressBean.getAddress()==null ? null :
						addressDAO.retrieve(modifiedAddressBean.getAddress()));

			modifiedAddress.setModifiedAt(modifiedAddressBean.getModifiedAt());

			modifiedAddress.setModifiedBy(
					modifiedAddressBean.getModifiedBy()==null ? null :
						userDAO.retrieve(modifiedAddressBean.getModifiedBy()));

			modifiedAddress.setDenomination(modifiedAddressBean.getDenomination());

			modifiedAddress.setNumber(modifiedAddressBean.getNumber());

			modifiedAddress.setDistrict(modifiedAddressBean.getDistrict());

			modifiedAddress.setReference(modifiedAddressBean.getReference());

			modifiedAddress.setPostalCode(modifiedAddressBean.getPostalCode());

			modifiedAddress.setCity(
					modifiedAddressBean.getCity()==null ? null :
						cityDAO.retrieve(modifiedAddressBean.getCity()));

			modifiedAddress.setLatitude(modifiedAddressBean.getLatitude());

			modifiedAddress.setLongitude(modifiedAddressBean.getLongitude());
			
			modifiedAddress.setEdifications(new ArrayList<ModifiedAddressEdification>());

			//EDIFICATIONS
			for(Integer edification : modifiedAddressBean.getEdifications().keySet()) {

				ModifiedAddressEdificationBean001 modifiedAddressEdificationBean = 
						modifiedAddressBean.
						getEdifications().
						get(edification);

				ModifiedAddressEdificationPK modifiedAddressEdificationPK = new ModifiedAddressEdificationPK();

				modifiedAddressEdificationPK.setModifiedAddress(modifiedAddress);

				modifiedAddressEdificationPK.setEdification(edification);

				ModifiedAddressEdification modifiedAddressEdification = new ModifiedAddressEdification();

				modifiedAddressEdification.setIdentifier(modifiedAddressEdificationPK);

				modifiedAddressEdification.setType(   
						modifiedAddressEdificationBean.getType()==null ? null :
							typeDAO.retrieve(modifiedAddressEdificationBean.getType()));

				modifiedAddressEdification.setReference(modifiedAddressEdificationBean.getReference());

				modifiedAddressEdification.setFrom(modifiedAddressEdificationBean.getFrom());

				modifiedAddressEdification.setTo(modifiedAddressEdificationBean.getTo());

				modifiedAddressEdification.setDwellers(new ArrayList<ModifiedAddressEdificationDweller>());

				//DWELLERS
				for(Integer dweller : modifiedAddressEdificationBean.getDwellers().keySet()) {

					ModifiedAddressEdificationDwellerBean001 modifiedAddressEdificationDwellerBean = 
							modifiedAddressEdificationBean.
							getDwellers().
							get(dweller);

					ModifiedAddressEdificationDwellerPK modifiedAddressEdificationDwellerPK = new ModifiedAddressEdificationDwellerPK();

					modifiedAddressEdificationDwellerPK.setModifiedAddressEdification(modifiedAddressEdification);

					modifiedAddressEdificationDwellerPK.setDweller(dweller);

					ModifiedAddressEdificationDweller modifiedAddressEdificationDweller = new ModifiedAddressEdificationDweller();

					modifiedAddressEdificationDweller.setIdentifier(modifiedAddressEdificationDwellerPK);

					modifiedAddressEdificationDweller.setType(modifiedAddressEdificationDwellerBean.getType());

					modifiedAddressEdificationDweller.setSubject(
							modifiedAddressEdificationDwellerBean.getSubject()==null ? null : 
								subjectDAO.retrieve(modifiedAddressEdificationDwellerBean.getSubject()));

					modifiedAddressEdificationDweller.setNameOrDenomination(modifiedAddressEdificationDwellerBean.getNameOrDenomination());

					modifiedAddressEdificationDweller.setMotherName(modifiedAddressEdificationDwellerBean.getMotherName());

					modifiedAddressEdificationDweller.setFatherName(modifiedAddressEdificationDwellerBean.getFatherName());

					modifiedAddressEdificationDweller.setCpf(modifiedAddressEdificationDwellerBean.getCpf());

					modifiedAddressEdificationDweller.setRgNumber(modifiedAddressEdificationDwellerBean.getRgNumber());

					modifiedAddressEdificationDweller.setRgAgency(
							modifiedAddressEdificationDwellerBean.getRgAgency()==null ? null : 
								agencyDAO.retrieve(modifiedAddressEdificationDwellerBean.getRgAgency()));

					modifiedAddressEdificationDweller.setRgState(
							modifiedAddressEdificationDwellerBean.getRgState()==null ? null : 
								stateDAO.retrieve(modifiedAddressEdificationDwellerBean.getRgState()));

					modifiedAddressEdificationDweller.setBirthDate(modifiedAddressEdificationDwellerBean.getBirthDate());

					modifiedAddressEdificationDweller.setBirthPlace(
							modifiedAddressEdificationDwellerBean.getBirthPlace()==null ? null : 
								cityDAO.retrieve(modifiedAddressEdificationDwellerBean.getBirthPlace()));

					modifiedAddressEdificationDweller.setGender(modifiedAddressEdificationDwellerBean.getGender());

					modifiedAddressEdificationDweller.setFrom(modifiedAddressEdificationDwellerBean.getFrom());

					modifiedAddressEdificationDweller.setTo(modifiedAddressEdificationDwellerBean.getTo());

					modifiedAddressEdification.getDwellers().add(modifiedAddressEdificationDweller);	

				}

				modifiedAddress.getEdifications().add(modifiedAddressEdification);

			}

			session.beginTransaction();
			
			modifiedAddressDAO.create(modifiedAddress);
			
			session.getTransaction().commit();

			getResult().
			getModifiedAddressesThatWasImported().
			put(modifiedAddressBean.getIdentifier(), true);

		}

		return this;

	}


	public UploadDatasetReader001 configureUser(User user) {

		this.user = user;

		return this;

	}


	public UploadDatasetReader001 configureGovernment(Government government) {

		this.government = government;

		return this;

	}


	public UploadDatasetReaderResultBean001 getResult(){

		if (result == null)

			result = new UploadDatasetReaderResultBean001();

		return result;

	}


	public class UploadDatasetReaderResultBean001{

		private Map<Integer, Boolean> modifiedAddressesThatWasImported;

		public Map<Integer, Boolean> getModifiedAddressesThatWasImported() {

			if (modifiedAddressesThatWasImported == null) 

				modifiedAddressesThatWasImported = new HashMap<Integer, Boolean>();

			return modifiedAddressesThatWasImported;

		}

		public void setModifiedAddressesThatWasImported(Map<Integer, Boolean> modifiedAddressesThatWasImported) {

			this.modifiedAddressesThatWasImported = modifiedAddressesThatWasImported;

		}

	}


}