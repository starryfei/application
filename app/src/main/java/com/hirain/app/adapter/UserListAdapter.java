package com.hirain.app.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.hirain.app.R;
import com.hirain.app.activity.ModeActivity;
import com.hirain.app.entity.User;
import com.hirain.app.task.NucMessageThread;
import com.hirain.app.util.ImageBase64Utils;
import com.xuexiang.xui.widget.dialog.materialdialog.GravityEnum;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.edittext.ValidatorEditText;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.hirain.app.common.Constants.AGE_INPUTS;
import static com.hirain.app.common.Constants.APP_LOG;
import static com.hirain.app.common.Constants.HEiGHT_INPUTS;
import static com.hirain.app.common.Constants.WEIGHT_INPUTS;
import static com.hirain.app.common.Constants.ageIndex;
import static com.hirain.app.common.Constants.heightIndex;
import static com.hirain.app.common.Constants.weightIndex;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.SwipeViewHolder> implements Filterable {

    private Context context;
    private List<User> users;
    private Filter filter;
    private List<User> orig;


    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();


    public UserListAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Filter getFilter() {
        if (null == filter) {
            filter = new ArrayFilter();
        }
        return filter;

    }

    @NonNull
    @Override
    public SwipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_item, viewGroup, false);

        SwipeViewHolder swipeViewHolder = new SwipeViewHolder(view);
