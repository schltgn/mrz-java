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
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests {@link MrtdTd1}.
 *
 * @author Martin Vysny
 */
public class MrtdTd1Test {

    @Test
    public void testTd1Parsing() {
        final MrtdTd1 r = (MrtdTd1) MrzParser.parse("CIUTOD231458907A123X5328434D23\n3407127M9507122UTO<<<<<<<<<<<6\nSTEVENSON<<PETER<<<<<<<<<<<<<<\n");
        assertEquals(MrzDocumentCode.TypeC, r.getCode());
        assertEquals('C', r.getCode1());
        assertEquals('I', r.getCode2());
        assertEquals("UTO", r.getIssuingCountry());
        assertEquals("UTO", r.getNationality());
        assertEquals("D23145890", r.getDocumentNumber());
        assertEquals("A123X5328434D23", r.getOptional());
        assertEquals("", r.getOptional2());
        assertEquals(new MrzDate(95, 7, 12), r.getExpirationDate());
        assertEquals(new MrzDate(34, 7, 12), r.getDateOfBirth());
        assertEquals(MrzSex.Male, r.getSex());
        assertEquals("STEVENSON", r.getSurname());
        assertEquals("PETER", r.getGivenNames());
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
        r.setSex(MrzSex.Male);
        r.setSurname("Stevenson");
        r.setGivenNames("Peter");
        assertEquals("CIUTOD231458907A123X5328434D23\n3407127M9507122UTO<<<<<<<<<<<6\nSTEVENSON<<PETER<<<<<<<<<<<<<<\n", r.toMrz());
    }
}
