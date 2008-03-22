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


package jd.plugins.decrypt;  import jd.config.ConfigContainer;
import jd.config.ConfigEntry;
import jd.plugins.DownloadLink;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;
import java.util.regex.Pattern;

import jd.plugins.PluginForDecrypt;
import jd.plugins.PluginStep;
import jd.plugins.RequestInfo;
import jd.utils.JDLocale;

public class FrozenRomsIn extends PluginForDecrypt {

    final static String host             = "frozen-roms.in";

    private String      version          = "0.2.0";
    
    private Pattern     patternSupported = getSupportPattern(
    		"http://[*]frozen-roms\\.in/(details_[0-9]+|get_[0-9]+_[0-9]+).html");
    
    private static final String[] HOSTERS = new String[] {
    	"FileFactory.com", "Netload.in", "Rapidshare.com"
		};

    public FrozenRomsIn() {
    	
        super();
        steps.add(new PluginStep(PluginStep.STEP_DECRYPT, null));
        currentStep = steps.firstElement();
        this.setConfigElements();
        
    }

    @Override
    public String getCoder() {
        return "eXecuTe";
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getPluginID() {
        return host+"-"+version;
    }

    @Override
    public String getPluginName() {
        return host;
    }

    @Override
    public Pattern getSupportedLinks() {
        return patternSupported;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public boolean doBotCheck(File file) {
        return false;
    }
    
    private boolean getHosterUsed(String link) {
    	
        if ( link == null ) return false;
        link = link.toLowerCase();
        
        for ( int i = 0; i < HOSTERS.length; i++ ) {
        	
            if ( link.contains(HOSTERS[i].toLowerCase()) ) {
                return getProperties().getBooleanProperty(HOSTERS[i], true);
            }
            
        }
        
        return false;
        
    }

    @Override
    public PluginStep doStep(PluginStep step, String parameter) {
    	
    	if(step.getStep() == PluginStep.STEP_DECRYPT) {
    		
            Vector<DownloadLink> decryptedLinks = new Vector<DownloadLink>();
            RequestInfo reqinfo;
            Vector<Vector<String>> getLinks = new Vector<Vector<String>>();
            
    		try {
    			
    			if ( parameter.indexOf("get") != -1 ) {
    				
    				Vector<String> tempVector = new Vector<String>();
    				tempVector.add(getBetween(parameter,"http://frozen-roms.in/get_",".html"));
    				getLinks.add(tempVector);
    				
    			} else {
    				
    				reqinfo = getRequest(new URL(parameter));
    				getLinks = getAllSimpleMatches(reqinfo.getHtmlCode(),
    						"href=\"http://frozen-roms.in/get_°.html\"");
    				
    			}
    			
    			progress.setRange(getLinks.size());
    			
    			for ( int i=0; i<getLinks.size(); i++ ) {
    				
    				reqinfo = getRequest(new URL(
    						"http://frozen-roms.in/get_"+getLinks.get(i).get(0)+".html"));
    				String link = reqinfo.getConnection().getHeaderField("Location");
    				if ( getHosterUsed(link) ) decryptedLinks.add(this.createDownloadlink(link));
    				progress.increase(1);
    				
    			}
    			
    			step.setParameter(decryptedLinks);
    			
    		} catch(IOException e) {
    			 e.printStackTrace();
    		}
    		
    	}
    	
    	return null;
    	
    }
    
    private void setConfigElements() {
    	
        ConfigEntry cfg;
        
        config.addEntry(cfg = new ConfigEntry(ConfigContainer.TYPE_LABEL,
        		JDLocale.L("plugins.decrypt.general.hosterSelection", "Hoster Auswahl")));
        config.addEntry(cfg = new ConfigEntry(ConfigContainer.TYPE_SEPARATOR));
        
        for ( int i = 0; i < HOSTERS.length; i++ ) {
        	
            config.addEntry(cfg = new ConfigEntry(ConfigContainer.TYPE_CHECKBOX,
            		getProperties(), HOSTERS[i], HOSTERS[i]));
            cfg.setDefaultValue(true);
            
        }
        
    }
    
}