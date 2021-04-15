package zhongmu.org.lib_common.http

import okhttp3.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*
import zhongmu.org.zhongmu_lib.restful.HttpRequest
import zhongmu.org.zhongmu_lib.restful.HttpResponse
import zhongmu.org.zhongmu_lib.restful.callback.HttpCall
import zhongmu.org.zhongmu_lib.restful.callback.HttpCallBack
import zhongmu.org.zhongmu_lib.restful.convert.HttpConvert
import java.lang.IllegalStateException

class RetrofitCallFactory(baseUrl: String) : HttpCall.Factory {
    private var gsonConvert: HttpConvert

    private var apiService: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()
        apiService = retrofit.create(ApiService::class.java)
        gsonConvert = GsonConvert()
    }

    override fun newCall(request: HttpRequest): HttpCall<Any> {
        return RetrofitCall(request)
    }

    internal inner class RetrofitCall<T>(val request: HttpRequest) : HttpCall<T> {
        override fun execute(): HttpResponse<T> {
            val realCall: Call<ResponseBody> = createRealCall(request)
            val response: Response<ResponseBody> = realCall.execute()
            return parseResponse(response)
        }

        override fun enqueue(callBack: HttpCallBack<T>) {
            val realCall = createRealCall(request)
            realCall.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    callBack.onFailed(throwable = t)
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val response = parseResponse(response)
                    callBack.onSuccess(response)
                }

            })

        }

        private fun createRealCall(request: HttpRequest): Call<ResponseBody> {
            when (request.httpMethod) {
                HttpRequest.METHOD.GET -> {
                    return apiService.get(
                        request.headers,
                        request.endPointUrl(),
                        request.parameters
                    )
                }
                HttpRequest.METHOD.POST -> {
                    val requestBody: RequestBody = buildRequestBody(request)
                    return apiService.post(
                        request.headers,
                        request.endPointUrl(),
                        requestBody
                    )
                }
                else -> {
                    throw IllegalStateException("hirestful only support GET POST for now ,url=" + request.endPointUrl())
                }
            }

        }


        private fun buildRequestBody(request: HttpRequest): RequestBody {
            val params: MutableMap<String, String>? = request.parameters
            val builder = FormBody.Builder()
            val requestBody: RequestBody
            val jsonObject = JSONObject()
            if (params != null) {
                for ((key, value) in params) {
                    if (request.formPost) {
                        builder.add(key, value)
                    } else {
                        jsonObject.put(key, value)
                    }
                }
            }
            if (request.formPost) {
                requestBody = builder.build()
            } else {
                requestBody = RequestBody.create(
                    MediaType.parse("application/json;charset=utf-8"),
                    jsonObject.toString()
                )
            }
            return requestBody
        }

        private fun parseResponse(response: Response<ResponseBody>): HttpResponse<T> {
            var rawData: String? = null
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    rawData = body.string()
                }
            } else {
                val body = response.errorBody()
                if (body != null) {
                    rawData = body.string()
                }
            }
            return gsonConvert.convert(rawData!!, request.returnType!!)
        }
    }

    interface ApiService {

        @GET
        fun get(
            @HeaderMap headers: MutableMap<String, String>?, @Url url: String,
            @QueryMap(encoded = true) params: MutableMap<String, String>?
        ): Call<ResponseBody>

        @POST
        fun post(
            @HeaderMap headers: MutableMap<String, String>?, @Url url: String,
            @Body body: RequestBody?
        ): Call<ResponseBody>
    }
}