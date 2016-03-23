package com.cainwong.flowdemo.models.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by cwong on 1/7/15.
 */
@Root(strict = false)
public class Item {

    @Element
    public String guid;

    @Element
    public String title;

    @Element
    public String link;

    @Element
    public String description;

    @Element
    public String pubDate;

    @Element(required = false)
    public String creator;

    @Element(required = false)
    public Enclosure enclosure;

    @Element(required = false)
    public String encoded;

    @ElementList(inline = true, required = false)
    public List<Thumbnail> thumbnails;

    public Item() {
    }

}
