package com.adaxiom.downloaders;

import com.adaxiom.model.ModelJobList;
import com.adaxiom.network.BackendConnector;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class GeneralDownloader extends BaseContentDownloader<BackendConnector.GeneralApis> {

    public GeneralDownloader(BackendConnector.GeneralApis beConnector) {
        super(beConnector);
    }

    public Observable<List<ModelJobList>> getJobs(final int user_id) {

        return Observable.create(new Observable.OnSubscribe<List<ModelJobList>>() {
            @Override
            public void call(final Subscriber<? super List<ModelJobList>> subscriber) {
                beConnector.getAllJobs(user_id)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<List<ModelJobList>>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(List<ModelJobList> authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }



}