//        view.setOnClickListener(onClickListener);

        return swipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SwipeViewHolder swipeViewHolder, int i) {
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(swipeViewHolder.swipeLayout, String.valueOf(users.get(i).getName()));
        viewBinderHelper.closeLayout(String.valueOf(users.get(i).getName()));
        swipeViewHolder.bindData(users.get(i));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    class SwipeViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private SwipeRevealLayout swipeLayout;
        private TextView lastTime;
        private TextView deleteView;
        private LinearLayout linearLayout;
        private ImageView imageView;

        SwipeViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            lastTime = itemView.findViewById(R.id.last_time);
            deleteView = itemView.findViewById(R.id.delete);
            swipeLayout = itemView.findViewById(R.id.swipe_layout);
            linearLayout = itemView.findViewById(R.id.user_layout);
            imageView = itemView.findViewById(R.id.image_view);


            linearLayout.setOnClickListener(v -> {
                modify();
            });

            deleteView.setOnClickListener(v -> {
                delete();
            });


        }

        /**
         * 测试修改用户信息
         */
        private void modify() {
            int adapterPosition = getAdapterPosition();
            User item = users.get(adapterPosition);
//                Toast.makeText(context, item.getName(), Toast.LENGTH_LONG).show();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View inflate = layoutInflater.inflate(R.layout.user_info_layout, null);
            LinearLayout ly = (LinearLayout) inflate.findViewById(R.id.user_layout);
            ValidatorEditText text = ly.findViewById(R.id.user_name);
            RadiusImageView imageView = ly.findViewById(R.id.imageView);
            MaterialSpinner sexSpinner = ly.findViewById(R.id.sexSpinner);
            MaterialSpinner heightSpinner = ly.findViewById(R.id.heightSpinner);
            MaterialSpinner ageSpinner = ly.findViewById(R.id.ageSpinner);
            MaterialSpinner weightSpinner = ly.findViewById(R.id.weightSpinner);
            text.setText(item.getName());

            heightSpinner.setSelectedIndex(heightIndex(item.getHeight()));
            weightSpinner.setSelectedIndex(weightIndex(item.getWeight()));
            ageSpinner.setSelectedIndex(ageIndex(item.getAge()));
            sexSpinner.setSelectedIndex(Integer.valueOf(item.getSex()));
            imageView.setImageBitmap(ImageBase64Utils.stringToBitmap(item.getImage()));

            new MaterialDialog.Builder(context)
                    .customView(inflate, true)
                    .title("用户信息")
                    .positiveText(R.string.login).titleGravity(GravityEnum.CENTER)
                    .backgroundColorRes(R.color.dialog_color)
                    .titleColorRes(R.color.white).positiveColorRes(R.color.dialog_ok_color)
                    .negativeText(R.string.lab_cancel).onPositive((dialog, which) -> {

                SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                editor.putString("account", item.getName());
                editor.commit();//提交修改
                Intent intent = new Intent(context, ModeActivity.class);
                context.startActivity(intent);


            }).neutralText(R.string.modify).onNeutral((dialog, which) -> {
                String age = AGE_INPUTS.get(ageSpinner.getSelectedItem());
                String height = HEiGHT_INPUTS.get(heightSpinner.getSelectedItem());
                String weight = WEIGHT_INPUTS.get(weightSpinner.getSelectedItem());
                String sex = sexSpinner.getSelectedItem();
                String name = item.getName();
                if (StringUtils.equals(sex, "男")) {
                    sex = "0";
                } else if (StringUtils.equals(sex, "女")) {
                    sex = "1";
                } else {
                    sex = "2";
                }
                String command = "{\n" +
                        "    \"command\": \"modify\",\n" +
                        "    \"age\": \"" + age + "\",\n" +
                        "    \"weight\": \"" + weight + "\",\n" +
                        "    \"name\": \"" + item.getName() + "\",\n" +
                        "    \"sex\": \"" + sex + "\",\n" +
                        "    \"hight\": \"" + height + "\"\n" +
                        "}";
                Log.d(APP_LOG, command);
                NucMessageThread nucMessageThread = new NucMessageThread();
                nucMessageThread.sendMessage(command);
                item.setAge(age);
                item.setHeight(height);
                item.setWeight(weight);
                item.setSex(sex);
                item.setName(name);
                notifyDataSetChanged();

            }).show();

        }

        /**
         * 测试删除用户信息
         */
        private void delete() {
            int adapterPosition = getAdapterPosition();
            User item = users.get(adapterPosition);
            new MaterialDialog.Builder(context)
                    .content("是否删除此用户信息").titleGravity(GravityEnum.CENTER)
                    .backgroundColorRes(R.color.dialog_color)
                    .titleColorRes(R.color.white).positiveColorRes(R.color.dialog_ok_color)
                    .positiveText("确定").onPositive((dialog, which) -> {

                String command = "{\n" +
                        "    \"command\": \"delete\",\n" +
                        "    \"name\":\"" + item.getName() + "\"" +
                        "}";
                Log.d(APP_LOG, command);

                NucMessageThread nucMessageThread = new NucMessageThread();
                nucMessageThread.sendMessage(command);
                remove(getAdapterPosition());
//                nucMessageThread.recvMessage(message -> {
//                    JSONObject jsonObject = JSONObject.parseObject(message);
//                    String status = jsonObject.getString("status");
//                    if (StringUtils.equalsIgnoreCase(status, "success")) {
//                        remove(getAdapterPosition());
//                    } else {
//                        Log.d(APP_LOG, "delete failure");
//                    }
//                },"modify");
//                new NucMessageTask(message -> {
//                    JSONObject jsonObject = JSONObject.parseObject(message);
//                    String status = jsonObject.getString("status");
//                    if (StringUtils.equalsIgnoreCase(status, "success")) {
//                        remove(getAdapterPosition());
//                    } else {
//                        Log.d(APP_LOG, "delete failure");
//                    }
//
//                }).execute(command, "delete");


            }).negativeText("取消")
                    .show();
        }

        void bindData(User user) {
            textView.setText(user.getName());
            lastTime.setText(user.getLastTime());
            String image = user.getImage();
            Bitmap bitmap = ImageBase64Utils.decodeStringToBitmap(image);
            imageView.setImageBitmap(bitmap);
        }

    }


    private void remove(int position) {
        User user = users.get(position);
        users.remove(position);
        if (orig != null) {
            orig.remove(user);
        }
        notifyItemRemoved(position);
    }

    /**
     * <p>An array filter constrains the content of the array adapter with
     * a prefix. Each item that does not start with the supplied prefix
     * is removed from the list.</p>
     */
    private class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            final FilterResults results = new FilterResults();

            if (orig == null) {
                orig = new ArrayList<>(users);
            }

            if (prefix == null || prefix.length() == 0) {
                final ArrayList<User> list;
                list = new ArrayList<>(orig);

                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                final ArrayList<User> values = new ArrayList<>(orig);


                final int count = values.size();
                final ArrayList<User> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    final User value = values.get(i);
                    final String valueText = value.getName().toLowerCase();

                    // First match against the whole, non-splitted value
                    if (valueText.startsWith(prefixString)) {
                        newValues.add(value);
                    } else {
                        final String[] words = valueText.split(" ");
                        for (String word : words) {
                            if (word.startsWith(prefixString)) {
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //noinspection unchecked
            users = (List<User>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            }
        }
    }

}

