package rxhttp.wrapper.param

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.functions.Consumer
import rxhttp.wrapper.BodyParamFactory
import rxhttp.wrapper.entity.Progress
import rxhttp.wrapper.param.AbstractBodyParam
import rxhttp.wrapper.parse.Parser

/**
 * Github
 * https://github.com/liujingxing/rxhttp
 * https://github.com/liujingxing/rxlife
 * https://github.com/liujingxing/rxhttp/wiki/FAQ
 * https://github.com/liujingxing/rxhttp/wiki/更新日志
 */
open class RxHttpAbstractBodyParam<P : AbstractBodyParam<P>> 
protected constructor(
    param: P
) : RxHttp<P>(param), BodyParamFactory {
    //Controls the downstream callback thread
    private var observeOnScheduler: Scheduler? = null

    //Upload progress callback
    private var progressConsumer: Consumer<Progress>? = null
    
    fun setUploadMaxLength(maxLength: Long) = apply {
        param.setUploadMaxLength(maxLength)
    }

    fun upload(progressConsumer: Consumer<Progress>) = upload(null, progressConsumer)

    /**
     * @param progressConsumer   Upload progress callback
     * @param observeOnScheduler Controls the downstream callback thread
     */
    fun upload(observeOnScheduler: Scheduler?, progressConsumer: Consumer<Progress>) = apply {
        this.progressConsumer = progressConsumer
        this.observeOnScheduler = observeOnScheduler
    }

    override fun <T> asParser(parser: Parser<T>): Observable<T> =
        asParser(parser, observeOnScheduler, progressConsumer)

    override fun <T> asParser(
        parser: Parser<T>,
        scheduler: Scheduler?,
        progressConsumer: Consumer<Progress>?
    ): Observable<T> {
        if (progressConsumer == null) {
            return super.asParser(parser, scheduler, null)
        }
        val observableCall: ObservableCall = if (isAsync) {
            ObservableCallEnqueue(this, true)
        } else {
            ObservableCallExecute(this, true)
        }
        return observableCall.asParser(parser, scheduler, progressConsumer)
    }
}
