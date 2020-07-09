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

import com.innovatrics.mrz.MrzParser;
import com.innovatrics.mrz.types.MrzDate;
import com.innovatrics.mrz.types.MrzDocumentCode;
import com.innovatrics.mrz.types.MrzSex;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link MrvA}.
 *
 * @author Martin Vysny
 */
public class MrvATest {

	@Test
	public void testMrvVisaACardParsing() {
		final MrvA r = (MrvA) MrzParser.parse("V<UTOERIKSSON<<ANNA<MARIA<<<<<<<<<<<<<<<<<<<\nL898902C<3UTO6908061F9406236ZE184226B<<<<<<<\n");
		Assert.assertEquals(MrzDocumentCode.TYPE_V, r.getCode());
		Assert.assertEquals('V', r.getCode1());
		Assert.assertEquals('<', r.getCode2());
		Assert.assertEquals("UTO", r.getIssuingCountry());
		Assert.assertEquals("UTO", r.getNationality());
		Assert.assertEquals("L898902C", r.getDocumentNumber());
		Assert.assertEquals(new MrzDate(94, 6, 23), r.getExpirationDate());
		Assert.assertEquals("ZE184226B", r.getOptional());
		Assert.assertEquals(new MrzDate(69, 8, 6), r.getDateOfBirth());
		Assert.assertEquals(MrzSex.FEMALE, r.getSex());
		Assert.assertEquals("ERIKSSON", r.getSurname());
		Assert.assertEquals("ANNA MARIA", r.getGivenNames());
	}

	@Test
	public void testMrvVisaAMrz() {
		final MrvA r = new MrvA();
		r.setIssuingCountry("FRA");
		r.setNationality("FRA");
		r.setOptional("123456");
		r.setDocumentNumber("ABCDE1234512");
		r.setExpirationDate(new MrzDate(18, 1, 2));
		r.setDateOfBirth(new MrzDate(81, 10, 25));
		r.setSex(MrzSex.MALE);
		r.setSurname("NOVAK");
		r.setGivenNames("JAN");
		Assert.assertEquals("V<FRANOVAK<<JAN<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\nABCDE12346FRA8110251M1801020123456<<<<<<<<<<\n", r.toMrz());
	}
}
