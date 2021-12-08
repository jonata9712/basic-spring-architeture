package br.com.bernhoeft.meetings.converter;

import br.com.bernhoeft.meetings.domain.AbstractEntity;
import br.com.bernhoeft.meetings.dto.AbstractDTO;

public abstract interface Converter<Entity extends AbstractEntity, DTO extends AbstractDTO>
{
	public default DTO from(Entity entity)
	{
		throw new UnsupportedOperationException();
	}
	
	public default Entity to(DTO dto)
	{
		return to(dto, null);
	}
	
	public default Entity to(DTO dto, Entity entity)
	{
		throw new UnsupportedOperationException();
	}
}