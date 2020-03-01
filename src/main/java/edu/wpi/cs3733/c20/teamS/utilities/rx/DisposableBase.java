package edu.wpi.cs3733.c20.teamS.utilities.rx;

import io.reactivex.rxjava3.disposables.Disposable;

public abstract class DisposableBase implements Disposable {
    private boolean isDisposed = false;

    public final void dispose() {
        if (isDisposed)
            return;
        onDispose();
        isDisposed = true;
    }

    public final boolean isDisposed() {
        return isDisposed;
    }

    protected abstract void onDispose();
}
