package org.twittercity.twittercitymod.data.db;

import javax.persistence.AttributeConverter;

import org.twittercity.twittercitymod.tileentity.Feeling;

public class FeelingEnumConverter implements AttributeConverter<Feeling, Integer>{

	@Override
	public Integer convertToDatabaseColumn(Feeling attribute) {
		return new Integer(attribute.getFeelingID());
	}

	@Override
	public Feeling convertToEntityAttribute(Integer dbData) {
		return Feeling.forFeelingID(dbData);
	}

}
