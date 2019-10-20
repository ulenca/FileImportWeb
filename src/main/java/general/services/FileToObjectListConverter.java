package general.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import general.model.ConversionResult;

public interface FileToObjectListConverter<T> {
	
	public List<ConversionResult<T>> convertFileToResultList(MultipartFile file) throws IOException;

}
