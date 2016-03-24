package com.cainwong.flowdemo.rss.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;

/**
 * Created by cwong on 1/7/15.
 */

@NamespaceList({
        @Namespace(reference = "http://www.w3.org/2005/Atom", prefix = "atom"),
        @Namespace(reference="http://purl.org/dc/elements/1.1/", prefix="dc"),
        @Namespace(reference="http://purl.org/rss/1.0/modules/content/", prefix="content"),
        @Namespace(reference="http://wellformedweb.org/CommentAPI/", prefix="wfw"),
        @Namespace(reference="http://purl.org/rss/1.0/modules/syndication/", prefix="sy"),
        @Namespace(reference="http://purl.org/rss/1.0/modules/slash/", prefix="slash"),
        @Namespace(reference="http://www.georss.org/georss", prefix="georss"),
        @Namespace(reference="http://www.w3.org/2003/01/geo/wgs84_pos#", prefix="geo"),
        @Namespace(reference="http://search.yahoo.com/mrss/", prefix="media")
})
@org.simpleframework.xml.Root( strict = false )
public class RssRoot {

    @Attribute
    public String version;

    @Element
    public Channel channel;


    public RssRoot() {
    }

}
