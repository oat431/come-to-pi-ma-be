package panomete.project.cometopimabe.common.helper;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import panomete.project.cometopimabe.common.response.PageDto;

public interface BaseHelper<T,G> {
    PageDto<T> convertToPageDto(Page<G> result);
    PageDto<T> convertDtoToPageDto(Page<T> result);
    ResponseEntity<T> showResponse(G result);
}
