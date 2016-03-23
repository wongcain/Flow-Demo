package com.cainwong.flowdemo.models.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 * Created by cwong on 1/8/15.
 */
@Root(strict = false)
public class Link {

    @Attribute(required = false)
    public String href;

    @Attribute(required = false)
    public String rel;

    @Attribute(required = false)
    public String type;

    @Attribute(required = false)
    public String title;

    @Text(required = false)
    public String link;

    public Link() {
    }

}
