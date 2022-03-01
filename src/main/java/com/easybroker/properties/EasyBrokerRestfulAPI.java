package com.easybroker.properties;


import com.easybroker.core.exceptions.EasyBrokerIntegrityDataException;
import com.easybroker.core.ws.operations.BrokerConfiguration;
import com.easybroker.core.ws.operations.EasyBrokerPropertiesRequest;

public class EasyBrokerRestfulAPI {

	public static void main(String[] args) throws EasyBrokerIntegrityDataException {
		//Change for production values
		String AUTH_VALUE = "l7u502p8v46ba3ppgvj5y2aad50lb9";
		String URL = "https://api.stagingeb.com";

		BrokerConfiguration brokerConfiguration = new BrokerConfiguration(URL, AUTH_VALUE);
		EasyBrokerPropertiesRequest propertiesRequest = new EasyBrokerPropertiesRequest(brokerConfiguration);
		System.out.println(propertiesRequest.getAllTitleProperties());
	}

}
