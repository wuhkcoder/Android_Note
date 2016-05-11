package com.wuhk.note.activity.edit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;
import com.iflytek.thridparty.G;
import com.jopool.jsharelib.JShare;
import com.jopool.jsharelib.config.JShareConfig;
import com.jopool.loaction.JLocation;
import com.wuhk.note.R;
import com.wuhk.note.activity.BaseActivity;
import com.wuhk.note.activity.EncryptActivity;
import com.wuhk.note.activity.frame.fragment.Fragment1;
import com.wuhk.note.adapter.DiaryAdapter;
import com.wuhk.note.callback.DiarySaveCallback;
import com.wuhk.note.common.Constants;
import com.wuhk.note.dao.DaoFactory;
import com.wuhk.note.entity.DiaryEntity;
import com.wuhk.note.entity.enums.EncryptEnum;
import com.wuhk.note.receiver.RefreshNormalDiaryReceiver;
import com.wuhk.note.task.GetCityCodeTask;
import com.wuhk.note.task.GetWeatherTask;
import com.wuhk.note.task.data.CityCodeData;
import com.wuhk.note.task.data.WeatherData;
import com.wuhk.note.utils.FileUtil;
import com.wuhk.note.utils.JsonParser;
import com.wuhk.note.utils.LogUtil;
import com.wuhk.note.utils.ToastUtil;
import com.xuan.bigapple.lib.asynctask.callback.AsyncTaskFailCallback;
import com.xuan.bigapple.lib.asynctask.callback.AsyncTaskSuccessCallback;
import com.xuan.bigapple.lib.asynctask.helper.Result;
import com.xuan.bigapple.lib.bitmap.BPBitmapLoader;
import com.xuan.bigapple.lib.io.FileUtils;
import com.xuan.bigapple.lib.ioc.InjectView;
import com.xuan.bigapple.lib.utils.BitmapUtils;
import com.xuan.bigapple.lib.utils.DateUtils;
import com.xuan.bigapple.lib.utils.Validators;
import com.xuan.bigapple.lib.utils.sharepreference.BPPreferences;
import com.xuan.bigapple.lib.utils.uuid.UUIDUtils;
import com.xuan.bigappleui.lib.album.BUAlbum;
import com.xuan.bigappleui.lib.album.entity.ImageItem;
import com.xuan.bigdog.lib.dialog.DGProgressDialog;
import com.xuan.bigdog.lib.dialog.DGSingleSelectDialog;
import com.xuan.bigdog.lib.widgets.title.DGTitleLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.validation.Validator;

/**写日记
 * Created by wuhk on 2016/5/5.
 */
public class EditDiaryActivity extends BaseActivity {
    @InjectView(R.id.titleLayout)
    private DGTitleLayout titleLayout;

    @InjectView(R.id.weatherTv)
    private TextView weatherTv;

    @InjectView(R.id.dateTv)
    private TextView dateTv;

    @InjectView(R.id.weekTv)
    private TextView weekTv;

    @InjectView(R.id.contentLayout)
    private LinearLayout contentLayout;

    @InjectView(R.id.picIv)
    private ImageView picIv;

    @InjectView(R.id.contentEt)
    private EditText contentEt;

    @InjectView(R.id.speakBtn)
    private FloatingActionButton speakBtn;

    @InjectView(R.id.addPicBtn)
    private FloatingActionButton addPicBtn;

    @InjectView(R.id.shareBtn)
    private FloatingActionButton shareBtn;

    private DiaryEntity diaryEntity;//日记对象

    //界面原有文字
    private String oldStr;

    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    private Handler handler = new Handler();

    private String picFileName;//图片路径

    private boolean isEncrypt;//是否加密

