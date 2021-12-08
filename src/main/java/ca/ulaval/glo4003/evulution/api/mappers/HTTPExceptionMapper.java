package ca.ulaval.glo4003.evulution.api.mappers;

import ca.ulaval.glo4003.evulution.api.mappers.mapping.HTTPExceptionMapping;

public interface HTTPExceptionMapper {
    HTTPExceptionMapping map(Object genericExceptionClass);
}
