package br.com.developen.sig.bean;

import java.util.ArrayList;
import java.util.List;

public class UploadDatasetBean001 implements UploadDatasetBean {

	private List<ModifiedAddressBean001> modifiedAddresses;

	public List<ModifiedAddressBean001> getModifiedAddresses() {

		if (modifiedAddresses == null)

			modifiedAddresses = new ArrayList<ModifiedAddressBean001>();

		return modifiedAddresses;

	}

	public void setModifiedAddresses(List<ModifiedAddressBean001> modifiedAddresses) {

		this.modifiedAddresses = modifiedAddresses;

	}

}