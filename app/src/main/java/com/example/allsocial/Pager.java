package com.example.allsocial;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class Pager extends FragmentStatePagerAdapter {

    int numOfTabs;

    public Pager(FragmentManager manager, int numOfTabs){
        super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numOfTabs=numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0 :
                FacebookFragment facebookFragment=new FacebookFragment();
                return facebookFragment;
            case 1 :
                InstagramFragment instagramFragment=new InstagramFragment();
                return instagramFragment;
            case 2 :
                LinkedInFragment linkedInFragment=new LinkedInFragment();
                return linkedInFragment;
            case 3 :
                SnapchatFragment snapchatFragment=new SnapchatFragment();
                return snapchatFragment;
            case 4 :
                GithubFragment githubFragment=new GithubFragment();
                return githubFragment;
            case 5 :
                TwitterFragment twitterFragment=new TwitterFragment();
                return twitterFragment;
            case 6 :
                YouTubeFragment youTubeFragment=new YouTubeFragment();
                return youTubeFragment;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
