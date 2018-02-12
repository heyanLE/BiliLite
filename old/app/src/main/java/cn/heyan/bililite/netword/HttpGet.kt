package cn.heyan.bililite.netword

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.observable.ObservableCache
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import org.reactivestreams.Subscriber
import java.io.IOException


/**
 * HttpGet
 * Created by HeYan on 2018/2/4 0004.
 */

object HttpGet{

    /**
     * 获取网络数据 并且返回一个List<VideoData>视频列表
     * VideoData为一个封装的视频数据类
     * @param url 获取的URL
     * @param type 解析类型 最新投稿 or 热度排行
     * @return 视频列表
     */
    fun getData(url:String, type:Int):Observable<MutableList<VideoData>> {

        /**
         * 新建被观察者（发送） 并返回
         *
         * 在被观察者内通过OkHttp获取网络数据（异步方法）
         * 同时把结果交给HttpData解析
         *
         * 然后发送结果
         */
        return Observable.create<MutableList<VideoData>> {
            val request = Request.Builder().url(url).build()
            OkHttpClient().newCall(request).enqueue(object :Callback{

                override fun onResponse(call: Call?, response: Response?) {
                    if (response!!.isSuccessful){
                        val res = response.body()!!.string()
                        val rest = HttpData.loadJson(res,type)
                        if (rest != null){
                            it.onNext(rest)
                        }else{
                            it.onError(Throwable())
                        }

                    }
                }
                override fun onFailure(call: Call?, e: IOException) {
                    it.onError(e)
                }
            })


        }


        /**
        object : Thread() {
            override fun run() {

                val client = OkHttpClient()
                val request = Request.Builder()
                        .url(url).build()
                val response = client.newCall(request).execute()
                //response.body()!!.string()
                val msg = Message()
                val st = response.body()!!.string()
                //Log.i("HttpGet","result --> ${st}")
                val result = HttpData.loadJson(st,type)
                msg.obj = result
                handler.sendMessage(msg)

            }
        }.start()**/
    }

}
