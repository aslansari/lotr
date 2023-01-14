package com.aslansari.lotr.network.di

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val lotrDispatcher: LotrDispatchers)

enum class LotrDispatchers {
    IO
}
