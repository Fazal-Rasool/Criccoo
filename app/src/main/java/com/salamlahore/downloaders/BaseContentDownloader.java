package com.salamlahore.downloaders;


public class BaseContentDownloader<T> {
    protected T beConnector;

    public BaseContentDownloader(T beConnector) {
        this.beConnector = beConnector;
    }

}
