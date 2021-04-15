package zhongmu.org.biz_login.api

import zhongmu.org.service_login.UserProfile
import zhongmu.org.zhongmu_lib.restful.annotation.Field
import zhongmu.org.zhongmu_lib.restful.annotation.POST
import zhongmu.org.zhongmu_lib.restful.annotation.GET
import zhongmu.org.zhongmu_lib.restful.callback.HttpCall

interface AccountApi {
    @POST("user/login")
    fun login(
        @Field("userName") userName: String, @Field("password") password: String
    ): HttpCall<String>


    @POST("user/registration")
    fun register(
        @Field("userName") userName: String,
        @Field("password") password: String,
        @Field("imoocId") imoocId:
        String, @Field("orderId") orderId: String
    ): HttpCall<String>

    @GET("user/profile")
    fun profile(): HttpCall<UserProfile>
}