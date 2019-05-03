package com.adaxiom.downloaders;

import com.adaxiom.model.request.SignUpBody;
import com.adaxiom.model.response.ModelJobList;
import com.adaxiom.model.response.RM_MatchActive;
import com.adaxiom.model.response.RM_SignUp;
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


    public Observable<RM_SignUp> API_SignUp(final SignUpBody signUpBody) {

        return Observable.create(new Observable.OnSubscribe<RM_SignUp>() {
            @Override
            public void call(final Subscriber<? super RM_SignUp> subscriber) {
                beConnector.signUp(signUpBody)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<RM_SignUp>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(RM_SignUp authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }


    public Observable<List<RM_MatchActive>> API_MatchActive() {

        return Observable.create(new Observable.OnSubscribe<List<RM_MatchActive>>() {
            @Override
            public void call(final Subscriber<? super List<RM_MatchActive>> subscriber) {
                beConnector.matchActive()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<List<RM_MatchActive>>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(List<RM_MatchActive> authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }


}
