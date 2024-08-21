package kg.attractor.jobsearch.util;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class CustomMultipartFile implements MultipartFile {

    private final byte[] fileContent;
    private final String fileName;
    private final String contentType;

    public CustomMultipartFile(@NonNull byte[] fileContent, @NonNull String fileName, @NonNull String contentType) {
        this.fileContent = fileContent;
        this.fileName = fileName;
        this.contentType = contentType;
    }

    @Override
    @NonNull
    public String getName() {
        return fileName;
    }

    @Override
    @NonNull
    public String getOriginalFilename() {
        return fileName;
    }

    @Override
    @NonNull
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return fileContent.length == 0;
    }

    @Override
    public long getSize() {
        return fileContent.length;
    }

    @Override
    @NonNull
    public byte[] getBytes() {
        return fileContent;
    }

    @Override
    @NonNull
    public InputStream getInputStream() {
        return new ByteArrayInputStream(fileContent);
    }

    @Override
    public void transferTo(@NonNull java.io.File dest) throws IllegalStateException {
        throw new UnsupportedOperationException("This operation is not supported.");
    }
}