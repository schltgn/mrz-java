package com.innovatrics.mrz;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link MrzFinderUtil}.
 */
public class MrzFinderUtilTest {

	private static final String VALID_MRZ = "I<SVKNOVAK<<JAN<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n123456<AA5SVK8110251M1801020749313<<<<<<<<70";
	private static final String INVALID_MRZ = "I<SVKNOVAK<<JAN<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n123456";
	private static final String WRAPPED_VALID = "xx\n\nyyy\n" + VALID_MRZ + "\nZZZZ";
	private static final String WRAPPED_INVALID_MRZ = "XX\nAZ09<\nYYY\n" + INVALID_MRZ + "\nAZ09<\nZZZZ";
	private static final String NO_MRZ = "AZ09<\n\nBBB\n\nAZ09<\nCCCCC";

	@Test
	public void testValidMrz() throws MrzNotFoundException, MrzParseException {
		Assert.assertEquals("Did not find valid MRZ", VALID_MRZ, MrzFinderUtil.findMrz(VALID_MRZ));
	}

	@Test
	public void testValidWrappedMrz() throws MrzNotFoundException, MrzParseException {
		Assert.assertEquals("Did not find valid wrapped MRZ", VALID_MRZ, MrzFinderUtil.findMrz(WRAPPED_VALID));
	}

	@Test(expected = MrzParseException.class)
	public void testInvalidMrz() throws MrzNotFoundException, MrzParseException {
		MrzFinderUtil.findMrz(INVALID_MRZ);
	}

	@Test(expected = MrzParseException.class)
	public void testInvalidWrappedMrz() throws MrzNotFoundException, MrzParseException {
		MrzFinderUtil.findMrz(WRAPPED_INVALID_MRZ);
	}

	@Test(expected = MrzNotFoundException.class)
	public void testNotFoundMrz() throws MrzNotFoundException, MrzParseException {
		MrzFinderUtil.findMrz(NO_MRZ);
	}

	@Test(expected = MrzNotFoundException.class)
	public void testNullMrz() throws MrzNotFoundException, MrzParseException {
		MrzFinderUtil.findMrz(null);
	}

	@Test(expected = MrzNotFoundException.class)
	public void testEmptyMrz() throws MrzNotFoundException, MrzParseException {
		MrzFinderUtil.findMrz("");
	}

}
