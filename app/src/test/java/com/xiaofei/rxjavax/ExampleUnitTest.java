package com.xiaofei.rxjavax;

import com.xiaofei.rxjavax.data.Network;
import com.xiaofei.rxjavax.data.api.ZhuangbiApi;
import com.xiaofei.rxjavax.model.ZhuangbiImage;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        Observable.from(makeStrings())
                .map(String::toLowerCase)
                .subscribe(System.out::println);
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

    private List<String> makeStrings(){
        List<String> strings = new ArrayList<>();
        strings.add("Allo");
        strings.add("Bob");
        strings.add("Career");
        strings.add("dog");
        strings.add("Elva");
        return Collections.unmodifiableList(strings);
    }

    @Test
    public void testZhuangbiApi(){
        ZhuangbiApi api = Network.getInstance().getZhuangbiAPI();
        Observable<List<ZhuangbiImage>> observable = api.getZhuangbiImage("装逼");
        observable.take(10)
                .subscribe(System.out::println);
    }
}