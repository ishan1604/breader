package in.ishankhanna.breader.ui.android.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import in.ishankhanna.breader.R;
import in.ishankhanna.breader.data.models.Item;
import in.ishankhanna.breader.presenters.main.HomePresenter;
import in.ishankhanna.breader.ui.android.fragments.FeedListFragment;
import in.ishankhanna.breader.ui.android.fragments.StoryDetailFragment;
import in.ishankhanna.breader.ui.views.main.HomeMvpView;

public class HomeActivity extends AppCompatActivity implements HomeMvpView {

    public static final String TAG = HomeActivity.class.getSimpleName();

    public static final String FEED_LIST_FRAG_TRANSACTION = "FEED_LIST_FRAG_TRANSACTION";
    public static final String STORY_DETAIL_FRAG_TRANSACTION = "STORY_DETAIL_FRAG_TRANSACTION";

    private HomePresenter homePresenter;

    @Override
    protected void onStart() {
        super.onStart();
        homePresenter = new HomePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        homePresenter.attachView(this);
        homePresenter.presentFeedList();
    }

    @Override
    protected void onPause() {
        homePresenter.detachView();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        homePresenter = null;
        super.onDestroy();
    }

    @Override
    public void showFeedListFragment() {

        FeedListFragment feedListFragment = FeedListFragment.newInstance();
        replaceFragment(feedListFragment, FEED_LIST_FRAG_TRANSACTION);

    }

    @Override
    public void showStoryDetailFragment(Item item) {

        StoryDetailFragment storyDetailFragment = StoryDetailFragment.newInstance(item);
        replaceFragment(storyDetailFragment, STORY_DETAIL_FRAG_TRANSACTION);

    }

    private void replaceFragment(Fragment fragment, String transactionName) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(transactionName);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        } else {
            finish();
            moveTaskToBack(true);
        }

    }
}
