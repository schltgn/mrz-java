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
 * Tests {@link FrenchIdCard}.
 *
 * @author Martin Vysny
 */
public class MRPTest {

	@Test
	public void testParsing() {
		final MRP r = (MRP) MrzParser.parse("I<SVKNOVAK<<JAN<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n123456<AA5SVK8110251M1801020749313<<<<<<<<70\n");
		Assert.assertEquals(MrzDocumentCode.TYPE_I, r.getCode());
		Assert.assertEquals('I', r.getCode1());
		Assert.assertEquals('<', r.getCode2());
		Assert.assertEquals("SVK", r.getIssuingCountry());
		Assert.assertEquals("SVK", r.getNationality());
		Assert.assertEquals("749313", r.getPersonalNumber());
		Assert.assertEquals("123456 AA", r.getDocumentNumber());
		Assert.assertEquals(new MrzDate(18, 1, 2), r.getExpirationDate());
		Assert.assertEquals(new MrzDate(81, 10, 25), r.getDateOfBirth());
		Assert.assertEquals(MrzSex.MALE, r.getSex());
		Assert.assertEquals("NOVAK", r.getSurname());
		Assert.assertEquals("JAN", r.getGivenNames());
	}

	@Test
	public void testToMrz() {
		final MRP r = new MRP();
		r.setCode1('I');
		r.setCode2('<');
		r.setIssuingCountry("SVK");
		r.setNationality("SVK");
		r.setPersonalNumber("749313");
		r.setDocumentNumber("123456 AA");
		r.setExpirationDate(new MrzDate(18, 1, 2));
		r.setDateOfBirth(new MrzDate(81, 10, 25));
		r.setSex(MrzSex.MALE);
		r.setSurname("NOVAK");
		r.setGivenNames("JAN");
		Assert.assertEquals("I<SVKNOVAK<<JAN<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n123456<AA5SVK8110251M1801020749313<<<<<<<<70\n", r.toMrz());
	}
}
