package rxhttp.wrapper.param

import rxhttp.wrapper.BodyParamFactory
import rxhttp.wrapper.param.AbstractBodyParam

/**
 * Github
 * https://github.com/liujingxing/rxhttp
 * https://github.com/liujingxing/rxlife
 * https://github.com/liujingxing/rxhttp/wiki/FAQ
 * https://github.com/liujingxing/rxhttp/wiki/更新日志
 */
@Suppress("UNCHECKED_CAST")
open class RxHttpAbstractBodyParam<P : AbstractBodyParam<P>, R : RxHttpAbstractBodyParam<P, R>> 
protected constructor(
    param: P
) : RxHttp<P, R>(param), BodyParamFactory {

    fun setUploadMaxLength(maxLength: Long): R {
        param.setUploadMaxLength(maxLength)
        return this as R
    }
}