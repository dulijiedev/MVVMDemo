package com.mrdo.design.mvvmktx.ext.autodispose

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.*
import com.uber.autodispose.AutoDispose.autoDisposable
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider.from
import io.reactivex.*

/**
 * 对lifecycle相关的dispose
 */

fun <T> Observable<T>.bindLifecycle(
    lifecycleOwner: LifecycleOwner,
    lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
): ObservableSubscribeProxy<T> =
    bindLifecycleEvent(lifecycleOwner, lifecycleEvent)

fun <T> Flowable<T>.bindLifecycle(
    lifecycleOwner: LifecycleOwner,
    lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
): FlowableSubscribeProxy<T> =
    bindLifecycleEvent(lifecycleOwner, lifecycleEvent)

fun <T> Single<T>.bindLifecycle(
    lifecycleOwner: LifecycleOwner,
    lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
): SingleSubscribeProxy<T> =
    bindLifecycleEvent(lifecycleOwner, lifecycleEvent)

fun <T> Maybe<T>.bindLifecycle(
    lifecycleOwner: LifecycleOwner,
    lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
): MaybeSubscribeProxy<T> =
    bindLifecycleEvent(lifecycleOwner, lifecycleEvent)

fun Completable.bindLifecycle(
    lifecycleOwner: LifecycleOwner,
    lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
): CompletableSubscribeProxy =
    bindLifecycleEvent(lifecycleOwner, lifecycleEvent)


fun <T> Observable<T>.bindLifecycleEvent(
    lifecycleOwner: LifecycleOwner,
    lifecycleEvent: Lifecycle.Event
): ObservableSubscribeProxy<T> =
    `as`(AutoDispose.autoDisposable(from(lifecycleOwner, lifecycleEvent)))

fun <T> Flowable<T>.bindLifecycleEvent(
    lifecycleOwner: LifecycleOwner,
    lifecycleEvent: Lifecycle.Event
): FlowableSubscribeProxy<T> =
    `as`(AutoDispose.autoDisposable(from(lifecycleOwner, lifecycleEvent)))

fun <T> Single<T>.bindLifecycleEvent(
    lifecycleOwner: LifecycleOwner,
    lifecycleEvent: Lifecycle.Event
): SingleSubscribeProxy<T> =
    `as`(AutoDispose.autoDisposable(from(lifecycleOwner, lifecycleEvent)))

fun <T> Maybe<T>.bindLifecycleEvent(
    lifecycleOwner: LifecycleOwner,
    lifecycleEvent: Lifecycle.Event
): MaybeSubscribeProxy<T> =
    `as`(AutoDispose.autoDisposable(from(lifecycleOwner, lifecycleEvent)))

fun Completable.bindLifecycleEvent(
    lifecycleOwner: LifecycleOwner,
    lifecycleEvent: Lifecycle.Event
): CompletableSubscribeProxy =
    `as`(autoDisposable<Unit>(from(lifecycleOwner, lifecycleEvent)))