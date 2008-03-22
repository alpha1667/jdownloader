package jd;

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






import java.awt.Toolkit;
import java.io.File;
import java.net.CookieHandler;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jd.config.Configuration;
import jd.controlling.JDController;
import jd.controlling.ProgressController;
import jd.controlling.interaction.InfoFileWriter;
import jd.controlling.interaction.Interaction;
import jd.controlling.interaction.Unrar;
import jd.gui.UIInterface;
import jd.gui.skins.simple.SimpleGUI;
import jd.plugins.BackupLink;
import jd.plugins.DownloadLink;
import jd.plugins.PluginForContainer;
import jd.plugins.PluginForDecrypt;
import jd.plugins.PluginForHost;
import jd.plugins.PluginOptional;
import jd.update.WebUpdater;
import jd.utils.JDLocale;
import jd.utils.JDTheme;
import jd.utils.JDUtilities;
import sun.misc.Service;

/**
 * @author JD-Team
 */

public class JDInit {

    private static Logger logger = JDUtilities.getLogger();
    private boolean installerVisible=false;
    private int cid=-1;
    public JDInit() {

    }

    void init() {
        CookieHandler.setDefault(null);
      

    }
/**
 * Bilder werden dynamisch aus dem Homedir geladen.
 */
    public void loadImages() {
        ClassLoader cl = JDUtilities.getJDClassLoader();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
     
        
       File dir=JDUtilities.getResourceFile("jd/img/");
        
        String[] images = dir.list();
        if(images==null||images.length==0){
            logger.severe("Could not find the img directory");
            return;
        }
        for( int i=0; i<images.length;i++){
            if(images[i].toLowerCase().endsWith(".png")||images[i].toLowerCase().endsWith(".gif")){
                File f=new File(images[i]);
                
               logger.finer("Loaded image: "+f.getName().split("\\.")[0]+" from "+cl.getResource("jd/img/"+f.getName()));
                JDUtilities.addImage(f.getName().split("\\.")[0], toolkit.getImage(cl.getResource("jd/img/"+f.getName())));
            }
            
        }
//        
//        JDUtilities.addImage("add", toolkit.getImage(cl.getResource("img/add.png")));
//        JDUtilities.addImage("configuration", toolkit.getImage(cl.getResource("img/configuration.png")));
//        JDUtilities.addImage("delete", toolkit.getImage(cl.getResource("img/delete.png")));
//        JDUtilities.addImage("dnd", toolkit.getImage(cl.getResource("img/dnd.png")));
//        JDUtilities.addImage("clipboard", toolkit.getImage(cl.getResource("img/clipboard.png")));
//        JDUtilities.addImage("clipboard", toolkit.getImage(cl.getResource("img/clipboard.png")));
//        JDUtilities.addImage("down", toolkit.getImage(cl.getResource("img/down.png")));
//        JDUtilities.addImage("exit", toolkit.getImage(cl.getResource("img/shutdown.png")));
//        JDUtilities.addImage("led_empty", toolkit.getImage(cl.getResource("img/led_empty.gif")));
//        JDUtilities.addImage("led_green", toolkit.getImage(cl.getResource("img/led_green.gif")));
//        JDUtilities.addImage("load", toolkit.getImage(cl.getResource("img/load.png")));
//        JDUtilities.addImage("log", toolkit.getImage(cl.getResource("img/log.png")));
//        JDUtilities.addImage("jd_logo", toolkit.getImage(cl.getResource("img/jd_logo.png")));
//        JDUtilities.addImage("jd_logo_large", toolkit.getImage(cl.getResource("img/jd_logo_large.png")));
//        JDUtilities.addImage("jd_logo_blog", toolkit.getImage(cl.getResource("img/jd_blog.png")));
//        JDUtilities.addImage("reconnect", toolkit.getImage(cl.getResource("img/reconnect.png")));
//        JDUtilities.addImage("save", toolkit.getImage(cl.getResource("img/save.png")));
//        JDUtilities.addImage("start", toolkit.getImage(cl.getResource("img/start.png")));
//        JDUtilities.addImage("stop", toolkit.getImage(cl.getResource("img/stop.png")));
//        JDUtilities.addImage("up", toolkit.getImage(cl.getResource("img/up.png")));
//        JDUtilities.addImage("update", toolkit.getImage(cl.getResource("img/update.png")));
//        JDUtilities.addImage("search", toolkit.getImage(cl.getResource("img/search.png")));
//        JDUtilities.addImage("bottom", toolkit.getImage(cl.getResource("img/bottom.png")));
//        JDUtilities.addImage("bug", toolkit.getImage(cl.getResource("img/bug.png")));
//        JDUtilities.addImage("home", toolkit.getImage(cl.getResource("img/home.png")));
//        JDUtilities.addImage("loadContainer", toolkit.getImage(cl.getResource("img/loadContainer.png")));
//        JDUtilities.addImage("ok", toolkit.getImage(cl.getResource("img/ok.png")));
//        JDUtilities.addImage("pause", toolkit.getImage(cl.getResource("img/pause.png")));
//        JDUtilities.addImage("pause_disabled", toolkit.getImage(cl.getResource("img/pause_disabled.png")));
//        JDUtilities.addImage("pause_active", toolkit.getImage(cl.getResource("img/pause_active.png")));
//        JDUtilities.addImage("shutdown", toolkit.getImage(cl.getResource("img/shutdown.png")));
//        JDUtilities.addImage("top", toolkit.getImage(cl.getResource("img/top.png")));
    }

