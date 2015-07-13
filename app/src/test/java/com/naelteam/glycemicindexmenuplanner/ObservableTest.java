package com.naelteam.glycemicindexmenuplanner;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by fab on 04/07/15.
 */
public class ObservableTest extends TestCase{

    private Subscriber mSubscriber;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("subscribe, completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("subscribe, error");
            }

            @Override
            public void onNext(String s) {
                System.out.println("subscribe, s = " + s);
            }
        };
    }

    public void testFlatMapObs(){
        Observable<String> obs1 = Observable.just("abcd");

        Observable<String> obs2 = obs1.flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                System.out.println("obs2, s = " + s);
                return Observable.just(s + "efgh");
            }
        });

        obs2.subscribe(mSubscriber);
    }

    public void testMapObs(){
        List<String> list = new ArrayList<String>();
        list.add("abcd");
        list.add("1234");
        Observable<String> obs1 = Observable.from(list);

        obs1.map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                System.out.println("map, s = " + s);
                return s + "efgh";
            }
        }).concatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                System.out.println("concatMap, s = " + s);
                return Observable.just(s + "efgh");
            }
        }).subscribe(mSubscriber);
    }
}
