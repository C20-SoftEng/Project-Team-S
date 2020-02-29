package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class EditingTool implements Disposable {
    private final CompositeDisposable disposables = new CompositeDisposable();

    protected EditingTool() {}

    protected void addSub(Disposable subscription) {
        if (subscription == null) ThrowHelper.illegalNull("subscription");

        disposables.add(subscription);
    }
    protected void addAllSubs(Disposable... subscriptions) {
        this.disposables.addAll(subscriptions);
    }

    @Override public void dispose() {
        disposables.dispose();
    }

    @Override public boolean isDisposed() {
        return disposables.isDisposed();
    }
}
