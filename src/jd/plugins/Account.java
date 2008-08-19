//    jDownloader - Downloadmanager
//    Copyright (C) 2008  JD-Team jdownloader@freenet.de
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

package jd.plugins;

import jd.config.Property;

public class Account extends Property {
    /**
     * 
     */
    private static final long serialVersionUID = -7578649066389032068L;
    private String user;
    private String pass;

    private boolean enabled = false;
    private String status = null;
    private transient boolean tempDisabled = false;

    public Account(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public String getUser() {

        return user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isTempDisabled() {
        // TODO Auto-generated method stub
        return tempDisabled;
    }

    public void setTempDisabled(boolean tempDisabled) {
        this.tempDisabled = tempDisabled;
    }

}
