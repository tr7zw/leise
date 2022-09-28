package dev.tr7zw.leise.api;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Entry {
    private String title;
    private String url;
    private String description;
    private String previewImage;
}
