package com.simon.credit.toolkit.ext.format;

import com.simon.credit.exception.MultipleInstanceException;
import com.simon.credit.toolkit.ext.image.ImageProcesser;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import org.apache.commons.io.IOUtils;

import java.io.ObjectStreamException;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * FreeMarker格式化
 * @author XUZIMING 2020-08-15
 */
public class FreeMarkerFormatter {

    private Configuration config = new Configuration(Configuration.getVersion());
    private StringTemplateLoader loader = new StringTemplateLoader();
    private AtomicLong templateIndex = new AtomicLong(0);

    private static final class SingletonHolder {
        private static final FreeMarkerFormatter INSTANCE = new FreeMarkerFormatter();
    }

    public FreeMarkerFormatter() {
        // 避免采用反射方式直接调用私有构造器, 从而破解单例模式
        if (FreeMarkerFormatter.SingletonHolder.INSTANCE != null) {
            throw new MultipleInstanceException(ImageProcesser.class);
        }
    }

    public static FreeMarkerFormatter getInstance() {
        return FreeMarkerFormatter.SingletonHolder.INSTANCE;
    }

    private Object readResolve() throws ObjectStreamException {
        // 避免反序列化破解单例模式: 发序列化时, 若定义了readResolve(),
        // 则直接返回此方法指定的对象, 而无需单独再创建新对象.
        return FreeMarkerFormatter.SingletonHolder.INSTANCE;
    }

    public String format(String templateContent, Map<String, Object> dataModel) {
        String templateName  = Long.toString(templateIndex.incrementAndGet());
        loader.putTemplate(templateName, templateContent);
        config.setTemplateLoader(loader);

        StringWriter out = new StringWriter();
        try {
            config.getTemplate(templateName).process(dataModel, out);
            return out.toString();
        } catch (Exception e) {
            throw new RuntimeException("FreeMarker format error", e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

}