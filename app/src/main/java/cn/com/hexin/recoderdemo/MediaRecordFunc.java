package cn.com.hexin.recoderdemo;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

import static com.android.yzw.myapplication.OpenAudioManagerKt.open;

public class MediaRecordFunc {
    private boolean isRecord = false;

    public boolean isRecord() {
        return isRecord;
    }

    private MediaRecorder mMediaRecorder;

    private MediaRecordFunc() {
    }

    private static MediaRecordFunc mInstance;

    public synchronized static MediaRecordFunc getInstance() {
        if (mInstance == null)
            mInstance = new MediaRecordFunc();
        return mInstance;
    }

    public int startRecordAndFile() {
        //判断是否有外部存储设备sdcard 
        if (AudioFileFunc.isSdcardExit()) {
            if (isRecord) {
                return ErrorCode.E_STATE_RECODING;
            } else {
                if (mMediaRecorder == null)
                    createMediaRecord(MediaRecorder.OutputFormat.AAC_ADTS);
//                    createMediaRecord(MediaRecorder.OutputFormat.AMR_NB);
                try {
                    mMediaRecorder.prepare();
                    mMediaRecorder.start();
                    // 让录制状态为true   
                    isRecord = true;
                    return ErrorCode.SUCCESS;
                } catch (IOException ex) {
                    ex.printStackTrace();
                    return ErrorCode.E_UNKOWN;
                }
            }

        } else {
            return ErrorCode.E_NOSDCARD;
        }
    }


    public void stopRecordAndFile() {
        close();
    }

    public long getRecordFileSize() {
        return AudioFileFunc.getFileSize(AudioFileFunc.getAMRFilePath());
    }


    private void createMediaRecord(int format) {
        /* ①Initial：实例化MediaRecorder对象 */
        mMediaRecorder = new MediaRecorder();

        /* setAudioSource/setVedioSource*/
        mMediaRecorder.setAudioSource(AudioFileFunc.AUDIO_INPUT);//设置麦克风

        /* 设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default
         * THREE_GPP(3gp格式，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
         */
        String path = "";
        switch (format) {
//            case MediaRecorder.OutputFormat.AMR_NB:
//                mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
//                /* 设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default */
//                mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//                path = AudioFileFunc.getRawFilePath();
//                break;
            case MediaRecorder.OutputFormat.AMR_NB:
                mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
                /* 设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default */
                mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                path = AudioFileFunc.getAMRFilePath();
                break;
            case MediaRecorder.OutputFormat.AAC_ADTS:
                mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
                /* 设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default */
                mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                path = AudioFileFunc.getWavFilePath();
                break;


        }
        /* 设置输出文件的路径 */
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        mMediaRecorder.setOutputFile(path);
    }

    public void startRecording(int outputfileformat,Context context) {
        open(context);

        mMediaRecorder = new MediaRecorder();

/*        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(outputfileformat);
//		mRecorder.setAudioSamplingRate(44100);
//		mRecorder.setAudioEncodingBitRate(128000);
//		if(extension!=null && extension.equalsIgnoreCase(".amr"))
	        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//		else
//			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setOutputFile(mSampleFile.getAbsolutePath());
*/
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(outputfileformat);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mMediaRecorder.setOutputFile(AudioFileFunc.getAMRFilePath());
        mMediaRecorder.setAudioSamplingRate(44100);
        mMediaRecorder.setAudioEncodingBitRate(128000);


        // Handle IOException
        try {
            mMediaRecorder.prepare();
        } catch(IOException exception) {
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            return;
        }
        // Handle RuntimeException if the recording couldn't start
        try {
            mMediaRecorder.start();
        } catch (RuntimeException exception) {
            AudioManager audioMngr = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
            boolean isInCall = ((audioMngr.getMode() == AudioManager.MODE_IN_CALL) ||
                    (audioMngr.getMode() == AudioManager.MODE_IN_COMMUNICATION));
            if (isInCall) {
            } else {
            }
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            return;
        }
        isRecord = true;
    }

    private void close() {
        if (mMediaRecorder != null) {
            System.out.println("stopRecord");
            isRecord = false;
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }

}