    public Configuration loadConfiguration() {
        File fileInput = null;
        try {
            fileInput = JDUtilities.getResourceFile(JDUtilities.CONFIG_PATH);
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        boolean allOK = true;
        try {

            if (fileInput != null && fileInput.exists()) {
                Object obj = JDUtilities.loadObject(null, fileInput, Configuration.saveAsXML);
                if (obj instanceof Configuration) {
                    Configuration configuration = (Configuration) obj;
                    JDUtilities.setConfiguration(configuration);
                    JDUtilities.getLogger().setLevel((Level) configuration.getProperty(Configuration.PARAM_LOGGER_LEVEL, Level.WARNING));
                    JDLocale.setLocale(JDUtilities.getSubConfig(SimpleGUI.GUICONFIGNAME).getStringProperty(SimpleGUI.PARAM_LOCALE,"german"));
                    JDTheme.setTheme(JDUtilities.getSubConfig(SimpleGUI.GUICONFIGNAME).getStringProperty(SimpleGUI.PARAM_THEME,"default"));
                }
                else {
                    // log += "\r\n" + ("Configuration error: " + obj);
                    // log += "\r\n" + ("Konfigurationskonflikt. Lade Default
                    // einstellungen");
                    allOK = false;
                    if (JDUtilities.getConfiguration() == null) JDUtilities.getConfiguration().setDefaultValues();
                }
            }
            else {
                logger.info ("no configuration loaded");
                logger.info ("Konfigurationskonflikt. Lade Default einstellungen");
                
                allOK = false;
                if (JDUtilities.getConfiguration() == null) JDUtilities.getConfiguration().setDefaultValues();
            }
        }
        catch (Exception e) {
           logger.info("Konfigurationskonflikt. Lade Default einstellungen");
           e.printStackTrace();
            allOK = false;
            if (JDUtilities.getConfiguration() == null) JDUtilities.setConfiguration(new Configuration());
            JDUtilities.getConfiguration().setDefaultValues();
        }

        if (!allOK) {
         
        
            installerVisible=true;
            Installer inst = new Installer();
            if (!inst.isAborted() && inst.getHomeDir() != null && inst.getDownloadDir() != null) {

                String newHome = inst.getHomeDir();
                logger.info("Home Dir: " + newHome);
                File homeDirectoryFile = new File(newHome);
                boolean createSuccessfull = true;
                if (!homeDirectoryFile.exists()) createSuccessfull = homeDirectoryFile.mkdirs();
                if (createSuccessfull && homeDirectoryFile.canWrite()) {
                    System.setProperty("jdhome", homeDirectoryFile.getAbsolutePath());
                    String dlDir = inst.getDownloadDir();

                    JOptionPane.showMessageDialog(new JFrame(), JDLocale.L("installer.welcome","Welcome to jDownloader. Download missing files."));

                    JDUtilities.getConfiguration().setProperty(Configuration.PARAM_DOWNLOAD_DIRECTORY, dlDir);

                    JDUtilities.download(new File(homeDirectoryFile, "webupdater.jar"), "http://jdownloaderwebupdate.ath.cx");

                    JDUtilities.setHomeDirectory(homeDirectoryFile.getAbsolutePath());

                    JDUtilities.saveConfig();
                    logger.info(JDUtilities.runCommand("java", new String[] { "-jar", "webupdater.jar", "/restart", "/rt" + JDUtilities.RUNTYPE_LOCAL_JARED }, homeDirectoryFile.getAbsolutePath(), 0));
                    System.exit(0);

                }
                logger.info("INSTALL abgebrochen");
                JOptionPane.showMessageDialog(new JFrame(), JDLocale.L("installer.error.noWriteRights","Fehler. Bitte wähle Pfade mit Schreibrechten!"));

                System.exit(1);
                inst.dispose();
            }
            else {
                logger.info("INSTALL abgebrochen2");
                JOptionPane.showMessageDialog(new JFrame(), JDLocale.L("installer.abortInstallation","Fehler. Installation abgebrochen"));
                System.exit(0);
                inst.dispose();
            }
        }
        this.afterConfigIsLoaded();
        return JDUtilities.getConfiguration();
    }

    private void afterConfigIsLoaded() {
      
        
    }

    public JDController initController() {
        return new JDController();
    }

    public UIInterface initGUI(JDController controller) {
     
        UIInterface uiInterface = new SimpleGUI();
        controller.setUiInterface(uiInterface);
        return uiInterface;
    }

    @SuppressWarnings("unchecked")
	public Vector<PluginForDecrypt> loadPluginForDecrypt() {
        Vector<PluginForDecrypt> plugins = new Vector<PluginForDecrypt>();
        try {
            JDClassLoader jdClassLoader = JDUtilities.getJDClassLoader();
            logger.finer("Load PLugins");
            Iterator iterator = Service.providers(PluginForDecrypt.class, jdClassLoader);
            while (iterator.hasNext()) {
                PluginForDecrypt p = (PluginForDecrypt) iterator.next();
                logger.info("Load "+ p);
                plugins.add(p);
            }
            return plugins;
        }
        catch (Exception e) {
            e.printStackTrace();
            return plugins;
        }
    }

    @SuppressWarnings("unchecked")
	public Vector<PluginForHost> loadPluginForHost() {
        Vector<PluginForHost> plugins = new Vector<PluginForHost>();
        try {
            JDClassLoader jdClassLoader = JDUtilities.getJDClassLoader();
            Iterator iterator;
            logger.finer("Load PLugins");
            iterator = Service.providers(PluginForHost.class, jdClassLoader);
            while (iterator.hasNext()) {
                PluginForHost next = (PluginForHost) iterator.next();
                logger.finer("Load PLugins"+next);
                PluginForHost p = next;

                plugins.add(p);
            }
            return plugins;
        }
        catch (Exception e) {
            e.printStackTrace();
            return plugins;
        }
    }
 public void loadModules(){
     logger.finer("create Module: Unrar");
     JDUtilities.getController().setUnrarModule(Unrar.getInstance());
     logger.finer("create Module: InfoFileWriter");
     JDUtilities.getController().setInfoFileWriterModule(InfoFileWriter.getInstance());
     
     
     
 }

    @SuppressWarnings("unchecked")
	public Vector<PluginForContainer> loadPluginForContainer() {
        Vector<PluginForContainer> plugins = new Vector<PluginForContainer>();
        try {
            JDClassLoader jdClassLoader = JDUtilities.getJDClassLoader();
            Iterator iterator;
            logger.finer("Load PLugins");
            iterator = Service.providers(PluginForContainer.class, jdClassLoader);
            while (iterator.hasNext()) {
                PluginForContainer p = (PluginForContainer) iterator.next();

                plugins.add(p);
            }
            return plugins;
        }
        catch (Exception e) {
            e.printStackTrace();
            return plugins;
        }
    }

    @SuppressWarnings("unchecked")
	public HashMap<String, PluginOptional> loadPluginOptional() {
        HashMap<String, PluginOptional> pluginsOptional = new HashMap<String, PluginOptional>();
        try {
            JDClassLoader jdClassLoader = JDUtilities.getJDClassLoader();
            Iterator iterator;

            iterator = Service.providers(PluginOptional.class, jdClassLoader);
            while (iterator.hasNext()) {
                try {

                    PluginOptional p = (PluginOptional) iterator.next();
                    pluginsOptional.put(p.getPluginName(), p);
                    logger.info("Optionales-Plugin : " + p.getPluginName());

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return pluginsOptional;    
        }
        catch (Exception e) {
            e.printStackTrace();
            return pluginsOptional;
        }
    }

    public void loadDownloadQueue() {
        if (!JDUtilities.getController().initDownloadLinks()) {
            File links = JDUtilities.getResourceFile("links.dat");
            if (links != null && links.exists()) {
                File newFile = new File(links.getAbsolutePath() + ".bup");
                newFile.delete();
                links.renameTo(newFile);
                JDUtilities.getController().getUiInterface().showMessageDialog(JDLocale.L("sys.warning.linklist.incompatible","Linkliste inkompatibel. \r\nBackup angelegt."));
            }
        }

    }

    public void initPlugins() {
        logger.info("Lade Plugins");
        JDController controller = JDUtilities.getController();
        JDUtilities.setPluginForDecryptList(this.loadPluginForDecrypt());
        JDUtilities.setPluginForHostList(this.loadPluginForHost());
        JDUtilities.setPluginForContainerList(this.loadPluginForContainer());
        try {
            JDUtilities.setPluginOptionalList(this.loadPluginOptional());
        }
        catch (Exception e1) {
        }

        Iterator<PluginForHost> iteratorHost = JDUtilities.getPluginsForHost().iterator();
        while (iteratorHost.hasNext()) {
            iteratorHost.next().addPluginListener(controller);
        }
        Iterator<PluginForDecrypt> iteratorDecrypt = JDUtilities.getPluginsForDecrypt().iterator();
        while (iteratorDecrypt.hasNext()) {
            iteratorDecrypt.next().addPluginListener(controller);
        }
        Iterator<PluginForContainer> iteratorContainer = JDUtilities.getPluginsForContainer().iterator();
        while (iteratorContainer.hasNext()) {
            iteratorContainer.next().addPluginListener(controller);
        }

        Iterator<String> iteratorOptional = JDUtilities.getPluginsOptional().keySet().iterator();
        while (iteratorOptional.hasNext()) {
            JDUtilities.getPluginsOptional().get(iteratorOptional.next()).addPluginListener(controller);
        }

        HashMap<String, PluginOptional> pluginsOptional = JDUtilities.getPluginsOptional();

        Iterator<String> iterator = pluginsOptional.keySet().iterator();
        String key;

        while (iterator.hasNext()) {
            key = iterator.next();
            PluginOptional plg = pluginsOptional.get(key);
            if (JDUtilities.getConfiguration().getBooleanProperty("OPTIONAL_PLUGIN_" + plg.getPluginName(), false)) {
                try {
                    pluginsOptional.get(key).enable(true);
                }
                catch (Exception e) {
                    logger.severe("Error loading Optional Plugin: " + e.getMessage());
                }
            }
        }
    }

    public void checkWebstartFile() {

    }
    protected void createQueueBackup() {
        Vector<DownloadLink> links = JDUtilities.getController().getDownloadLinks();
        Iterator<DownloadLink> it = links.iterator();
        Vector<BackupLink> ret= new Vector<BackupLink>();
        while(it.hasNext()){
            DownloadLink next = it.next();
            if(next.getLinkType()==DownloadLink.LINKTYPE_CONTAINER){
                ret.add(new BackupLink(new File(next.getContainerFile()), next.getContainerIndex(), next.getContainer()));
            }else{
                ret.add(new BackupLink(next.getDownloadURL()));  
            }          
            
        }
    
        JDUtilities.getResourceFile("links.linkbackup").delete();
        
        JDUtilities.saveObject(null, ret, JDUtilities.getResourceFile("links.linkbackup"), "links.linkbackup", "linkbackup", false);
        logger.info("hallo "+JDUtilities.getResourceFile("links.linkbackup"));
    }
    public void doWebupdate(final int oldCid,final boolean guiCall) {

        new Thread() {
          

            public void run() {
                ProgressController progress = new ProgressController(JDLocale.L("init.webupdate.progress.0_title","Webupdate"),100);
                
                logger.finer("Init Webupdater");
                WebUpdater updater = new WebUpdater(null);
                if(JDUtilities.getSubConfig("WEBUPDATE").getBooleanProperty("WEBUPDATE_BETA",false)){
                    logger.info("BETA");
                    updater.setListPath("http://ns2.km32221.keymachine.de/jdownloader/betaupdate/");  
                    
                }
                updater.setCid(oldCid);
                logger.finer("Get available files");
                Vector<Vector<String>> files = updater.getAvailableFiles();
                logger.info(files+"");
                updater.filterAvailableUpdates(files, JDUtilities.getResourceFile("."));
//                if(JDUtilities.getSubConfig("JAC").getBooleanProperty(Configuration.USE_CAPTCHA_EXCHANGE_SERVER, false)){
//                for (int i = files.size() - 1; i >= 0; i--) {
//                  
////                    if (files.get(i).get(0).startsWith("jd/captcha/methods/")&&files.get(i).get(0).endsWith("mth")) {
////                        logger.info("Autotrain active. ignore "+files.get(i).get(0));
////                        files.remove(i);
////                    }
//                }
//                }
                if(files!=null ){
                    JDUtilities.getController().setWaitingUpdates(files);
                }
                cid=updater.getCid();
                if(getCid()>0 &&getCid()!= JDUtilities.getConfiguration().getIntegerProperty(Configuration.CID, -1)){
                    JDUtilities.getConfiguration().setProperty(Configuration.CID, getCid());
                    JDUtilities.saveConfig();
                }
                if(!guiCall &&JDUtilities.getConfiguration().getBooleanProperty(Configuration.PARAM_WEBUPDATE_DISABLE, false)){
                    logger.severe("Webupdater disabled");
                    progress.finalize();
                    return;  
                }
                
                if(files==null){
                    logger.severe("Webupdater offline");
                    progress.finalize();
                    return;
                }
          
                int org;
                progress.setRange(org = files.size());
                logger.finer("Files found: " + files);
               
                logger.finer("init progressbar");
                progress.setStatusText(JDLocale.L("init.webupdate.progress.1_title","Update Check"));
                if (files != null) {

                  
                    progress.setStatus(org - files.size());
                    logger.finer("FIles to update: " + files);
                    if (files.size() > 0 ) {
                        createQueueBackup();
                        
                        logger.info("New Updates Available! " + files);
                      
                        if (JDUtilities.getConfiguration().getBooleanProperty(Configuration.PARAM_WEBUPDATE_AUTO_RESTART, false)) {
                            JDUtilities.download(JDUtilities.getResourceFile("webupdater.jar"), "http://jdownloaderwebupdate.ath.cx");
                            JDUtilities.download(JDUtilities.getResourceFile("changeLog.txt"), "http://www.syncom.org/projects/jdownloader/log/?format=changelog");
                            
                            JDUtilities.writeLocalFile(JDUtilities.getResourceFile("webcheck.tmp"), new Date().toString() + "\r\n(Revision" + JDUtilities.getRevision() + ")");
                            logger.info(JDUtilities.runCommand("java", new String[] { "-jar", "webupdater.jar", JDUtilities.getConfiguration().getBooleanProperty(Configuration.PARAM_WEBUPDATE_LOAD_ALL_TOOLS, false) ? "/all" : "", "/restart", "/rt" + JDUtilities.getRunType() }, JDUtilities.getResourceFile(".").getAbsolutePath(), 0));
                            System.exit(0);
                        }
                        else {
                            if (JDUtilities.getController().getUiInterface().showConfirmDialog(files.size() + " update(s) available. Start Webupdater now?")) {
                                JDUtilities.download(JDUtilities.getResourceFile("webupdater.jar"), "http://jdownloaderwebupdate.ath.cx");
                                JDUtilities.download(JDUtilities.getResourceFile("changeLog.txt"), "http://www.syncom.org/projects/jdownloader/log/?format=changelog");
                                
                                JDUtilities.writeLocalFile(JDUtilities.getResourceFile("webcheck.tmp"), new Date().toString() + "\r\n(Revision" + JDUtilities.getRevision() + ")");
                                logger.info(JDUtilities.runCommand("java", new String[] { "-jar", "webupdater.jar", JDUtilities.getConfiguration().getBooleanProperty(Configuration.PARAM_WEBUPDATE_LOAD_ALL_TOOLS, false) ? "/all" : "", "/restart", "/rt" + JDUtilities.getRunType() }, JDUtilities.getResourceFile(".").getAbsolutePath(), 0));
                                System.exit(0);
                            }

                        }

                    }

                }

                progress.finalize();
                if(getCid()>0 &&getCid()!= JDUtilities.getConfiguration().getIntegerProperty(Configuration.CID, -1)){
                    JDUtilities.getConfiguration().setProperty(Configuration.CID, getCid());
                    JDUtilities.saveConfig();
                }
            }

          
        }.start();
    }

    public void checkUpdate() {
        File updater = JDUtilities.getResourceFile("webupdater.jar");
if(updater.exists()){
    if(!updater.delete()){
        logger.severe("Webupdater.jar could not be deleted. PLease remove JDHOME/webupdater.jar to ensure a proper update");
    }
}
        if (JDUtilities.getResourceFile("webcheck.tmp").exists() && JDUtilities.getLocalFile(JDUtilities.getResourceFile("webcheck.tmp")).indexOf("(Revision" + JDUtilities.getRevision() + ")") > 0) {
            JDUtilities.getController().getUiInterface().showTextAreaDialog("Error", "Failed Update detected!", "It seems that the previous webupdate failed.\r\nPlease ensure that your java-version is equal- or above 1.5.\r\nMore infos at http://www.syncom.org/projects/jdownloader/wiki/FAQ.\r\n\r\nErrorcode: \r\n" + JDUtilities.getLocalFile(JDUtilities.getResourceFile("webcheck.tmp")));
            JDUtilities.getResourceFile("webcheck.tmp").delete();
            JDUtilities.getConfiguration().setProperty(Configuration.PARAM_WEBUPDATE_AUTO_RESTART, false);
        }
        else {

            Interaction.handleInteraction(Interaction.INTERACTION_APPSTART, false);
        }

        String hash = "";

        if (JDUtilities.getResourceFile("updatemessage.html").exists()) {
            hash = JDUtilities.getLocalHash(JDUtilities.getResourceFile("updatemessage.html"));
        }

        JDUtilities.getRunType();
        if (!JDUtilities.getConfiguration().getStringProperty(Configuration.PARAM_UPDATE_HASH, "").equals(hash)) {
            logger.info("Returned from Update");
            String lastLog = JDUtilities.UTF8Decode(JDUtilities.getLocalFile(JDUtilities.getResourceFile("updatemessage.html")));
           if(lastLog.trim().length()>5) JDUtilities.getController().getUiInterface().showHTMLDialog("Update!",  lastLog);

        }
        JDUtilities.getConfiguration().setProperty(Configuration.PARAM_UPDATE_HASH, hash);
        JDUtilities.saveConfig();
    }

    public boolean installerWasVisible() {
        // TODO Auto-generated method stub
        return installerVisible;
    }

    public int getCid() {
        return cid;
    }

}
