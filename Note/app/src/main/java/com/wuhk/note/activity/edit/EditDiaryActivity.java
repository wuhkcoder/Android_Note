package com.wuhk.note.activity.edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.jopool.loaction.JLocation;
import com.wuhk.note.R;
import com.wuhk.note.activity.BaseActivity;
import com.wuhk.note.activity.frame.fragment.Fragment1;
import com.wuhk.note.dao.DaoFactory;
import com.wuhk.note.entity.DiaryEntity;
import com.wuhk.note.task.GetCityCodeTask;
import com.wuhk.note.task.GetWeatherTask;
import com.wuhk.note.task.data.CityCodeData;
import com.wuhk.note.task.data.WeatherData;
import com.wuhk.note.utils.LogUtil;
import com.wuhk.note.utils.ToastUtil;
import com.xuan.bigapple.lib.asynctask.callback.AsyncTaskFailCallback;
import com.xuan.bigapple.lib.asynctask.callback.AsyncTaskSuccessCallback;
import com.xuan.bigapple.lib.asynctask.helper.Result;
import com.xuan.bigapple.lib.ioc.InjectView;
import com.xuan.bigapple.lib.utils.DateUtils;
import com.xuan.bigapple.lib.utils.Validators;
import com.xuan.bigapple.lib.utils.uuid.UUIDUtils;
import com.xuan.bigdog.lib.widgets.title.DGTitleLayout;

import java.util.Date;

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

    @InjectView(R.id.contentEt)
    private TextView contentEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_diary);

        initWidgets();
    }

    private void initWidgets(){
        titleLayout.configReturn(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleLayout.configTitle("写日记");
        titleLayout.configRightText("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = contentEt.getText().toString();
                if (Validators.isEmpty(content)){
                    ToastUtil.toast("请写点什么再保存吧");
                }else{
                    DiaryEntity diaryEntity = new DiaryEntity();
                    diaryEntity.setId(UUIDUtils.createId());
                    diaryEntity.setCreateTime(dateTv.getText().toString());
                    diaryEntity.setWeekDay(weekTv.getText().toString());
                    diaryEntity.setContent(content);
                    diaryEntity.setWeather(weatherTv.getText().toString());

                    DaoFactory.getDiaryDao().insertOrReplace(diaryEntity);
                    Fragment1.isReload = true;
                    finish();
                }
            }
        });


        configWeather();
        configDate();
    }

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
                        //TODO:
                        weatherTv.setText(result.getValue().getRetData().getWeather());
                    }
                });

                getCityCodeTask.execute(city);
                JLocation.stop();
            }
        });
        JLocation.start();
    }
}
