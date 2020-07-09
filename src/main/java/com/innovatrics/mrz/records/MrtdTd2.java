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
import com.innovatrics.mrz.MrzRange;
import com.innovatrics.mrz.MrzRecord;
import com.innovatrics.mrz.types.MrzFormat;

/**
 * MRTD td2 format: A two line long, 36 characters per line format.
 *
 * @author Martin Vysny
 */
public class MrtdTd2 extends MrzRecord {

	private static final long serialVersionUID = 1L;

	/**
	 * For use of the issuing State or organization. Unused character positions shall be completed with filler characters (&lt;) repeated up to
	 * position 35 as required.
	 */
	private String optional;

	/**
	 * Construct a MrtdTd2 Record.
	 */
	public MrtdTd2() {
		super(MrzFormat.MRTD_TD2);
	}

	@Override
	public void fromMrz(final String mrz) {
		super.fromMrz(mrz);
		final MrzParser p = new MrzParser(mrz);
		setName(p.parseName(new MrzRange(5, 36, 0)));
		setDocumentNumber(p.parseString(new MrzRange(0, 9, 1)));
		setValidDocumentNumber(p.checkDigit(9, 1, new MrzRange(0, 9, 1), "document number"));
		setNationality(p.parseString(new MrzRange(10, 13, 1)));
		setDateOfBirth(p.parseDate(new MrzRange(13, 19, 1)));
		setValidDateOfBirth(p.checkDigit(19, 1, new MrzRange(13, 19, 1), "date of birth") && getDateOfBirth().isDateValid());
		setSex(p.parseSex(20, 1));
		setExpirationDate(p.parseDate(new MrzRange(21, 27, 1)));
		setValidExpirationDate(p.checkDigit(27, 1, new MrzRange(21, 27, 1), "expiration date") && getExpirationDate().isDateValid());
		setOptional(p.parseString(new MrzRange(28, 35, 1)));
		setValidComposite(p.checkDigit(35, 1, p.rawValue(new MrzRange(0, 10, 1), new MrzRange(13, 20, 1), new MrzRange(21, 35, 1)), "mrz"));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("MRTD-TD2");
		sb.append('{');
		sb.append(super.toString());
		sb.append(", optional=").append(getOptional());
		sb.append('}');
		return sb.toString();
	}

	@Override
	public String toMrz() {
		// first line
		final StringBuilder sb = new StringBuilder();
		sb.append(getCode1());
		sb.append(getCode2());
		sb.append(MrzParser.toMrz(getIssuingCountry(), 3));
		sb.append(MrzParser.nameToMrz(getSurname(), getGivenNames(), 31));
		sb.append('\n');
		// second line
		final String dn = MrzParser.toMrz(getDocumentNumber(), 9) + MrzParser.computeCheckDigitChar(MrzParser.toMrz(getDocumentNumber(), 9));
		sb.append(dn);
		sb.append(MrzParser.toMrz(getNationality(), 3));
		final String dob = getDateOfBirth().toMrz() + MrzParser.computeCheckDigitChar(getDateOfBirth().toMrz());
		sb.append(dob);
		sb.append(getSex().getMrz());
		final String ed = getExpirationDate().toMrz() + MrzParser.computeCheckDigitChar(getExpirationDate().toMrz());
		sb.append(ed);
		sb.append(MrzParser.toMrz(getOptional(), 7));
		sb.append(MrzParser.computeCheckDigitChar(dn + dob + ed + MrzParser.toMrz(getOptional(), 7)));
		sb.append('\n');
		return sb.toString();
	}

	/**
	 * For use of the issuing State or organization. Unused character positions shall be completed with filler characters (&lt;) repeated up to
	 * position 35 as required.
	 *
	 * @return the optional data
	 */
	public String getOptional() {
		return optional;
	}

	/**
	 * For use of the issuing State or organization. Unused character positions shall be completed with filler characters (&lt;) repeated up to
	 * position 35 as required.
	 *
	 * @param optional the optional data
	 */
	public void setOptional(final String optional) {
		this.optional = optional;
	}

}
