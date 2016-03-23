package com.cainwong.flowdemo.models.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by cwong on 1/8/15.
 */
@Root(strict = false)
public class Thumbnail {

    @Attribute
    public String url;

    public Thumbnail() {
    }

}
