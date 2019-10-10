package com.mxdl.customview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.mxdl.customview.R;
import com.mxdl.customview.entity.Group;
import com.mxdl.customview.entity.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description: <ExpandableListViewAdapter><br>
 * Author:      mxdl<br>
 * Date:        2019/10/7<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    private List<Group> mGroups = new ArrayList<>();
    private List<List<Student>> mStudents = new ArrayList<>();
    private Context mContext;

    public ExpandableListViewAdapter(Context context) {
        mContext = context;
        initData();
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mStudents.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mStudents.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
        if(convertView == null){
            groupHolder =  new GroupHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sticky_group, null);
            TextView txtTitle = convertView.findViewById(R.id.group);
            groupHolder.txtTitle = txtTitle;
            convertView.setTag(groupHolder);
        }else{
            groupHolder = (GroupHolder) convertView.getTag();
        }
        groupHolder.txtTitle.setText(mGroups.get(groupPosition).getTitle());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        StudentHolder studentHolder;
        if(convertView == null){
            studentHolder = new StudentHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sticky_child, null);
            studentHolder.name = convertView.findViewById(R.id.name);
            studentHolder.age = convertView.findViewById(R.id.age);
            studentHolder.address = convertView.findViewById(R.id.address);
            convertView.setTag(studentHolder);
        }else{
            studentHolder = (StudentHolder) convertView.getTag();
        }
        Student student = mStudents.get(groupPosition).get(childPosition);
        studentHolder.name.setText(student.getName());
        studentHolder.age.setText(String.valueOf(student.getAge()));
        studentHolder.address.setText(student.getAddress());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    class GroupHolder{
        TextView txtTitle;
    }
    class StudentHolder{
        TextView name;
        TextView age;
        TextView address;
    }
    private void initData() {
        for (int i = 0; i < 3; i++) {
            mGroups.add(new Group("item_sticky_group-" + i));
        }
        for (int i = 0; i < 3; i++) {
            List<Student> students =  new ArrayList<>();
            if (i == 0) {
                for (int j = 0; j < 20; j++) {
                    students.add(new Student("a"+j,new Random().nextInt(20),"add"+j));
                }
            } else if (i == 1) {
                for (int j = 0; j < 15; j++) {
                    students.add(new Student("b"+j,new Random().nextInt(20),"add"+j));
                }
            } else {
                for (int j = 0; j < 30; j++) {
                    students.add(new Student("c"+j,new Random().nextInt(20),"add"+j));
                }
            }
            mStudents.add(students);
        }
    }
}
