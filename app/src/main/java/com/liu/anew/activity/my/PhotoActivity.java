package com.liu.anew.activity.my;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;


import com.liu.anew.R;
import com.liu.anew.adapter.FullyGridLayoutManager;
import com.liu.anew.adapter.GridImageAdapter;
import com.liu.anew.adapter.ImageAdapter;
import com.liu.anew.base.BaseActivity;
import com.liu.anew.base.BaseRecyclerAdapter;
import com.liu.anew.base.SmartViewHolder;
import com.liu.anew.utils.PhotoUtils;
import com.liu.anew.utils.ToastUtils;
import com.liu.anew.view.DialogIosImage;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PhotoActivity extends BaseActivity implements DialogIosImage.OnActionSheetSelected {

    @BindView(R.id.rv_photo)
    RecyclerView rvPhoto;
    @BindView(R.id.rv_show)
    RecyclerView rvShow;
    private BaseRecyclerAdapter<String> mAdapter;
    private int themeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_photo;
    }

    @Override
    public void initData() {
        themeId = R.style.picture_default_style;


        FullyGridLayoutManager manager = new FullyGridLayoutManager(PhotoActivity.this, 4, GridLayoutManager.VERTICAL, false);
        rvShow.setLayoutManager(manager);
        adapter = new GridImageAdapter(PhotoActivity.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(9);
        rvShow.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(PhotoActivity.this).themeStyle(themeId).openExternalPreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(PhotoActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(PhotoActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });

        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(PhotoActivity.this);
                } else {
                    Toast.makeText(PhotoActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });


        rvPhoto.setLayoutManager(new LinearLayoutManager(this));
        rvPhoto.setItemAnimator(new DefaultItemAnimator());
        rvPhoto.setAdapter(mAdapter = new BaseRecyclerAdapter<String>(loadModels(), R.layout.item_type) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, String model, int position) {
                holder.text(R.id.tv_title, model);

            }
        });
    }

    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        PhotoUtils.showImagePickDialog(PhotoActivity.this);
                        break;
                    case 1:
                        iosdialog();

                        break;
                    case 2:
                        danxuan();
                        break;
                    case 3:
                        duoxuan();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void iosdialog() {
        DialogIosImage.showSheet(mContext, this, null);
    }
    private List<LocalMedia> selectList = new ArrayList<>();
    private void duoxuan() {
        PictureSelector.create(PhotoActivity.this)
                .openGallery(PictureMimeType.ofImage())
                .forResult(PictureConfig.CHOOSE_REQUEST);
//        FullyGridLayoutManager manager = new FullyGridLayoutManager(PhotoActivity.this, 4, GridLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(manager);
//        adapter = new GridImageAdapter(PhotoActivity.this, onAddPicClickListener);
//        adapter.setList(selectList);
//        adapter.setSelectMax(maxSelectNum);
//        recyclerView.setAdapter(adapter);
    }

    private List<Uri> list = new ArrayList<>();

    /**
     * 自定义单选
     */
    private void danxuan() {
        // 进入相册 以下是例子：用不到的api可以不写

        PictureSelector.create(PhotoActivity.this)
                .openGallery(PictureConfig.TYPE_IMAGE)//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                .theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
//                .previewVideo()// 是否可预览视频 true or false
//                .enablePreviewAudio() // 是否可播放音频 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
//                .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                .hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
//                .isGif()// 是否显示gif图片 true or false
//                .compressSavePath(getPath())//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
//                .openClickSound()// 是否开启点击声音 true or false
//                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                .cropCompressQuality()// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
//                .videoQuality()// 视频录制质量 0 or 1 int
//                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
//                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
//                .recordVideoSecond()//视频秒数录制 默认60s int
                .isDragFrame(true)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }
    private GridImageAdapter adapter;
    private Collection<String> loadModels() {
        return Arrays.asList(
                "自定义图片选择", "IOS图片选择", "PictureSelector单选", "PictureSelector多选");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PhotoUtils.REQUEST_CODE_FROM_ALBUM:
                if (resultCode == RESULT_CANCELED) {
                    return;
                }
                Uri imageUri = data.getData();
                list.add(imageUri);
                ImageAdapter madapter = new ImageAdapter(mContext, list);
                rvShow.setAdapter(madapter);
                log.e(imageUri);
                break;
            case PhotoUtils.REQUEST_CODE_FROM_CAMERA:
                if (resultCode == RESULT_CANCELED) {
                    PhotoUtils.deleteImageUri(this, PhotoUtils.imageUriFromCamera);
                } else {
                    Uri imageUriCamera = PhotoUtils.imageUriFromCamera;
                    log.e(imageUriCamera);
                    //这里得到图片后做相应操作
                    list.add(imageUriCamera);
                    ImageAdapter adapter1 = new ImageAdapter(mContext, list);
                    rvShow.setAdapter(adapter1);
                }
                break;
                //加载框架
                case PictureConfig.CHOOSE_REQUEST:

                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        log.e("图片-----》"+media.getPath());
                    }
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                break;

            default:
                break;
        }
    }

    @Override
    public void onClick(int whichButton) {
        switch (whichButton) {
            case DialogIosImage.CHOOSE_PICTURE:
                //相册
                ToastUtils.showLong("相册");
                break;
            case DialogIosImage.TAKE_PICTURE:
                //拍照
                ToastUtils.showLong("拍照");
                break;
            case DialogIosImage.CANCEL:
                ToastUtils.showLong("取消");
                //取消
                break;
        }
    }
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            log.e("onAddPicClick");
//            boolean mode = cb_mode.isChecked();
//            if (mode) {
//
//            } else {
//                // 单独拍照
//
//            }
        }
    };
}
