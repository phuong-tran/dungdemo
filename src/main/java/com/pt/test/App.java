package com.pt.test;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class App {
    private static volatile boolean running = true;

    private static void terminate() {
        running = false;
    }

    private static void waitForTermination() {

        while (running) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Disposable test() {
        final DataController<SampleData> controller = new DataControllerImpl();
        // Giả lập add data cứ mỗi giây sẽ thêm data mới
        return Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(Schedulers.io())
                .subscribe(timeStamp -> {
                    long currentTimeInSec = TimeUnit.SECONDS.convert(
                            System.currentTimeMillis(),
                            TimeUnit.MILLISECONDS
                    );
                    controller.addData(new SampleData(currentTimeInSec, "A", 20));
                });
    }

    public static void main(String[] args) {
        final Disposable producerDisposable = test();
        waitForTermination();
        producerDisposable.dispose();
    }
}
