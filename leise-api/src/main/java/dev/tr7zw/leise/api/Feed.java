package dev.tr7zw.leise.api;

import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Feed {
    private String title;
    @Singular
    private List<Entry> entries;
}
