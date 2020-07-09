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
package com.innovatrics.mrz.types;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Martin Vysny
 */
public class MrzDateTest {

	@Test
	public void testCompareTo() {
		Assert.assertEquals(0, new MrzDate(0, 1, 1).compareTo(new MrzDate(0, 1, 1)));
		Assert.assertEquals(0, new MrzDate(55, 4, 31).compareTo(new MrzDate(55, 4, 31)));
		Assert.assertTrue(new MrzDate(55, 4, 31).compareTo(new MrzDate(55, 4, 30)) > 0);
		Assert.assertTrue(new MrzDate(55, 4, 31).compareTo(new MrzDate(54, 4, 31)) > 0);
		Assert.assertTrue(new MrzDate(55, 4, 31).compareTo(new MrzDate(55, 3, 31)) > 0);
		Assert.assertTrue(new MrzDate(55, 4, 30).compareTo(new MrzDate(55, 4, 31)) < 0);
		Assert.assertTrue(new MrzDate(55, 3, 31).compareTo(new MrzDate(55, 4, 31)) < 0);
		Assert.assertTrue(new MrzDate(54, 4, 31).compareTo(new MrzDate(55, 4, 31)) < 0);
	}

	@Test
	public void testEquals() {
		Assert.assertEquals(new MrzDate(0, 1, 1), new MrzDate(0, 1, 1));
		Assert.assertEquals(new MrzDate(55, 4, 31), new MrzDate(55, 4, 31));
		Assert.assertFalse(new MrzDate(55, 4, 31).equals(new MrzDate(55, 4, 30)));
		Assert.assertFalse(new MrzDate(55, 4, 31).equals(new MrzDate(54, 4, 31)));
		Assert.assertFalse(new MrzDate(55, 4, 31).equals(new MrzDate(55, 3, 31)));
		Assert.assertFalse(new MrzDate(55, 4, 30).equals(new MrzDate(55, 4, 31)));
		Assert.assertFalse(new MrzDate(55, 3, 31).equals(new MrzDate(55, 4, 31)));
		Assert.assertFalse(new MrzDate(54, 4, 31).equals(new MrzDate(55, 4, 31)));
	}

	@Test
	public void testToMrz() {
		Assert.assertEquals("550431", new MrzDate(55, 4, 31).toMrz());
		Assert.assertEquals("081201", new MrzDate(8, 12, 1).toMrz());
		Assert.assertEquals("880941", new MrzDate(88, 9, 41).toMrz());
		Assert.assertEquals("BB1201", new MrzDate(-1, 12, 1, "BB1201").toMrz());
	}

	@Test
	public void testInvalidDate() {
		MrzDate date = new MrzDate(88, 9, 41);
		Assert.assertEquals(88, date.getYear());
		Assert.assertEquals(9, date.getMonth());
		Assert.assertEquals(41, date.getDay());
		Assert.assertEquals(false, date.isDateValid());
	}

	@Test
	public void testValidDate() {
		MrzDate date = new MrzDate(88, 9, 30);
		Assert.assertEquals(88, date.getYear());
		Assert.assertEquals(9, date.getMonth());
		Assert.assertEquals(30, date.getDay());
		Assert.assertEquals(true, date.isDateValid());
	}
}
