package com.pt.test;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.ReplaySubject;

import java.util.concurrent.TimeUnit;

public class DataControllerImpl implements DataController<SampleData> {

    private final static long autoDeleteInSec = 60 * 2; // 2 mins

    private final ReplaySubject<SampleData> subject;
    private final Disposable removeDisposable;
    private final Disposable consumerDisposable;


    public DataControllerImpl() {
        subject = ReplaySubject.create();
        removeDisposable = Observable.interval(autoDeleteInSec(), TimeUnit.SECONDS)
                .observeOn(Schedulers.io())
                .subscribe(timeStamp -> {
                    long currentTimeInSec = TimeUnit.SECONDS.convert(
                            System.currentTimeMillis(),
                            TimeUnit.MILLISECONDS
                    );
                    removeOldItems(currentTimeInSec);
                });

        consumerDisposable = subscribeToData();
    }


    @Override
    public void terminate() {
        subject.onComplete();
        removeDisposable.dispose();
        consumerDisposable.dispose();
    }


    @Override
    public long autoDeleteInSec() {
        return autoDeleteInSec;
    }

    /**
     * @param data add Data
     */
    @Override
    public void addData(SampleData data) {
        subject.onNext(data);
    }

    /**
     * @param data Handle data
     */
    @Override
    public void onData(SampleData data) {
        System.out.println("Receive Data " + data);
    }


    private void removeOldItems(long currentTimeInSec) {
        long outOfDatedTimeStamp = currentTimeInSec - autoDeleteInSec;
        subject
                .filter(data -> data.getTimeStamp() >= outOfDatedTimeStamp)
                .toList()
                .subscribe();
    }


    private Disposable subscribeToData() {
        return subject.subscribe(this::onData);
    }
}
