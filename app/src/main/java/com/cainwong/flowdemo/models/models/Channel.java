package com.cainwong.flowdemo.models.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by cwong on 1/7/15.
 */
@Root(strict = false)
public class Channel {

    @ElementList(inline = true, required = false)
    public List<Link> links;

    @ElementList(inline = true)
    public List<Item> items;

    @Element
    String title;

    @Element(required = false)
    String language;

    @Element(required = false)
    String description;

    @Element(required = false)
    String lastBuildDate;

    @Element(required = false)
    Integer ttl;

    @Element(required = false)
    String updatePeriod;

    @Element(required = false)
    Integer updateFrequency;

    @Element(required = false)
    String generator;

    @Element(required = false)
    Image image;

    public Channel() {
    }

}
