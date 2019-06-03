package com.adaxiom.downloaders;

import android.widget.Toast;

import com.adaxiom.criccoo.Signup;
import com.adaxiom.model.response.IsVoted;
import com.adaxiom.model.response.RM_BlockList;
import com.adaxiom.model.response.RM_CityList;
import com.adaxiom.model.response.RM_Commentry;
import com.adaxiom.model.response.RM_GetAllVote;
import com.adaxiom.model.response.RM_GetEarning;
import com.adaxiom.model.response.RM_GetYourCash;
import com.adaxiom.model.response.RM_LeaderBoard;
import com.adaxiom.model.response.RM_Login;
import com.adaxiom.model.response.RM_MatchActive;
import com.adaxiom.model.response.RM_MatchPrediction;
import com.adaxiom.model.response.RM_SignUp;
import com.adaxiom.model.response.RM_SignUpOther;
import com.adaxiom.model.response.RM_UserResult;
import com.adaxiom.model.response.RM_WinnerPrediction;
import com.adaxiom.model.response.RM_WinnerPredictionNew;
import com.adaxiom.network.BackendConnector;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class GeneralDownloader extends BaseContentDownloader<BackendConnector.GeneralApis> {

    public GeneralDownloader(BackendConnector.GeneralApis beConnector) {
        super(beConnector);
    }



    public Observable<RM_SignUp> API_SignUp(final String name,
                                            final String uName,
                                            final String email,
                                            final String pswrd,
                                            final String fcmToken,
                                            final String city,
                                            final String from
    ) {

        return Observable.create(new Observable.OnSubscribe<RM_SignUp>() {
            @Override
            public void call(final Subscriber<? super RM_SignUp> subscriber) {
                beConnector.signUp(name,uName,email,pswrd,fcmToken,city,from)
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




    public Observable<RM_SignUpOther> API_SignUpOther(final String name,
                                                      final String uName,
                                                      final String pswrd,
                                                      final String fcmToken,
                                                      final String city,
                                                      final String from
    ) {

        return Observable.create(new Observable.OnSubscribe<RM_SignUpOther>() {
            @Override
            public void call(final Subscriber<? super RM_SignUpOther> subscriber) {
                beConnector.signUpOther(name,uName,pswrd,fcmToken,city,from)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<RM_SignUpOther>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(RM_SignUpOther authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }





//    public Observable<RM_SignUp> API_SignUp(final SignUpBody signUpBody) {
//
//        return Observable.create(new Observable.OnSubscribe<RM_SignUp>() {
//            @Override
//            public void call(final Subscriber<? super RM_SignUp> subscriber) {
//                beConnector.signUp(signUpBody)
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(Schedulers.newThread())
//                        .subscribe(new Subscriber<RM_SignUp>() {
//                            @Override
//                            public void onCompleted() {
//                                subscriber.onCompleted();
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                subscriber.onError(e);
//                            }
//
//                            @Override
//                            public void onNext(RM_SignUp authResponse) {
//                                subscriber.onNext(authResponse);
//                            }
//                        });
//            }
//        });
//    }


//    public Observable<RM_Login> API_Login(final LoginBody loginBody) {
//
//        return Observable.create(new Observable.OnSubscribe<RM_Login>() {
//            @Override
//            public void call(final Subscriber<? super RM_Login> subscriber) {
//                beConnector.Login(loginBody)
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(Schedulers.newThread())
//                        .subscribe(new Subscriber<RM_Login>() {
//                            @Override
//                            public void onCompleted() {
//                                subscriber.onCompleted();
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                subscriber.onError(e);
//                            }
//
//                            @Override
//                            public void onNext(RM_Login authResponse) {
//                                subscriber.onNext(authResponse);
//                            }
//                        });
//            }
//        });
//    }



    public Observable<RM_Login> API_LoginParam(final String user, final String pass, final String from, final String fcm) {

        return Observable.create(new Observable.OnSubscribe<RM_Login>() {
            @Override
            public void call(final Subscriber<? super RM_Login> subscriber) {
                beConnector.LoginParam(user, pass, from, fcm)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<RM_Login>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(RM_Login authResponse) {
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



    public Observable<List<RM_BlockList>> API_BlockList() {

        return Observable.create(new Observable.OnSubscribe<List<RM_BlockList>>() {
            @Override
            public void call(final Subscriber<? super List<RM_BlockList>> subscriber) {
                beConnector.BlockList()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<List<RM_BlockList>>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(List<RM_BlockList> authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }


    public Observable<List<RM_CityList>> API_CityList() {

        return Observable.create(new Observable.OnSubscribe<List<RM_CityList>>() {
            @Override
            public void call(final Subscriber<? super List<RM_CityList>> subscriber) {
                beConnector.CityList()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<List<RM_CityList>>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(List<RM_CityList> authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }


    public Observable<RM_MatchPrediction> API_PostMatchPredictions(final int userId,
                                                                   final String matchId,
                                                                   final int blockId,
                                                                   final String innings,
                                                                   final int matchOver,
                                                                   final String ball_1,
                                                                   final String ball_2,
                                                                   final String ball_3,
                                                                   final String ball_4,
                                                                   final String ball_5,
                                                                   final String ball_6) {

        return Observable.create(new Observable.OnSubscribe<RM_MatchPrediction>() {
            @Override
            public void call(final Subscriber<? super RM_MatchPrediction> subscriber) {
                beConnector.PostMatchPrediction(userId, matchId,blockId, innings,matchOver,ball_1,ball_2,ball_3,ball_4,ball_5,ball_6)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<RM_MatchPrediction>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(RM_MatchPrediction authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }



    public Observable<RM_LeaderBoard> API_LeaderBoard(final int user_id, final String matchid) {

        return Observable.create(new Observable.OnSubscribe<RM_LeaderBoard>() {
            @Override
            public void call(final Subscriber<? super RM_LeaderBoard> subscriber) {
                beConnector.LeaderBoard(user_id, matchid)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<RM_LeaderBoard>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(RM_LeaderBoard authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }



    public Observable<RM_Commentry> API_Commentary(final String match_id) {

        return Observable.create(new Observable.OnSubscribe<RM_Commentry>() {
            @Override
            public void call(final Subscriber<? super RM_Commentry> subscriber) {
                beConnector.Commentary(match_id)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<RM_Commentry>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(RM_Commentry authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }



    public Observable<RM_WinnerPredictionNew> API_WinnerPrediction(final int userid, final String matchid, final String prediction) {

        return Observable.create(new Observable.OnSubscribe<RM_WinnerPredictionNew>() {
            @Override
            public void call(final Subscriber<? super RM_WinnerPredictionNew> subscriber) {
                beConnector.WinnerPrediction(userid,matchid,prediction)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<RM_WinnerPredictionNew>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(RM_WinnerPredictionNew authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }




    public Observable<List<RM_UserResult>> API_UserResult(final int userid) {

        return Observable.create(new Observable.OnSubscribe<List<RM_UserResult>>() {
            @Override
            public void call(final Subscriber<? super List<RM_UserResult>> subscriber) {
                beConnector.UserResult(userid)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<List<RM_UserResult>>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(List<RM_UserResult> authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }



    public Observable<IsVoted> API_IsVoted(final int userid, final String matchid) {

        return Observable.create(new Observable.OnSubscribe<IsVoted>() {
            @Override
            public void call(final Subscriber<? super IsVoted> subscriber) {
                beConnector.IsUserVoted(userid,matchid)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<IsVoted>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(IsVoted authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }



    public Observable<List<RM_GetAllVote>> API_GetAllVote(final String matchid) {

        return Observable.create(new Observable.OnSubscribe<List<RM_GetAllVote>>() {
            @Override
            public void call(final Subscriber<? super List<RM_GetAllVote>> subscriber) {
                beConnector.GetAllVote(matchid)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<List<RM_GetAllVote>>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(List<RM_GetAllVote> authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }



    public Observable<RM_GetYourCash> API_GetYourCash(final int userid, final String phone, final String cnic) {

        return Observable.create(new Observable.OnSubscribe<RM_GetYourCash>() {
            @Override
            public void call(final Subscriber<? super RM_GetYourCash> subscriber) {
                beConnector.GetYourCash(userid,phone, cnic)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<RM_GetYourCash>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(RM_GetYourCash authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }




    public Observable<RM_GetEarning> API_GetEarning(final int user_id) {

        return Observable.create(new Observable.OnSubscribe<RM_GetEarning>() {
            @Override
            public void call(final Subscriber<? super RM_GetEarning> subscriber) {
                beConnector.GetEarning(user_id)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<RM_GetEarning>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(RM_GetEarning authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }





}
