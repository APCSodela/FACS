package com.pup.cea.facs.model.load;

import java.util.ArrayList;
import java.util.List;

public class LoadCreationDto {
	private List<LoadDetail> loadDetails = new ArrayList<LoadDetail>();
	private Load load = new Load();
	
	public void addDetail(LoadDetail loadDetail) {
		this.loadDetails.add(loadDetail);
	}

	public List<LoadDetail> getLoadDetails() {
		return loadDetails;
	}

	public void setLoadDetails(List<LoadDetail> loadDetails) {
		this.loadDetails = loadDetails;
	}

	public Load getLoad() {
		return load;
	}

	public void setLoad(Load load) {
		this.load = load;
	}
	

}
