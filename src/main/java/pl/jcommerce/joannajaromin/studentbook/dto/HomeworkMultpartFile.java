package pl.jcommerce.joannajaromin.studentbook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import pl.jcommerce.joannajaromin.studentbook.entity.Homework;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@AllArgsConstructor
@Getter

public class HomeworkMultpartFile implements MultipartFile {

    private Homework homework;

    @Override
    public String getName() {
        return homework.getFileName();
    }

    @Override
    public String getOriginalFilename() {
        return homework.getFileName();
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return homework == null || homework.getFileData().length == 0;
    }

    @Override
    public long getSize() {
        return homework.getFileData().length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return homework.getFileData();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(homework.getFileData());
    }

    @Override
    public void transferTo(File file) throws IOException, IllegalStateException {
        new FileOutputStream(file).write(homework.getFileData());
    }
}
