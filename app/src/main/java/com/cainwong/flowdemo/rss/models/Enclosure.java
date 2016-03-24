package com.cainwong.flowdemo.rss.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by cwong on 1/7/15.
 */
@Root( strict = false )
public class Enclosure {

    @Attribute
    public String url;

    @Attribute
    public String type;

    @Attribute(required = false)
    public Integer length;

    public Enclosure() {
    }

}
