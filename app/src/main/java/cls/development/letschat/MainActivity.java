package cls.development.letschat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;


import cls.development.letschat.CustomViews.HeaderView;
import cls.development.letschat.Fragments.LoginFragment;

public class MainActivity extends AppCompatActivity implements FragmentSwitcher {
    public HeaderView headerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transitionToFragment(new LoginFragment());
    }

    @Override
    public void changeToFragment(Fragment fragment) {
      transitionToFragment(fragment);

    }

    private void transitionToFragment(Fragment fragment) {
        headerView.setVisibility(View.VISIBLE);
        if(fragment instanceof LoginFragment)
            headerView.setVisibility(View.GONE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }
}