package com.naelteam.glycemicindexmenuplanner.network;

import android.os.CountDownTimer;

import com.naelteam.glycemicindexmenuplanner.BaseTest;
import com.naelteam.glycemicindexmenuplanner.BuildConfig;
import com.naelteam.glycemicindexmenuplanner.FileUtils;
import com.naelteam.glycemicindexmenuplanner.MainActivity;
import com.naelteam.glycemicindexmenuplanner.provider.wik.WikProvider;
import com.naelteam.glycemicindexmenuplanner.provider.wik.parser.WikSearchGIByNameParser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

/**
 */
public class WikSearchGIByNameServiceTest extends BaseTest {

    private WikSearchGIByNameService sut;

    public WikSearchGIByNameServiceTest() {
    }

    @Before
    public void setup()  {
        sut = new WikSearchGIByNameService(WikProvider.getBaseUrl(Locale.FRANCE));
    }

    @Test
    public void testServiceWithExistingSearch() {
        System.out.println("------ testServiceWithExistingSearch");
        processServiceOnSuccess("almond");
    }

    @Test
    public void testServiceWithNoResultSearch() {
        System.out.println("------ testServiceWithNoResultSearch");
        processServiceOnSuccess("almond2");
    }

    @Test
    public void testServiceWithBadUrl() {
        sut = new WikSearchGIByNameService("dummyURL");

        System.out.println("------ testServiceWithBadUrl");

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        Observable<String> obs = sut.requestAndParseObs("almond");
        obs.subscribeOn(Schedulers.io());
        obs.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
                Assert.assertTrue(true);
                countDownLatch.countDown();
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError e=" + e);
                Assert.assertTrue(true);
                countDownLatch.countDown();
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext s=" + s);
                Assert.assertTrue(false);
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void processServiceOnSuccess(String searchStr){

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        Observable<String> obs = sut.requestAndParseObs(searchStr);
        obs.subscribeOn(Schedulers.io());
        obs.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
                Assert.assertTrue(true);
                countDownLatch.countDown();
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError e=" + e);
                Assert.assertTrue(false);
                countDownLatch.countDown();
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext s=" + s);
                Assert.assertTrue(true);
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}