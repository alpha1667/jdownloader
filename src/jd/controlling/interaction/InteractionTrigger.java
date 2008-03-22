//    jDownloader - Downloadmanager
//    Copyright (C) 2008  JD-Team jdownloader@freenet.de
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program  is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSSee the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://wnu.org/licenses/>.


package jd.controlling.interaction;

import java.io.Serializable;
import java.util.Vector;
import java.util.logging.Logger;

import jd.utils.JDUtilities;

/**
 * Diese Klasse stellt einen Trigger für den Eventmanager dar
 * 
 * @author JD-Team
 */
public class InteractionTrigger implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long                 serialVersionUID = 8656898503474841842L;

 
/**
 * Vector mit allen bisher angelegten triggern
 */

    private static Vector<InteractionTrigger> events           = new Vector<InteractionTrigger>();

    protected static Logger                   logger           = JDUtilities.getLogger();

    /**
     * EventiD
     */
    private int                               eventID;
/**
 * Trigger Name
 */
    private String                            name;
/**
 * Triggerbeschreibung
 */
    private String                            description;
/**
 * Gibt alle bisher angelegten Trigger zurück
 * @return
 */
    public static InteractionTrigger[] getAllTrigger() {
        InteractionTrigger[] ret = new InteractionTrigger[events.size()];
        for (int i = 0; i < events.size(); i++)
            ret[i] = events.elementAt(i);
        return ret;
    }
    

/**
 * Erstellt einen neuen Trigger. ACHTUNG. Beim instanzieren werden die TRigger gleich in einen vector geschrieben und dadurch NIE! vom GarbageCollector erfasst.
 * Man sollte also im Normalen programmablauf keine neuen Trigger mehr Instanzieren
 * @param id
 * @param name
 * @param description
 */
    public InteractionTrigger(int id,String name, String description) {
        eventID = id;
        events.add(this);
        this.name = name;
        this.description = description;
    }

    public String toString() {
        return name+" ("+description+")";
    }
    public String getName(){
        return name;
    }
/**
 * Gibt die EventID zurück. Es gibt keine setID!
 * @return
 */
    public int getID() {
        return eventID;
    }
/**
 * Gibt die Triggerbeschreibung zurück
 * @return
 */
    public String getDescription() {
        return description;
    }
/**
 * Setzt die TRiggerbeschreibung
 * @param description
 */
    public void setDescription(String description) {
        this.description = description;
    }

}
