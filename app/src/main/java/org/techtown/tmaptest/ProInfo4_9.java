package org.techtown.tmaptest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class ProInfo4_9 extends Fragment implements onBackPressedListener {

    private View view;
    ListView proInfo_content;
    TextView proTitle;

    public ProInfo4_9(){

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_pro_info,container,false);

        // DB 열기.
        DBHelper helper;
        SQLiteDatabase db;
        helper = new DBHelper(getContext(), "capdb.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        proTitle = view.findViewById(R.id.major);
        proTitle.setText("무역학전공");

        proInfo_content=view.findViewById(R.id.proInfo_content);
        ProInfoAdapter adapter= new ProInfoAdapter();


        String sql = "select name, phone, email from pro where major='무역학전공';";
        Cursor c = db.rawQuery(sql, null);
        if(c != null) {
            while(c.moveToNext()) {
                String name = c.getString(c.getColumnIndex("name"));
                String phone = c.getString(c.getColumnIndex("phone"));
                String email = c.getString(c.getColumnIndex("email"));

                adapter.addItem(new ProInfoItem(name, phone, email));
            }
        }
        /*adapter.addItem(new ProInfoItem("김경원", "031-249-9478", "kwkim@kyonggi.ac.kr"));
        adapter.addItem(new ProInfoItem("배희성", "031-249-9746", "hsbae@kgu.ac.kr"));
        adapter.addItem(new ProInfoItem("안건형", "031-249-9349", "khahn20@kgu.ac.kr"));
        adapter.addItem(new ProInfoItem("배명렬", "031-249-1465", "myong@kyonggi.ac.kr"));
        adapter.addItem(new ProInfoItem("성봉석", "031-249-1528", "bsssung@kgu.ac.kr"));
        adapter.addItem(new ProInfoItem("양일석", "031-249-9437", "isyang@kgu.ac.kr"));
        adapter.addItem(new ProInfoItem("김소형", "031-249-9747", "shkim2@kgu.ac.kr"));
        adapter.addItem(new ProInfoItem("윤동훈", "031-249-9356", "nature@kgu.ac.kr"));
        adapter.addItem(new ProInfoItem("김동균", "031-249-1336", "kidg1986@kgu.ac.kr"));*/

        proInfo_content.setAdapter(adapter);

        return view;
    }
    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        ProInfo4 pro = new ProInfo4();
        transaction.replace(R.id.tmap, pro);
        transaction.commit();
    }

    //프래그먼트 종료
    private void goToMain(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(ProInfo4_9.this).commit();
        fragmentManager.popBackStack();
    }

    //리스트뷰 어댑터 구현.
    class ProInfoAdapter extends BaseAdapter {
        ArrayList<ProInfoItem> items = new ArrayList<ProInfoItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(ProInfoItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ProInfoItemView proInfoItemView = null;
            if (convertView == null) {
                proInfoItemView = new ProInfoItemView(getActivity().getApplicationContext() );
            } else {
                proInfoItemView = (ProInfoItemView) convertView;
            }
            ProInfoItem item = items.get(position);
            proInfoItemView.setProName(item.getProName());
            proInfoItemView.setProCall(item.getProCall());
            proInfoItemView.setProMail(item.getProMail());
            return proInfoItemView;
        }
    }

}
