package general.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileToObjectListConverter<T> {
	
	public List<T> convertFileToObjectList(MultipartFile file) throws IOException;

}
