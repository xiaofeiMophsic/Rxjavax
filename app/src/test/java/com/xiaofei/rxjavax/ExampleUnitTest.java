package com.xiaofei.rxjavax;

import org.junit.Test;

import rx.Observable;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testDefer(){

        /*
        Observable<Integer> deferObservable = Observable.defer(this::getInt);
        deferObservable.subscribe(System.out::print);
        */

    }

    private Observable<Integer> getInt() {
        return Observable.create(subscriber -> {
            if (subscriber.isUnsubscribed()) {
                return;
            }
            System.out.println("JUST DEBUG");
            subscriber.onNext(100);
            subscriber.onCompleted();
        });
    }
}