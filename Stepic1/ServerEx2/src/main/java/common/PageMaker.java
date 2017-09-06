package common;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class PageMaker {
    public static String getPage(String strPath, Map<String, Object> mp){
        Writer wr=new StringWriter();
        try {
            Configuration cfg=new Configuration(new Version(2,3,0));
            Template tmp_l=cfg.getTemplate(Main.PATH_TO_TEMPLATES+ File.separator+strPath);
            tmp_l.process(mp, wr);
        }catch(IOException | TemplateException e) {
            e.printStackTrace();
        }

        return wr.toString();
    }
}
