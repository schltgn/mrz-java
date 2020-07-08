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
 * MRTD TD1 format: A three line long, 30 characters per line format.
 *
 * @author Martin Vysny
 */
public class MrtdTd1 extends MrzRecord {

    private static final long serialVersionUID = 1L;

    /**
     * Optional data at the discretion of the issuing State. May contain an extended document number as per 6.7, note (j).
     */
    private String optional;
    /**
     * Optional (for U.S. passport holders, 21-29 may be corresponding passport number).
     */
    private String optional2;

    /**
     * Construct a MrtdTd1 Record.
     */
    public MrtdTd1() {
        super(MrzFormat.MRTD_TD1);
    }

    @Override
    public void fromMrz(final String mrz) {
        super.fromMrz(mrz);
        final MrzParser p = new MrzParser(mrz);
        setDocumentNumber(p.parseString(new MrzRange(5, 14, 0)));
        setValidDocumentNumber(p.checkDigit(14, 0, new MrzRange(5, 14, 0), "document number"));
        setOptional(p.parseString(new MrzRange(15, 30, 0)));
        setDateOfBirth(p.parseDate(new MrzRange(0, 6, 1)));
        setValidDateOfBirth(p.checkDigit(6, 1, new MrzRange(0, 6, 1), "date of birth") && getDateOfBirth().isDateValid());
        setSex(p.parseSex(7, 1));
        setExpirationDate(p.parseDate(new MrzRange(8, 14, 1)));
        setValidExpirationDate(p.checkDigit(14, 1, new MrzRange(8, 14, 1), "expiration date") && getExpirationDate().isDateValid());
        setNationality(p.parseString(new MrzRange(15, 18, 1)));
        setOptional2(p.parseString(new MrzRange(18, 29, 1)));
        setValidComposite(p.checkDigit(29, 1, p.rawValue(new MrzRange(5, 30, 0), new MrzRange(0, 7, 1), new MrzRange(8, 15, 1), new MrzRange(18, 29, 1)), "mrz"));
        setName(p.parseName(new MrzRange(0, 30, 2)));
    }

    @Override
    public String toString() {
        return "MRTD-TD1{" + super.toString() + ", optional=" + getOptional() + ", optional2=" + getOptional2() + '}';
    }

    @Override
    public String toMrz() {
        // first line
        final StringBuilder sb = new StringBuilder();
        sb.append(getCode1());
        sb.append(getCode2());
        sb.append(MrzParser.toMrz(getIssuingCountry(), 3));
        final String dno = MrzParser.toMrz(getDocumentNumber(), 9) + MrzParser.computeCheckDigitChar(MrzParser.toMrz(getDocumentNumber(), 9)) + MrzParser.toMrz(getOptional(), 15);
        sb.append(dno);
        sb.append('\n');
        // second line
        final String dob = getDateOfBirth().toMrz() + MrzParser.computeCheckDigitChar(getDateOfBirth().toMrz());
        sb.append(dob);
        sb.append(getSex().getMrz());
        final String ed = getExpirationDate().toMrz() + MrzParser.computeCheckDigitChar(getExpirationDate().toMrz());
        sb.append(ed);
        sb.append(MrzParser.toMrz(getNationality(), 3));
        sb.append(MrzParser.toMrz(getOptional2(), 11));
        sb.append(MrzParser.computeCheckDigitChar(dno + dob + ed + MrzParser.toMrz(getOptional2(), 11)));
        sb.append('\n');
        // third line
        sb.append(MrzParser.nameToMrz(getSurname(), getGivenNames(), 30));
        sb.append('\n');
        return sb.toString();
    }

    /**
     * Optional data at the discretion of the issuing State. May contain an extended document number as per 6.7, note (j).
     *
     * @return the optional data
     */
    public String getOptional() {
        return optional;
    }

    /**
     * Optional data at the discretion of the issuing State. May contain an extended document number as per 6.7, note (j).
     *
     * @param optional the optional data
     */
    public void setOptional(final String optional) {
        this.optional = optional;
    }

    /**
     * Optional (for U.S. passport holders, 21-29 may be corresponding passport number).
     *
     * @return the optional2 data
     */
    public String getOptional2() {
        return optional2;
    }

    /**
     * Optional (for U.S. passport holders, 21-29 may be corresponding passport number).
     *
     * @param optional2 the optional2 data
     */
    public void setOptional2(final String optional2) {
        this.optional2 = optional2;
    }

}
