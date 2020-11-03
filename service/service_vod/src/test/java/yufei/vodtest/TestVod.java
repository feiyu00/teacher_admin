package yufei.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVod {
    public static void main(String[] args) throws ClientException {
        String accessKeyId = "LTAI4G7w2Fgu1dzY6Miuakq8";
        String accessKeySecret = "sGz1S1F5ZRlSlVpmSYcEu7GZbvtvkp";
        String fileName = "G:/20201011_145003.mp4";
        String title = "3 - How Does Project Submission Work - upload by sdk";

        //本地文件上传

        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);

        /* 可指定分片上传时每个分片的大小，默认为1M字节 */

        request.setPartSize(2 * 1024 * 1024L);

        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/

        request.setTaskNum(2);

    /* 是否开启断点续传, 默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。

        注意: 断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速度，请您根据实际情况选择是否开启*/

        request.setEnableCheckpoint(false);


        UploadVideoImpl uploader = new UploadVideoImpl();

        UploadVideoResponse response = uploader.uploadVideo(request);

        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID

        if (response.isSuccess()) {

            System.out.print("VideoId=" + response.getVideoId() + "\n");

        } else {

            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */

            System.out.print("VideoId=" + response.getVideoId() + "\n");

            System.out.print("ErrorCode=" + response.getCode() + "\n");

            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }

    }

    private static void getPlayAuth() throws ClientException {
        //创建初始化对象
        DefaultAcsClient client = Test.initVodClient("LTAI4G7w2Fgu1dzY6Miuakq8", "sGz1S1F5ZRlSlVpmSYcEu7GZbvtvkp");
        //创建获取视频凭证视request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        //像request对象里面设置视频id
        request.setVideoId("4131ed6e942f4015994b24a90c9a92a7");
        //调用初始化对象里面的方法,传递request获取数据
        response  = client.getAcsResponse(request);

        String playAuth = response.getPlayAuth();
        System.out.println(playAuth);
    }

    public static void  getPlayAddressById() throws ClientException {
        //创建初始化对象
        DefaultAcsClient client = Test.initVodClient("LTAI4G7w2Fgu1dzY6Miuakq8", "sGz1S1F5ZRlSlVpmSYcEu7GZbvtvkp");
        //创建视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        //像request对象里面设置视频id
        request.setVideoId("94620e8a77034afaa21403c73a29ae53");
        //调用初始化对象里面的方法,传递request获取数据
        response  = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
