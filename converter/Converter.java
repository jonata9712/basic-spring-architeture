
import br.com.bernhoeft.meetings.domain.AbstractEntity;
import br.com.bernhoeft.meetings.dto.AbstractDTO;

public abstract interface Converter<T extends AbstractEntity, DTO extends AbstractDTO>
{
	public default DTO from(T entity)
	{
		throw new UnsupportedOperationException();
	}
	
	public default T to(DTO dto)
	{
		return to(dto, null);
	}
	
	public default T to(DTO dto, T entity)
	{
		throw new UnsupportedOperationException();
	}
}