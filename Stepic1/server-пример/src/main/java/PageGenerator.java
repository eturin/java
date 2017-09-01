import freemarker.template.Template;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class PageGenerator {
    private static String PATH_TO_TEMPLATES="templatesHTML";
    private static PageGenerator gen;
    private final Configuration cfg;

    public static PageGenerator instance(){
        if(gen==null)
            gen=new PageGenerator();
        return gen;
    }
    private PageGenerator(){
        cfg=new Configuration(new Version(2,3,0));
    }

    public String getPage(String strFile, Map<String,Object> var){
        Writer wr=new StringWriter();
        try{
            Template tmp_l=cfg.getTemplate(PATH_TO_TEMPLATES+ File.separator+strFile);
            tmp_l.process(var,wr);
        }catch(IOException | TemplateException e){
            e.printStackTrace();
        }
        return wr.toString();
    }
}
