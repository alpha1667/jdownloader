//    jDownloader - Downloadmanager
//    Copyright (C) 2009  JD-Team support@jdownloader.org
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

package jd.plugins.decrypter;

import java.util.ArrayList;

import jd.PluginWrapper;
import jd.controlling.ProgressController;
import jd.plugins.CryptedLink;
import jd.plugins.DecrypterException;
import jd.plugins.DecrypterPlugin;
import jd.plugins.DownloadLink;
import jd.plugins.FilePackage;
import jd.plugins.PluginForDecrypt;
import jd.utils.locale.JDL;

import org.appwork.utils.Regex;

@DecrypterPlugin(revision = "$Revision$", interfaceVersion = 2, names = { "przeklej.pl" }, urls = { "http://[\\w\\.]*?przeklej\\.pl/folder/.*?-\\d+" }, flags = { 0 })
public class PrzeklejPlFolder extends PluginForDecrypt {

    public PrzeklejPlFolder(PluginWrapper wrapper) {
        super(wrapper);
    }

    public ArrayList<DownloadLink> decryptIt(CryptedLink param, ProgressController progress) throws Exception {
        ArrayList<DownloadLink> decryptedLinks = new ArrayList<DownloadLink>();
        String parameter = param.toString();
        br.getPage(parameter);
        if (br.containsHTML("Folder nie istnieje\\!")) throw new DecrypterException(JDL.L("plugins.decrypt.errormsg.unavailable", "Perhaps wrong URL or the download is not available anymore."));
        String[] links = br.getRegex("<input class=\"txt\" type=\"text\" value=\"(.*?)\"").getColumn(0);
        if (links == null || links.length == 0) links = br.getRegex("\"(http://www\\.przeklej\\.pl/plik/.*?)\"").getColumn(0);
        if (links == null || links.length == 0) return null;
        for (String dl : links)
            if (!this.canHandle(dl)) decryptedLinks.add(createDownloadlink(dl));
        FilePackage fp = FilePackage.getInstance();
        fp.setName(new Regex(parameter, "przeklej\\.pl/folder/(.*?)-\\d+").getMatch(0));
        fp.addLinks(decryptedLinks);
        return decryptedLinks;
    }
}
