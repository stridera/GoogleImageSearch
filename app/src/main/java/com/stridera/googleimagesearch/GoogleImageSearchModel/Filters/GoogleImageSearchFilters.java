package com.stridera.googleimagesearch.GoogleImageSearchModel.Filters;

import java.io.Serializable;

public class GoogleImageSearchFilters implements Serializable {
    // Constants
    private static final String SIZE_QUERY = "imgSize";
    private static final String COLOR_QUERY = "imgDominantColor";
    private static final String COLOR_TYPE_QUERY = "imgColorType";
    private static final String TYPE_QUERY = "imgType";
    private static final String RIGHTS_QUERY = "rights";
    private static final String SAFE_QUERY = "safe";
    private static final String SITE_QUERY = "siteSearch";

    // Public Options
    public enum ImageSizeFilter { Huge, Icon, Large, Medium, Small, XLarge, XXLarge, None }
    public enum ImageColorFilter {Black, Blue, Brown, Gray, Green, Pink, Purple, Teal, White, Yellow, None}
    public enum ImageColorTypeFilter {Color, Gray, Mono, None}
    public enum ImageTypeFilter {Clipart, Face, Lineart, News, Photo, None}
    public enum ImageSafeFilter {Strict, Medium, Off}

    // Private Variables
    private ImageSizeFilter imageSizeFilter;
    private ImageColorFilter imageColorFilter;
    private ImageColorTypeFilter imageColorTypeFilter;
    private ImageTypeFilter imageTypeFilter;
    private ImageSafeFilter imageSafeFilter;
    private String imageSiteFilter;

    // Constructor
    public GoogleImageSearchFilters() {
        this.imageSizeFilter = ImageSizeFilter.None;
        this.imageColorFilter = ImageColorFilter.None;
        this.imageColorTypeFilter = ImageColorTypeFilter.None;
        this.imageTypeFilter = ImageTypeFilter.None;
        this.imageSafeFilter = ImageSafeFilter.Medium;
        this.imageSiteFilter = "";
    }

    // Setters
    public void setImageSizeFilter(ImageSizeFilter imageSizeFilter) {
        this.imageSizeFilter = imageSizeFilter;
    }

    public void setImageColorFilter(ImageColorFilter imageColorFilter) {
        this.imageColorFilter = imageColorFilter;
    }

    public void setImageColorTypeFilter(ImageColorTypeFilter imageColorTypeFilter) {
        this.imageColorTypeFilter = imageColorTypeFilter;
    }

    public void setImageTypeFilter(ImageTypeFilter imageTypeFilter) {
        this.imageTypeFilter = imageTypeFilter;
    }

    public void setImageSafeFilter(ImageSafeFilter imageSafeFilter) {
        this.imageSafeFilter = imageSafeFilter;
    }

    public void setImageSiteFilter(String imageSiteFilter) {
        this.imageSiteFilter = imageSiteFilter;
    }

    // Getters
    public ImageSizeFilter getImageSizeFilter() {
        return imageSizeFilter;
    }

    public ImageColorFilter getImageColorFilter() {
        return imageColorFilter;
    }

    public ImageColorTypeFilter getImageColorTypeFilter() {
        return imageColorTypeFilter;
    }

    public ImageTypeFilter getImageTypeFilter() {
        return imageTypeFilter;
    }

    public ImageSafeFilter getImageSafeFilter() {
        return imageSafeFilter;
    }

    public String getImageSiteFilter() {
        return imageSiteFilter;
    }

    private String getImageSizeParams() {
        String query = "&" + SIZE_QUERY + "=";
        switch (imageSizeFilter) {
            case Huge: query += "huge"; break;
            case Icon: query += "icon"; break;
            case Large: query += "large"; break;
            case Medium: query += "medium"; break;
            case Small: query += "small"; break;
            case XLarge: query += "xlarge"; break;
            case XXLarge: query += "xxlarge"; break;
            default: query = "";
        }
        return query;
    }

    private String getImageColorParams() {
        String query = "&" + COLOR_QUERY + "=";
        switch (imageColorFilter) {
            case Black: query += "black"; break;
            case Blue: query += "blue"; break;
            case Brown: query += "brown"; break;
            case Gray: query += "gray"; break;
            case Green: query += "green"; break;
            case Pink: query += "pink"; break;
            case Purple: query += "purple"; break;
            case Teal: query += "teal"; break;
            case White: query += "white"; break;
            case Yellow: query += "yellow"; break;
            default: query = "";
        }
        return query;
    }

    private String getImageColorTypeParams() {
        String query = "&" + COLOR_TYPE_QUERY + "=";
        switch (imageColorTypeFilter) {
            case Color: query += "color"; break;
            case Gray: query += "gray"; break;
            case Mono: query += "mono"; break;
            default: query = "";
        }
        return query;
    }

    private String getImageTypeParams() {
        String query = "&" + TYPE_QUERY + "=";
        switch (imageTypeFilter) {
            case Clipart: query += "clipart"; break;
            case Face: query += "face"; break;
            case Lineart: query += "lineart"; break;
            case News: query += "news"; break;
            case Photo: query += "photo"; break;
            default: query = "";
        }
        return query;

    }

    private String getImageSafeParams() {
        String query = "&" + SAFE_QUERY + "=";
        switch (imageSafeFilter) {
            case Strict: query += "high"; break;
            case Medium: query += "medium"; break;
            case Off: query += "off"; break;
        }
        return query;

    }

    private String getImageSiteParams() {
        if (!imageSiteFilter.equals("")) {
            // TODO: Google Custom Search really is horrible.  Why, oh why, did I not listen?  Need to go back to the depricated api to get site search functionality.  Trying to fudge it by adding the site to the query.
            //return "&" + SITE_QUERY + "=" + imageSiteFilter;
            return " " + imageSiteFilter;
        } else {
            return "";
        }
    }

    // Public Members
    public String getFilterParams() {
        return
                getImageSiteParams() +
                getImageSizeParams() +
                getImageColorParams() +
                getImageColorTypeParams() +
                getImageSafeParams() +
                getImageTypeParams();
    }

}
