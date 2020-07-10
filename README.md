# MRZ

Machine-Readable Zone (MRZ, see http://en.wikipedia.org/wiki/Machine-readable_passport ) parser for Java, as defined by ICAO: http://www.icao.int/

## Status

[![Build Status](https://travis-ci.com/BorderTech/mrz-java.svg?branch=master)](https://travis-ci.com/BorderTech/mrz-java)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=bordertech-mrz-java&metric=alert_status)](https://sonarcloud.io/dashboard?id=bordertech-mrz-java)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=bordertech-mrz-java&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=bordertech-mrz-java)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=bordertech-mrz-java&metric=coverage)](https://sonarcloud.io/dashboard?id=bordertech-mrz-java)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/6c7067bc7882480abdae338befb41e11)](https://www.codacy.com/gh/BorderTech/mrz-java?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=BorderTech/mrz-java&amp;utm_campaign=Badge_Grade)
[![javadoc](https://javadoc.io/badge2/com.github.bordertech.mrz/mrz-java/javadoc.svg)](https://javadoc.io/doc/com.github.bordertech.mrz/mrz-java)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.bordertech.mrz/mrz-java.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.bordertech.mrz%22%20AND%20a:%22mrz-java%22)

## Getting started

Add dependency:

``` xml
<project>
  ....
  <dependency>
    <groupId>com.github.bordertech.mrz</groupId>
    <artifactId>mrz-java</artifactId>
    <version>0.6</version>
  </dependency>
  ....
</project>
```

Simple usage:

``` java
try {
  final MrzRecord record = MrzParser.parse("I<UTOSTEVENSON<<PETER<<<<<<<<<<<<<<<\nD231458907UTO3407127M9507122<<<<<<<2");
  LOG.debug("Name: " + record.getGivenNames() + " " + record.getSurname());
} catch (MrzParseException ex) {
  LOG.error("Invalid MRZ.", ex);
}
```

## Download and run demo

* Download the latest `mrz-java-VERSION-jar-with-dependencies.jar` from [maven](https://repo1.maven.org/maven2/com/github/bordertech/mrz/mrz-java)
* cd download directory
* run `java -jar mrz-java-VERSION-jar-with-dependencies.jar`

## Build and run demo

If you wish to build the project you will need [Apache Maven](https://maven.apache.org/) installed. Minimum requirements are `Maven 3.3.9` and `Java 8`.

Follow these commands to fetch the source, build and run:

* git clone https://github.com/bordertech/mrz-java.git (first time only)
* cd mrz-java
* mvn install

Run standalone jar:

* cd target
* java -jar mrz-java-VERSION-jar-with-dependencies.jar

Run in IDE:

* Run main class `com.innovatrics.mrz.Demo`

## Branches

Branch "master" is for hotfixes only. For enhancements and minor bugfixes, please use branch "development".

## License
  Java parser for the MRZ records, as specified by the ICAO organization.
  Copyright (C) 2011 Innovatrics s.r.o.

  This library is free software; you can redistribute it and/or
  modify it under the terms of the GNU Lesser General Public
  License as published by the Free Software Foundation; either
  version 2.1 of the License, or (at your option) any later version.

  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public
  License along with this library; if not, write to the Free Software
  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

Original maintainer (till 2017-05-28): Martin Vysny
