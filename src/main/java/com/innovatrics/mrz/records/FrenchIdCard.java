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
import com.innovatrics.mrz.types.MrzDocumentCode;
import com.innovatrics.mrz.types.MrzFormat;

/**
 * Format used for French ID Cards. The structure of the card: 2 lines of 36 characters :
 * <pre>
 * First line : IDFRA{name}{many &lt; to complete line}{6 numbers unknown} Second line : {card number on 12 numbers}{Check digit}{given names
 * separated by "&lt;&lt;" and maybe troncated if too long}{date of birth YYMMDD}{Check digit}{sex M/F}{1 number checksum}
 * </pre>
 *
 * @author Pierrick Martin, Marin Moulinier
 */
public class FrenchIdCard extends MrzRecord {

	private static final long serialVersionUID = 1L;

	/**
	 * For use of the issuing State or organization.
	 */
	private String optional;

	/**
	 * Construct French ID Record.
	 */
	public FrenchIdCard() {
		super(MrzFormat.FRENCH_ID);
		setCode(MrzDocumentCode.TypeI);
		setCode1('I');
		setCode2('D');
	}

	@Override
	public void fromMrz(final String mrz) {
		super.fromMrz(mrz);
		final MrzParser p = new MrzParser(mrz);
		//Special because surname and firstname not on the same line
		String[] name = new String[]{"", ""};
		name[0] = p.parseString(new MrzRange(5, 30, 0));
		name[1] = p.parseString(new MrzRange(13, 27, 1));
		setName(name);
		setNationality(p.parseString(new MrzRange(2, 5, 0)));
		optional = p.parseString(new MrzRange(30, 36, 0));
		setDocumentNumber(p.parseString(new MrzRange(0, 12, 1)));
		setValidDocumentNumber(p.checkDigit(12, 1, new MrzRange(0, 12, 1), "document number"));
		setDateOfBirth(p.parseDate(new MrzRange(27, 33, 1)));
		setValidDateOfBirth(p.checkDigit(33, 1, new MrzRange(27, 33, 1), "date of birth") && getDateOfBirth().isDateValid());
		setSex(p.parseSex(34, 1));
		final String finalChecksum = mrz.replace("\n", "").substring(0, 36 + 35);
		setValidComposite(p.checkDigit(35, 1, finalChecksum, "final checksum"));
		// TODO expirationDate is missing
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("FrenchIdCard");
		sb.append('{');
		sb.append(super.toString());
		sb.append(", optional=").append(getOptional());
		sb.append('}');
		return sb.toString();
	}

	@Override
	public String toMrz() {
		final StringBuilder sb = new StringBuilder("IDFRA");
		// first row
		sb.append(MrzParser.toMrz(getSurname(), 25));
		sb.append(MrzParser.toMrz(optional, 6));
		sb.append('\n');
		// second row
		sb.append(MrzParser.toMrz(getDocumentNumber(), 12));
		sb.append(MrzParser.computeCheckDigitChar(MrzParser.toMrz(getDocumentNumber(), 12)));
		sb.append(MrzParser.toMrz(getGivenNames(), 14));
		sb.append(getDateOfBirth().toMrz());
		sb.append(MrzParser.computeCheckDigitChar(getDateOfBirth().toMrz()));
		sb.append(getSex().getMrz());
		sb.append(MrzParser.computeCheckDigitChar(sb.toString().replace("\n", "")));
		sb.append('\n');
		return sb.toString();
	}

	/**
	 * @return the issuing State or Organization
	 */
	public String getOptional() {
		return optional;
	}

	/**
	 * @param optional the issuing State or Organization
	 */
	public void setOptional(final String optional) {
		this.optional = optional;
	}

}