    /**
     * 去相册
     */
    public static final int REQUEST_CODE_ALBUM = 1;
    /**
     * 去拍照
     */
    public static final int REQUEST_CODE_CAMERA = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_diary);

        mIat = SpeechRecognizer.createRecognizer(EditDiaryActivity.this ,null);
        mIatDialog  = new RecognizerDialog(EditDiaryActivity.this , mInitListener);
        // 设置参数
        setParam();
        mIatDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                parseResult(recognizerResult);
            }

            @Override
            public void onError(SpeechError speechError) {
                ToastUtil.toast(speechError.getErrorDescription());
            }
        });

        initWidgets();
        initFab();
    }

    private void initWidgets(){
        titleLayout.configReturn(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleLayout.configTitle("写日记");

        isEncrypt = getIntent().getBooleanExtra("diaryType" , false);
        String jsonStr = getIntent().getStringExtra(DiaryAdapter.DIARY);
        if (Validators.isEmpty(jsonStr)){
            //新建日记的时候自动设置日期天气等参数
            configWeather();
            configDate();
        }else{
            //将原先的日记数据显示到界面上
            diaryEntity = JSON.parseObject( jsonStr , DiaryEntity.class);
            bindData(diaryEntity);
        }

        titleLayout.configRightText("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(new DiarySaveCallback() {
                    @Override
                    public void saveEmpty() {
                        ToastUtil.toast("保存信息不能为空");
                    }

                    @Override
                    public void saveSuccess() {
                        finish();
                    }
                });
            }
        });

    }

    //初始化Fab
    private void initFab(){
        //添加图片
        addPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DGSingleSelectDialog d = new DGSingleSelectDialog.Builder(EditDiaryActivity.this)
                        .setItemTextAndOnClickListener(new String[]{"相册", "拍照"}, new View.OnClickListener[]{new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //从相册中选择
                                BUAlbum.gotoAlbumForSingle(EditDiaryActivity.this, REQUEST_CODE_ALBUM);

                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //拍照
                                gotoCamera();
                            }
                        }}).create();
                d.show();
            }
        });

        //语音
        speakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldStr = contentEt.getText().toString();
                mIatResults.clear();
                // 显示听写对话框
                mIatDialog.show();
                ToastUtil.toast("请开始说话…");
            }
        });

        //分享
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享之前先保存数据
                saveData(new DiarySaveCallback() {
                    @Override
                    public void saveEmpty() {
                        ToastUtil.toast("分享信息不能为空");
                    }

                    @Override
                    public void saveSuccess() {
                        if (picIv.getVisibility() == View.VISIBLE){
                            DGSingleSelectDialog d = new DGSingleSelectDialog.Builder(EditDiaryActivity.this)
                                    .setItemTextAndOnClickListener(new String[]{"分享图片", "分享文字"}, new View.OnClickListener[]{new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            showSharePic();
                                        }
                                    }, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String text = contentEt.getText().toString();
                                            if (Validators.isEmpty(text)){
                                                ToastUtil.toast("分享文字不能为空");
                                            }else{
                                                showShareContent();
                                            }

                                        }
                                    }}).create();
                            d.show();
                        }else{
                            DGSingleSelectDialog d = new DGSingleSelectDialog.Builder(EditDiaryActivity.this)
                                    .setItemTextAndOnClickListener(new String[]{"分享"}, new View.OnClickListener[]{new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String text = contentEt.getText().toString();
                                            if (Validators.isEmpty(text)){
                                                ToastUtil.toast("分享文字不能为空");
                                            }else{
                                                showShareContent();
                                            }

                                        }
                                    }}).create();
                            d.show();
                        }
                    }
                });
            }
        });

    }

    //分享图片
    private void showSharePic(){
        JShareConfig config = new JShareConfig();
        config.setContext(EditDiaryActivity.this);
        config.setTitle("我的分享");
        config.setImagePath(picFileName);
        JShare.getInstance().share(config);
    }

    //分享文字
    private void showShareContent(){
        JShareConfig config = new JShareConfig();
        config.setContext(EditDiaryActivity.this);
        config.setTitle("我的分享");
        config.setText(contentEt.getText().toString());
        JShare.getInstance().share(config);
    }
    //保存数据
    private void saveData(DiarySaveCallback diarySaveCallback){
        String content = contentEt.getText().toString();
        if (Validators.isEmpty(content) && picIv.getVisibility() == View.GONE){
            diarySaveCallback.saveEmpty();
        }else{
            if (null == diaryEntity){
                DiaryEntity diary = new DiaryEntity();
                if (isEncrypt){
                    diary.setEncrypt(EncryptEnum.ENCRYPT.getValue());
                    diary.setPassword(BPPreferences.instance().getString("myPassword" , ""));
                }else{
                    diary.setEncrypt(EncryptEnum.NORMAL.getValue());
                    diary.setPassword("");
                }
                diary.setPic(picFileName);
                diary.setId(UUIDUtils.createId());
                diary.setCreateTime(dateTv.getText().toString());
                diary.setWeekDay(weekTv.getText().toString());
                diary.setContent(content);
                diary.setWeather(weatherTv.getText().toString());
                diary.setModifyTime(new Date().getTime());
                diaryEntity = diary;
                DaoFactory.getDiaryDao().insertOrReplace(diary);
            }else{
                diaryEntity.setPic(picFileName);
                diaryEntity.setContent(content);
                diaryEntity.setModifyTime(new Date().getTime());
                DaoFactory.getDiaryDao().insertOrReplace(diaryEntity);
            }
            RefreshNormalDiaryReceiver.notifyReceiver(isEncrypt);
            diarySaveCallback.saveSuccess();
        }
    }

    //绑定数据
    private void bindData(DiaryEntity data){
        if (Validators.isEmpty(data.getPic())){
            picIv.setVisibility(View.GONE);
        }else{
            picIv.setVisibility(View.VISIBLE);
            picFileName = data.getPic();
            BPBitmapLoader.getInstance().display(picIv, picFileName);
        }
        dateTv.setText(data.getCreateTime());
        weekTv.setText(data.getWeekDay());
        weatherTv.setText(data.getWeather());
        contentEt.setText(data.getContent());
    }

    //设置日期
    private void configDate(){
        dateTv.setText(DateUtils.date2String(new Date(), "yyyy年MM月dd日"));

        int week = DateUtils.getDayOfWeek(new Date());
        switch (week){
            case 1:
                weekTv.setText("星期日");
                break;
            case 2:
                weekTv.setText("星期一");
                break;
            case 3:
                weekTv.setText("星期二");
                break;
            case 4:
                weekTv.setText("星期三");
                break;
            case 5:
                weekTv.setText("星期四");
                break;
            case 6:
                weekTv.setText("星期五");
                break;
            case 7:
                weekTv.setText("星期六");
                break;
        }
    }

    //定位当前城市并获取天气
    private void configWeather(){
        JLocation.init(EditDiaryActivity.this, new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                String city = bdLocation.getCity();
                if (city.substring(city.length() - 1).equals("市")) {
                    city = city.substring(0, city.length() - 1);
                }
                GetCityCodeTask getCityCodeTask = new GetCityCodeTask(EditDiaryActivity.this);
                getCityCodeTask.setShowProgressDialog(false);
                getCityCodeTask.setAsyncTaskFailCallback(new AsyncTaskFailCallback<CityCodeData>() {
                    @Override
                    public void failCallback(Result<CityCodeData> result) {
                        ToastUtil.toast(result.getMessage());
                    }
                });
                getCityCodeTask.setAsyncTaskSuccessCallback(new AsyncTaskSuccessCallback<CityCodeData>() {
                    @Override
                    public void successCallback(Result<CityCodeData> result) {
                        weatherTv.setText(result.getValue().getRetData().getWeather());
                    }
                });

                getCityCodeTask.execute(city);
                JLocation.stop();
            }
        });
        JLocation.start();
    }

    /**
     * 去拍照
     */
    public void gotoCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        FileUtil.checkParentFile(Constants.SDCARD_NOTE_DIARY_TEMP_CAMREA);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Constants.SDCARD_NOTE_DIARY_TEMP_CAMREA)));
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    //解析讯飞语音返回结果
    private void parseResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        String allStr = oldStr + resultBuffer.toString();
