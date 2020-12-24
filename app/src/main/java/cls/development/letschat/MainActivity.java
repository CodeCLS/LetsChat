package cls.development.letschat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;


import cls.development.letschat.CustomViews.HeaderView;
import cls.development.letschat.Fragments.LoginFragment;
import cls.development.letschat.FrontendManagement.ViewModel;
import cls.development.letschat.FrontendManagement.ViewModelFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main";
    private ViewModel viewModel;
    public HeaderView headerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewModelFactory viewModelFactory = new ViewModelFactory();
        viewModel = new ViewModelProvider(this,viewModelFactory).get(ViewModel.class);

        initViews();
        transitionToFragment(new LoginFragment());
    }

    private void initViews() {
        headerView = findViewById(R.id.header_view_main);
    }


    private void transitionToFragment(Fragment fragment) {
        if(fragment instanceof LoginFragment)
            headerView.setVisibility(View.GONE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment , "LoginFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

    public void setHeaderVisibility(int visibility){
        headerView.setVisibility(visibility);
    }



}