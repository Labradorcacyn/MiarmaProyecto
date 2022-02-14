package com.miarma.cynthia.users.dto.file;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor
@Builder @Data
public class FileDto {

    private String name;
    private String uri;
    private String type;
    private long size;
}