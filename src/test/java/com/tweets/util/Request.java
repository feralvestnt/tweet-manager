package com.tweets.util;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

//@Component
public class Request {

    public static MediaType getContentType() {
        return new MediaType(MediaType.APPLICATION_JSON.getType(),
                MediaType.APPLICATION_JSON.getSubtype(),
                Charset.forName("utf8"));
    }
}
