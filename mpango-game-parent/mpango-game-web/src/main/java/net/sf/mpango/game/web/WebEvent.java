package net.sf.mpango.game.web;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: etux
 * Date: 7/3/11
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebEvent {

    private String name;
    private Date date;

    public WebEvent(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {

        return name;
    }
}
