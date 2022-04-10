/*
 * Copyright (c) 2022. Vladislav Golubev
 */

package com.jiubredeemer.alfabanktesttask.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Gif {
    private String type;
    private String id;
    private String slug;
    private String bitly_url;
    private String embed_url;
    private String username;
    private String source;
    private String rating;
    private String content_url;
    private String string;
    private String source_post_url;
    private String update_datetime;
    private String create_datetime;
    private String import_datetime;
    private String trending_datetime;
    private String title;
}
