package com.octopus.sample.ui.res

import android.os.Environment
import androidx.lifecycle.*
import com.octopus.sample.base.Results
import com.octopus.sample.entity.Res
import com.qingmei2.architecture.core.base.viewmodel.BaseViewModel
import com.qingmei2.architecture.core.ext.postNext
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.io.*

@SuppressWarnings("checkResult")
class DetailViewModel(
        private val repo: DetailRepository
) : BaseViewModel() {

    private val _stateLiveData: MutableLiveData<ResViewState> = MutableLiveData(ResViewState.initial())

    val stateLiveData: LiveData<ResViewState> = _stateLiveData

    fun getCheckinInfoList(classNum: String) {
        viewModelScope.launch {
            when (val result = repo.getCheckinInfoList(classNum)) {
                is Results.Failure -> _stateLiveData.postNext {
                    it.copy(msg = result.error.toString(), resList = null)
                }
                is Results.Success -> _stateLiveData.postNext {
                    it.copy(msg = result.data.msg, resList = result.data.data)
                }
            }
        }
    }

    fun downloadFile(res: Res) {
        viewModelScope.launch {
            when (val result = repo.downloadFile(res.filepathid.toString())) {
                is Results.Failure -> _stateLiveData.postNext {
                    it.copy(msg = result.error.toString(), resList = null)
                }
                is Results.Success -> {
                    writeFile2Disk(res.filepath.split("192/")[1], result.data)
                }
            }
        }
    }

    //将下载的文件写入本地存储
    private fun writeFile2Disk(fileName: String, response: ResponseBody) {
        var currentLength: Long = 0
        var os: OutputStream? = null
        val inputStream = response.byteStream() //获取下载输入流
        val totalLength = response.contentLength()
        val path = getFilePath(fileName)
        val file = File(path)
        if (file.exists()) {
            file.delete()
        }
        file.parentFile.mkdirs()
        file.createNewFile()
        try {
            os = FileOutputStream(path) //输出流
            var len: Int
            val buff = ByteArray(1024)
            while (inputStream.read(buff).also { len = it } != -1) {
                os.write(buff, 0, len)
                currentLength += len.toLong()
                //计算当前下载百分比，并经由回调传出
                // downloadListener.onProgress((int) (100 * currentLength / totalLength));
                //当百分比为100时下载结束，调用结束回调，并传出下载后的本地路径
//                if ((100 * currentLength / totalLength).toInt() == 100) {
//                    // downloadListener.onFinish(mVideoPath); //下载完成
//                }
                _stateLiveData.postNext {
                    it.copy(msg = "下载完成，文件路径：$path", resList = null)
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (os != null) {
                try {
                    os.close() //关闭输出流
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            try {
                inputStream.close() //关闭输入流
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


}

fun getFilePath(fileName: String): String {
    return Environment.getExternalStorageDirectory().absolutePath + File.separator + "daoyun" + File.separator + fileName
}

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
        private val repo: DetailRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            DetailViewModel(repo) as T
}