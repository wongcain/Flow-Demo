package com.cainwong.flowdemo.models.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by cwong on 1/8/15.
 */
@Root(strict = false)
public class Image {

    @Element
    public String url;

    @Element
    public String title;

    @Element
    public String link;

    public Image() {
    }

}
