/**
 * Java parser for the MRZ records, as specified by the ICAO organization.
 * Copyright (C) 2011 Innovatrics s.r.o.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.innovatrics.mrz.records;

import com.innovatrics.mrz.MrzFinderUtil;
import com.innovatrics.mrz.MrzNotFoundException;
import com.innovatrics.mrz.MrzParseException;
import com.innovatrics.mrz.MrzParser;
import com.innovatrics.mrz.types.MrzDate;
import com.innovatrics.mrz.types.MrzDocumentCode;
import com.innovatrics.mrz.types.MrzSex;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link FrenchIdCard}.
 *
 * @author Martin Vysny
 */
public class FrenchIdCardTest {

	private static final String PARSE = "IDFRAPETE<<<<<<<<<<<<<<<<<<<<<952042\n0509952018746NICOLAS<<PAUL<8206152M3\n";
	private static final String TOMRZ = "IDFRANOVAK<<<<<<<<<<<<<<<<<<<<123456\nABCDE12345126JAN<<<<<<<<<<<8110251M8\n";
	private static final String WRAPPED = "xx\n\nyyy\n" + PARSE + "\nZZZZ";

	@Test
	public void testFrenchIdCardParsing() throws MrzParseException {
		final FrenchIdCard r = (FrenchIdCard) MrzParser.parse(PARSE);
		Assert.assertEquals(MrzDocumentCode.TYPE_I, r.getCode());
		Assert.assertEquals('I', r.getCode1());
		Assert.assertEquals('D', r.getCode2());
		Assert.assertEquals("FRA", r.getIssuingCountry());
		Assert.assertEquals("FRA", r.getNationality());
		Assert.assertEquals("050995201874", r.getDocumentNumber());
//        Assert.assertEquals(new MrzDate(95, 1, 2), r.expirationDate);
		Assert.assertEquals("952042", r.getOptional());
		Assert.assertEquals(new MrzDate(82, 6, 15), r.getDateOfBirth());
		Assert.assertEquals(MrzSex.MALE, r.getSex());
		Assert.assertEquals("PETE", r.getSurname());
		Assert.assertEquals("NICOLAS, PAUL", r.getGivenNames());
	}

	@Test
	public void testFrenchIdToMrz() {
		final FrenchIdCard r = new FrenchIdCard();
		r.setIssuingCountry("FRA");
		r.setNationality("FRA");
		r.setOptional("123456");
		r.setDocumentNumber("ABCDE1234512");
		r.setExpirationDate(new MrzDate(18, 1, 2));
		r.setDateOfBirth(new MrzDate(81, 10, 25));
		r.setSex(MrzSex.MALE);
		r.setSurname("NOVAK");
		r.setGivenNames("JAN");
		Assert.assertEquals(TOMRZ, r.toMrz());
	}

	@Test
	public void testFindMrz() throws MrzNotFoundException, MrzParseException {
		Assert.assertEquals("Did not find MRZ", PARSE.trim(), MrzFinderUtil.findMrz(PARSE));
	}

	@Test
	public void testFindMrzWrapped() throws MrzNotFoundException, MrzParseException {
		Assert.assertEquals("Did not find wrapped MRZ", PARSE.trim(), MrzFinderUtil.findMrz(WRAPPED));
	}

}
