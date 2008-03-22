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


package jd.config;

import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.logging.Logger;

import jd.utils.JDUtilities;

public class ConfigEntry implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7422046260361380162L;

    @SuppressWarnings("unused")
    private static Logger logger = JDUtilities.getLogger();
    private int              type;

    private String           label;
    private ActionListener   actionListener;
    private String           propertyName;
    private Property         propertyInstance;
    private Object[]         list;
    private Object           defaultValue;
    private int              step   = 1;
    private int              start;
    private int              end;

    private boolean enabled=true;

    private boolean expertEntry =false;

    private String instantHelp;

    /**
     * Konstruktor für z.B. Buttons (Label+ Actionlistener)
     * 
     * @param type Typ ID (ConfigContainer.TYPE_*)
     * @param listener Actionlistener. Actionlistener werden z.B. von Buttons
     *            unterstützt
     * @param label Label für die Komponente
     */
    public ConfigEntry(int type, ActionListener listener, String label) {
        this.type = type;
        this.label = label;
        this.actionListener = listener;
        enabled=true;
    }

    /**
     * Konstruktor für ein einfaches Label
     * 
     * @param type
     * @param label
     */
    public ConfigEntry(int type, String label) {
        this.type = type;
        this.label = label;
        enabled=true;
    }
    /**
     * Konstruktor für z.B. einen Link (label& url
     * 

    public ConfigEntry(int type, String label, String link) {
     */
    
    public ConfigEntry(int type,String label, String link) {
        this.type = type;
        this.propertyName = link;     
        this.label = label;
        enabled=true;
    }
    /**
     * Konstruktor für z.B. ein Textfeld (label& ein eingabefeld
     * 
     * @param type TYP ID
     * @param propertyInstance EINE Instanz die von der propertyklasse
     *            abgeleitet wurde. mit hilfe von propertyName werden
     *            Informationen aus ihr gelesen und wieder in ihr abgelegt
     * @param propertyName propertyname über den auf einen wert in der
     *            propertyInstanz zugegriffen wird
     * @param label angezeigtes label
     */
    public ConfigEntry(int type, Property propertyInstance, String propertyName, String label) {
        this.type = type;
        this.propertyName = propertyName;
        this.propertyInstance = propertyInstance;
        this.label = label;
        enabled=true;
    }

    public ConfigEntry setEnabled(boolean value){
        this.enabled=value;
        return this;
    }
    /**
     * Konstruktor z.B. für Combobox oder radiofield ( mehrere werte(list),
     * eine auswahl (property)
     * 
     * @param type
     * @param propertyInstance EINE INstanz die von der propertyklasse
     *            abgeleitet wurde. mit hilfe von propertyName werden
     *            Informationen aus ihr gelesen und wieder in ihr abgelegt
     * @param propertyName propertyname über den auf einen wert in der
     *            propertyInstanz zugegriffen wird
     * @param list Liste mit allen werten aus denen ausgewählt werden kann
     * @param label
     */
    public ConfigEntry(int type, Property propertyInstance, String propertyName, Object[] list, String label) {
        this.type = type;
        this.propertyName = propertyName;
        this.propertyInstance = propertyInstance;

        this.list = list;
        this.label = label;
    }

    /**
     * Konstruktor z.B. für einen JSpinner (property, label, range (start/end)
     * 
     * @param type
     * @param propertyInstance EINE INstanz die von der propertyklasse
     *            abgeleitet wurde. mit hilfe von propertyName werden
     *            Informationen aus ihr gelesen und wieder in ihr abgelegt
     * @param propertyName propertyname über den auf einen wert in der
     *            propertyInstanz zugegriffen wird
     * @param label
     * @param start Range-start
     * @param end range-ende
     */
    public ConfigEntry(int type, Property propertyInstance, String propertyName, String label, int start, int end) {
        this.type = type;
        this.propertyName = propertyName;
        this.propertyInstance = propertyInstance;

        this.label = label;
        this.start = start;
        this.end = end;
    }

    /**
     * KOnstruktor für Komponenten die nix brauchen. z.B. JSpearator
     * 
     * @param type
     */
    public ConfigEntry(int type) {
        this.type = type;
    }

    /**
     * Gibt den Typ zurück
     * 
     * @return Typ des Eintrages
     */
    public int getType() {
        return type;
    }

    public ConfigEntry setType(int type) {
        this.type = type;
        return this;
    }
    
    public String getLabel() {
        return label;
    }
    
    public ConfigEntry setLabel(String label) {
        this.label = label;
        return this;
    }

    public ActionListener getActionListener() {
        return actionListener;
    }

    public ConfigEntry setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
        return this;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public ConfigEntry setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    public Property getPropertyInstance() {
        return propertyInstance;
    }

    public ConfigEntry setPropertyInstance(Property propertyInstance) {
        this.propertyInstance = propertyInstance;
        return this;
    }

    public Object[] getList() {
        return list;
    }

    public ConfigEntry setList(Object[] list) {
        this.list = list;
        return this;
    }

    public Object getDefaultValue() {
        return defaultValue;

    }

    /**
     * Legtd en defaultwert fest, falls in der propertyinstanz keiner gefunden
     * wurde.
     * 
     * @param value
     * @return this. damit ist eine Struktur new
     *         ConfigEntry(...).setdefaultValue(...).setStep(...).setBla...
     *         möglich
     */
    public ConfigEntry setDefaultValue(Object value) {
        defaultValue = value;
        return this;

    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }

    public int getStep() {
        return step;
    }

    /**
     * Setzt die Schrittbreite für alle Komponenten die mit Schritten arbeiten.
     * z.B. JSpiner
     * 
     * @param step
     * @return this. damit ist eine Struktur new
     *         ConfigEntry(...).setdefaultValue(...).setStep(...).setBla...
     *         möglich
     * 
     */
    public ConfigEntry setStep(int step) {
        this.step = step;
        return this;
    }

    public ConfigEntry setStart(int start) {
        this.start = start;
        return this;
    }

    public ConfigEntry setEnd(int end) {
        this.end = end;
        return this;
    }

    public boolean isEnabled() {
     
        return enabled;
    }

    public ConfigEntry setExpertEntry(boolean b) {
        this.expertEntry =b;
        return this;
    }

    public boolean isExpertEntry() {
        return expertEntry;
    }

    public ConfigEntry setInstantHelp(String l) {
        this.instantHelp=l;
        return this;
        
    }

    public String getInstantHelp() {
        return instantHelp;
    }

}