package com.alibaba.webmvc.velocity;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zizhi.zhzzh
 *         Date: 7/23/14
 *         Time: 8:09 PM
 */
public class PageTool {

    /** Script references */
    private final List<String> scripts = new ArrayList<String>();

    /** Stylesheet references */
    private final List<StyleSheet> styleSheets = new ArrayList<StyleSheet>();

    private String title;


    public String getTitle() {
        return title;
    }

    public PageTool setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<String> getScripts() {
        return scripts;
    }

    public List<StyleSheet> getStyleSheets() {
        return styleSheets;
    }

    public PageTool addStyleSheet(String styleSheetURL) {
        addStyleSheet(styleSheetURL, "screen", null, "text/css");
        return this;
    }


    public PageTool addScript(String scriptURL) {
        this.scripts.add(scriptURL);
        return this;
    }


    public PageTool addStyleSheet(String styleSheetURL, String media, String title, String type) {
        StyleSheet ss = new StyleSheet(styleSheetURL);

        ss.setMedia(media);
        ss.setTitle(title);
        ss.setType(type);
        this.styleSheets.add(ss);
        return this;
    }

    @Override
    public String toString() {
        return "";
    }

    public class StyleSheet {
        private String url;
        private String title;
        private String media;
        private String type;

        /**
         * Constructor requiring the URL to be set
         *
         * @param url URL of the external style sheet
         */
        public StyleSheet(String url) {
            setUrl(url);
        }

        /**
         * Gets the content type of the style sheet
         *
         * @return content type
         */
        public String getType() {
            return StringUtils.isEmpty(type) ? "" : type;
        }

        /**
         * Sets the content type of the style sheet
         *
         * @param type content type
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * @return String representation of the URL
         */
        public String getUrl() {
            return url;
        }

        /**
         * Sets the URL of the external style sheet
         *
         * @param url The URL of the stylesheet
         */
        private void setUrl(String url) {
            this.url = url;
        }

        /**
         * Gets the title of the style sheet
         *
         * @return title
         */
        public String getTitle() {
            return StringUtils.isEmpty(title) ? "" : title;
        }

        /**
         * Sets the title of the stylesheet
         *
         * @param title
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * Gets the media for which the stylesheet should be applied.
         *
         * @return name of the media
         */
        public String getMedia() {
            return StringUtils.isEmpty(media) ? "" : media;
        }

        /**
         * Sets the media for which the stylesheet should be applied.
         *
         * @param media name of the media
         */
        public void setMedia(String media) {
            this.media = media;
        }
    }

}
