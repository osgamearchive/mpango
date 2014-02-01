package net.sf.mpango.game.web;

import java.util.Date;

/**
 *
 */
public class WebEvent {

    private String name;
    private Date date;

    public WebEvent(final String name, final Date date) {
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
