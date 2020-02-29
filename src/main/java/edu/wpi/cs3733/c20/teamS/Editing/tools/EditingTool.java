package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.function.Consumer;

public abstract class EditingTool implements Disposable {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final Consumer<Memento> mementoRunner;

    protected EditingTool(Consumer<Memento> mementoRunner) {
        if (mementoRunner == null) ThrowHelper.illegalNull("mementoExecutor");

        this.mementoRunner = mementoRunner;
    }
    protected final void execute(Memento memento) {
        mementoRunner.accept(memento);
    }
    protected final void addSub(Disposable subscription) {
        if (subscription == null) ThrowHelper.illegalNull("subscription");

        disposables.add(subscription);
    }
    protected final void addAllSubs(Disposable... subscriptions) {
        this.disposables.addAll(subscriptions);
    }

    @Override public final void dispose() {
        if (isDisposed())
            return;
        disposables.dispose();
        onDispose();
    }
    /**
     * Called when the EditingTool is disposed.
     */
    protected void onDispose() {}

    @Override public final boolean isDisposed() {
        return disposables.isDisposed();
    }
}