//        ToastUtil.toast(allStr);
        contentEt.setText(allStr);
        contentEt.setSelection(allStr.length() -1);
    }

    /**
     * 参数设置
     *
     * @return
     */
    public void setParam() {

        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "1");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
    }
    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                ToastUtil.toast("初始化失败，错误码：" + code);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK != resultCode) {
            return;
        }

        if (REQUEST_CODE_ALBUM == requestCode) {
            //图库选择处理
            List<ImageItem> selectedList = BUAlbum.getSelListAndClear();
            dealImageToEditForAlbum(selectedList);
        } else if (REQUEST_CODE_CAMERA == requestCode) {
            //拍照处理
            dealImageToEditForCamera();
        }
    }


    /**
     * 把拍照的图片放到编辑文件夹下
     */
    private void dealImageToEditForCamera() {
        dealImageToEdit(Constants.SDCARD_NOTE_DIARY_TEMP_CAMREA);
    }

    /**
     * 把选择的图片压缩放倒编辑文件夹下
     */
    private void dealImageToEditForAlbum(final List<ImageItem> selectedList) {
        if (Validators.isEmpty(selectedList)) {
            return;
        }

        final DGProgressDialog d = new DGProgressDialog(this);
        d.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (ImageItem imageItem : selectedList) {
                    dealImageToEdit(imageItem.imagePath);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        d.dismiss();
                    }
                });
            }
        }).start();
    }

    private void dealImageToEdit(String filePathName) {
        int degree = BitmapUtils.getBitmapDegree(filePathName);//旋转角度
        final String editFileName = Constants.SDCARD_NOTE_DIARY_EDIT + UUIDUtils.createId() + ".PNG";
        FileUtil.checkParentFile(editFileName);//编辑时临时存放图片
        final Bitmap b = BitmapUtils.changeOppositeSizeMayDegree(filePathName, editFileName, Constants.IMAGE_LIMIT_SIZE, Constants.IMAGE_LIMIT_SIZE, degree);
        if (null != b) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    picFileName = editFileName;
                    picIv.setVisibility(View.VISIBLE);
                    picIv.setImageBitmap(b);
                }
            });
        }
    }

}
