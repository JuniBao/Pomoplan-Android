package com.rooftrellen.pomoplan.activity.main;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rooftrellen.pomoplan.R;
import com.rooftrellen.pomoplan.activity.other.TagActivity;
import com.rooftrellen.pomoplan.activity.util.ExtraName;
import com.rooftrellen.pomoplan.db.PomoDbHelper;
import com.rooftrellen.pomoplan.model.PomoTag;
import com.rooftrellen.pomoplan.model.PomoUser;
import com.rooftrellen.pomoplan.model.Pomodoro;

/**
 * TagFragment is a Fragment for showing tag list.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class TagFragment extends Fragment {

    /**
     * The parent Activity.
     *
     * @since 1.0.0
     */
    private MainActivity mainActivity;

    /**
     * The database connection.
     *
     * @since 1.0.0
     */
    private PomoDbHelper dbHelper;

    /**
     * The context.
     *
     * @since 1.0.0
     */
    private Context context;

    /**
     * The tag list view.
     *
     * @since 1.0.0
     */
    private ListView listView;

    /**
     * The tag list.
     *
     * @since 1.0.0
     */
    private ArrayList<PomoTag> tags;

    /**
     * Required empty constructor.
     *
     * @since 1.0.0
     */
    public TagFragment() {}

    /**
     * Creates a new instance of TagFragment.
     *
     * @return the new instance.
     * @since 1.0.0
     */
    public static TagFragment newInstance() {
        return new TagFragment();
    }

    /**
     * Stores the parent activity and database connection and calendar.
     *
     * @param savedInstanceState the saved instance state.
     * @since 1.0.0
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        dbHelper = mainActivity.getDbHelper();
    }

    /**
     * Initializes view components.
     *
     * @param inflater the inflater.
     * @param container the container.
     * @param savedInstanceState the saved instance state.
     * @return the root view.
     * @since 1.0.0
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tag, container, false);
        context = root.getContext();
        // Setup tag list
        listView = (ListView) root.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Shows tag detail when click.
             *
             * @param parent the parent view.
             * @param view the view.
             * @param position the row position.
             * @param id the row ID.
             * @since 1.0.0
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), TagActivity.class);
                intent.putExtra(ExtraName.POMO_TAG, tags.get(position));
                intent.putExtra(ExtraName.TAG_INDEX, position);
                startActivityForResult(intent, 1);
            }
        });
        return root;
    }

    /**
     * Updates tag information.
     *
     * @param requestCode the request code.
     * @param resultCode the result code.
     * @param data the intent data.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Get data
        int tagIndex = data.getIntExtra(ExtraName.TAG_INDEX, -1);
        PomoTag tag = (PomoTag) data.getSerializableExtra(ExtraName.POMO_TAG);
        tags.set(tagIndex, tag);
        dbHelper.updateTag(tag);
        listView.invalidateViews();
    }

    /**
     * Updates user view after login.
     *
     * @param user the user.
     * @since 1.0.0
     */
    public void loginUpdate(PomoUser user) {
        // Get user's related tag
        String userId = user.getId();
        tags = dbHelper.selectTagsByUserId(userId);
        // Setup list view
        TagArrayAdapter adapter = new TagArrayAdapter(context, R.layout.row_tag, tags);
        listView.setAdapter(adapter);
    }

    /**
     * Updates list view when add new tag.
     *
     * @param tag the new tag.
     * @since 1.0.0
     */
    public void addTag(PomoTag tag) {
        // Get the position
        int i;
        for (i = 0; i < tags.size(); i++) {
            if (tags.get(i).getName().toLowerCase().compareTo(tag.getName().toLowerCase()) > 0) {
                break;
            }
        }
        // Update
        tags.add(i, tag);
        listView.invalidateViews();
    }

    /**
     * Updates when add new Pomodoro.
     *
     * @param pomo the new Pomodoro.
     * @since 1.0.0
     */
    public void addPomo(Pomodoro pomo){
        for (PomoTag tag :tags) {
            if (tag.getId().equals(pomo.getTagId())) {
                tag.getCompleted().add(pomo);
            }
        }
        listView.invalidateViews();
    }

    /**
     * TagArrayAdapter is an ArrayAdapter for listing all tags.
     *
     * @since 1.0.0
     */
    private class TagArrayAdapter extends ArrayAdapter<PomoTag> {

        /**
         * Initializes with context, and list of tags.
         *
         * @param context the context.
         * @param resource the layout resource.
         * @param tags the tag list.
         * @since 1.0.0
         */
        public TagArrayAdapter(Context context, int resource, ArrayList<PomoTag> tags) {
            super(context, resource, tags);
        }

        /**
         * Generates the view of each row.
         *
         * @param position the row position.
         * @param convertView the old view to reuse.
         * @param parent the parent view group.
         * @return the view of row.
         * @since 1.0.0
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Generate view
            if (convertView == null) {
                convertView = mainActivity.getLayoutInflater()
                        .inflate(R.layout.row_tag, parent, false);
            }
            // Load one student
            PomoTag tag = getItem(position);
            // Modify view
            ((TextView) convertView.findViewById(R.id.tagName)).setText(tag.getName());
            ((TextView) convertView.findViewById(R.id.tagPlan)).setText(tag.getCompleted().size()
                    + "/" + Integer.toString(tag.getPlan()));
            return convertView;
        }

    }

}
