package com.arms.mvvm.base;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.arms.mvvm.utils.Resource;
import com.arms.mvvm.utils.Status;


public abstract class NetworkRepository<ResultType> {
//    // 调用该方法将 API 响应的结果保存到数据库中。
//    @WorkerThread
//    protected abstract void saveCallResult(@NonNull ResultType item);
    //查看是否已经有缓存
    private boolean  isCache;


    // 调用该方法判断是否加载缓存内容
    @MainThread
    protected abstract boolean shouldCache();


    // 调用该方法从数据库中获取缓存数据。
    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    // 调用该方法创建 API 请求。
    @NonNull
    @MainThread
    protected abstract LiveData<Resource<ResultType>> createCall();

    // 获取失败时调用。
    @MainThread
    protected void onFetchFailed() {
    }

    // 返回一个代表 Resource 的 LiveData。
    @NonNull
    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return result;
    }

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    private LiveData<ResultType> dbSource;

    protected static String logTag = "network";

    @MainThread
    protected NetworkRepository() {
        reload();
    }

    /**
     * 1.展示加载中
     * 2.加载本地数据
     * 3.判断是否需要加载网络（需要就加载网络数据，不需要就返回本地数据咯）
     * */
    @MainThread
    public void reload() {
        result.setValue(Resource.loading(null));
        result.removeSource(dbSource);
        dbSource = loadFromDb();

        fetchFromNetwork(dbSource);
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        LiveData<Resource<ResultType>> apiResponse = createCall();
        //判断是否加载缓存，如果需要，加载缓存
        if(shouldCache()){
            result.addSource(dbSource,
                    newData -> {
                        if(newData!=null){
                            isCache=true;
                            result.setValue(Resource.success(newData));
                        }else{
                            result.setValue(Resource.loading(null));
                        }
             });
        }

        //请求网络数据
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);
            if (response.status == Status.SUCCESS
                    || response.status == Status.EMPTY) {
                result.addSource(apiResponse, new Observer<Resource<ResultType>>() {
                    @Override
                    public void onChanged(@Nullable Resource<ResultType> requestTypeResource) {
                        result.setValue(requestTypeResource);
                    }
                });
            } else {
                onFetchFailed();
                if(isCache){//如果有缓存
                    Log.d("DaLongNet","网络请求错误，有缓存，使用缓存的");
                }else{
                    Log.d("DaLongNet","网络请求错误，没缓存，使用缓存的");
                    result.addSource(dbSource,
                            newData -> result.setValue(
                                    Resource.error(response.message, newData)));
                }
            }
        });
    }


//    private void fetchFromDb(final LiveData<ResultType> dbSource) {
//        result.addSource(dbSource,
//                newData -> {
//                    if (newData == null ||
//                            (newData instanceof Collection && ((Collection)newData).size() == 0)) {
////                        result.setValue(Resource.empty());
//                    } else {
//
//                        result.setValue(Resource.success(newData));
//                    }
//                });
//    }


}
