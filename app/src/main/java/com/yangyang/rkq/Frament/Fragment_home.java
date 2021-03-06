package com.yangyang.rkq.Frament;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.superluo.textbannerlibrary.ITextBannerItemClickListener;
import com.superluo.textbannerlibrary.TextBannerView;
import com.yangyang.rkq.R;
import com.yangyang.rkq.Utils.CommonUtil;
import com.yangyang.rkq.View.AnimationNestedScrollView;
import com.yangyang.rkq.View.PublishDialog;
import com.yangyang.rkq.activity.ResultInquiryAty;
import com.yangyang.rkq.activity.ScanActivity;
import com.yangyang.rkq.activity.SearchActivity;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment_home extends Fragment implements View.OnClickListener {

    private TextBannerView tvBanner;
    private View mView;
    private GridView gridView, gridViewFound;
    private Banner banner;
    private AnimationNestedScrollView sv_view;
    private LinearLayout ll_search;
    private ImageView iv_release;
    private ImageView iv_school;
    private TextView tv_title_school;
    private ImageView scan;
    private LinearLayout search_ll_search;
    private PublishDialog publishDialog;
    private ViewGroup.MarginLayoutParams searchLayoutParams, titleLayoutParams;
    private static final int BAIDU_READ_PHONE_STATE = 201;//???????????????
    private static final int PRIVATE_CODE = 1315;//??????GPS??????
    private static final int SCAN_REQUEST_CODE = 200;//?????????
    private static final int SCAN_CAMERA_CODE = 202;
    private float LL_SEARCH_MIN_TOP_MARGIN, LL_SEARCH_MAX_TOP_MARGIN, LL_SEARCH_MAX_WIDTH, LL_SEARCH_MIN_WIDTH, TV_TITLE_MAX_TOP_MARGIN;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        initListener();
        initTopTitle();
        initBanner();
        initGird();
        initTextBanner();
        return mView;
    }

    //???????????????
    private void initView() {
        search_ll_search = (LinearLayout) mView.findViewById(R.id.search_ll_search);
        scan = (ImageView) mView.findViewById(R.id.scan);//??????
        iv_release = (ImageView) mView.findViewById(R.id.iv_release);//??????
        tvBanner = (TextBannerView) mView.findViewById(R.id.tv_banner);//????????????
        gridView = (GridView) mView.findViewById(R.id.gridView);//??????
        sv_view = (AnimationNestedScrollView) mView.findViewById(R.id.search_sv_view);
        ll_search = (LinearLayout) mView.findViewById(R.id.search_ll_search);//??????
        tv_title_school = (TextView) mView.findViewById(R.id.tv_title_school);//????????????
        iv_school = mView.findViewById(R.id.iv_school);//????????????
        banner = (Banner) mView.findViewById(R.id.banner);
    }

    private void initListener() {
        iv_release.setOnClickListener(this);
        scan.setOnClickListener(this);
        tv_title_school.setOnClickListener(this);
        search_ll_search.setOnClickListener(this);
    }


    //???????????????
    private void initTopTitle() {
        searchLayoutParams = (ViewGroup.MarginLayoutParams) ll_search.getLayoutParams();
        titleLayoutParams = (ViewGroup.MarginLayoutParams) tv_title_school.getLayoutParams();
        LL_SEARCH_MIN_TOP_MARGIN = CommonUtil.dp2px(this, 10f);//???????????????????????????
        LL_SEARCH_MAX_TOP_MARGIN = CommonUtil.dp2px(this, 50f);//?????????????????????????????????
        LL_SEARCH_MAX_WIDTH = CommonUtil.getScreenWidth(this) - CommonUtil.dp2px(this, 30f);//??????????????????????????????
        LL_SEARCH_MIN_WIDTH = CommonUtil.getScreenWidth(this) - CommonUtil.dp2px(this, 65f);//????????????????????????
        TV_TITLE_MAX_TOP_MARGIN = CommonUtil.dp2px(this, 11.5f);

        sv_view.setOnAnimationScrollListener(new AnimationNestedScrollView.OnAnimationScrollChangeListener() {
            @Override
            public void onScrollChanged(float dy) {
                float searchLayoutNewTopMargin = LL_SEARCH_MAX_TOP_MARGIN - dy;
                float searchLayoutNewWidth = LL_SEARCH_MAX_WIDTH - dy * 3.0f;//?????? * 1.3f ??????????????????????????????????????????

                float titleNewTopMargin = (float) (TV_TITLE_MAX_TOP_MARGIN - dy * 0.5);

                //???????????????????????????
                searchLayoutNewWidth = searchLayoutNewWidth < LL_SEARCH_MIN_WIDTH ? LL_SEARCH_MIN_WIDTH : searchLayoutNewWidth;

                if (searchLayoutNewTopMargin < LL_SEARCH_MIN_TOP_MARGIN) {
                    searchLayoutNewTopMargin = LL_SEARCH_MIN_TOP_MARGIN;

                }

                if (searchLayoutNewWidth < LL_SEARCH_MIN_WIDTH) {
                    searchLayoutNewWidth = LL_SEARCH_MIN_WIDTH;
                }

                float titleAlpha = 255 * titleNewTopMargin / TV_TITLE_MAX_TOP_MARGIN;
                if (titleAlpha < 0) {
                    titleAlpha = 0;
                }

                //?????????????????????LayoutParams  ??????????????????MarginLayoutParams???????????????params???topMargin??????
                searchLayoutParams.topMargin = (int) searchLayoutNewTopMargin;
                searchLayoutParams.width = (int) searchLayoutNewWidth;
                ll_search.setLayoutParams(searchLayoutParams);
            }
        });
    }

    //?????????
    private void initBanner() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.image8);
        images.add(R.drawable.image9);
        images.add(R.drawable.image10);
        images.add(R.drawable.image11);
        images.add(R.drawable.image12);
        images.add(R.drawable.image6);
        images.add(R.drawable.image7);
        banner.setBannerAnimation(Transformer.DepthPage);
        banner.setDelayTime(2500);
        banner.setImages(images);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                imageView.setImageResource((Integer) path);
            }
        });
        banner.start();
    }

    //GridView one?????????
    private void initGird() {
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 1; i < 9; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            if (i == 1) {
                map.put("ItemImage", R.mipmap.job);
                map.put("ItemText", "????????????");

            } else if (i == 2) {
                map.put("ItemImage", R.mipmap.activity);
                map.put("ItemText", "????????????");

            } else if (i == 3) {
                map.put("ItemImage", R.mipmap.lost);
                map.put("ItemText", "????????????");
            } else if (i == 4) {
                map.put("ItemImage", R.mipmap.electric);
                map.put("ItemText", "????????????");

            } else if (i == 5) {
                map.put("ItemImage", R.mipmap.errand);
                map.put("ItemText", "????????????");

            } else if (i == 6) {
                map.put("ItemImage", R.mipmap.flea);
                map.put("ItemText", "????????????");

            } else if (i == 7) {
                map.put("ItemImage", R.mipmap.performance);
                map.put("ItemText", "????????????");
            } else if (i == 8) {
                map.put("ItemImage", R.mipmap.more);
                map.put("ItemText", "??????");
            }
            lstImageItem.add(map);
        }
        SimpleAdapter saImageItems = new SimpleAdapter(getActivity(), lstImageItem, R.layout.grid_item, new String[]{"ItemImage", "ItemText"}, new int[]{R.id.itemImage, R.id.itemText});
        gridView.setAdapter(saImageItems);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                int index = arg2 + 1;// id??????0????????????????????????+1
                if (index == 7) {
                    ResultInquiryAty.start(getContext(), "http://cet.neea.edu.cn/cet/");
                }
            }
        });
    }

    //????????????
    private void initTextBanner() {
        //????????????
        List<String> list = new ArrayList<>();
        list.add("????????????????????????????????????");
        list.add("?????????????????????????????????");
        list.add("????????????????????????");
        //??????setDatas(List<String>)?????????,TextBannerView??????????????????
//?????????????????????????????????List<String>??????
        tvBanner.setDatas(list);
//??????TextBannerView????????????????????????????????????data??????, ???position??????
        tvBanner.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                Log.i("????????????", String.valueOf(position) + ">>" + data);
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_release:
                if (publishDialog == null) {
                    publishDialog = new PublishDialog(getActivity());
                    publishDialog.setFabuClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getActivity(), "??????", Toast.LENGTH_SHORT).show();
                        }
                    });
                    publishDialog.setHuishouClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getActivity(), "??????", Toast.LENGTH_SHORT).show();

                        }
                    });
                    publishDialog.setPingguClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getActivity(), "??????", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                publishDialog.show();
                break;
            case R.id.scan:
                if (Build.VERSION.SDK_INT >= 23) { //???????????????android6.0???????????????????????????????????????????????????
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        // ??????????????????????????????
                        Fragment_home.this.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                    } else {
                        Intent intent_scan = new Intent(getActivity(), ScanActivity.class);
                        startActivityForResult(intent_scan, BAIDU_READ_PHONE_STATE);
                    }
                } else {
                    Intent intent_scan = new Intent(getActivity(), ScanActivity.class);
                    startActivityForResult(intent_scan, BAIDU_READ_PHONE_STATE);
                }

                break;
            case R.id.search_ll_search:
                Intent intent_search = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent_search);
                break;
            default:
                break;
        }

    }

    /**
     * Android6.0???????????????????????????
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
//             requestCode????????????????????????????????????checkSelfPermission?????????
            case 1:
                //?????????????????????permissions?????????null.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //?????????
                    // ?????????????????????????????????
                } else {
                }
                break;
            case 2:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //?????????
                    // ?????????????????????????????????
                } else {
                    final AlertDialog.Builder normalDialog =
                            new AlertDialog.Builder(getActivity());
                    normalDialog.setMessage("?????????-??????-?????????-?????????????????????????????????????????????????????????");
                    normalDialog.setPositiveButton("?????????",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                    dialog.dismiss();
                                }
                            });
                    normalDialog.setNegativeButton("??????",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    normalDialog.show();

                }
                break;

        }
    }

}