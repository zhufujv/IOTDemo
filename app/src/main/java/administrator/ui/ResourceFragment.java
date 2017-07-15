package administrator.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.zxing.activity.CaptureActivity;
import com.qrcodescan.R;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import administrator.asistant.CommonUtil;

/**
“资源”页面
 */
public class ResourceFragment extends Fragment {

    private static final String ARG_PARAM1 = "title";
    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;
    private LayoutInflater inflater;
    private String[] titles = {"预览","设备"};
    private List<String> titleList = new ArrayList<>();
    private View previewPage,devicePage;
    private List<View> viewList = new ArrayList<>();
    private ImageView gotoScan;

    //打开扫描界面请求码
    public static int REQUEST_CODE = 0x01;
    //扫描成功返回码
    public static int RESULT_OK = 0xA1;

    //以下是对预览页面的初始化定义
    private TextView previewTitle;
    //以下是对设备页面的初始化定义
    private TextView deviceTitle;

    private String title;

    private OnFragmentInteractionListener mListener;

    public ResourceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment MineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResourceFragment newInstance(String param1) {
        ResourceFragment fragment = new ResourceFragment();
        fragment.title = param1;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
        }
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_resource, null);

        tabLayout = (SlidingTabLayout)v.findViewById(R.id.tabs);
        viewPager = (ViewPager)v.findViewById(R.id.vp);
        previewPage = inflater.inflate(R.layout.page_preview,null);
        devicePage = inflater.inflate(R.layout.page_device,null);

        //向容器中填装view
        viewList.add(previewPage);
        viewList.add(devicePage);

        //初始化标题
        titleList = Arrays.asList(titles);

        //设置适配器
        MyPagerAdapter adapter = new MyPagerAdapter(viewList);
        viewPager.setAdapter(adapter);

        //将tabLayout与viewPager关联起来
        tabLayout.setViewPager(viewPager);

        //对各个视图的内部进行初始化
        initPreview();
        initDevice();

        //对其它view执行初始化
        gotoScan = (ImageView) v.findViewById(R.id.gotoScan);
        gotoScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CommonUtil.isCameraCanUse()){
//                    Intent intent = new Intent(getActivity(), ScannerActivity.class);
                    Intent intent = new Intent(getActivity(), CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }else{
                    Toast.makeText(getContext(),"请打开此应用的摄像头权限！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    private void initPreview(){
        previewTitle = (TextView) previewPage.findViewById(R.id.name);
        previewTitle.setText(titles[0]);
    }

    private void initDevice(){
        deviceTitle = (TextView) devicePage.findViewById(R.id.name);
        deviceTitle.setText(titles[1]);
    }

    public void changeText(String text) {
        previewTitle.setText(text);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    //ViewPager适配器
    class MyPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方推荐写法
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return titleList.get(position);//页卡标题
        }

    }

}
