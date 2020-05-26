package me.brendonp.tabcorp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

abstract class UseCase<in P, R> {

    var cachedResult: LiveData<Result<R>>? = null
        private set

    operator fun invoke(parameters: P): LiveData<Result<R>> {
        val result = liveData(Dispatchers.IO) {
            try {
                emit(Result.success(execute(parameters)))
            } catch (error: Throwable) {
                emit(Result.failure(error))
            }
        }
        cachedResult = result
        return result
    }

    protected abstract fun execute(parameters: P): R
}

operator fun <R> UseCase<Unit, R>.invoke(): LiveData<Result<R>> = this(Unit)