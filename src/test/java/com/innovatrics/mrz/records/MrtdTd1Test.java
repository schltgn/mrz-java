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

import com.innovatrics.mrz.MrzParseException;
import com.innovatrics.mrz.MrzParser;
import com.innovatrics.mrz.types.MrzDate;
import com.innovatrics.mrz.types.MrzDocumentCode;
import com.innovatrics.mrz.types.MrzSex;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link MrtdTd1}.
 *
 * @author Martin Vysny
 */
public class MrtdTd1Test {

	@Test
	public void testTd1Parsing() throws MrzParseException {
		final MrtdTd1 r = (MrtdTd1) MrzParser.parse("CIUTOD231458907A123X5328434D23\n3407127M9507122UTO<<<<<<<<<<<6\nSTEVENSON<<PETER<<<<<<<<<<<<<<\n");
		Assert.assertEquals(MrzDocumentCode.TYPE_C, r.getCode());
		Assert.assertEquals('C', r.getCode1());
		Assert.assertEquals('I', r.getCode2());
		Assert.assertEquals("UTO", r.getIssuingCountry());
		Assert.assertEquals("UTO", r.getNationality());
		Assert.assertEquals("D23145890", r.getDocumentNumber());
		Assert.assertEquals("A123X5328434D23", r.getOptional());
		Assert.assertEquals("", r.getOptional2());
		Assert.assertEquals(new MrzDate(95, 7, 12), r.getExpirationDate());
		Assert.assertEquals(new MrzDate(34, 7, 12), r.getDateOfBirth());
		Assert.assertEquals(MrzSex.MALE, r.getSex());
		Assert.assertEquals("STEVENSON", r.getSurname());
		Assert.assertEquals("PETER", r.getGivenNames());
	}

	@Test
	public void testToMrz() {
		final MrtdTd1 r = new MrtdTd1();
		r.setCode1('C');
		r.setCode2('I');
		r.setIssuingCountry("UTO");
		r.setNationality("UTO");
		r.setDocumentNumber("D23145890");
		r.setOptional("A123X5328434D23");
		r.setOptional2("");
		r.setExpirationDate(new MrzDate(95, 7, 12));
		r.setDateOfBirth(new MrzDate(34, 7, 12));
		r.setSex(MrzSex.MALE);
		r.setSurname("Stevenson");
		r.setGivenNames("Peter");
		Assert.assertEquals("CIUTOD231458907A123X5328434D23\n3407127M9507122UTO<<<<<<<<<<<6\nSTEVENSON<<PETER<<<<<<<<<<<<<<\n", r.toMrz());
	}
}
