package zhongmu.org.lib_common.http

 import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import zhongmu.org.zhongmu_lib.restful.HttpResponse
import zhongmu.org.zhongmu_lib.restful.convert.HttpConvert
import java.lang.reflect.Type

class GsonConvert : HttpConvert {
    private var gson = Gson()

    override fun <T> convert(rawData: String, dataType: Type): HttpResponse<T> {
        val response: HttpResponse<T> = HttpResponse()
        try {
            val jsonObject = JSONObject(rawData)
            response.code = jsonObject.optInt("code")
            response.msg = jsonObject.optString("msg")
            val data = jsonObject.opt("data")
            if ((data is JSONObject) or (data is JsonArray)) {
                if (response.code == HttpResponse.SUCCESS) {
                    response.data = gson.fromJson(data.toString(), dataType)
                } else {
                    response.errorData = gson.fromJson<MutableMap<String, String>>(
                        data.toString(),
                        object : TypeToken<MutableMap<String, String>>() {}.type
                    )
                }
            } else {
                response.data = data as? T
            }
        } catch (e: Exception) {
            e.printStackTrace()
            response.code = -1
            response.msg = e.message
        }
        response.rawData = rawData
        return response
    }

}