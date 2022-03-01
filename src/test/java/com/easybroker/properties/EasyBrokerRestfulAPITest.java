package com.easybroker.properties;

import com.easybroker.core.exceptions.EasyBrokerIntegrityDataException;
import com.easybroker.core.ws.operations.EasyBrokerPropertiesRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EasyBrokerRestfulAPITest {
	static EasyBrokerPropertiesRequest propertiesRequest;

	@BeforeAll
	public static void setup(){
		propertiesRequest = new EasyBrokerPropertiesRequest(new BrokerConfigurationTest());
	}

	@Test
	void checkTotalProperties() {
		Integer totalProperties = propertiesRequest.getTotalProperties(EasyBrokerPropertiesRequest.pathProperties);
		assertEquals( 766, totalProperties);
	}

	@Test
	void checkTitlePropertiesList() throws EasyBrokerIntegrityDataException {
		List<String> titleProperties = propertiesRequest.getAllTitleProperties();
		Integer totalTitleProperties = titleProperties.size();
		String firstTitle =  titleProperties.get(0);
		String lastTitle =  titleProperties.get(titleProperties.size()-1);

		assertEquals( 766, totalTitleProperties);
		assertEquals( "Casa con uso de suelo prueba", firstTitle);
		assertEquals( "TERRENO EN VENTA EN CIENEGA DE FLORES", lastTitle);
	}

}
