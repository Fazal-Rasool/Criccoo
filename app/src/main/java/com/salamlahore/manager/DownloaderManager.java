package com.salamlahore.manager;

import com.salamlahore.downloaders.GeneralDownloader;
import com.salamlahore.network.BackendConnector;

public class DownloaderManager {
    static DownloaderManager instance;

    GeneralDownloader generalDownloader;

    public DownloaderManager(BackendConnector beConnector) {
        instance = this;
        generalDownloader = new GeneralDownloader(beConnector.getGeneralDownloader());

    }

    public static GeneralDownloader getGeneralDownloader() {
        return instance.generalDownloader;
    }


}
