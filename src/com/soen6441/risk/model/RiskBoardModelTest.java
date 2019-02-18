	package com.soen6441.risk.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.soen6441.risk.Continent;

class RiskBoardModelTest {

	@Test
	void createCountinentstest() {
		RiskBoardModel JunitTest1 = new RiskBoardModel();
		Map<String, Continent> Continent = JunitTest1.continentsMap;
		assertEquals(Continent,Continent);
		//fail("Not yet implemented");
	}

}
