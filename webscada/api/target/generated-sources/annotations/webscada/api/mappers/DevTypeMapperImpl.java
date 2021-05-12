package webscada.api.mappers;

import javax.annotation.Generated;
import webscada.api.dto.DevTypeDto;
import webscada.entity.DevType;
import webscada.entity.DevType.DevTypeBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-12T15:42:31+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.10 (Oracle Corporation)"
)
public class DevTypeMapperImpl implements DevTypeMapper {

    @Override
    public DevType mapDevType(DevTypeDto source) {
        if ( source == null ) {
            return null;
        }

        DevTypeBuilder<?, ?> devType = DevType.builder();

        devType.id( source.getId() );
        devType.description( source.getDescription() );
        devType.url( source.getUrl() );

        return devType.build();
    }

    @Override
    public DevTypeDto mapDevTypeDto(DevType source) {
        if ( source == null ) {
            return null;
        }

        DevTypeDto devTypeDto = new DevTypeDto();

        if ( source.getId() != null ) {
            devTypeDto.setId( source.getId() );
        }

        return devTypeDto;
    }
}
