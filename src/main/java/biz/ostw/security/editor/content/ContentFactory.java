package biz.ostw.security.editor.content;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public abstract class ContentFactory {

    /**
     * Method return list of contents or null if not supported.
     *
     * @param object object for contents building.
     * @return contents, never {@code null}.
     */
    public static List<Content<?>> resolve(Object object) throws IOException {
        final List<Content<?>> result = new ArrayList<>();

        for (ContentFactory contentFactory : ServiceLoader.load(ContentFactory.class)) {
            List<Content<?>> c = contentFactory.build(object);

            if (c != null) {
                result.addAll(contentFactory.build(object));
                break;
            }
        }

        return result;
    }

    /**
     * Method return list of contents or null if not supported.
     *
     * @param object object for contents building.
     * @return lits of contents or {@code null} if not supported.
     */
    protected abstract List<Content<?>> build(Object object) throws IOException;
}
